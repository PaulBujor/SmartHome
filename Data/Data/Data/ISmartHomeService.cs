using System.Collections.Generic;
using System.Threading.Tasks;
using Data.Data;

namespace Data
{
    public interface ISmartHomeService
    {
        // TODO: Divide into smaller interfaces
        Task RemoveHumidity(int id);
        
        //Device
        Task<Device> AddDevice(Device device);
        Task RemoveDevice(long DeviceId);
    }
}