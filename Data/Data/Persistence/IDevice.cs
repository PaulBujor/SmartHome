using System.Collections.Generic;
using System.Threading.Tasks;
using Data.Data;

namespace Data.Properties.Persistence
{
    public interface IDevice
    {
        Task AddDevice(Device device);
        Task<Device> GetDevice(long id);
        Task<List<Device>> GetDevices();
        Task UpdateDevice(Device device);
        Task RemoveDevice(long id);
    }
}