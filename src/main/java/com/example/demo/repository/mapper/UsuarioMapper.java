package com.example.demo.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.demo.repository.entity.Usuario;

@Mapper
public interface UsuarioMapper {
	
	@Select("select * from public.usuario")
	public List<Usuario> list();
	
	@Select("select id_usuario as id, username, senha, nome, email, nascimento, ativo "
			+ "from public.usuario "
			+ "where username = #{username}")
	public Usuario buscarProUsername(@Param(value = "username") String username);

}
