package com.ejsistemas.lima.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ejsistemas.lima.modelo.Contas;
import com.ejsistemas.lima.repository.filter.ReciboFilter;

public class ReciboRepository implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Contas guardar(Contas recibo) {
		return this.manager.merge(recibo);
	}
	
	public void remover(Contas recibo){
		Session session = manager.unwrap(Session.class);
		Query query = session.createQuery("delete Contas where id_recibo = :cod")
				.setParameter("cod", recibo.getId_recibo());
		
		int result = query.executeUpdate();
	}
	
	public Contas porId(Long id){
		return this.manager.find(Contas.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Contas> filtrados(Contas recibo) {
		Session session = this.manager.unwrap(Session.class);

		Criteria criteria = session.createCriteria(Contas.class)
				// fazemos uma associação (join) com tipolicitacao e nomeamos
				// como "t"
				// .createAlias("tipolicitacao", "t")
				// fazemos uma associação (join) com modalidade e nomeamos
				// como "m"
				// .createAlias("modalidade", "m");

				// fazemos uma associa��o (join) com Convenio e nomeamos como
				// "c"

				.createAlias("contrato", "c");

		// if (recibo.getDt_emissao()!= null) {
		// id deve ser maior ou igual (ge = greater or equals) a filtro.numeroDe
		// criteria.add(Restrictions.le("dt_emissao", recibo.getDt_emissao()));
		// }

		// if (recibo.getDt_vencimento()!= null) {
		// (le = lower or equal) a filtro.numeroDe
		// criteria.add(Restrictions.le("dt_vencimento",
		// recibo.getDt_vencimento()));
		// }

		// if (recibo.getDt_pgto() != null) {
		// criteria.add(Restrictions.le("dt_pgto", recibo.getDt_pgto()));
		// }

		if (recibo.getContrato().getId_contrato() != null) {
			criteria.add(Restrictions.eq("c.id_contrato", recibo.getContrato().getId_contrato()));
		}

		// if (filtro.getProcesso() != ""){
		// criteria.add(Restrictions.le("txt_processo", filtro.getProcesso()));
		// }

		// if (StringUtils.isNotBlank(filtro.getTipoLicitacao())) {
		// acessamos o nome do cliente associado ao pedido pelo alias "t",
		// criado anteriormente
		// criteria.add(Restrictions.ilike("t.nome", filtro.getTipoLicitacao(),
		// MatchMode.ANYWHERE));
		// }

		// if (StringUtils.isNotBlank(filtro.getNomeModalidade())) {
		// acessamos o nome da modalidade associado a licitacao pelo alias "m",
		// criado anteriormente
		// criteria.add(Restrictions.ilike("m.nm_modalidade",
		// filtro.getNomeModalidade(), MatchMode.ANYWHERE));
		// }

		// if (filtro.getStatuses() != null && filtro.getStatuses().length > 0)
		// {
		// adicionamos uma restrição "in", passando um array de constantes da
		// enum StatusPedido
		// criteria.add(Restrictions.in("status", filtro.getStatuses()));
		// }

		return criteria.list();
	}

	@SuppressWarnings("unchecked") 
	public List<Contas> filter(ReciboFilter filtro) {
		Session session = this.manager.unwrap(Session.class);

		Criteria criteria = session.createCriteria(Contas.class)

				.createAlias("contrato.cliente", "c");

		if (filtro.getDatade() != null) {
			criteria.add(Restrictions.ge("dt_emissao", filtro.getDatade()));
		}
		if (filtro.getDateate() != null) {
			criteria.add(Restrictions.le("dt_emissao", filtro.getDateate()));
		}
		if(filtro.getVencimento() != null){
			criteria.add(Restrictions.ge("dt_vencimento", filtro.getVencimento()));
		}
		if(filtro.getVencimentoate() != null){
			criteria.add(Restrictions.le("dt_vencimento", filtro.getVencimentoate()));
		}

		if (filtro.getCliente() != null) {
			criteria.add(Restrictions.ilike("c.nome", "%"+filtro.getCliente().getNome()+"%"));
		}
		if(filtro.getStatus() != ""){
			criteria.add(Restrictions.ilike("status", filtro.getStatus()));
		}

		return criteria.addOrder(Order.asc("dt_vencimento")).list();

	}

}
