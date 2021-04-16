using System.Collections.Generic;
using System.Threading.Tasks;
using Data.Data;

namespace Data.Properties.Persistence.Impl
{
    public class DeviceImpl : IDevice
    {
        public Task AddDevice(Device device)
        {
            throw new System.NotImplementedException();
        }

        public Task<Device> GetDevice(long id)
        {
            throw new System.NotImplementedException();
        }

        public Task<List<Device>> GetDevices()
        {
            throw new System.NotImplementedException();
        }

        public Task UpdateDevice(Device device)
        {
            throw new System.NotImplementedException();
        }

        public Task RemoveDevice(long id)
        {
            throw new System.NotImplementedException();
        }
    }
}