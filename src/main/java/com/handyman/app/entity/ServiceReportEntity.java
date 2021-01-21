package com.handyman.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_service_report")
public class ServiceReportEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "sr_id")
	private Long id;

	@Column(name = "sr_technical_id")
	private String technicalId;

	@Column(name = "sr_service_id")
	private String serviceId;

	@Column(name = "sr_start_date_time")
	private String startDateTime;

	@Column(name = "sr_end_date_time")
	private String endDateTime;

}
