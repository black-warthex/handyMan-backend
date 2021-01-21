package com.handyman.app.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.handyman.app.dto.HoursReportDto;
import com.handyman.app.dto.ResponseDto;
import com.handyman.app.entity.ServiceReportEntity;
import com.handyman.app.repository.IServiceReportRepository;
import com.handyman.app.service.IServiceReportService;
import com.handyman.app.utility.DefaultAnswerEnum;

import lombok.NonNull;

@Service
public class ServiceReportServiceImpl implements IServiceReportService {

	@Autowired
	private IServiceReportRepository repository;
	private ResponseDto response = new ResponseDto();
	private HoursReportDto report = new HoursReportDto();
	private static final Integer WEEKLYHOURS = 48;

	/**
	 * agregar reporte de servicio
	 * 
	 * @Param ServiceReportEntity Corresponde a la entidad reporte de servicio, se
	 *        usará para filtrar e ingresar un nuevo reporte en la fuente de datos.
	 * @Return true y mensaje de ingreso establecidos, en caso de ingresar la
	 *         asignación en la fuente de datos. false y mensaje de informacion
	 *         duplicada o error de fecha establecidos.
	 */
	@Override
	public ResponseDto addServiceReport(@NonNull ServiceReportEntity serviceReportEntity) {

		response.setSuccess(false);
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
		LocalDateTime startDateTime = LocalDateTime.parse(serviceReportEntity.getStartDateTime(), format);
		LocalDateTime endDateTime = LocalDateTime.parse(serviceReportEntity.getEndDateTime(), format);

		if (repository
				.findByTechnicalIdAndServiceId(serviceReportEntity.getTechnicalId(), serviceReportEntity.getServiceId())
				.isEmpty()) {
			if (endDateTime.isAfter(startDateTime)) {
				repository.save(serviceReportEntity);
				response.setMessage(DefaultAnswerEnum.MESSAGE_INSERT.getValue());
				response.setSuccess(true);
			} else {
				response.setMessage(DefaultAnswerEnum.MESSAGE_ERROR_DATE.getValue());
			}

		} else {
			response.setMessage(DefaultAnswerEnum.MESSAGE_DUPLICATE.getValue());
		}
		return response;
	}
	
	/**
	 * Calcular Horas Laboradas
	 * 
	 * @Param technicalId Corresponde a el id del tecnico, se usará para buscar en la fuente de datos.
	 * @Param weekNumber corresponde al numero de semana, se usara para buscar en la fuente de datos
	 * @Return devuelve la clase HoursReportDto con los campos con su respectivo calculo o 0 si no encuentra información. 
	 */
	@Override
	public HoursReportDto getHoursReport(@NonNull String technicalId, @NonNull Long weekNumber) {

		setEmptyReport();
		if (repository.checkTechnicalReport(technicalId, weekNumber) > 0) {
			report.setBaseSalary(960.000);
			Double hourlyPrice = (report.getBaseSalary() / 240);
			report.setHourlyPrice(hourlyPrice);
			// normal
			calculateNormal(technicalId, weekNumber);
			// night
			calculateNight(technicalId, weekNumber);
			// sunday
			calculateSundayHours(technicalId, weekNumber);
			report.setFinalSalary(report.getNormalHours() + report.getNightHours() + report.getSundayHours()
					+ report.getNormalExtraHours() + report.getNightlyOvertime() + report.getSundayExtraHours());
		}

		return report;
	}
	
	/**
	 * Inicializa todos los campos de la clase HoursReportDto en 0 	   
	 */
	private void setEmptyReport() {
		report.setBaseSalary(0.0);
		report.setHourlyPrice(0.0);
		report.setNormalWorkingHours(0.0);
		report.setNormalOvertimeWorked(0.0);
		report.setWorkingNightHours(0.0);
		report.setOvernigthHoursWorked(0.0);
		report.setSundayWorkingHours(0.0);
		report.setSundayOvertimeWork(0.0);
		report.setNormalHours(0.0);
		report.setNightHours(0.0);
		report.setSundayHours(0.0);
		report.setNormalExtraHours(0.0);
		report.setNightlyOvertime(0.0);
		report.setSundayExtraHours(0.0);
		report.setFinalSalary(0.0);
	}
	
