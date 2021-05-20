using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Data.Data;

namespace Data.Services
{
	public interface IUserService
	{
		Task<List<Device>> getDevices(long userId);
		Task registerUser(User user);
		Task<User> loginUser(User user);
		Task addDevice(long userId, long deviceId);
	}
}