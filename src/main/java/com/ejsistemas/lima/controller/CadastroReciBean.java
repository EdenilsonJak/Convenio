package com.ejsistemas.lima.controller;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import com.ejsistemas.lima.modelo.Contas;
import com.ejsistemas.lima.modelo.Convenio;
import com.ejsistemas.lima.repository.ConvenioRepository;
import com.ejsistemas.lima.service.CadastrarConvenioService;
import com.ejsistemas.lima.service.CadastrarReciboService;
import com.ejsistemas.lima.util.jsf.FacesUtil;
import com.ejsistemas.lima.util.report.ExecutorRelatorio;

@Named
@ViewScoped
public class CadastroReciBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Contas conta;
	private Convenio convenio;
	
	@Inject
	private ConvenioRepository convenioRepository;
	
	@Inject
	private CadastrarConvenioService cadastrarConvenioService;
	
	@Inject
	private CadastrarReciboService cadastrarReciboService;
	
	@Inject
	private FacesContext facesContext;
	
	@Inject
	private HttpServletResponse response;
	
	@Inject
	private EntityManager manager;
	
	private boolean postback = false;
	
	
	public CadastroReciBean() {
		limpar();
		
	}
	
	public void inicializar() {
		
		if(FacesUtil.isNotPostback()){
			setConvenio(getConta().getContrato());
			if(this.postback == false){
				getConvenio().getContas().clear();
				this.postback = true;
			}
		}
		
	}


	public void limpar(){
		conta = new Contas();
		convenio = new Convenio();
	}
	
	
	public Contas getConta() {
		return conta;
	}


	public void setConta(Contas conta) {
		this.conta = conta;
	}


	public Convenio getConvenio() {
		return convenio;
	}


	public void setConvenio(Convenio convenio) {
		this.convenio = convenio;
	}
	
	
	public void consultaPorId(){
		this.conta.setContrato(convenioRepository.porId(convenio.getId_contrato()));
		
		if(this.conta.getContrato() == null){
			FacesUtil.addErrorMessage("Convênio não existe!");
			limpar();
		}
		else{
			this.conta.getContrato().getContas().clear();
		}
		
		if (this.conta.getContrato().isEmitido()) {
			
		} else {
			FacesUtil.addErrorMessage("este serviço só pode ser realizado com Convênios emitidos!!");
			limpar();
		}	
		
		
	}
	
	public void incluirRecibo(){
		
		if(this.conta.getContrato().getId_contrato() != null){
			//this.conta.setContrato(getConvenio());
			
			
			
			if(this.conta.getVlr().equals(new BigDecimal("0.00"))){
				FacesUtil.addErrorMessage("Insira valor do Recibo");
			}
			
			else {
				this.conta.setDt_emissao(new Date());
				this.setConvenio(this.conta.getContrato());
				this.conta = cadastrarReciboService.salvar(this.conta);
				if(FacesUtil.isPostback()){
					if(this.postback == false){
						getConvenio().getContas().clear();
						this.postback = true;
					}				
					
				}
	
				getConvenio().getContas().add(conta);
				this.conta = new Contas();
				this.conta.setContrato(this.getConvenio());
				
			}
			
		}
		else{
			FacesUtil.addErrorMessage("Adicione um convênio para incluir recibos!");
		}
		
		
	}
	
	public void salvar(){
		if(convenio != new Convenio()){
		this.convenio = cadastrarConvenioService.salvar(convenio);
		FacesUtil.addInfoMessage("Recibos gerados com sucesso!");
		limpar();
		
		}
		
	}
	
	public void removerRecibo(Contas item, int linha){
		cadastrarReciboService.excluir(item);
		FacesUtil.addInfoMessage("Recibo excluido com sucesso!");
		this.getConvenio().getContas().remove(linha);		
	}
	
	
	public void imprimirRecibo(Long codigo) {

		Map<String, Object> parametros = new HashMap<String, Object>();
		//String image = "/resources/images/logo.jpg";

		
		parametros.put("codigo", codigo);
		//parametros.put("logo", logo);
		parametros.put("logo", facesContext.getExternalContext().getResourceAsStream("/resources/images/logo.jpg"));
		parametros.put("logo1", facesContext.getExternalContext().getResourceAsStream("/resources/images/logo.jpg"));
		//parametros.put("idade", paciente.getCalculaIdade());

		ExecutorRelatorio executor = new ExecutorRelatorio("/relatorios/Recibo_Convenio.jasper", "Recibo.pdf",
				this.response, parametros);

		Session session = manager.unwrap(Session.class);
		session.doWork(executor);

		if (executor.isRelatorioGerado()) {
			facesContext.responseComplete();
		} else {
			FacesUtil.addErrorMessage("A execução do relatório não retornou dados.");
		}

	}
	
	
	public void entidadeOutroForm(Convenio convenio) throws IOException{
		if(convenio != null){
			
		this.convenio = convenio;
		consultaPorId();
			
		}
		FacesContext.getCurrentInstance().getExternalContext().dispatch("/SantaRosa/recibo/CadastroRecibo.xhtml");
	}


}
