using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Data.Data;
using Data.Services;

namespace Data.Controllers
{
	[ApiController]
	public class UserController : ControllerBase
	{
		private IUserService userService;

		public UserController(IUserService userService)
		{
			this.userService = userService;
		}

		//this is a rather naive apporach, anyone can see what devices you have if they know your id, but it's ok for our scope
		[HttpGet("api/users/{id}/devices")]
		public async Task<ActionResult<List<Device>>> getDevices(long id)
		{
			try
			{
				return Ok(await userService.getDevices(id));
			}
			catch (Exception e)
			{
				Console.WriteLine(e.Message);
				Console.WriteLine(e.StackTrace);
				return StatusCode(500, e.Message);
			}
		}

		[HttpPost("api/users/{id}/device/{deviceId}")]
		public async Task<ActionResult> addDevice(long id, long deviceId, [FromBody] User user)
		{
			try
			{
				var myUser = await userService.loginUser(user);
				if (myUser.UserID != id)
					throw new ArgumentException("User id does not match actual user");

				await userService.addDevice(id, deviceId);
				return Ok();
			}
			catch (Exception e)
			{
				Console.WriteLine(e.Message);
				Console.WriteLine(e.StackTrace);
				return StatusCode(500, e.Message);
			}
		}

		[HttpPost("api/users/register")]
		public async Task<ActionResult<User>> register([FromBody] User user)
		{
			try
			{
				await userService.registerUser(user);
				return Ok();
			}
			catch (Exception e)
			{
				Console.WriteLine(e.Message);
				Console.WriteLine(e.StackTrace);
				return StatusCode(500, e.Message);
			}
		}

		[HttpPost("api/users/login")]
		public async Task<ActionResult<User>> login([FromBody] User user)
		{
			try
			{
				return Ok(await userService.loginUser(user));
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