﻿// <auto-generated />
using System;
using Data.Properties.Persistence;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Infrastructure;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;
using Microsoft.EntityFrameworkCore.Storage.ValueConversion;

namespace Data.Migrations
{
    [DbContext(typeof(Database))]
    [Migration("20210514184655_Change_Settings")]
    partial class Change_Settings
    {
        protected override void BuildTargetModel(ModelBuilder modelBuilder)
        {
#pragma warning disable 612, 618
            modelBuilder
                .HasAnnotation("Relational:MaxIdentifierLength", 128)
                .HasAnnotation("ProductVersion", "5.0.5")
                .HasAnnotation("SqlServer:ValueGenerationStrategy", SqlServerValueGenerationStrategy.IdentityColumn);

            modelBuilder.Entity("Data.Data.Device", b =>
                {
                    b.Property<long>("ID")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("bigint")
                        .HasAnnotation("SqlServer:ValueGenerationStrategy", SqlServerValueGenerationStrategy.IdentityColumn);

                    b.Property<long>("DeviceID")
                        .HasColumnType("bigint");

                    b.Property<long?>("DeviceSettingsSettingsID")
                        .HasColumnType("bigint");

                    b.HasKey("ID");

                    b.HasIndex("DeviceSettingsSettingsID");

                    b.ToTable("Devices");
                });

            modelBuilder.Entity("Data.Data.MeasurementSet", b =>
                {
                    b.Property<long>("MeasurementID")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("bigint")
                        .HasAnnotation("SqlServer:ValueGenerationStrategy", SqlServerValueGenerationStrategy.IdentityColumn);

                    b.Property<double>("CO2")
                        .HasColumnType("float");

                    b.Property<long?>("DeviceID")
                        .HasColumnType("bigint");

                    b.Property<double>("Humidity")
                        .HasColumnType("float");

                    b.Property<double>("Sound")
                        .HasColumnType("float");

                    b.Property<double>("Temperature")
                        .HasColumnType("float");

                    b.Property<DateTime>("Timestamp")
                        .HasColumnType("datetime2");

                    b.HasKey("MeasurementID");

                    b.HasIndex("DeviceID");

                    b.ToTable("Measurements");
                });

            modelBuilder.Entity("Data.Data.Settings", b =>
                {
                    b.Property<long>("SettingsID")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("bigint")
                        .HasAnnotation("SqlServer:ValueGenerationStrategy", SqlServerValueGenerationStrategy.IdentityColumn);

                    b.Property<bool>("DeviceConfiguration")
                        .HasColumnType("bit");

                    b.Property<int>("MaxCo2")
                        .HasColumnType("int");

                    b.Property<int>("MaxHumidity")
                        .HasColumnType("int");

                    b.Property<int>("MaxTemperature")
                        .HasColumnType("int");

                    b.Property<int>("MinCo2")
                        .HasColumnType("int");

                    b.Property<int>("MinHumidity")
                        .HasColumnType("int");

                    b.Property<int>("MinTemperature")
                        .HasColumnType("int");

                    b.HasKey("SettingsID");

                    b.ToTable("Settings");
                });

            modelBuilder.Entity("Data.Data.Device", b =>
                {
                    b.HasOne("Data.Data.Settings", "DeviceSettings")
                        .WithMany()
                        .HasForeignKey("DeviceSettingsSettingsID");

                    b.Navigation("DeviceSettings");
                });

            modelBuilder.Entity("Data.Data.MeasurementSet", b =>
                {
                    b.HasOne("Data.Data.Device", null)
                        .WithMany("Measurements")
                        .HasForeignKey("DeviceID");
                });

            modelBuilder.Entity("Data.Data.Device", b =>
                {
                    b.Navigation("Measurements");
                });
#pragma warning restore 612, 618
        }
    }
}
