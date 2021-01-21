package com.handyman.app.service;

import org.springframework.stereotype.Service;

import com.handyman.app.dto.HoursReportDto;
import com.handyman.app.dto.ResponseDto;
import com.handyman.app.entity.ServiceReportEntity;

import lombok.NonNull;

@Service
public interface IServiceReportService {
	/**
	 * agregar reporte de servicio
	 * 
	 * @Param ServiceReportEntity Corresponde a la entidad reporte de servicio, se
	 *        usará para filtrar e ingresar un nuevo reporte en la fuente de datos.
	 * @Return true y mensaje de ingreso establecidos, en caso de ingresar la
	 *         asignación en la fuente de datos. false y mensaje de informacion
	 *         duplicada o error de fecha establecidos.
	 */
	public ResponseDto addServiceReport(@NonNull ServiceReportEntity serviceReportEntity);

	/**
	 * Calcular Horas Laboradas
	 * 
	 * @Param technicalId Corresponde a el id del tecnico, se usará para buscar en
	 *        la fuente de datos.
	 * @Param weekNumber corresponde al numero de semana, se usara para buscar en la
	 *        fuente de datos
	 * @Return devuelve la clase HoursReportDto con los campos con su respectivo
	 *         calculo o 0 si no encuentra información.
	 */
	public HoursReportDto getHoursReport(@NonNull String technicalId, @NonNull Long weekNumber);

}
