using System.Collections.Generic;
using System.Threading.Tasks;
using Data.Data;
using Data.Persistence;
using Data.Persistence.Impl;
using Data.Properties.Persistence.Impl;

namespace Data.Properties.Persistence
{
	public class PersistenceRouter : IDevice, IMeasurement, ISettings, IUser
	{
		private IDevice _device;
		private IMeasurement _measurement;
		private ISettings _settings;
		private IUser _users;

		public PersistenceRouter()
		{
			Database context = new Database();
			_device = new DeviceImpl(context);
			_measurement = new MeasurementImpl(context);
			_settings = new SettingsImpl(context);
			_users = new UserImpl(context);
		}

		public async Task AddDevice(Device device)
		{
			await _device.AddDevice(device);
		}

		public async Task<Device> GetDevice(long id)
		{
			return await _device.GetDevice(id);
		}

		public async Task<List<Device>> GetDevices()
		{
			return await _device.GetDevices();
		}

		public async Task UpdateDevice(Device device)
		{
			await _device.UpdateDevice(device);
		}

		public async Task RemoveDevice(long id)
		{
			await _device.RemoveDevice(id);
		}

		public async Task addMeasurementSet(MeasurementSet measurementSet, long deviceID)
		{
			await _measurement.addMeasurementSet(measurementSet, deviceID);
		}

		public async Task<MeasurementSet> getLatestMeasurmentSet(long deviceID)
		{
			return await _measurement.getLatestMeasurmentSet(deviceID);
		}

		public async Task<IEnumerable<MeasurementSet>> getAllMeasurementSets(long deviceID)
		{
			return await _measurement.getAllMeasurementSets(deviceID);
		}

		public async Task RemoveMeasurement(long id)
		{
			await _measurement.RemoveMeasurement(id);
		}

		public async Task<Thresholds> GetSettings(long deviceID)

		{
			return await _settings.GetSettings(deviceID);
		}

		public async Task SetSettings(Thresholds thresholds, long deviceID)
		{
			await _settings.SetSettings(thresholds, deviceID);
		}

		public async Task<List<Device>> GetDevices(long userId)
		{
			return await _users.GetDevices(userId);
		}

		public async Task RegisterUser(User user)
		{
			await _users.RegisterUser(user);
		}

		public async Task<User> LoginUser(User user)
		{
			return await _users.LoginUser(user);
		}

		public async Task AddDevice(long userId, long deviceId)
		{
			await _users.AddDevice(userId, deviceId);
		}

		public async Task ChangeDeviceName(long id, string newDeviceName)
		{
			await _device.ChangeDeviceName(id, newDeviceName);
		}

		public async Task RemoveDevice(long id, long deviceId)
		{
			await _users.RemoveDevice(id, deviceId);
		}
	}
}