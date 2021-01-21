package com.handyman.app.utility;

import com.handyman.app.dto.ServiceReportDto;
import com.handyman.app.entity.ServiceReportEntity;

import lombok.NonNull;

public class DtoToEntity {
	/**
	 * convierte entity a Dto
	 * 
	 * @Param ServiceReportDto corresponde a la clase ServiceReportDtoD
	 * @Return retorna ServiceReportEntity sera usado para trabajar con los services
	 */
	public ServiceReportEntity reportDtoToEntity(@NonNull ServiceReportDto report) {

		ServiceReportEntity reportEntity = new ServiceReportEntity();
		reportEntity.setId(report.getId());
		reportEntity.setTechnicalId(report.getTechnicalId());
		reportEntity.setServiceId(report.getServiceId());
		reportEntity.setStartDateTime(report.getStartDateTime());
		reportEntity.setEndDateTime(report.getEndDateTime());

		return reportEntity;

	}
}
