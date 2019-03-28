package com.ejsistemas.lima.controller;

import java.io.Serializable;
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

import com.ejsistemas.lima.modelo.Cliente;
import com.ejsistemas.lima.modelo.Dependente;
import com.ejsistemas.lima.repository.ClienteRepository;
import com.ejsistemas.lima.service.CadastrarClienteService;
import com.ejsistemas.lima.service.WebServiceCep;
import com.ejsistemas.lima.util.jsf.FacesUtil;
import com.ejsistemas.lima.util.report.ExecutorRelatorio;

@Named
@ViewScoped
public class CadastroClienteBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Cliente cliente;
	private Dependente dependente;
	private int activeIndex;
	private WebServiceCep webServiceCep;

	// private List<Convenio> convenioRazies;

	@Inject
	private FacesContext facesContext;

	@Inject
	CadastrarClienteService clienteService;
	
	@Inject
	ClienteRepository repository;

	private HttpServletResponse response;

	@Inject
	private EntityManager manager;

	public CadastroClienteBean() {
		limpar();
	}

	public void inicializar() {
		// this.convenioRazies = convenioRepository.raizes();
		// estadosRaizes = estados.raizes();
	}

	public void salvar() {

		// System.out.print("Convenio selecionado"
		// +this.cliente.getConvenio().getConvenio());
		Cliente resultado = repository.porCPF(this.cliente.getCpf());
		
			if(this.cliente.getId_cliente() != null){
				
				this.cliente = clienteService.salvar(cliente);
			}
			else{
				if(resultado != null){
					FacesUtil.addErrorMessage("Cliente com CPF já Cadastrado!");
				}
				else{
				this.cliente.setData(new Date());
				this.cliente = clienteService.salvar(cliente);
			}
		}
			
	}	
	
	public void consultaCep(){
		webServiceCep = WebServiceCep.searchCep(this.cliente.getCep());
		
		if(webServiceCep.wasSuccessful()){
			this.cliente.setTipoLogradouro(webServiceCep.getLogradouroType());
			this.cliente.setLogradouro(webServiceCep.getLogradouro());
			this.cliente.setBairro(webServiceCep.getBairro());
			this.cliente.setCidade(webServiceCep.getCidade());
			this.cliente.setUf(webServiceCep.getUf());
		}
		else{
			FacesUtil.addErrorMessage("Cep Incorreto!");
		}
		
	}
	
	public void confirmarDependente() {
		if(this.dependente.getNome() != ""){
			
			this.dependente.setPaciente(getCliente());
			this.cliente.getDependentes().add(dependente);

			this.dependente = new Dependente();

		}else{
			FacesUtil.addErrorMessage("Por favor, Informe o nome do Dependente");
		}
	}
	
	
	public void removerDependente(Dependente item, int linha){
		this.cliente.getDependentes().remove(linha);
		
				
	}

	private void limpar() {
		dependente = new Dependente();
		cliente = new Cliente();

	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Dependente getDependente() {
		return dependente;
	}

	public void setDependente(Dependente dependente) {
		this.dependente = dependente;
	}

	public boolean isEditando() {
		return this.cliente.getId_cliente() != null;
	}

	public void imprimirEtiqueta() {

		Map<String, Object> parametros = new HashMap<String, Object>();

		parametros.put("cdcliente", cliente.getId_cliente());

		ExecutorRelatorio executor = new ExecutorRelatorio("/relatorios/etiquetagem.jasper", "Etiqueta.pdf",
				this.response, parametros);

		Session session = manager.unwrap(Session.class);
		session.doWork(executor);

		if (executor.isRelatorioGerado()) {
			facesContext.responseComplete();
		} else {
			FacesUtil.addErrorMessage("A execução do relatório não retornou dados.");
		}

	}

	public void imprimirEtiqueta2() {

		Map<String, Object> parametros = new HashMap<String, Object>();

		parametros.put("cdcliente", cliente.getId_cliente());
		// parametros.put("idade", cliente.getCalculaIdade());

		ExecutorRelatorio executor = new ExecutorRelatorio("/relatorios/Etique-Nome.jasper", "Etiqueta.pdf",
				this.response, parametros);

		Session session = manager.unwrap(Session.class);
		session.doWork(executor);

		if (executor.isRelatorioGerado()) {
			facesContext.responseComplete();
		} else {
			FacesUtil.addErrorMessage("A execução do relatório não retornou dados.");
		}

	}

	public void imprimirEtiqueta3(Long id) {

		Map<String, Object> parametros = new HashMap<String, Object>();

		parametros.put("cdcliente", id);
		// parametros.put("idade", cliente.getCalculaIdade());

		ExecutorRelatorio executor = new ExecutorRelatorio("/relatorios/Etique-Nome.jasper", "Etiqueta.pdf",
				this.response, parametros);

		Session session = manager.unwrap(Session.class);
		session.doWork(executor);

		if (executor.isRelatorioGerado()) {
			facesContext.responseComplete();
		} else {
			FacesUtil.addErrorMessage("A execução do relatório não retornou dados.");
		}

	}

	public int getActiveIndex() {
		return activeIndex;
	}

	public void setActiveIndex(int activeIndex) {
		this.activeIndex = activeIndex;
	}

	public void goToTab(int index){
		this.activeIndex = index;
	}
	
}
