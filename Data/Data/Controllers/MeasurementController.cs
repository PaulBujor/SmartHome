using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Data.Data;

namespace Data.Controllers
{
	[ApiController]
	public class MeasurementController : ControllerBase
	{
		/*private IMeasurementService _service;

		public MeasurementController(IMeasurementService service)
		{
			_service = service;
		}

		// Adds new co2 measurement to device
		[HttpPost("api/devices/{id}/measurements")]
		public async Task<ActionResult> Post(long id, [FromBody] MeasurementSet value)
		{
			try
			{
				//todo add to device
				return Ok(await _service.AddMeasurement(value, id));
			}
			catch (Exception e)
			{
				Console.WriteLine(e.Message);
				Console.WriteLine(e.StackTrace);
				return StatusCode(500, e.Message);
			}
		}*/
	}
}
