using System.Collections.Generic;
using System.Threading.Tasks;
using Data.Data;
using Data.Properties.Persistence;

namespace Data.Services.ServicesImpl
{
    public class HardwareServiceImpl
    {
        PersistenceRouter persistenceRouter;
        public HardwareServiceImpl(PersistenceRouter persistenceRouter)
        {
            this.persistenceRouter = persistenceRouter;
        }
        //SETTINGS
        public async Task<Data.Settings> AddSetting(Settings settings)
        {
            await persistenceRouter.AddSetting(settings);
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

        public Task<Settings> GetSettingsByID(long id)
        {
            //not sure if this method is needed...
            throw new System.NotImplementedException();
        }
    }
}