package com.handyman.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HoursReportDto {

	private Double baseSalary;
	private Double hourlyPrice;

	private Double normalWorkingHours;
	private Double normalOvertimeWorked;
	private Double workingNightHours;
	private Double overnigthHoursWorked;
	private Double sundayWorkingHours;
	private Double sundayOvertimeWork;

	private Double normalHours;
	private Double nightHours;
	private Double sundayHours;
	private Double normalExtraHours;
	private Double nightlyOvertime;
	private Double sundayExtraHours;
	private Double finalSalary;

}
