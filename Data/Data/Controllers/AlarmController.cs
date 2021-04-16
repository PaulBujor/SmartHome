using Data.Data;
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
		// gets alarm trigger by id
		[HttpGet("api/alarms/{id}")]
		public Measurement Get(int id)
		{
			return new Measurement
			{
				MeasurementID = 0,
				Timestamp = DateTime.Now,
				Value = 0
			};
		}

		// gets all alarm trigger by device id
		[HttpGet("api/device/{id}/alarms")]
		public IEnumerable<Measurement> GetByDevice(int id)
		{
			return new Measurement[] {
				new Measurement
				{
					MeasurementID = 0,
					Timestamp = DateTime.Now,
					Value = 0
				},
				new Measurement
				{
					MeasurementID = 1,
					Timestamp = DateTime.Now,
					Value = 1
				}
			};
		}

		// gets latest measurement by device id
		[HttpGet("api/device/{id}/last_alarm")]
		public Measurement GetLastByDevice(int id)
		{
			return new Measurement
			{
				MeasurementID = 0,
				Timestamp = DateTime.Now,
				Value = 0
			};
		}

		// Adds new alarm trigger to device
		[HttpPost("api/devices/{id}/alarms")]
		public void Post(int id, [FromBody] Measurement value)
		{
		}

		// deletes alarm trigger with ID
		[HttpDelete("api/alarms/{id}")]
		public void Delete(int id)
		{
		}
	}
}
