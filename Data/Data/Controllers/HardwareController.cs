using Data.Data;
using Data.Data.ConcreteConfigurations;
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
	public class HardwareController : ControllerBase
	{
		private readonly IHardwareService _service;

		public HardwareController(IHardwareService service)
		{
			_service = service;
		}

		// gets settings by device id
		[HttpGet("api/devices/{id}/settings")]
		public async Task<ActionResult<Settings>> Get(long id)
		{
			try
			{
				return Ok(await _service.GetSettings(id));
			}
			catch (Exception e)
			{
				Console.WriteLine(e.Message);
				Console.WriteLine(e.StackTrace);
				return StatusCode(500, e.Message);
			}

			//return new Settings
			//{
			//	SettingsID = 0,
			//	DeviceConfiguration = new DeviceConfiguration { Active = true},
			//	AlarmConfiguration = new AlarmConfiguration { Active = true, MinOrDefault = 0.5},
			//	CO2Configuration = new CO2Configuration { Active = true, Max = 650, MinOrDefault = 200},
			//	HumidityConfiguration = new HumidityConfiguration { Active = true, Max = 50, MinOrDefault = 30 },
			//	TemperatureConfiguration = new TemperatureConfiguration { Active = true, Max = 23, MinOrDefault = 20 }
			//};
		}

		//patches all settings
		[HttpPatch("api/devices/{id}/settings")]
		public async Task<ActionResult> Patch(long id, [FromBody] Settings settings)
		{
			try
			{
				//todo by device ID, change Add to Set
				await _service.SetSettings(settings, id);
				return Ok();
			}
			catch (Exception e)
			{
				Console.WriteLine(e.Message);
				Console.WriteLine(e.StackTrace);
				return StatusCode(500, e.Message);
			}
		}

		// resets settings of device
		[HttpDelete("api/devices/{id}/settings")]
		public async Task<ActionResult> Delete(long id)
		{
			try
			{
				//todo Remove -> Reset, remove cast
				await _service.Reset(id);
				return Ok();
			}
			catch (Exception e)
			{
				Console.WriteLine(e.Message);
				Console.WriteLine(e.StackTrace);
				return StatusCode(500, e.Message);
			}
		}
	}
}
