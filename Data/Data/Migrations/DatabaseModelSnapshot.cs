﻿// <auto-generated />
using System;
using Data.Properties.Persistence;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Infrastructure;
using Microsoft.EntityFrameworkCore.Storage.ValueConversion;

namespace Data.Migrations
{
    [DbContext(typeof(Database))]
    partial class DatabaseModelSnapshot : ModelSnapshot
    {
        protected override void BuildModel(ModelBuilder modelBuilder)
        {
#pragma warning disable 612, 618
            modelBuilder
                .HasAnnotation("ProductVersion", "5.0.5");

            modelBuilder.Entity("Data.Data.Configuration", b =>
                {
                    b.Property<long>("ConfigurationID")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("INTEGER");

                    b.Property<bool>("Active")
                        .HasColumnType("INTEGER");

                    b.Property<string>("Discriminator")
                        .IsRequired()
                        .HasColumnType("TEXT");

                    b.Property<double>("Max")
                        .HasColumnType("REAL");

                    b.Property<double>("MinOrDefault")
                        .HasColumnType("REAL");

                    b.HasKey("ConfigurationID");

                    b.ToTable("Configurations");

                    b.HasDiscriminator<string>("Discriminator").HasValue("Configuration");
                });

            modelBuilder.Entity("Data.Data.Device", b =>
                {
                    b.Property<long>("DeviceID")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("INTEGER");

                    b.Property<long?>("DeviceConfigurationConfigurationID")
                        .HasColumnType("INTEGER");

                    b.HasKey("DeviceID");

                    b.HasIndex("DeviceConfigurationConfigurationID");

                    b.ToTable("Devices");
                });

            modelBuilder.Entity("Data.Data.Measurement", b =>
                {
                    b.Property<long>("MeasurementID")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("INTEGER");

                    b.Property<string>("Discriminator")
                        .IsRequired()
                        .HasColumnType("TEXT");

                    b.Property<DateTime>("Timestamp")
                        .HasColumnType("TEXT");

                    b.Property<double>("Value")
                        .HasColumnType("REAL");

                    b.HasKey("MeasurementID");

                    b.ToTable("Measurements");

                    b.HasDiscriminator<string>("Discriminator").HasValue("Measurement");
                });

            modelBuilder.Entity("Data.Data.Settings", b =>
                {
                    b.Property<long>("SettingsID")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("INTEGER");

                    b.Property<long?>("AlarmConfigurationConfigurationID")
                        .HasColumnType("INTEGER");

                    b.Property<long?>("CO2ConfigurationConfigurationID")
                        .HasColumnType("INTEGER");

                    b.Property<long?>("DeviceConfigurationConfigurationID")
                        .HasColumnType("INTEGER");

                    b.Property<long?>("HumidityConfigurationConfigurationID")
                        .HasColumnType("INTEGER");

                    b.Property<long?>("TemperatureConfigurationConfigurationID")
                        .HasColumnType("INTEGER");

                    b.HasKey("SettingsID");

                    b.HasIndex("AlarmConfigurationConfigurationID");

                    b.HasIndex("CO2ConfigurationConfigurationID");

                    b.HasIndex("DeviceConfigurationConfigurationID");

                    b.HasIndex("HumidityConfigurationConfigurationID");

                    b.HasIndex("TemperatureConfigurationConfigurationID");

                    b.ToTable("Settings");
                });

            modelBuilder.Entity("Data.Data.ConcreteConfigurations.AlarmConfiguration", b =>
                {
                    b.HasBaseType("Data.Data.Configuration");

                    b.HasDiscriminator().HasValue("AlarmConfiguration");
                });

            modelBuilder.Entity("Data.Data.ConcreteConfigurations.CO2Configuration", b =>
                {
                    b.HasBaseType("Data.Data.Configuration");

                    b.HasDiscriminator().HasValue("CO2Configuration");
                });

            modelBuilder.Entity("Data.Data.ConcreteConfigurations.DeviceConfiguration", b =>
                {
                    b.HasBaseType("Data.Data.Configuration");

                    b.HasDiscriminator().HasValue("DeviceConfiguration");
                });

            modelBuilder.Entity("Data.Data.ConcreteConfigurations.HumidityConfiguration", b =>
                {
                    b.HasBaseType("Data.Data.Configuration");

                    b.HasDiscriminator().HasValue("HumidityConfiguration");
                });

            modelBuilder.Entity("Data.Data.ConcreteConfigurations.TemperatureConfiguration", b =>
                {
                    b.HasBaseType("Data.Data.Configuration");

                    b.HasDiscriminator().HasValue("TemperatureConfiguration");
                });

            modelBuilder.Entity("Data.Data.CO2Measurement", b =>
                {
                    b.HasBaseType("Data.Data.Measurement");

                    b.Property<long?>("DeviceID")
                        .HasColumnType("INTEGER")
                        .HasColumnName("CO2Measurement_DeviceID");

                    b.HasIndex("DeviceID");

                    b.HasDiscriminator().HasValue("CO2Measurement");
                });

            modelBuilder.Entity("Data.Data.ConcreteMeasurements.AlarmMeasurement", b =>
                {
                    b.HasBaseType("Data.Data.Measurement");

                    b.Property<long?>("DeviceID")
                        .HasColumnType("INTEGER")
                        .HasColumnName("AlarmMeasurement_DeviceID");

                    b.HasIndex("DeviceID");

                    b.HasDiscriminator().HasValue("AlarmMeasurement");
                });

            modelBuilder.Entity("Data.Data.ConcreteMeasurements.HumidityMeasurement", b =>
                {
                    b.HasBaseType("Data.Data.Measurement");

                    b.Property<long?>("DeviceID")
                        .HasColumnType("INTEGER");

                    b.HasIndex("DeviceID");

                    b.HasDiscriminator().HasValue("HumidityMeasurement");
                });

            modelBuilder.Entity("Data.Data.TemperatureMeasurement", b =>
                {
                    b.HasBaseType("Data.Data.Measurement");

                    b.Property<long?>("DeviceID")
                        .HasColumnType("INTEGER")
                        .HasColumnName("TemperatureMeasurement_DeviceID");

                    b.HasIndex("DeviceID");

                    b.HasDiscriminator().HasValue("TemperatureMeasurement");
                });

            modelBuilder.Entity("Data.Data.Device", b =>
                {
                    b.HasOne("Data.Data.Configuration", "DeviceConfiguration")
                        .WithMany()
                        .HasForeignKey("DeviceConfigurationConfigurationID");

                    b.Navigation("DeviceConfiguration");
                });

            modelBuilder.Entity("Data.Data.Settings", b =>
                {
                    b.HasOne("Data.Data.ConcreteConfigurations.AlarmConfiguration", "AlarmConfiguration")
                        .WithMany()
                        .HasForeignKey("AlarmConfigurationConfigurationID");

                    b.HasOne("Data.Data.ConcreteConfigurations.CO2Configuration", "CO2Configuration")
                        .WithMany()
                        .HasForeignKey("CO2ConfigurationConfigurationID");

                    b.HasOne("Data.Data.ConcreteConfigurations.DeviceConfiguration", "DeviceConfiguration")
                        .WithMany()
                        .HasForeignKey("DeviceConfigurationConfigurationID");

                    b.HasOne("Data.Data.ConcreteConfigurations.HumidityConfiguration", "HumidityConfiguration")
                        .WithMany()
                        .HasForeignKey("HumidityConfigurationConfigurationID");

                    b.HasOne("Data.Data.ConcreteConfigurations.TemperatureConfiguration", "TemperatureConfiguration")
                        .WithMany()
                        .HasForeignKey("TemperatureConfigurationConfigurationID");

                    b.Navigation("AlarmConfiguration");

                    b.Navigation("CO2Configuration");

                    b.Navigation("DeviceConfiguration");

                    b.Navigation("HumidityConfiguration");

                    b.Navigation("TemperatureConfiguration");
                });

            modelBuilder.Entity("Data.Data.CO2Measurement", b =>
                {
                    b.HasOne("Data.Data.Device", null)
                        .WithMany("CO2")
                        .HasForeignKey("DeviceID");
                });

            modelBuilder.Entity("Data.Data.ConcreteMeasurements.AlarmMeasurement", b =>
                {
                    b.HasOne("Data.Data.Device", null)
                        .WithMany("Alarm")
                        .HasForeignKey("DeviceID");
                });

            modelBuilder.Entity("Data.Data.ConcreteMeasurements.HumidityMeasurement", b =>
                {
                    b.HasOne("Data.Data.Device", null)
                        .WithMany("Humidity")
                        .HasForeignKey("DeviceID");
                });

            modelBuilder.Entity("Data.Data.TemperatureMeasurement", b =>
                {
                    b.HasOne("Data.Data.Device", null)
                        .WithMany("Temperature")
                        .HasForeignKey("DeviceID");
                });

            modelBuilder.Entity("Data.Data.Device", b =>
                {
                    b.Navigation("Alarm");

                    b.Navigation("CO2");

                    b.Navigation("Humidity");

                    b.Navigation("Temperature");
                });
#pragma warning restore 612, 618
        }
    }
}
