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
        public async Task<Settings> GetSettings(long deviceID)
        {
            Device tmpDevice = await _databaseContext.Devices.Include(p=>p.DeviceSettings).FirstOrDefaultAsync(p => p.DeviceID == deviceID);
            return tmpDevice.DeviceSettings;
        }

        public async Task SetSettings(Settings settings, long deviceID)
        {
            Device tmpDevice = await _databaseContext.Devices.Include(p => p.DeviceSettings)
                .FirstOrDefaultAsync(p => p.DeviceID == deviceID);
            tmpDevice.DeviceSettings = settings;
            await _databaseContext.SaveChangesAsync();
        }
    }
}