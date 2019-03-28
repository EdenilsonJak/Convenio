package com.ejsistemas.lima.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ejsistemas.lima.modelo.Cliente;
import com.ejsistemas.lima.modelo.Convenio;

public class ConvenioRepository implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Convenio guardar(Convenio convenio) {
		return manager.merge(convenio);
	}

	public Convenio porId(Long id) {
		return this.manager.find(Convenio.class, id);
	}

	public List<Convenio> porID(Long id) {
		return this.manager.createQuery("from Convenio v where upper (v.cliente.id_cliente) =:id order by v.data cres",
				Convenio.class).setParameter("id", id).getResultList();
	}

	public List<Convenio> porCodigo(Long id) {
		return this.manager.createQuery("from Convenio where (id_contrato) like :cod", Convenio.class)
				.setParameter("cod", id).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Convenio> todos() {

		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Convenio.class);

		return criteria.addOrder(Order.asc("id_contrato")).list();

	}
	
	@SuppressWarnings("unchecked")
	public List<Convenio> filtrados(Cliente cliente) {

		Session session = manager.unwrap(Session.class);
		
		Criteria criteria = session.createCriteria(Convenio.class)
		//associação com cliente
			.createAlias("cliente", "c");
		
		if(StringUtils.isNotBlank(cliente.getNome())){
			criteria.add(Restrictions.ilike("c.nome", cliente.getNome(), MatchMode.ANYWHERE));
		}
			

		return criteria.addOrder(Order.asc("id_contrato")).list();

	}

}
