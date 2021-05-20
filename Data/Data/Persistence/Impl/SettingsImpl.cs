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
			tmpDevice.DeviceThresholds = thresholds;
			await _databaseContext.SaveChangesAsync();
		}
	}
}