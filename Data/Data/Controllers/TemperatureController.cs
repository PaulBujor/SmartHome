using Data.Data;
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
	public class TemperatureController : ControllerBase
	{
		private readonly ITemperatureService _service;

		public TemperatureController(ITemperatureService service)
		{
			_service = service;
		}

		// gets temperature measurement by id
		[HttpGet("api/temperatures/{id}")]
		public async Task<ActionResult<Measurement>> Get(long id)
		{
			return Ok(new TemperatureMeasurement
			{
				MeasurementID = 0,
				Timestamp = DateTime.Now,
				Value = 0
			});
		}

		// gets all temperature measurement by device id
		[HttpGet("api/devices/{id}/temperatures")]
		public async Task<ActionResult<IEnumerable<Measurement>>> GetByDevice(long id)
		{
			try
			{
				//todo get by device ID
				return Ok(await _service.GetAllTemperatures(id));
			}
			catch (Exception e)
			{
				Console.WriteLine(e.StackTrace);
				return StatusCode(500, e.Message);
			}
		}

		// gets latest measurement by device id
		[HttpGet("api/devices/{id}/last_temperature")]
		public async Task<ActionResult<Measurement>> GetLastByDevice(long id)
		{
			try
			{
				//todo get by device id
				return Ok(await _service.GetLastTemperature(id));
			}
			catch (Exception e)
			{
				Console.WriteLine(e.StackTrace);
				return StatusCode(500, e.Message);
			}
		}

		// Adds new temperature measurement to device
		[HttpPost("api/devices/{id}/temperatures")]
		public async Task<ActionResult> Post(long id, [FromBody] Measurement value)
		{
			try
			{
				//add by device id
				return Ok(await _service.AddTemperature(value));
			}
			catch (Exception e)
			{
				Console.WriteLine(e.StackTrace);
				return StatusCode(500, e.Message);
			}
		}

		// deletes temperature measurement with ID
		[HttpDelete("api/temperatures/{id}")]
		public async Task<ActionResult> Delete(long id)
		{
			return StatusCode(404, "still NO.");
		}
	}
}
