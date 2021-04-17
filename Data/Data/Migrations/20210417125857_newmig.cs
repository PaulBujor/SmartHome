using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace Data.Migrations
{
    public partial class newmig : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "Configurations",
                columns: table => new
                {
                    ConfigurationID = table.Column<long>(type: "INTEGER", nullable: false)
                        .Annotation("Sqlite:Autoincrement", true),
                    Active = table.Column<bool>(type: "INTEGER", nullable: false),
                    MinOrDefault = table.Column<double>(type: "REAL", nullable: false),
                    Max = table.Column<double>(type: "REAL", nullable: false),
                    Discriminator = table.Column<string>(type: "TEXT", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Configurations", x => x.ConfigurationID);
                });

            migrationBuilder.CreateTable(
                name: "Settings",
                columns: table => new
                {
                    SettingsID = table.Column<long>(type: "INTEGER", nullable: false)
                        .Annotation("Sqlite:Autoincrement", true),
                    DeviceConfigurationConfigurationID = table.Column<long>(type: "INTEGER", nullable: true),
                    AlarmConfigurationConfigurationID = table.Column<long>(type: "INTEGER", nullable: true),
                    TemperatureConfigurationConfigurationID = table.Column<long>(type: "INTEGER", nullable: true),
                    CO2ConfigurationConfigurationID = table.Column<long>(type: "INTEGER", nullable: true),
                    HumidityConfigurationConfigurationID = table.Column<long>(type: "INTEGER", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Settings", x => x.SettingsID);
                    table.ForeignKey(
                        name: "FK_Settings_Configurations_AlarmConfigurationConfigurationID",
                        column: x => x.AlarmConfigurationConfigurationID,
                        principalTable: "Configurations",
                        principalColumn: "ConfigurationID",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_Settings_Configurations_CO2ConfigurationConfigurationID",
                        column: x => x.CO2ConfigurationConfigurationID,
                        principalTable: "Configurations",
                        principalColumn: "ConfigurationID",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_Settings_Configurations_DeviceConfigurationConfigurationID",
                        column: x => x.DeviceConfigurationConfigurationID,
                        principalTable: "Configurations",
                        principalColumn: "ConfigurationID",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_Settings_Configurations_HumidityConfigurationConfigurationID",
                        column: x => x.HumidityConfigurationConfigurationID,
                        principalTable: "Configurations",
                        principalColumn: "ConfigurationID",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_Settings_Configurations_TemperatureConfigurationConfigurationID",
                        column: x => x.TemperatureConfigurationConfigurationID,
                        principalTable: "Configurations",
                        principalColumn: "ConfigurationID",
                        onDelete: ReferentialAction.Restrict);
                });

            migrationBuilder.CreateTable(
                name: "Devices",
                columns: table => new
                {
                    DeviceID = table.Column<long>(type: "INTEGER", nullable: false)
                        .Annotation("Sqlite:Autoincrement", true),
                    DeviceSettingsSettingsID = table.Column<long>(type: "INTEGER", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Devices", x => x.DeviceID);
                    table.ForeignKey(
                        name: "FK_Devices_Settings_DeviceSettingsSettingsID",
                        column: x => x.DeviceSettingsSettingsID,
                        principalTable: "Settings",
                        principalColumn: "SettingsID",
                        onDelete: ReferentialAction.Restrict);
                });

            migrationBuilder.CreateTable(
                name: "Measurements",
                columns: table => new
                {
                    MeasurementID = table.Column<long>(type: "INTEGER", nullable: false)
                        .Annotation("Sqlite:Autoincrement", true),
                    Timestamp = table.Column<DateTime>(type: "TEXT", nullable: false),
                    Value = table.Column<double>(type: "REAL", nullable: false),
                    Discriminator = table.Column<string>(type: "TEXT", nullable: false),
                    CO2Measurement_DeviceID = table.Column<long>(type: "INTEGER", nullable: true),
                    AlarmMeasurement_DeviceID = table.Column<long>(type: "INTEGER", nullable: true),
                    DeviceID = table.Column<long>(type: "INTEGER", nullable: true),
                    TemperatureMeasurement_DeviceID = table.Column<long>(type: "INTEGER", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Measurements", x => x.MeasurementID);
                    table.ForeignKey(
                        name: "FK_Measurements_Devices_AlarmMeasurement_DeviceID",
                        column: x => x.AlarmMeasurement_DeviceID,
                        principalTable: "Devices",
                        principalColumn: "DeviceID",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_Measurements_Devices_CO2Measurement_DeviceID",
                        column: x => x.CO2Measurement_DeviceID,
                        principalTable: "Devices",
                        principalColumn: "DeviceID",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_Measurements_Devices_DeviceID",
                        column: x => x.DeviceID,
                        principalTable: "Devices",
                        principalColumn: "DeviceID",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_Measurements_Devices_TemperatureMeasurement_DeviceID",
                        column: x => x.TemperatureMeasurement_DeviceID,
                        principalTable: "Devices",
                        principalColumn: "DeviceID",
                        onDelete: ReferentialAction.Restrict);
                });

            migrationBuilder.CreateIndex(
                name: "IX_Devices_DeviceSettingsSettingsID",
                table: "Devices",
                column: "DeviceSettingsSettingsID");

            migrationBuilder.CreateIndex(
                name: "IX_Measurements_AlarmMeasurement_DeviceID",
                table: "Measurements",
                column: "AlarmMeasurement_DeviceID");

            migrationBuilder.CreateIndex(
                name: "IX_Measurements_CO2Measurement_DeviceID",
                table: "Measurements",
                column: "CO2Measurement_DeviceID");

            migrationBuilder.CreateIndex(
                name: "IX_Measurements_DeviceID",
                table: "Measurements",
                column: "DeviceID");

            migrationBuilder.CreateIndex(
                name: "IX_Measurements_TemperatureMeasurement_DeviceID",
                table: "Measurements",
                column: "TemperatureMeasurement_DeviceID");

            migrationBuilder.CreateIndex(
                name: "IX_Settings_AlarmConfigurationConfigurationID",
                table: "Settings",
                column: "AlarmConfigurationConfigurationID");

            migrationBuilder.CreateIndex(
                name: "IX_Settings_CO2ConfigurationConfigurationID",
                table: "Settings",
                column: "CO2ConfigurationConfigurationID");

            migrationBuilder.CreateIndex(
                name: "IX_Settings_DeviceConfigurationConfigurationID",
                table: "Settings",
                column: "DeviceConfigurationConfigurationID");

            migrationBuilder.CreateIndex(
                name: "IX_Settings_HumidityConfigurationConfigurationID",
                table: "Settings",
                column: "HumidityConfigurationConfigurationID");

            migrationBuilder.CreateIndex(
                name: "IX_Settings_TemperatureConfigurationConfigurationID",
                table: "Settings",
                column: "TemperatureConfigurationConfigurationID");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "Measurements");

            migrationBuilder.DropTable(
                name: "Devices");

            migrationBuilder.DropTable(
                name: "Settings");

            migrationBuilder.DropTable(
                name: "Configurations");
        }
    }
}
