package com.ejsistemas.lima.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.ejsistemas.lima.modelo.Convenio;
import com.ejsistemas.lima.repository.ConvenioRepository;
import com.ejsistemas.lima.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = Convenio.class)
public class ConvenioConverter implements Converter{

	private ConvenioRepository convenioRepository;
	
	public ConvenioConverter() {
		convenioRepository = (ConvenioRepository)CDIServiceLocator.getBean(ConvenioRepository.class);
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		
		Convenio retorno = null;
		if(value != null){
			Long id = new Long(value);
			retorno = convenioRepository.porId(id);
		}
			return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value != null){
			Convenio convenio = (Convenio) value;
 			return convenio.getId_contrato()== null ? null : convenio.getId_contrato().toString();
		}
		return "";
	}

}
