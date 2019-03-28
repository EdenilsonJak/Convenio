package com.ejsistemas.lima.util.security;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Named
@RequestScoped
public class Seguranca {
	
	@Inject
	private ExternalContext externalContext;
	
	public String getNomeUsuario(){
		String nome = null;
		
		UsuarioSistema usuarioLogado = getUsuarioLogado();
		
		if(usuarioLogado != null){
		nome = usuarioLogado.getUsuario().getNome();
		}
		return nome;
	}

	private UsuarioSistema getUsuarioLogado() {
		UsuarioSistema usuario = null;
		
		UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken)
				FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
		
		if(auth != null && auth.getPrincipal() != null){
			usuario = (UsuarioSistema) auth.getPrincipal();
		}
		
		return usuario;
	}

	public boolean isEmitirLicitacaoPermitido(){
		return externalContext.isUserInRole("ADMINISTRADORES")
			|| externalContext.isUserInRole("VENDEDORES")
			|| externalContext.isUserInRole("REQUISITORES");
	}
	
	public boolean isCancelarLicitacaoPermitido(){
		return externalContext.isUserInRole("ADMINISTRADORES")
			|| externalContext.isUserInRole("VENDEDORES")
			|| externalContext.isUserInRole("REQUISITORES");
	}
	
	public boolean isEmitirRequisicaoPermitido(){
		return externalContext.isUserInRole("ADMINISTRADORES")
				|| externalContext.isUserInRole("VENDEDORES")
				|| externalContext.isUserInRole("REQUISITORES");
	}
	
	public boolean isCancelarRequisicaoPermitido(){
		return externalContext.isUserInRole("ADMINISTRADORES")
				|| externalContext.isUserInRole("VENDEDORES")
				|| externalContext.isUserInRole("REQUISITORES");
	}
	
	public boolean isCadastroUsuarios(){
		return externalContext.isUserInRole("ADMINISTRADORES")
				|| externalContext.isUserInRole("REQUISITORES");
	}
	
}
