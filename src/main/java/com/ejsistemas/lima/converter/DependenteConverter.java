package com.ejsistemas.lima.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.ejsistemas.lima.modelo.Dependente;
import com.ejsistemas.lima.repository.DependenteRepository;
import com.ejsistemas.lima.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = Dependente.class)
public class DependenteConverter implements Converter {

	private DependenteRepository dependenteRepository;

	public DependenteConverter() {
		dependenteRepository = (DependenteRepository) CDIServiceLocator.getBean(DependenteRepository.class);
	}

	public Object getAsObject(FacesContext context, UIComponent component, String value) {

		Dependente retorno = null;
		if (value != null) {
			Long id = new Long(value);
			retorno = dependenteRepository.porId(id);
		}
		return retorno;
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value != null){
			Dependente dependente = (Dependente) value;
			return dependente.getId_dependente() == null ? null : dependente.getId_dependente().toString();
		}
		return "";
	}

}
