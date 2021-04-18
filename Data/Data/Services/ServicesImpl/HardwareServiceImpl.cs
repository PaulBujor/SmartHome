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

        public void CheckDeviceExists(long deviceId)
        {
            if (persistenceRouter.GetDevice(deviceId) == null)
            {
                Device device = new Device();
                device.DeviceSettings = Settings.Defaults();
                persistenceRouter.AddDevice(device);
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

        public async Task Reset(long id){
            await SetSettings(Settings.Defaults(), id); 
        }
    }
}