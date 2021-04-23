using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Data.Data.ConcreteConfigurations
{
	public class CO2Configuration : Configuration
	{
		public CO2Configuration() { }
		public CO2Configuration(Configuration config)
		{
			ConfigurationID = config.ConfigurationID;
			MinOrDefault = config.MinOrDefault;
			Max = config.Max;
			Active = config.Active;
		}
	}
}
