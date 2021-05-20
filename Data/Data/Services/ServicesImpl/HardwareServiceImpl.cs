using System.Collections.Generic;
using System.Threading.Tasks;
using Data.Data;
using Data.Properties.Persistence;

namespace Data.Services.ServicesImpl
{
	public class HardwareServiceImpl : IHardwareService
	{
		PersistenceRouter persistenceRouter;
		public HardwareServiceImpl(PersistenceRouter persistenceRouter)
		{
			this.persistenceRouter = persistenceRouter;
		}

		public async Task CheckDeviceExists(long deviceId)
		{
			if ((await persistenceRouter.GetDevice(deviceId)) == null)
			{
				Device device = new Device { DeviceID = deviceId, DeviceThresholds = Thresholds.Defaults() };
				await persistenceRouter.AddDevice(device);
			}
		}

		//SETTINGS

		public async Task<Thresholds> GetSettings(long deviceID)
		{
			return await persistenceRouter.GetSettings(deviceID);
		}

		public async Task SetSettings(Thresholds thresholds, long deviceID)
		{
			await persistenceRouter.SetSettings(thresholds, deviceID);

		}

		public async Task Reset(long id)
		{
			await SetSettings(Thresholds.Defaults(), id);
		}
	}
}