package com.ejsistemas.lima.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.ejsistemas.lima.modelo.Contas;
import com.ejsistemas.lima.repository.ReciboRepository;
import com.ejsistemas.lima.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = Contas.class)
public class ContaVonverter implements Converter {
	
	private ReciboRepository reciboRepository;
	
	public ContaVonverter() {
		reciboRepository = (ReciboRepository)CDIServiceLocator.getBean(ReciboRepository.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {

		Contas retorno = null;
		if(value != null){
			Long id = new Long(value);
			retorno = reciboRepository.porId(id);
			
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value != null){
			Contas conta = (Contas) value;
 			return conta.getId_recibo() == null ? null : conta.getId_recibo().toString();
		}
		return "";
	}
}