using System.Collections.Generic;
using System.Threading.Tasks;
using Data.Data;
using Microsoft.EntityFrameworkCore;

namespace Data.Properties.Persistence.Impl
{
	public class SettingsImpl : ISettings
	{
		private Database _databaseContext;

		public SettingsImpl(Database context)
		{
			_databaseContext = context;
		}

		public async Task<Thresholds> GetSettings(long deviceID)
		{
			Device tmpDevice = await _databaseContext.Devices
				.Include(p => p.DeviceThresholds)
				.FirstOrDefaultAsync(p => p.DeviceID == deviceID);
			return tmpDevice.DeviceThresholds;
		}

		public async Task SetSettings(Thresholds thresholds, long deviceID)
		{
			Device tmpDevice = await _databaseContext.Devices
				.Include(p => p.DeviceThresholds)
				.FirstOrDefaultAsync(p => p.DeviceID == deviceID);

			//THIS FIXES bug where updating settings creates a new settings row in db
			tmpDevice.DeviceThresholds.DeviceConfiguration = thresholds.DeviceConfiguration;

			tmpDevice.DeviceThresholds.MinTemperature = thresholds.MinTemperature;
			tmpDevice.DeviceThresholds.MaxTemperature = thresholds.MaxTemperature;

			tmpDevice.DeviceThresholds.MinHumidity = thresholds.MinHumidity;
			tmpDevice.DeviceThresholds.MaxHumidity = thresholds.MaxHumidity;

			tmpDevice.DeviceThresholds.MinCo2 = thresholds.MinCo2;
			tmpDevice.DeviceThresholds.MaxCo2 = thresholds.MaxCo2;

			await _databaseContext.SaveChangesAsync();
		}
	}
}