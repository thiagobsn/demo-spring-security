package com.example.demo.acesso.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.demo.acesso.repository.entity.Grupo;
import com.example.demo.repository.entity.Usuario;

@Mapper
public interface GrupoMapper {

	@Select("select g.id_grupo as id, g.nome, g.descricao \r\n"
			+ "from public.usuario_grupo gu\r\n"
			+ "join public.grupo g on g.id_grupo = gu.id_usuario_grupo\r\n"
			+ "join public.usuario u on u.id_usuario = gu.usuario_id\r\n"
			+ "where u.username = #{usuario.username}"
			+ "and u.id_usuario = #{usuario.id}")
	public List<Grupo> listarPorUsername(@Param(value = "usuario") Usuario usuario);
}
