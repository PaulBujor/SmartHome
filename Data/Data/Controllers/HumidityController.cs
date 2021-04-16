using Data.Data;
using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace Data.Controllers
{
	[Route("api/[controller]")]
	[ApiController]
	public class HumidityController : ControllerBase
	{
		// gets humidity measurement by id
		[HttpGet("api/humidity/{id}")]
		public Measurement Get(int id)
		{
			return new Measurement
			{
				MeasurementID = 0,
				Timestamp = DateTime.Now,
				Value = 0
			};
		}

		// gets all humidity measurement by device id
		[HttpGet("api/device/{id}/humidity")]
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
		[HttpGet("api/device/{id}/last_humidity")]
		public Measurement GetLastByDevice(int id)
		{
			return new Measurement
			{
				MeasurementID = 0,
				Timestamp = DateTime.Now,
				Value = 0
			};
		}

		// Adds new humidity measurement to device
		[HttpPost("api/devices/{id}/humidity")]
		public void Post(int id, [FromBody] Measurement value)
		{
		}

		// deletes humidity measurement with ID
		[HttpDelete("api/humidity/{id}")]
		public void Delete(int id)
		{
		}
	}
}
