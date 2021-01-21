package com.handyman.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceReportDto {

	private long id;
	private String technicalId;
	private String serviceId;
	private String startDateTime;
	private String endDateTime;

}
