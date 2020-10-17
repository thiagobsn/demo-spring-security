package com.example.demo.acesso.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.acesso.repository.entity.Grupo;
import com.example.demo.acesso.repository.entity.Permissao;
import com.example.demo.acesso.repository.mapper.PermissaoMapper;

@Service
public class PermissaoService {
	
	final private PermissaoMapper permissaoMapper;
	
	@Autowired
	public PermissaoService(PermissaoMapper permissaoMapper) {
		this.permissaoMapper = permissaoMapper;
	}
	
	public List<Permissao> listarPorGrupo(Grupo grupo) {
		return permissaoMapper.listarPorGrupo(grupo);
	}
	
	

}
