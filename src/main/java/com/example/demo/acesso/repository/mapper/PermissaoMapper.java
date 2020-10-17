package com.example.demo.acesso.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.demo.acesso.repository.entity.Grupo;
import com.example.demo.acesso.repository.entity.Permissao;

@Mapper
public interface PermissaoMapper {
	
	@Select("SELECT p.id_permissao as id, p.nome\r\n"
			+ "	FROM public.grupo_permissao gp\r\n"
			+ "	join public.grupo g on g.id_grupo = gp.grupos_id \r\n"
			+ "	join public.permissao p on p.id_permissao = gp.permissoes_id\r\n"
			+ "	where g.nome = #{grupo.nome}")
	public List<Permissao> listarPorGrupo(@Param(value = "grupo") Grupo grupo);

}
