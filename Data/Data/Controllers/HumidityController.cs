using Data.Data;
using Data.Data.ConcreteMeasurements;
using Data.Services;
using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace Data.Controllers
{
	[ApiController]
	public class HumidityController : ControllerBase
	{
		private readonly IHumidityService _service;

		public HumidityController(IHumidityService service)
		{
			_service = service;
		}

		// gets humidity measurement by id
		[HttpGet("api/humidity/{id}")]
		public async Task<ActionResult<Measurement>> Get(long id)
		{
			return Ok(new HumidityMeasurement
			{
				MeasurementID = 0,
				Timestamp = DateTime.Now,
				Value = 0
			});
		}

		// gets all humidity measurement by device id
		[HttpGet("api/devices/{id}/humidity")]
		public async Task<ActionResult<IEnumerable<Measurement>>> GetByDevice(long id)
		{
			try
			{
				//todo get by device id
				return Ok(await _service.GetAllHumidities(id));
			}
			catch (Exception e)
			{
				Console.WriteLine(e.Message);
				Console.WriteLine(e.StackTrace);
				return StatusCode(500, e.Message);
			}
		}

		// gets latest measurement by device id
		[HttpGet("api/devices/{id}/last-humidity")]
		public async Task<ActionResult<Measurement>> GetLastByDevice(long id)
		{
			try
			{
				//todo get by ID
				return Ok(await _service.GetLastHumidity(id));
			}
			catch (Exception e)
			{
				Console.WriteLine(e.Message);
				Console.WriteLine(e.StackTrace);
				return StatusCode(500, e.Message);
			}
		}

		// Adds new humidity measurement to device
		[HttpPost("api/devices/{id}/humidity")]
		public async Task<ActionResult> Post(long id, [FromBody] Measurement value)
		{
			try
			{
				return Ok(await _service.AddHumidity(value, id));
			}
			catch (Exception e)
			{
				Console.WriteLine(e.Message);
				Console.WriteLine(e.StackTrace);
				return StatusCode(500, e.Message);
			}
		}

		// deletes humidity measurement with ID
		[HttpDelete("api/humidity/{id}")]
		public async Task<ActionResult> Delete(long id)
		{
			return StatusCode(404, "NO.");
		}
	}
}
