package cl.villegas.mybatis.util;

import cl.villegas.mybatis.constants.Constants;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

@Component
public class ReportUtil {
	private static final Logger logger = LoggerFactory.getLogger(ReportUtil.class);

	@Autowired
	DataSource datasource;

	public byte[] generateReportBytesByNameAndParametersAndType(String name, String type, Map<String, Object> parameters) {
		try {
			Connection connection = datasource.getConnection();

			final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			InputStream inputStream = getClass().getResourceAsStream(Constants.Report.PATH + name + Constants.FileExtension.JRXML);

			JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);

			switch (type.toUpperCase()) {
				case Constants.FileType.XLSX:
					generateXlsxExporter(jasperPrint, byteArrayOutputStream);
					break;
				case Constants.FileType.PDF:
					generatePdfExporter(jasperPrint, byteArrayOutputStream);
					break;
				case Constants.FileType.CSV:
					generateCsvExporter(jasperPrint, byteArrayOutputStream);
					break;
				case Constants.FileType.HTML:
					generateHtmlExporter(jasperPrint, byteArrayOutputStream);
					break;
				default:
					break;
			}
			connection.close();
			return byteArrayOutputStream.toByteArray();
		} catch (SQLException | JRException e) {
			logger.error(e.getMessage());
			return new byte[0];
		}
	}

	private void generateXlsxExporter(JasperPrint jasperPrint, ByteArrayOutputStream byteArrayOutputStream) {
		JRXlsxExporter jrXlsxExporter = new JRXlsxExporter();
		jrXlsxExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		jrXlsxExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArrayOutputStream));
		SimpleXlsxReportConfiguration simpleXlsxReportConfiguration = new SimpleXlsxReportConfiguration();
		simpleXlsxReportConfiguration.setRemoveEmptySpaceBetweenRows(true);
		jrXlsxExporter.setConfiguration(simpleXlsxReportConfiguration);
		try {
			jrXlsxExporter.exportReport();
		} catch (JRException e) {
			logger.error(e.getMessage());
		}
	}

	private void generatePdfExporter(JasperPrint jasperPrint, ByteArrayOutputStream byteArrayOutputStream) {
		JRPdfExporter jrPdfExporter = new JRPdfExporter();
		jrPdfExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		jrPdfExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArrayOutputStream));
		try {
			jrPdfExporter.exportReport();
		} catch (JRException e) {
			logger.error(e.getMessage());
		}
	}

	private void generateCsvExporter(JasperPrint jasperPrint, ByteArrayOutputStream byteArrayOutputStream) {
		JRCsvExporter jrCsvExporter = new JRCsvExporter();
		jrCsvExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		jrCsvExporter.setExporterOutput(new SimpleWriterExporterOutput(byteArrayOutputStream));
		try {
			jrCsvExporter.exportReport();
		} catch (JRException e) {
			logger.error(e.getMessage());
		}
	}

	private void generateHtmlExporter(JasperPrint jasperPrint, ByteArrayOutputStream byteArrayOutputStream) {
		HtmlExporter htmlExporter = new HtmlExporter();
		htmlExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		htmlExporter.setExporterOutput(new SimpleHtmlExporterOutput(byteArrayOutputStream));
		try {
			htmlExporter.exportReport();
		} catch (JRException e) {
			logger.error(e.getMessage());
		}
	}
}