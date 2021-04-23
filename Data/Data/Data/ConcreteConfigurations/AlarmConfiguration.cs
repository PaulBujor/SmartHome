using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Data.Data.ConcreteConfigurations
{
	public class AlarmConfiguration : Configuration
	{
		public AlarmConfiguration() { }
		public AlarmConfiguration(Configuration config)
		{
			ConfigurationID = config.ConfigurationID;
			MinOrDefault = config.MinOrDefault;
			Max = config.Max;
			Active = config.Active;
		}
	}
}
