package com.handyman.app.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.handyman.app.dto.HoursReportDto;
import com.handyman.app.dto.ResponseDto;
import com.handyman.app.dto.ServiceReportDto;
import com.handyman.app.entity.ServiceReportEntity;
import com.handyman.app.service.IServiceReportService;
import com.handyman.app.utility.DefaultAnswerEnum;


@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")

class HandyManApiUnitTest {

	@Autowired
	private MockMvc mock;

	@MockBean
	private IServiceReportService service;
	private ResponseDto response = new ResponseDto();

	@Test
	void testAddReport() throws Exception {

		response.setSuccess(true);
		response.setMessage(DefaultAnswerEnum.MESSAGE_INSERT.getValue());
		ServiceReportDto report = new ServiceReportDto();
		report.setTechnicalId("WTX-1");
		report.setServiceId("S-1");
		report.setStartDateTime("2021-01-18T07:00");
		report.setEndDateTime("2021-01-18T20:00");

		String inputInJson = this.mapToJson(report);
		String url = "/serviceReport/add";

		Mockito.when(service.addServiceReport(Mockito.any(ServiceReportEntity.class))).thenReturn(response);
		RequestBuilder request = MockMvcRequestBuilders.post(url).accept(MediaType.APPLICATION_JSON)
				.content(inputInJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mock.perform(request).andReturn();
		MockHttpServletResponse res = result.getResponse();

		String outputInJson = res.getContentAsString();
		assertEquals(outputInJson, this.mapToJson(response));
		assertEquals(HttpStatus.OK.value(), res.getStatus());
	}

	@Test
	void testACalulateHours() throws Exception {

		String url = "/serviceReport/report";
		String technicalId = "wtx-1";
		Long weekNumber = (long) 3;

		HoursReportDto report = new HoursReportDto();
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

		Mockito.when(service.getHoursReport(technicalId, weekNumber)).thenReturn(report);
		MvcResult result = mock.perform(
				MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON)
						.param("technicalId", technicalId).param("weekNumber", String.valueOf(weekNumber)))
				.andReturn();
		int status = result.getResponse().getStatus();

		assertEquals(200, status);

		String content = result.getResponse().getContentAsString();

		assertEquals(content, this.mapToJson(report));

	}

	private String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}

}
