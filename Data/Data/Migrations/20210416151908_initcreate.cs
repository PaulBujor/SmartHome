using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace Data.Migrations
{
    public partial class initcreate : Migration
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
                    Max = table.Column<double>(type: "REAL", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Configurations", x => x.ConfigurationID);
                });

            migrationBuilder.CreateTable(
                name: "Devices",
                columns: table => new
                {
                    DeviceID = table.Column<long>(type: "INTEGER", nullable: false)
                        .Annotation("Sqlite:Autoincrement", true),
                    DeviceConfigurationConfigurationID = table.Column<long>(type: "INTEGER", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Devices", x => x.DeviceID);
                    table.ForeignKey(
                        name: "FK_Devices_Configurations_DeviceConfigurationConfigurationID",
                        column: x => x.DeviceConfigurationConfigurationID,
                        principalTable: "Configurations",
                        principalColumn: "ConfigurationID",
                        onDelete: ReferentialAction.Restrict);
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
                name: "Measurements",
                columns: table => new
                {
                    MeasurementID = table.Column<long>(type: "INTEGER", nullable: false)
                        .Annotation("Sqlite:Autoincrement", true),
                    Timestamp = table.Column<DateTime>(type: "TEXT", nullable: false),
                    Value = table.Column<double>(type: "REAL", nullable: false),
                    DeviceID = table.Column<long>(type: "INTEGER", nullable: true),
                    DeviceID1 = table.Column<long>(type: "INTEGER", nullable: true),
                    DeviceID2 = table.Column<long>(type: "INTEGER", nullable: true),
                    DeviceID3 = table.Column<long>(type: "INTEGER", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Measurements", x => x.MeasurementID);
                    table.ForeignKey(
                        name: "FK_Measurements_Devices_DeviceID",
                        column: x => x.DeviceID,
                        principalTable: "Devices",
                        principalColumn: "DeviceID",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_Measurements_Devices_DeviceID1",
                        column: x => x.DeviceID1,
                        principalTable: "Devices",
                        principalColumn: "DeviceID",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_Measurements_Devices_DeviceID2",
                        column: x => x.DeviceID2,
                        principalTable: "Devices",
                        principalColumn: "DeviceID",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_Measurements_Devices_DeviceID3",
                        column: x => x.DeviceID3,
                        principalTable: "Devices",
                        principalColumn: "DeviceID",
                        onDelete: ReferentialAction.Restrict);
                });

            migrationBuilder.CreateIndex(
                name: "IX_Devices_DeviceConfigurationConfigurationID",
                table: "Devices",
                column: "DeviceConfigurationConfigurationID");

            migrationBuilder.CreateIndex(
                name: "IX_Measurements_DeviceID",
                table: "Measurements",
                column: "DeviceID");

            migrationBuilder.CreateIndex(
                name: "IX_Measurements_DeviceID1",
                table: "Measurements",
                column: "DeviceID1");

            migrationBuilder.CreateIndex(
                name: "IX_Measurements_DeviceID2",
                table: "Measurements",
                column: "DeviceID2");

            migrationBuilder.CreateIndex(
                name: "IX_Measurements_DeviceID3",
                table: "Measurements",
                column: "DeviceID3");

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
                name: "Settings");

            migrationBuilder.DropTable(
                name: "Devices");

            migrationBuilder.DropTable(
                name: "Configurations");
        }
    }
}
