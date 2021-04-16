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
	public class TemperatureController : ControllerBase
	{
		// gets temperature measurement by id
		[HttpGet("api/temperatures/{id}")]
		public Measurement Get(int id)
		{
			return new TemperatureMeasurement
			{
				MeasurementID = 0,
				Timestamp = DateTime.Now,
				Value = 0
			};
		}

		// gets all temperature measurement by device id
		[HttpGet("api/devices/{id}/temperatures")]
		public IEnumerable<Measurement> GetByDevice(int id)
		{
			return new Measurement[] {
				new TemperatureMeasurement
				{
					MeasurementID = 0,
					Timestamp = DateTime.Now,
					Value = 0
				},
				new TemperatureMeasurement
				{
					MeasurementID = 1,
					Timestamp = DateTime.Now,
					Value = 1
				}
			};
		}

		// gets latest measurement by device id
		[HttpGet("api/devices/{id}/last_temperature")]
		public Measurement GetLastByDevice(int id)
		{
			return new TemperatureMeasurement
			{
				MeasurementID = 0,
				Timestamp = DateTime.Now,
				Value = 0
			};
		}

		// Adds new temperature measurement to device
		[HttpPost("api/devices/{id}/temperatures")]
		public void Post(int id, [FromBody] Measurement value)
		{
		}

		// deletes temperature measurement with ID
		[HttpDelete("api/temperatures/{id}")]
		public void Delete(int id)
		{
		}
	}
}
