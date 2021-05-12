using System.Collections.Generic;
using System.Threading.Tasks;
using Data.Data;

namespace Data.Services
{
    public interface IMeasurementSetService
    {
        Task AddMeasurementSet(long deviceId, MeasurementSet measurementSet);
         Task<MeasurementSet> GetAllMeasurementsSet(long deviceId);
         Task<MeasurementSet> GetLastMeasurementSet(long deviceId);
    }
}