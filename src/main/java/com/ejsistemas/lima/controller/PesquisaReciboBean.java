package com.ejsistemas.lima.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import com.ejsistemas.lima.modelo.Cliente;
import com.ejsistemas.lima.modelo.Contas;
import com.ejsistemas.lima.repository.ClienteRepository;
import com.ejsistemas.lima.repository.ReciboRepository;
import com.ejsistemas.lima.repository.filter.ReciboFilter;
import com.ejsistemas.lima.service.CadastrarReciboService;
import com.ejsistemas.lima.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaReciboBean implements Serializable {

	private static final long serialVersionUID = 1L;

	// private Cliente cliente;

	private Contas recibo;
	BigDecimal totalRecibos = BigDecimal.ZERO;
	BigDecimal totalRecibosPagos = BigDecimal.ZERO;
	BigDecimal totalRecibosPendentes = BigDecimal.ZERO;

	@Inject
	private ReciboRepository reciboRepository;

	@Inject
	private ClienteRepository clienteRepository;

	@Inject
	private CadastrarReciboService cadastrarReciboService;

	private ReciboFilter filtro;
	private List<Contas> contasFiltrados;

	public PesquisaReciboBean() {
		limpar();
	}

	public void limpar() {
		// this.cliente = new Cliente();
		recibo = new Contas();
		filtro = new ReciboFilter();
		// filtro.setCliente(new Cliente());
		contasFiltrados = new ArrayList<>();
	}

	public ReciboFilter getFiltro() {
		return filtro;
	}

	public List<Contas> getContasFiltrados() {
		return contasFiltrados;
	}

	// public Cliente getCliente() {
	// return cliente;
	// }
	//
	// public void setCliente(Cliente cliente) {
	// this.cliente = cliente;
	// }

	public Contas getRecibo() {
		return recibo;
	}

	public void setRecibo(Contas recibo) {
		this.recibo = recibo;
	}

	public void pesquisar() {
		// filtro.setCliente(cliente.getNome());
		contasFiltrados = reciboRepository.filter(filtro);
		this.totalRecibos = BigDecimal.ZERO;
		this.totalRecibosPendentes = BigDecimal.ZERO;
		this.totalRecibosPagos = BigDecimal.ZERO;
		if(contasFiltrados.iterator().hasNext()){
			
			for(Contas contas : contasFiltrados){
				setTotalRecibos(getTotalRecibos().add(contas.getVlr()));
				if(contas.getStatus().equals("Pendente")){
					setTotalRecibosPendentes(getTotalRecibosPendentes().add(contas.getVlr()));
				}
				else{
					setTotalRecibosPagos(getTotalRecibosPagos().add(contas.getVlrPago()));
				}
				
			}
		}
		
		
	}

	public void salvar() {

		if (recibo.getDt_pgto() != null) {
			if (recibo.getVlrPago().equals(new BigDecimal("0.00"))) {
				FacesUtil.addErrorMessage("Informe o valor à ser pago!");	
			}
			else{				
				this.recibo.setStatus("Pago");
				this.recibo = cadastrarReciboService.salvar(recibo);
				this.recibo = new Contas();
				
				RequestContext request = RequestContext.getCurrentInstance();
				request.addCallbackParam("sucesso", true);
			}

		} else {
			FacesUtil.addErrorMessage("Por favor, informe data de pagamento!");
		}
		// this.recibo.setDt_pgto(new Date());

	}

	public List<Cliente> completarCliente(String nome) {
		return this.clienteRepository.porNome(nome);
	}

	public BigDecimal getTotalRecibos() {
		return totalRecibos;
	}

	public void setTotalRecibos(BigDecimal totalRecibos) {
		this.totalRecibos = totalRecibos;
	}

	public BigDecimal getTotalRecibosPagos() {
		return totalRecibosPagos;
	}

	public BigDecimal getTotalRecibosPendentes() {
		return totalRecibosPendentes;
	}

	public void setTotalRecibosPagos(BigDecimal totalRecibosPagos) {
		this.totalRecibosPagos = totalRecibosPagos;
	}

	public void setTotalRecibosPendentes(BigDecimal totalRecibosPendentes) {
		this.totalRecibosPendentes = totalRecibosPendentes;
	}
	
	

	
	
}
