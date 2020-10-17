package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.acesso.repository.entity.Grupo;
import com.example.demo.acesso.repository.entity.Permissao;
import com.example.demo.acesso.repository.entity.UsuarioSistema;
import com.example.demo.acesso.service.GrupoService;
import com.example.demo.acesso.service.PermissaoService;
import com.example.demo.conf.AcessoUserDetailsService;
import com.example.demo.conf.JwtTokenUtil;
import com.example.demo.repository.entity.JwtRequest;
import com.example.demo.repository.entity.JwtResponse;
import com.example.demo.repository.entity.Usuario;
import com.example.demo.service.UsuarioService;

@Controller
@RequestMapping("api/autenticacao")
public class AutenticacaoApiController {
	
	final private UsuarioService autenticacaoService;
	final private GrupoService grupoService;
	final private PermissaoService permissaoService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private AcessoUserDetailsService acessoUserDetailsService;
//	private JwtUserDetailsService userDetailsService;
	
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
		
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		
		final UsuarioSistema usuarioSistema = (UsuarioSistema) acessoUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		final String token = jwtTokenUtil.generateToken(usuarioSistema);
		
		return ResponseEntity.ok(new JwtResponse(token));
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
	
	
	
	
	@Autowired
	public AutenticacaoApiController(UsuarioService autenticacaoService, GrupoService grupoService, PermissaoService permissaoService) {
		this.autenticacaoService = autenticacaoService;
		this.grupoService = grupoService;
		this.permissaoService = permissaoService;
	}
	
	
	@GetMapping("/usuarios")
	public ResponseEntity<List<Usuario>> listaUsuarios() {
		return new ResponseEntity<List<Usuario>>(autenticacaoService.listarUsuarios(),HttpStatus.OK);
	}
	
	@GetMapping("/grupos")
	public ResponseEntity<List<Grupo>> listaGrupos() {
		Usuario usuario = new Usuario();
		usuario.setUsername("thiagobsn");
		usuario.setId(2);
		return new ResponseEntity<List<Grupo>>(grupoService.listarPorUsername(usuario),HttpStatus.OK);
	}
	
	@GetMapping("/permissoes")
	public ResponseEntity<List<Permissao>> listaPermissoes() {
		Usuario usuario = new Usuario();
		usuario.setUsername("thiagobsn");
		usuario.setId(2);
		ArrayList<Grupo> listaGrupos = (ArrayList<Grupo>) grupoService.listarPorUsername(usuario);
		ArrayList<Permissao> listaPermissoes = new ArrayList<>();
		for(Grupo grupo : listaGrupos) {
			listaPermissoes.addAll(permissaoService.listarPorGrupo(grupo));
		}
		return new ResponseEntity<List<Permissao>>(listaPermissoes,HttpStatus.OK);
	}
	
	

}
