package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repository.entity.Usuario;
import com.example.demo.repository.mapper.UsuarioMapper;

@Service
public class UsuarioService {
	
	final private UsuarioMapper usuarioMapper;
	
	@Autowired
	public UsuarioService(UsuarioMapper autenticacaoMapper) {
		this.usuarioMapper = autenticacaoMapper;
	}
	
	public List<Usuario> listarUsuarios(){
		return usuarioMapper.list();
	}
	
	public Usuario buscarProUsername(String username) {
		return usuarioMapper.buscarProUsername(username);
	}

}
