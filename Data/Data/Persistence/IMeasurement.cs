using System.Collections.Generic;
using System.Threading.Tasks;
using Data.Data;

namespace Data.Properties.Persistence
{
    public interface IMeasurement
    {
	    Task addMeasurementSet(MeasurementSet measurementSet,long deviceID);
        Task<MeasurementSet> getLatestMeasurmentSet(long deviceID);
        Task<IEnumerable<MeasurementSet>> getAllMeasurementSets(long deviceID);
        Task RemoveMeasurement(long id);
    }
}