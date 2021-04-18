using Data.Data;
using Microsoft.EntityFrameworkCore;
using System.Configuration;
using Configuration = Data.Data.Configuration;

namespace Data.Properties.Persistence
{
    public class Database : DbContext
    {
        public DbSet<Device> Devices {get; set;} 
        public DbSet<Configuration> Configurations {get; set;}
        public DbSet<Settings> Settings {get;set;}
        public DbSet<Measurement> Measurements {get;set;}
            
        
        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            var config = System.Configuration.ConfigurationManager.ConnectionStrings[0].ConnectionString;
            optionsBuilder.UseSqlServer(config);
        }
    }
}