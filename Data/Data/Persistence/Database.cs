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
            optionsBuilder.UseSqlServer("Server=tcp:sep4db.database.windows.net,1433;Initial Catalog=Data_db;Persist Security Info=False;User ID=sep4admin;Password=Lc7wQNyykW520S14;MultipleActiveResultSets=False;Encrypt=True;TrustServerCertificate=False;Connection Timeout=30;");
        }
    }
}