package com.handyman.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.handyman.app.dto.HoursReportDto;
import com.handyman.app.dto.ResponseDto;
import com.handyman.app.dto.ServiceReportDto;
import com.handyman.app.service.IServiceReportService;
import com.handyman.app.utility.DtoToEntity;

@RestController
@RequestMapping("/serviceReport")
public class ServiceReportController {

	@Autowired
	private IServiceReportService service;
	private DtoToEntity change = new DtoToEntity();

	/**
	 * agregar reporte de servicio
	 * 
	 * @Param ServiceReportDto Corresponde a la entidad reporte de servicio, se
	 *        convertira mediante la clase DtoToEntity a ServiceReportEntity usará
	 *        para filtrar e ingresar un nuevo reporte en la fuente de datos.
	 * @Return true y mensaje de ingreso establecidos, en caso de ingresar la
	 *         asignación en la fuente de datos. false y mensaje de informacion
	 *         duplicada o error de fecha establecidos.
	 */
	@PostMapping(path = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<ResponseDto> addServiceReport(@RequestBody ServiceReportDto serviceReport) {

		return new ResponseEntity<>(service.addServiceReport(change.reportDtoToEntity(serviceReport)), HttpStatus.OK);
	}

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
	@GetMapping(path = "/report", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<HoursReportDto> getHoursReport(
			@RequestParam(name = "technicalId") String technicalId,
			@RequestParam(name = "weekNumber") Long weekNumber) {

		return new ResponseEntity<>(service.getHoursReport(technicalId, weekNumber), HttpStatus.OK);
	}

}
