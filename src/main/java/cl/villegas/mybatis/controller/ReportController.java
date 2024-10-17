package cl.villegas.mybatis.controller;

import javax.servlet.http.HttpServletRequest;
import cl.villegas.mybatis.util.HttpHeaderUtil;
import cl.villegas.mybatis.util.ParameterUtil;
import cl.villegas.mybatis.util.ReportUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET })
@RequestMapping(path = "/reportes")
public class ReportController {
	private static final Logger logger = LoggerFactory.getLogger(ReportController.class);

	@Autowired
	private ReportUtil reportUtil;

	@ApiOperation(value = "Retorna un reporte segun nombre y tipo especificado a partir de los parametros ingresados")
	@GetMapping("{name}/{type}")
	public ResponseEntity<byte[]> generateReport(@PathVariable(value = "name") String name, @PathVariable(value = "type") String type, HttpServletRequest httpServletRequest) {
		logger.info(String.format("Reporte %s Generado en Formato %s", name, type));
		return new ResponseEntity<>(reportUtil.generateReportBytesByNameAndParametersAndType(name, type, ParameterUtil.getParameters(httpServletRequest)),
				HttpHeaderUtil.getHttpHeadersByFileType(name, type), HttpStatus.OK);
	}
}