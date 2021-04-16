using System.Collections.Generic;
using System.Threading.Tasks;
using Data.Data;

namespace Data.Properties.Persistence
{
    public interface IMeasurement
    {
        Task AddMeasurement(Measurement measurement);
        Task<Measurement> GetMeasurement(long id);
        Task<List<Measurement>> GetMeasurements();
        Task RemoveMeasurement(long id);
    }
}