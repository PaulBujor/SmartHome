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
				Device device = new Device { DeviceID = deviceId, DeviceSettings = Settings.Defaults() };
				await persistenceRouter.AddDevice(device);
			}
		}

		//SETTINGS

		public async Task<Settings> GetSettings(long deviceID)
		{
			return await persistenceRouter.GetSettings(deviceID);
		}

		public async Task SetSettings(Settings settings, long deviceID)
		{
			await persistenceRouter.SetSettings(settings, deviceID);

		}

		public async Task Reset(long id)
		{
			await SetSettings(Settings.Defaults(), id);
		}
	}
}