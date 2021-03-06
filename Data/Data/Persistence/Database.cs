using Data.Data;
using Microsoft.EntityFrameworkCore;
using System.Configuration;

namespace Data.Properties.Persistence
{
    public class Database : DbContext
    {
	    public DbSet<User> Users { get; set; }
        public DbSet<Device> Devices {get; set;} 
        public DbSet<Thresholds> Thresholds { get;set;}
        public DbSet<MeasurementSet> Measurements {get;set;}
            
        
        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            optionsBuilder.UseSqlServer("Server=tcp:sep4db.database.windows.net,1433;Initial Catalog=Data_db;Persist Security Info=False;User ID=sep4admin;Password=Lc7wQNyykW520S14;MultipleActiveResultSets=False;Encrypt=True;TrustServerCertificate=False;Connection Timeout=30;");
        }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
	        base.OnModelCreating(modelBuilder);
	        modelBuilder.Entity<User>().HasMany(u => u.Devices).WithMany(d => d.Owners);

        }
    }
}