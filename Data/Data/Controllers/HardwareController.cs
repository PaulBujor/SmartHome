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
	public class HardwareController : ControllerBase
	{
		private readonly IHardwareService _service;
		private readonly IUserService _user;

		public HardwareController(IHardwareService service, IUserService user)
		{
			_service = service;
			_user = user;
		}

		// gets thresholds by device id
		[HttpGet("api/devices/{id}/thresholds")]
		public async Task<ActionResult<Thresholds>> Get(long id)
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

			//return new Thresholds
			//{
			//	SettingsID = 0,
			//	DeviceConfiguration = new DeviceConfiguration { Active = true},
			//	AlarmConfiguration = new AlarmConfiguration { Active = true, MinOrDefault = 0.5},
			//	CO2Configuration = new CO2Configuration { Active = true, Max = 650, MinOrDefault = 200},
			//	HumidityConfiguration = new HumidityConfiguration { Active = true, Max = 50, MinOrDefault = 30 },
			//	TemperatureConfiguration = new TemperatureConfiguration { Active = true, Max = 23, MinOrDefault = 20 }
			//};
		}

		//patches all thresholds
		[HttpPatch("api/devices/{id}/thresholds")]
		public async Task<ActionResult> Patch(long id, [FromBody] Thresholds thresholds)
		{
			try
			{
				//todo by device ID, change Add to Set
				await _service.SetSettings(thresholds, id);
				return Ok();
			}
			catch (Exception e)
			{
				Console.WriteLine(e.Message);
				Console.WriteLine(e.StackTrace);
				return StatusCode(500, e.Message);
			}
		}

		// resets thresholds of device
		[HttpDelete("api/devices/{id}/thresholds")]
		public async Task<ActionResult> Delete(long id)
		{
			try
			{
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

		[HttpPatch("api/devices/{id}/name")]
		public async Task<ActionResult> UpdateDeviceName([FromBody] EUser userAndDeviceName, long id)
		{
			try
			{
				var myUser = await _user.LoginUser(userAndDeviceName);
				await _service.ChangeDeviceName(id, userAndDeviceName.NewDeviceName);
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
