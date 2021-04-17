using System.Collections.Generic;
using System.Threading.Tasks;
using Data.Data;

namespace Data
{
    public interface ISmartHomeService
    {
        // TODO: Divide into smaller interfaces
        Task RemoveHumidity(int id);

        //Motion
        Task<IList<Measurement>> GetAllMotions();   //<Measurement> --> not sure, but for now is okay.
        Task<Measurement> AddMotion(Measurement motion);   //<Measurement> --> not sure, but for now is okay.
        Task<Measurement> GetLastMotion();
        Task RemoveMotion(int id);

        //Device
        Task<Device> AddDevice(Device device);
        Task RemoveDevice(long DeviceId);
    }
}