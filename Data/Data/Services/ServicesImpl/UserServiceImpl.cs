using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Data.Data;
using Data.Properties.Persistence;

namespace Data.Services.ServicesImpl
{
	public class UserServiceImpl : IUserService
	{
		private readonly PersistenceRouter persistence;

		public UserServiceImpl(PersistenceRouter persistence)
		{
			this.persistence = persistence;
		}

		public async Task<List<Device>> GetDevices(long userId)
		{
			return await persistence.GetDevices(userId);
		}

		public async Task RegisterUser(User user)
		{
			await persistence.RegisterUser(user);
		}

		public async Task<User> LoginUser(User user)
		{
			return await persistence.LoginUser(user);
		}

		public async Task AddDevice(long userId, long deviceId)
		{
			await persistence.AddDevice(userId, deviceId);
		}

		public async Task RemoveDevice(long id, long deviceId)
		{
			await persistence.RemoveDevice(id, deviceId);
		}
	}
}
