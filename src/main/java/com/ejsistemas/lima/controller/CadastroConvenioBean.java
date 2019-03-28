package com.ejsistemas.lima.controller;

import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import com.ejsistemas.lima.modelo.Cliente;
import com.ejsistemas.lima.modelo.Convenio;
import com.ejsistemas.lima.repository.ClienteRepository;
import com.ejsistemas.lima.repository.ConvenioRepository;
import com.ejsistemas.lima.service.CadastrarConvenioService;
import com.ejsistemas.lima.util.jsf.FacesUtil;
import com.ejsistemas.lima.util.report.ExecutorRelatorio;

@Named
@ViewScoped
public class CadastroConvenioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Convenio convenio;

	@Inject
	CadastrarConvenioService cadastrarConvenioService;

	@Inject
	ClienteRepository clienteRepository;

	@Inject
	ConvenioRepository convenioRepository;
	
	@Inject
	private FacesContext facesContext;
	
	@Inject
	private HttpServletResponse response;
	
	@Inject
	private EntityManager manager;

	private List<Convenio> convenios;

	public CadastroConvenioBean() {
		limpar();
	}

	public void limpar() {
		convenio = new Convenio();
		convenios = new ArrayList<>();
	}

	public void inicializar() {

	}

	public void salvar() {
		int a = 1;

		if (this.convenio.getId_contrato() == null) {
			convenios = convenioRepository.todos();

			for (Convenio convenio : this.convenios) {
				if (convenio.getCliente().getCpf().equals(this.convenio.getCliente().getCpf())) {
					FacesUtil.addErrorMessage("Convênio para este Cliente já existe!");
					a = 0;
					break;

				}
			}
			if (a == 1) {
				this.convenio = cadastrarConvenioService.salvar(convenio);
			}
		}

	}

	public void emitirConvenio() {
		if (this.convenio.isEmissivel()) {
	 				this.convenio.setStatus("Emitido");
					Date data = new Date();
					
					SimpleDateFormat ano = new SimpleDateFormat("yyyy");
					this.convenio.setCodinterno(this.convenio.getId_contrato()+ano.format(data));
					
					
					
					this.convenio = cadastrarConvenioService.salvar(convenio);
				}
			}

	public void cancelarConvenio() {
		if (this.convenio.isEmitido() || this.convenio.isCancelavel()) {
			this.convenio.setStatus("Cancelado");
			this.convenio = cadastrarConvenioService.salvar(convenio);
		}
	}

	public List<Cliente> completarCliente(String nome) {
		List<Cliente> clientes = clienteRepository.porNome(nome);
		if(clientes.size() == 0){
			FacesUtil.addAlerMessage("Cliente não encontrado!");
		}
		return clientes;

	}

	public Convenio getConvenio() {
		return convenio;
	}

	public void setConvenio(Convenio convenio) {
		this.convenio = convenio;
	}

	public void imprimirContrato1(Long codigo) {

		Map<String, Object> parametros = new HashMap<String, Object>();
		//InputStream image1 = this.getClass().getResourceAsStream("/resources/images/logo.jpg");
	//	Image logo = new ImageIcon(getClass().getResource("/resources/images/logo.jpg")).getImage();

		parametros.put("codigo", codigo);
		parametros.put("logo", facesContext.getExternalContext().getResourceAsStream("/resources/images/logo.jpg") );
		//parametros.put("logo", image1);
		// parametros.put("logo",
		// facesContext.getExternalContext().getResourceAsStream("/resources/images/logo.jpg"));
		// parametros.put("idade", paciente.getCalculaIdade());

		ExecutorRelatorio executor = new ExecutorRelatorio("/relatorios/Contrato_001.jasper", "Página 1.pdf",
				this.response, parametros);

		Session session = manager.unwrap(Session.class);
		session.doWork(executor);

		if (executor.isRelatorioGerado()) {
			facesContext.responseComplete();
		} else {
			FacesUtil.addErrorMessage("A execução do relatório não retornou dados.");
		}
	}
	
	public void imprimirContrato2(Long codigo) {

		Map<String, Object> parametros = new HashMap<String, Object>();
		//Image logo1 = new ImageIcon(getClass().getResource("/resources/images/logo.jpg")).getImage();

		parametros.put("cod_contrato", codigo);
		parametros.put("logo", facesContext.getExternalContext().getResourceAsStream("/resources/images/logo.jpg") );
		//parametros.put("logo", logo1);
		// parametros.put("logo",
		// facesContext.getExternalContext().getResourceAsStream("/resources/images/logo.jpg"));
		// parametros.put("idade", paciente.getCalculaIdade());

		ExecutorRelatorio executor = new ExecutorRelatorio("/relatorios/Contrato_002.jasper", "Página 2.pdf",
				this.response, parametros);

		Session session = manager.unwrap(Session.class);
		session.doWork(executor);

		if (executor.isRelatorioGerado()) {
			facesContext.responseComplete();
		} else {
			FacesUtil.addErrorMessage("A execução do relatório não retornou dados.");
		}
	}

}
