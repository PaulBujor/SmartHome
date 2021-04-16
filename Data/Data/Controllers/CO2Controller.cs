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
	public class CO2Controller : ControllerBase
	{
		// gets co2 measurement by id
		[HttpGet("api/co2/{id}")]
		public CO2Measurement Get(int id)
		{
			return new CO2Measurement
			{
				MeasurementID = 0,
				Timestamp = DateTime.Now,
				Value = 0
			};
		}

		// gets all co2 measurement by device id
		[HttpGet("api/devices/{id}/co2")]
		public IEnumerable<Measurement> GetByDevice(int id)
		{
			return new Measurement[] {
				new CO2Measurement
				{
					MeasurementID = 0,
					Timestamp = DateTime.Now,
					Value = 0
				},
				new CO2Measurement
				{
					MeasurementID = 1,
					Timestamp = DateTime.Now,
					Value = 1
				}
			};
		}

		// gets latest measurement by device id
		[HttpGet("api/devices/{id}/last_co2")]
		public Measurement GetLastByDevice(int id)
		{
			return new CO2Measurement
			{
				MeasurementID = 0,
				Timestamp = DateTime.Now,
				Value = 0
			};
		}

		// Adds new co2 measurement to device
		[HttpPost("api/devices/{id}/co2")]
		public void Post(int id, [FromBody] Measurement value)
		{
		}

		// deletes co2 measurement with ID
		[HttpDelete("api/co2/{id}")]
		public void Delete(int id)
		{
		}
	}
}
