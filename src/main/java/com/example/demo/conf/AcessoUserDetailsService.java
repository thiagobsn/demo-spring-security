package com.example.demo.conf;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.demo.acesso.repository.entity.Grupo;
import com.example.demo.acesso.repository.entity.Permissao;
import com.example.demo.acesso.repository.entity.UsuarioSistema;
import com.example.demo.acesso.service.GrupoService;
import com.example.demo.acesso.service.PermissaoService;
import com.example.demo.repository.entity.Usuario;
import com.example.demo.service.UsuarioService;

@Component
public class AcessoUserDetailsService implements UserDetailsService {
	
	final private UsuarioService autenticacaoService;
	final private GrupoService grupoService;
	final private PermissaoService permissaoService;
	
	@Autowired
	public AcessoUserDetailsService(UsuarioService autenticacaoService, GrupoService grupoService, PermissaoService permissaoService) {
		this.autenticacaoService = autenticacaoService;
		this.grupoService = grupoService;
		this.permissaoService = permissaoService;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = autenticacaoService.buscarProUsername(username);
		if(usuario == null) {
			throw new UsernameNotFoundException("Usuario não existe");
		}
		return new UsuarioSistema(usuario.getNome(), usuario.getUsername(), usuario.getSenha(), authorities(usuario));
	}
	
	public Collection<? extends GrantedAuthority> authorities(Usuario usuario) {
	    return authorities(grupoService.listarPorUsername(usuario));
	  }
	   
	  public Collection<? extends GrantedAuthority> authorities(List<Grupo> grupos) {
	    Collection<GrantedAuthority> auths = new ArrayList<>();
	     
	    for (Grupo grupo: grupos) {
	      List<Permissao> lista = permissaoService.listarPorGrupo(grupo);
	     
	      for (Permissao permissao: lista) {
	        auths.add(new SimpleGrantedAuthority("ROLE_" + permissao.getNome()));
	      }
	    }
	     
	    return auths;
	  }

}
