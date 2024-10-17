package cl.villegas.mybatis.controller;

import java.io.IOException;
import cl.villegas.mybatis.util.EmailUtil;
import cl.villegas.mybatis.util.ParameterUtil;
import cl.villegas.mybatis.util.ReportUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET })
@RequestMapping("correos")
public class EmailController {
    private static final Logger logger = LoggerFactory.getLogger(EmailController.class);

    @Autowired
    private ReportUtil reportUtil;

    @ApiOperation(value = "Envio de texto plano")
    @GetMapping(path = "plain-text")
    @ResponseStatus(HttpStatus.OK)
    public String sendPlainText(@RequestParam(value = "correo") String toEmail,
            @RequestParam(value = "asunto") String subject, @RequestParam(value = "cuerpo") String body) {
        logger.info("Envio de correo con texto plano ejecutado");
        EmailUtil.sendPlainText(toEmail, subject, body);
        return "Envio de correo con texto plano ejecutado";
    }

    @ApiOperation(value = "Envio de html")
    @GetMapping(path = "html")
    @ResponseStatus(HttpStatus.OK)
    public String sendHtml(@RequestParam(value = "correo") String toEmail,
            @RequestParam(value = "asunto") String subject, @RequestParam(value = "html") String html) {
        logger.info("Envio de correo con html ejecutado");
        EmailUtil.sendHtml(toEmail, subject, html);
        return "Envio de correo con html ejecutado";
    }

    @ApiOperation(value = "Envio de archivo")
    @PostMapping(path = "file")
    @ResponseStatus(HttpStatus.OK)
    public String sendFile(@RequestParam(value = "correo") String toEmail,
            @RequestParam(value = "asunto") String subject, @RequestParam(value = "cuerpo") String body,
            @RequestParam(value = "file") MultipartFile file) {
        String salida = "Envio de correo con archivo ejecutado exitosamente";
        try {
            EmailUtil.sendFile(toEmail, subject, body, file.getOriginalFilename(), file.getContentType(),
                    file.getBytes());
        } catch (IOException ex) {
            logger.error(ex.getMessage());
            salida = "Envio de correo con archivo con problemas";
        }
        return salida;
    }

    @ApiOperation(value = "Envio de reporte")
    @GetMapping(path = "report")
    @ResponseStatus(HttpStatus.OK)
    public String sendReport(@RequestParam(value = "correo") String toEmail,
            @RequestParam(value = "asunto") String subject, @RequestParam(value = "cuerpo") String body,
            @RequestParam(value = "reporte") String report, @RequestParam(value = "tipo") String type,
            HttpServletRequest httpServletRequest) {
        EmailUtil.sendReport(toEmail, subject, body, report, type, reportUtil.generateReportBytesByNameAndParametersAndType(report, type, ParameterUtil.getParameters(httpServletRequest)));
        return "Envio de correo con reporte ejecutado";
    }
}