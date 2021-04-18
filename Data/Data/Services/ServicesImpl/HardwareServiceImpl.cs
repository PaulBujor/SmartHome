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
        //SETTINGS
        public async Task<Data.Settings> SetSetting(Settings settings, long deviceId)
        {
            await persistenceRouter.AddSetting(settings, deviceId);
            return settings;
        }
        public async Task<IList<Settings>> GetAllSettings(long deviceId)
        {
            //missing deviceId in persistenceRouter???
            List<Settings> settings = await persistenceRouter.GetSettings(deviceId);
            return settings;
        }

        public async Task RemoveSetting(int id)
        {
            await persistenceRouter.RemoveSetting(id);
        }

        public async Task<Settings> GetSettingsByID(long id)
        {
            //not sure if this method is needed...
            Device device = await persistenceRouter.GetDevice(id);
            return device.DeviceSettings;
        }

        public async Task Reset(long id){
            Device device = await (persistenceRouter.GetDevice(id));
            device.DeviceSettings.Defaults();
        }
    }
}