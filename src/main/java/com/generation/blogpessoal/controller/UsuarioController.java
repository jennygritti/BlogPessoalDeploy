package com.generation.blogpessoal.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.blogpessoal.model.Usuario;
import com.generation.blogpessoal.model.UsuarioLogin;
import com.generation.blogpessoal.repository.UsuarioRepository;
import com.generation.blogpessoal.service.UsuarioService;

@RestController
@RequestMapping("/usuario")
@CrossOrigin(origins= "*", allowedHeaders= "*") //aceitar qualquer informação

public class UsuarioController {

		@Autowired
		private UsuarioRepository usuarioRepository;
		
		@Autowired
		private UsuarioService usuarioService;
		
		@GetMapping("/all")
		public ResponseEntity<List<Usuario>> getAll(){
			return ResponseEntity.ok(usuarioRepository.findAll());
		}
		
		@PostMapping("/cadastrar")
		public ResponseEntity<Usuario> cadastraUsuario(@Valid @RequestBody Usuario usuario){
			return usuarioService.cadastrarUsuario(usuario)
					.map(resposta -> ResponseEntity.status(HttpStatus.CREATED).body(resposta))
					.orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
		}

		@PostMapping("/logar")
		public ResponseEntity<UsuarioLogin> logarUsuario (@Valid @RequestBody Optional <UsuarioLogin> usuarioLogin){
			return usuarioService.autenticarUsuario(usuarioLogin).map(resposta -> ResponseEntity.ok(resposta))
					.orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
		}
		
		@PutMapping("/atualizar")
		public ResponseEntity<Usuario> putUsuario(@Valid @RequestBody Usuario usuario){
			
			return usuarioService.atualizarUsuario(usuario)
				.map(resposta -> ResponseEntity.status(HttpStatus.OK).body(resposta))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
		}
}
