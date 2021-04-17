using Data.Data;
using Data.Data.ConcreteMeasurements;
using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace Data.Controllers
{
	[ApiController]
	public class AlarmController : ControllerBase
	{
		private readonly IService _service;

		public AlarmController(IService service)
		{
			_service = service;
		}

		// gets alarm trigger by id
		[HttpGet("api/alarms/{id}")]
		public async Task<Measurement> Get(int id)
		{
			return new AlarmMeasurement
			{
				MeasurementID = 0,
				Timestamp = DateTime.Now,
				Value = 0
			};
			//return 
		}

		// gets all alarm trigger by device id
		[HttpGet("api/devices/{id}/alarms")]
		public async Task<IEnumerable<Measurement>> GetByDevice(int id)
		{
			return new Measurement[] {
				new AlarmMeasurement
				{
					MeasurementID = 0,
					Timestamp = DateTime.Now,
					Value = 0
				},
				new AlarmMeasurement
				{
					MeasurementID = 1,
					Timestamp = DateTime.Now,
					Value = 1
				}
			};
		}

		// gets latest measurement by device id
		[HttpGet("api/devices/{id}/last_alarm")]
		public async Task<Measurement> GetLastByDevice(int id)
		{
			return new AlarmMeasurement
			{
				MeasurementID = 0,
				Timestamp = DateTime.Now,
				Value = 0
			};
		}

		// Adds new alarm trigger to device
		[HttpPost("api/devices/{id}/alarms")]
		public async Task Post(int id, [FromBody] Measurement value)
		{
		}

		// deletes alarm trigger with ID
		[HttpDelete("api/alarms/{id}")]
		public async Task Delete(int id)
		{
		}
	}
}
