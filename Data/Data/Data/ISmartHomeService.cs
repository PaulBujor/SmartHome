using System.Collections.Generic;
using System.Threading.Tasks;
using Data.Data;

namespace Data
{
    public interface ISmartHomeService
    {
        // TODO: Divide into smaller interfaces
        Task RemoveHumidity(int id);

        //CO2
        Task<IList<Measurement>> GetAllCO2s();
        Task<Measurement> AddCO2(Measurement co2);
        Task<Measurement> GetLastCO2();
        Task RemoveCO2(int id);

        //Motion
        Task<IList<Measurement>> GetAllMotions();   //<Measurement> --> not sure, but for now is okay.
        Task<Measurement> AddMotion(Measurement motion);   //<Measurement> --> not sure, but for now is okay.
        Task<Measurement> GetLastMotion();
        Task RemoveMotion(int id);

        //Device
        Task<Device> AddDevice(Device device);
        Task RemoveDevice(long DeviceId);

        //Settings
        Task<IList<Measurement>> GetAllSettings();   //<Measurement> --> not sure, but for now is okay.
        Task<Settings> GetSettingsByID(long id);
        Task<Settings> AddSetting(Settings settings);
        Task RemoveSetting(int id);
    }
}