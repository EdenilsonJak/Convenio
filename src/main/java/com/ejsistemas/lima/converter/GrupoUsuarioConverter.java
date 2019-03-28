package com.ejsistemas.lima.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.ejsistemas.lima.modelo.Grupo_usuario;
import com.ejsistemas.lima.repository.Grupo_usuarioRepository;
import com.ejsistemas.lima.util.cdi.CDIServiceLocator;

@FacesConverter(forClass=Grupo_usuario.class)
public class GrupoUsuarioConverter implements Converter {

	private Grupo_usuarioRepository grupo_usuarioRepository;
	
	public GrupoUsuarioConverter(){
		this.grupo_usuarioRepository = (Grupo_usuarioRepository) CDIServiceLocator.getBean(Grupo_usuarioRepository.class);
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Grupo_usuario retorno = null;

		if (value != null) {
			retorno = this.grupo_usuarioRepository.porId(new Long(value));
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			return ((Grupo_usuario) value).getId().toString();
		}
		return "";
	}

}
