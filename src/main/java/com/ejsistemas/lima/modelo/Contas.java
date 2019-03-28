package com.ejsistemas.lima.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tb_recibo")
public class Contas implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long id_recibo;
	private Date dt_emissao;
	private Date dt_vencimento;
	private Date dt_pgto;
	private BigDecimal vlrPago = BigDecimal.ZERO;
	private BigDecimal vlr = BigDecimal.ZERO;
	private String status = "Pendente";
	private String obs;
	private Convenio contrato = new Convenio();
	private String tipopgto;
	
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	public Long getId_recibo() {
		return id_recibo;
	}
	public void setId_recibo(Long id_recibo) {
		this.id_recibo = id_recibo;
	}
	
	@Column
	@Temporal(TemporalType.DATE)
	public Date getDt_emissao() {
		return dt_emissao;
	}
	public void setDt_emissao(Date dt_emissao) {
		this.dt_emissao = dt_emissao;
	}
	
	@NotNull
	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	public Date getDt_vencimento() {
		return dt_vencimento;
	}
	public void setDt_vencimento(Date dt_vencimento) {
		this.dt_vencimento = dt_vencimento;
	}
	
	@Column
	@Temporal(TemporalType.DATE)
	public Date getDt_pgto() {
		return dt_pgto;
	}
	public void setDt_pgto(Date dt_pgto) {
		this.dt_pgto = dt_pgto;
	}
	
	
	@Column(precision = 10, scale = 2)
	public BigDecimal getVlrPago() {
		return vlrPago;
	}
	public void setVlrPago(BigDecimal vlrPago) {
		this.vlrPago = vlrPago;
	}
	
	
	@Column(precision = 10, scale = 2)
	public BigDecimal getVlr() {
		return vlr;
	}
	public void setVlr(BigDecimal vlr) {
		this.vlr = vlr;
	}
	
	@Column(nullable = false)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column
	public String getObs() {
		return obs;
	}
	public void setObs(String obs) {
		this.obs = obs;
	}
	
	@Column(length = 200)	
	public String getTipopgto() {
		return tipopgto;
	}
	public void setTipopgto(String tipopgto) {
		this.tipopgto = tipopgto;
	}
	@ManyToOne()
	@JoinColumn(name= "convenio_id")
	public Convenio getContrato() {
		return contrato;
	}
	public void setContrato(Convenio contrato) {
		this.contrato = contrato;
	}
	
	@Transient
	public boolean isPago(){
		return this.getStatus().equals("Pago");
	}
	
	
	@Transient
	public boolean isPendente(){
		return this.getStatus().equals("Pendente");
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id_recibo == null) ? 0 : id_recibo.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Contas other = (Contas) obj;
		if (id_recibo == null) {
			if (other.id_recibo != null)
				return false;
		} else if (!id_recibo.equals(other.id_recibo))
			return false;
		return true;
	}
	
	
	
}
