using System.Collections.Generic;
using System.Threading.Tasks;
using Data.Data;
using Microsoft.EntityFrameworkCore;

namespace Data.Properties.Persistence.Impl
{
    public class DeviceImpl : IDevice
    {
        private Database _databaseContext;

        public DeviceImpl(Database context)
        {
            _databaseContext = context;
        }
        public async Task AddDevice(Device device)
        {
            await _databaseContext.Devices.AddAsync(device);
            await _databaseContext.SaveChangesAsync();
        }

        public async Task<Device> GetDevice(long id)
        {
          
            
            Device tmpDevice = await _databaseContext.Devices.FirstOrDefaultAsync(p => p.DeviceID == id);
            return tmpDevice;
        }

        public async Task<List<Device>> GetDevices()
        {
            return await _databaseContext.Devices.ToListAsync();
        }

        public async Task UpdateDevice(Device device)
        {
            _databaseContext.Update(device);
            await _databaseContext.SaveChangesAsync();
        }

        public async Task RemoveDevice(long id)
        {
            Device tmpDevice = await _databaseContext.Devices.FirstOrDefaultAsync(p => p.DeviceID == id);
            if (tmpDevice != null)
            {
                _databaseContext.Remove(tmpDevice);
                await _databaseContext.SaveChangesAsync();
            }
        }

        public async Task ChangeDeviceName(long id, string newDeviceName)
        {
	        Device tmpDevice = await _databaseContext.Devices.FirstOrDefaultAsync(p => p.DeviceID == id);
	        if (tmpDevice != null)
	        {
		        tmpDevice.DeviceName = newDeviceName;
		        await _databaseContext.SaveChangesAsync();
	        }
        }
    }
}