	/**
	 * Calcular Horas Normales
	 * 
	 * @Param technicalId Corresponde a el id del tecnico, se usará para buscar en la fuente de datos.
	 * @Param weekNumber corresponde al numero de semana, se usara para buscar en la fuente de datos
	 * 
	 * Realiza el calculo de las horas normales y horas normales extra
	 */
	private void calculateNormal(String technicalId, Long weekNumber) {

		if (repository.findNormalHours(technicalId, weekNumber) != null) {

			if (repository.findNormalHours(technicalId, weekNumber) < WEEKLYHOURS) {
				report.setNormalWorkingHours(roundNumber(repository.findNormalHours(technicalId, weekNumber)));
				report.setNormalHours(roundNumber(report.getNormalWorkingHours() * report.getHourlyPrice()));

			} else {
				report.setNormalWorkingHours(roundNumber(Double.valueOf(WEEKLYHOURS)));
				report.setNormalHours(roundNumber(report.getNormalWorkingHours() * report.getHourlyPrice()));
				report.setNormalOvertimeWorked(
						roundNumber(repository.findNormalHours(technicalId, weekNumber) - WEEKLYHOURS));
				report.setNormalExtraHours(hourlyPrice(25) * report.getNormalOvertimeWorked());
			}

		}
	}
	/**
	 * Calcular Horas Nocturnas
	 * 
	 * @Param technicalId Corresponde a el id del tecnico, se usará para buscar en la fuente de datos.
	 * @Param weekNumber corresponde al numero de semana, se usara para buscar en la fuente de datos
	 * 
	 * Realiza el calculo de las horas nocturnas y horas nocturnas extra
	 */
	private void calculateNight(String technicalId, Long weekNumber) {

		if (repository.findNightHours(technicalId, weekNumber) != null) {
			if (report.getNormalWorkingHours() < WEEKLYHOURS) {

				if (report.getNormalHours() + repository.findNightHours(technicalId, weekNumber) < WEEKLYHOURS) {
					report.setWorkingNightHours(roundNumber(repository.findNightHours(technicalId, weekNumber)));
					report.setNightHours(roundNumber(hourlyPrice(35) * report.getWorkingNightHours()));
				} else {
					report.setWorkingNightHours(roundNumber(WEEKLYHOURS - report.getNormalWorkingHours()));
					report.setNightHours(roundNumber(hourlyPrice(35) * report.getWorkingNightHours()));
					report.setOvernigthHoursWorked(roundNumber(
							repository.findNightHours(technicalId, weekNumber) - report.getWorkingNightHours()));
					report.setNightlyOvertime(roundNumber(hourlyPrice(75) * report.getOvernigthHoursWorked()));
				}

			} else {
				report.setOvernigthHoursWorked(roundNumber(repository.findNightHours(technicalId, weekNumber)));
				report.setNightlyOvertime(roundNumber(hourlyPrice(75) * report.getOvernigthHoursWorked()));
			}
		}
	}
	/**
	 * Calcular Horas Dominicales
	 * 
	 * @Param technicalId Corresponde a el id del tecnico, se usará para buscar en la fuente de datos.
	 * @Param weekNumber corresponde al numero de semana, se usara para buscar en la fuente de datos
	 * 
	 * Realiza el calculo de las horas dominicales y horas dominicales extra
	 */
	private void calculateSundayHours(String technicalId, Long weekNumber) {
		if (repository.findSundayHours(technicalId, weekNumber) != null) {
			if (repository.findWeeklyHours(technicalId, weekNumber) < WEEKLYHOURS) {
				report.setSundayWorkingHours(roundNumber(repository.findSundayHours(technicalId, weekNumber)));
				report.setSundayHours(roundNumber(hourlyPrice(75) * report.getSundayWorkingHours()));
			} else {
				report.setSundayOvertimeWork(roundNumber(repository.findSundayHours(technicalId, weekNumber)));
				report.setSundayExtraHours(roundNumber(hourlyPrice(100) * report.getSundayOvertimeWork()));
			}
		}
	}

	/**
	 * Redondear numbero
	 * 
	 * @Param number Corresponde a un numero, se usará para redondear
	 * @Return devuelve numero redondeado a dos decimales
	 */
	private Double roundNumber(@NonNull Double number) {

		return Math.round(number * 100.0) / 100.0;
	}
	/**
	 * Calcular precio de hora
	 * 
	 * @Param percentaje Corresponde a el porcentaje, se usará para calcular el precio de la hora de acuerdo a este 
	 * @return devuelv 
	 */
	private Double hourlyPrice(@NonNull Integer percentaje) {
		return (roundNumber(report.getHourlyPrice() * Double.valueOf(percentaje) / 100) + report.getHourlyPrice());
	}

}
