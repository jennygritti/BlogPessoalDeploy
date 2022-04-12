package com.generation.blogpessoal.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import com.generation.blogpessoal.model.Usuario;
import com.generation.blogpessoal.repository.UsuarioRepository;

import java.util.Optional;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioRepositoryTeste {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@BeforeAll
	void start() {
		usuarioRepository.save(new Usuario(0L, "Cleiton Borges", "cleitinho@pedra.com", "cabecadegelo", "cleiton.jpeg"));;
		usuarioRepository.save(new Usuario(0L, "Laurinha Lero", "lalau@lero.com", "lerolero", "laura.jpeg"));
		usuarioRepository.save(new Usuario(0L, "Edinaldo Pereira", "ednaldo@banido.com", "banido", "naldo.jpeg"));
		usuarioRepository.save(new Usuario(0L, "Mc Naninha", "naninha@imperiobronze.com", "nananenem", "naninha.jpeg"));
	}
	
	@Test
	@DisplayName("Retorna apenas um usuario")
	public void deveRetornarUmUsuario() {
		Optional<Usuario> usuario = usuarioRepository.findByUsuario("naninha@imperiobronze.com");
		assertTrue(usuario.get().getUsuario().equals("naninha@imperiobronze.com"));

	}
	
	@Test
	@DisplayName("Retorna apenas um usuario")
	public void deveRetornarDoisUsuarios() {
		List<Usuario> listaDeUsuarios = usuarioRepository.findAllByNomeContainingIgnoreCase("Cleiton");
		assertEquals(2, listaDeUsuarios.size());
		
		assertTrue(listaDeUsuarios.get(0).getNome().equals("Cleiton Borges"));
		assertTrue(listaDeUsuarios.get(1).getNome().equals("Laurinha Lero"));

	}
}
