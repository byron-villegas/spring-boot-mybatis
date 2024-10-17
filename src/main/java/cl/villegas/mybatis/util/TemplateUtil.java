package cl.villegas.mybatis.util;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

@Component
public class TemplateUtil {
    private static final Logger logger = LoggerFactory.getLogger(TemplateUtil.class);

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    public String generateTemplateByNameTypeAndParameters(String name, String type, Map<String, Object> parameters) {
        try {
            Template template = freeMarkerConfigurer.getConfiguration().getTemplate(name + "." + type.toLowerCase());
            Writer writer = new StringWriter();
            template.process(parameters, writer);
            return writer.toString();
        } catch (TemplateException | IOException ex) {
            logger.error(ex.getMessage());
            return "";
        }
    }
}