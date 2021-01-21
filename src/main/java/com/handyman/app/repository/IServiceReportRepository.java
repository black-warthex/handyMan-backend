package com.handyman.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.handyman.app.entity.ServiceReportEntity;


public interface IServiceReportRepository extends JpaRepository<ServiceReportEntity, String> {

	/**
	 * encontrar technico y servicio
	 * 
	 * @Param technicalId Corresponde a el id del tecnico, se usará para buscar en
	 *        la fuente de datos.
	 * @Param weekNumber corresponde al numero de semana, se usara para buscar en la
	 *        fuente de datos false y mensaje de informacion duplicada o error de
	 *        fecha establecidos.
	 *@Return retorna lista de la entidad ServiceReportEntity
	 */
	public List<ServiceReportEntity> findByTechnicalIdAndServiceId(String technicalId, String serviceId);
	
	/**
	 * verificar technico y servicio
	 * 
	 * @Param technicalId Corresponde a el id del tecnico, se usará para buscar en
	 *        la fuente de datos.
	 * @Param weekNumber corresponde al numero de semana, se usara para buscar en la
	 *        fuente de datos false y mensaje de informacion duplicada o error de
	 *        fecha establecidos.
	 *@Return retorna  entero 0 o mayor traido de la fuente de datos. 
	 */
	@Query(value = "call checkTechnialOrReport(:technicalId,:weekNumber)", nativeQuery = true)
	public Integer checkTechnicalReport(@Param("technicalId") String technicalId, @Param("weekNumber") Long weekNumber);
	
	/**
	 * encontrar horas normales
	 * 
	 * @Param technicalId Corresponde a el id del tecnico, se usará para buscar en
	 *        la fuente de datos.
	 * @Param weekNumber corresponde al numero de semana, se usara para buscar en la
	 *        fuente de datos false y mensaje de informacion duplicada o error de
	 *        fecha establecidos.
	 *@Return retorna entero con la cantidad de horas normales encontradas en la fuente de datos
	 */
	@Query(value = "call normalWorked(:technicalId,:weekNumber)", nativeQuery = true)
	public Double findNormalHours(@Param("technicalId") String technicalId, @Param("weekNumber") Long weekNumber);
	
	/**
	 * encontrar horas nocturnas
	 * 
	 * @Param technicalId Corresponde a el id del tecnico, se usará para buscar en
	 *        la fuente de datos.
	 * @Param weekNumber corresponde al numero de semana, se usara para buscar en la
	 *        fuente de datos false y mensaje de informacion duplicada o error de
	 *        fecha establecidos.
	 *@Return retorna entero con la cantidad de horas nocturnas encontradas en la fuente de datos
	 */
	@Query(value = "call nightWorked(:technicalId,:weekNumber)", nativeQuery = true)
	public Double findNightHours(@Param("technicalId") String technicalId, @Param("weekNumber") Long weekNumber);
	
	/**
	 * encontrar horas dominicales
	 * 
	 * @Param technicalId Corresponde a el id del tecnico, se usará para buscar en
	 *        la fuente de datos.
	 * @Param weekNumber corresponde al numero de semana, se usara para buscar en la
	 *        fuente de datos false y mensaje de informacion duplicada o error de
	 *        fecha establecidos.
	 *@Return retorna entero con la cantidad de horas dominicales encontradas en la fuente de datos
	 */
	@Query(value = "call sundayWorked(:technicalId,:weekNumber)", nativeQuery = true)
	public Double findSundayHours(@Param("technicalId") String technicalId, @Param("weekNumber") Long weekNumber);
	
	/**
	 * encontrar horas semanales
	 * 
	 * @Param technicalId Corresponde a el id del tecnico, se usará para buscar en
	 *        la fuente de datos.
	 * @Param weekNumber corresponde al numero de semana, se usara para buscar en la
	 *        fuente de datos false y mensaje de informacion duplicada o error de
	 *        fecha establecidos.
	 *@Return retorna entero con la cantidad de horas semanasles de Lunes-Sabado encontradas en la fuente de datos
	 */
	@Query(value = "call findWeeklyHours(:technicalId,:weekNumber)", nativeQuery = true)
	public Double findWeeklyHours(@Param("technicalId") String technicalId, @Param("weekNumber") Long weekNumber);

}
