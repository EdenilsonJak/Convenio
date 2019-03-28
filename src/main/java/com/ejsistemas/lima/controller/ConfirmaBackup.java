package com.ejsistemas.lima.controller;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import com.ejsistemas.lima.controller.MysqlDumputils;

@Named
@ViewScoped
public class ConfirmaBackup implements Serializable {

	private static final long serialVersionUID = 1L;

	private String arquivo = "Digite aqui o nome do arquivo";

	public void fazBackup() throws IOException{
		
		Date data = new Date();
		String formatData = new SimpleDateFormat("dd-MM-YYYY").format(data);
		
		MysqlDumputils.dump("C:/bd_santarosa/bd_santarosa_"+formatData+".sql", "root", "root", "bd_santarosa", "") ;		
//		String commando = FacesContext.getCurrentInstance().getExternalContext().getRealPath("" +"/WEB-INF/classes/backup/mysql.bat");
//		 String line;
//	      Process p = Runtime.getRuntime().exec(commando);
//	      BufferedReader input =new BufferedReader(new InputStreamReader(p.getInputStream()));
//	      while ((line = input.readLine()) != null) {
//	        System.out.println(line);
//	      }
//	      input.close();
//	      FacesUtil.addInfoMessage("O arquivo de backup encontra-se na raiz do computador na pasta bd_santarosa com a data de hoje!");
//		
	}

	public void restoreBackup() throws IOException {
		
		MysqlDumputils.restore("C:/bd_santarosa/"+getArquivo(), "localhost", "3306", "root", "root", "bd_santarosa");
		
	}

	public String getArquivo() {
		return arquivo;
	}

	public void setArquivo(String arquivo) {
		this.arquivo = arquivo;
	}

	
	
}
