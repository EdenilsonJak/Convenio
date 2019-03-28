package com.ejsistemas.lima.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.ejsistemas.lima.modelo.Cliente;
import com.ejsistemas.lima.repository.ClienteRepository;
import com.ejsistemas.lima.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = Cliente.class)
public class ClienteConverter implements Converter {
	
	private ClienteRepository PacienteRepository;
	
	public ClienteConverter() {
		PacienteRepository = (ClienteRepository)CDIServiceLocator.getBean(ClienteRepository.class);
	}

	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		
		Cliente retorno = null;
		if(value != null){
			Long id = new Long(value);
			retorno = PacienteRepository.porId(id);
		}
			return retorno;
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value != null){
			Cliente paciente = (Cliente) value;
 			return paciente.getId_cliente()== null ? null : paciente.getId_cliente().toString();
		}
		return "";
	}

}
