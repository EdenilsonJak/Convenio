package com.ejsistemas.lima.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ejsistemas.lima.modelo.Cliente;
import com.ejsistemas.lima.repository.filter.ClienteFilter;


public class ClienteRepository implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;
	
	public Cliente guardar(Cliente paciente){
		return manager.merge(paciente);
	}
	
	@SuppressWarnings("unchecked")
	public List<Cliente> porNome(String nome){
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Cliente.class);
		
		Criterion name = Restrictions.like("nome", nome, MatchMode.ANYWHERE);
		Criterion cpf = Restrictions.like("cpf", nome, MatchMode.ANYWHERE);
		LogicalExpression orExp = Restrictions.or(name, cpf);
		criteria.add(orExp);
		
		return criteria.addOrder(Order.asc("id_cliente")).list();
		
	}

	public Cliente porId(Long id) {
		return this.manager.find(Cliente.class, id);
		
	}
	
	
	public Cliente porCPF(String cpf){
		
		try{
			return this.manager.createQuery("from Cliente where upper(cpf) like :cpf", Cliente.class)
					   .setParameter("cpf", cpf.toUpperCase()).getSingleResult();
		}
		catch(NoResultException e){
			return null;	
		}
		
	}

	@SuppressWarnings("unchecked")
	public List<Cliente> filtrados(ClienteFilter pacienteFilter) {
		
		
			Session session = manager.unwrap(Session.class);
			Criteria criteria = session.createCriteria(Cliente.class);
			
			if (pacienteFilter.getDataCadastroDe() != null) {
				criteria.add(Restrictions.ge("data", pacienteFilter.getDataCadastroDe()));
			}
			
			if (pacienteFilter.getDataCadastroAte() != null) {
				criteria.add(Restrictions.le("data", pacienteFilter.getDataCadastroAte()));
			}
			
			if(pacienteFilter.getDataNascimento() != null){
				criteria.add(Restrictions.ge("dt_nascimento", pacienteFilter.getDataNascimento()));
			}
			
			if(StringUtils.isNotBlank(pacienteFilter.getNome())){
				criteria.add(Restrictions.ilike("nome", pacienteFilter.getNome(), MatchMode.ANYWHERE));
				
				
			}
			
			if(StringUtils.isNotBlank(pacienteFilter.getProfissao())){
				criteria.add(Restrictions.ilike("profissao", pacienteFilter.getProfissao(), MatchMode.ANYWHERE));
			}
		
			return criteria.addOrder(Order.asc("id_cliente")).list();
			
		}
		
		
		
}
