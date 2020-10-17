package com.example.demo.acesso.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.acesso.repository.entity.Grupo;
import com.example.demo.acesso.repository.mapper.GrupoMapper;
import com.example.demo.repository.entity.Usuario;

@Service
public class GrupoService {
	
	final private GrupoMapper grupoMapper;
	
	@Autowired
	public GrupoService(GrupoMapper grupoMapper) {
		this.grupoMapper = grupoMapper;
	}
	
	public List<Grupo> listarPorUsername(Usuario usuario) {
		return grupoMapper.listarPorUsername(usuario);
	}

}
