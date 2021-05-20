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
		private PersistenceRouter persistence;

		public UserServiceImpl(PersistenceRouter persistence)
		{
			this.persistence = persistence;
		}

		public async Task<List<Device>> getDevices(long userId)
		{
			return await persistence.getDevices(userId);
		}

		public async Task registerUser(User user)
		{
			await persistence.registerUser(user);
		}

		public async Task<User> loginUser(User user)
		{
			return await persistence.loginUser(user);
		}

		public async Task addDevice(long userId, long deviceId)
		{
			await persistence.addDevice(userId, deviceId);
		}
	}
}
