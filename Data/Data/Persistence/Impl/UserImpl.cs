using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Data.Data;
using Data.Properties.Persistence;
using Microsoft.EntityFrameworkCore;

namespace Data.Persistence.Impl
{
	public class UserImpl : IUser
	{
		private Database dbContext;

		public UserImpl(Database dbContext)
		{
			this.dbContext = dbContext;
		}

		public async Task<List<Device>> GetDevices(long userId)
		{
			var user = await dbContext.Users.Include(u => u.Devices).FirstOrDefaultAsync(u => u.UserID == userId);
			return user.Devices;
		}

		public async Task RegisterUser(User user)
		{
			if ((await dbContext.Users.FirstOrDefaultAsync(u => u.Email.Equals(user.Email))) != null)
				throw new ArgumentException("User already exists");
			await dbContext.Users.AddAsync(user);
			await dbContext.SaveChangesAsync();
		}

		public async Task<User> LoginUser(User user)
		{
			var dbUser = await dbContext.Users
				.Include(u => u.Devices)
				.FirstOrDefaultAsync(u => u.Email.Equals(user.Email) && u.Password.Equals(user.Password));
			if (dbUser == null)
				throw new ArgumentException("Email or password are incorrect");
			return dbUser;
		}

		public async Task AddDevice(long userId, long deviceId)
		{
			var dbUser = await dbContext.Users
				.Include(u => u.Devices)
				.FirstOrDefaultAsync(u => u.UserID == userId);
			var dbDevice = await dbContext.Devices.FirstOrDefaultAsync(d => d.DeviceID == deviceId);
			if (dbDevice == null)
				throw new ArgumentException("Device does not exist, please turn on your device before using it, or check that the entered device id is correct");
			dbUser.Devices.Add(dbDevice);
			await dbContext.SaveChangesAsync();

		}

		public async Task RemoveDevice(long id, long deviceId)
		{
			var dbUser = await dbContext.Users
				.Include(u => u.Devices)
				.FirstOrDefaultAsync(u => u.UserID == id);
			var dbDevice = await dbContext.Devices.FirstOrDefaultAsync(d => d.DeviceID == deviceId);
			if (dbDevice == null)
				throw new ArgumentException("Device does not exist, please turn on your device before using it, or check that the entered device id is correct");
			dbUser.Devices.Remove(dbDevice);
			await dbContext.SaveChangesAsync();
		}
	}
}