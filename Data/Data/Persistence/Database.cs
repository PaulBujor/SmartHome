using Data.Data;
using Microsoft.EntityFrameworkCore;

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
            optionsBuilder.UseSqlite("Data source=database.db");
        }
    }
}