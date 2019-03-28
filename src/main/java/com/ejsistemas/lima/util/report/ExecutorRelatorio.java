package com.ejsistemas.lima.util.report;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.Exporter;
import net.sf.jasperreports.export.ExporterInput;
import net.sf.jasperreports.export.OutputStreamExporterOutput;
import net.sf.jasperreports.export.PdfExporterConfiguration;
import net.sf.jasperreports.export.PdfReportConfiguration;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

import org.hibernate.jdbc.Work;

public class ExecutorRelatorio implements Work{

	private String caminhoRelatorio;
	private String nomeArquivoSaida;
	private HttpServletResponse response;
	private Map<String, Object> parametros;
	
	private boolean relatorioGerado;	
	
	public ExecutorRelatorio(String caminhoRelatorio, String nomeArquivoSaida,
			HttpServletResponse response, Map<String, Object> parametros) {
		super();
		this.caminhoRelatorio = caminhoRelatorio;
		this.nomeArquivoSaida = nomeArquivoSaida;
		this.response = response;
		this.parametros = parametros;
		
		this.parametros.put(JRParameter.REPORT_LOCALE, new Locale("pt","BR"));
	}



	public void execute(Connection connection) throws SQLException {
		
		try {
			InputStream relatorioStream = this.getClass().getResourceAsStream(this.caminhoRelatorio);
			
			JasperPrint print = JasperFillManager.fillReport(relatorioStream, this.parametros, connection);
			this.relatorioGerado = print.getPages().size() > 0;
			
			if(this.relatorioGerado){
			
				//JRExporter exportador = new JRPdfExporter();
				//exportador.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
				//exportador.setParameter(JRExporterParameter.JASPER_PRINT, print);
				
				Exporter<ExporterInput, PdfReportConfiguration, PdfExporterConfiguration,
					OutputStreamExporterOutput> exportador = new JRPdfExporter();
				exportador.setExporterInput(new SimpleExporterInput(print));
				exportador.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
				
				response.setContentType("application/pdf");
				//response.setHeader("Content-Disposition", "attachment; filename=\""
					//	+ this.nomeArquivoSaida +"\"");
				
				exportador.exportReport();
				
			}			
			
		} catch (Exception e) {
			throw new SQLException("Erro ao executar requisição "+ this.caminhoRelatorio, e);
		}
		
	}



	public boolean isRelatorioGerado() {
		return relatorioGerado;
	}
	
	

}
