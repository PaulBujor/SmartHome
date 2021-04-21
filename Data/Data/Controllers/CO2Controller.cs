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
	public class CO2Controller : ControllerBase
	{
		private readonly ICO2Service _service;

		public CO2Controller(ICO2Service service)
		{
			_service = service;
		}

		// gets co2 measurement by id
		[HttpGet("api/co2/{id}")]
		public async Task<ActionResult<CO2Measurement>> Get(long id)
		{
			//todo implement in service
			return Ok(new CO2Measurement
			{
				MeasurementID = 0,
				Timestamp = DateTime.Now,
				Value = 0
			});
		}

		// gets all co2 measurement by device id
		[HttpGet("api/devices/{id}/co2")]
		public async Task<ActionResult<IEnumerable<Measurement>>> GetByDevice(long id)
		{
			try
			{
				//todo get by device
				return Ok(await _service.GetAllCO2s(id));
			} catch(Exception e)
			{
				Console.WriteLine(e.StackTrace);
				return StatusCode(500, e.Message);
			}
		}

		// gets latest measurement by device id
		[HttpGet("api/devices/{id}/last-co2")]
		public async Task<ActionResult<Measurement>> GetLastByDevice(long id)
		{
			try
			{
				//todo get by device
				return Ok(await _service.GetLastCO2(id));
			}
			catch (Exception e)
			{
				Console.WriteLine(e.StackTrace);
				return StatusCode(500, e.Message);
			}
		}

		// Adds new co2 measurement to device
		[HttpPost("api/devices/{id}/co2")]
		public async Task<ActionResult> Post(long id, [FromBody] Measurement value)
		{
			try
			{
				//todo add to device
				return Ok(await _service.AddCO2(value, id));
			}
			catch (Exception e)
			{
				Console.WriteLine(e.StackTrace);
				return StatusCode(500, e.Message);
			}
		}

		// deletes co2 measurement with ID
		[HttpDelete("api/co2/{id}")]
		public async Task<ActionResult> Delete(long id)
		{
			//probably won't use this at all
			return StatusCode(404, "NO.");
		}
	}
}
