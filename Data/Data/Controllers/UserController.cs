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
		public async Task<ActionResult<List<Device>>> GetDevices(long id)
		{
			try
			{
				return Ok(await userService.GetDevices(id));
			}
			catch (Exception e)
			{
				Console.WriteLine(e.Message);
				Console.WriteLine(e.StackTrace);
				return StatusCode(500, e.Message);
			}
		}

		[HttpPost("api/users/{id}/devices/{deviceId}")]
		public async Task<ActionResult> AddDevice(long id, long deviceId, [FromBody] User user)
		{
			try
			{
				var myUser = await userService.LoginUser(user);
				if (myUser.UserID != id)
					throw new ArgumentException("User id does not match actual user");

				await userService.AddDevice(id, deviceId);
				return Ok();
			}
			catch (Exception e)
			{
				Console.WriteLine(e.Message);
				Console.WriteLine(e.StackTrace);
				return StatusCode(500, e.Message);
			}
		}

		[HttpDelete("api/users/{id}/devices/{deviceId}")]
		public async Task<ActionResult> RemoveDevice(long id, long deviceId, [FromBody] User user)
		{
			try
			{
				var myUser = await userService.LoginUser(user);
				if (myUser.UserID != id)
					throw new ArgumentException("User id does not match actual user");

				await userService.RemoveDevice(id, deviceId);
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
		public async Task<ActionResult> Register([FromBody] User user)
		{
			try
			{
				await userService.RegisterUser(user);
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
		public async Task<ActionResult<User>> Login([FromBody] User user)
		{
			try
			{
				return Ok(await userService.LoginUser(user));
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