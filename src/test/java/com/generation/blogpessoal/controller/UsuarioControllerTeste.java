package com.generation.blogpessoal.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.generation.blogpessoal.model.Usuario;
import com.generation.blogpessoal.service.UsuarioService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsuarioControllerTeste {
	@Autowired
	private UsuarioService usuarioService;
	
	/* Somente no teste de controller, pois usamos o
	padrão rest que utiliza dos verbos e metodos 
	HTTP -> GET-POST-PUT-DELETE */
	@Autowired
	private TestRestTemplate testRestTemplate;
	
	@Test
	@Order(1)
	@DisplayName("Cadastrar apenas um usuário")
	public void deveCadastrarUmUsuario() {
		
		HttpEntity<Usuario> requisicao = new HttpEntity<Usuario>(new Usuario(0L, "Jose", "jose@imperiobronze.com", "trabalholindo", "naninha.jpeg"));
		ResponseEntity<Usuario> resposta = testRestTemplate.exchange("/usuario/cadastrar", HttpMethod.POST, requisicao, Usuario.class);
	
		assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
		assertEquals(requisicao.getBody().getNome(), resposta.getBody().getNome());
		assertEquals(requisicao.getBody().getUsuario(), resposta.getBody().getUsuario());
	}
	
	@Test
	@Order(2)
	@DisplayName("Não deve permitir duplicação do Usuário")
	public void naoDeveDuplicarUsuario() {
		
		usuarioService.cadastrarUsuario(new Usuario(0L, "Jane", "jane@gmail.com", "janinha", "jane.jpeg"));
		
		HttpEntity<Usuario> requisicao = new HttpEntity<Usuario>(new Usuario(0L, "Jane", "jane@gmail.com", "janinha", "jane.jpeg"));
		ResponseEntity<Usuario> resposta = testRestTemplate.exchange("/usuario/cadastrar", HttpMethod.POST, requisicao, Usuario.class);
	
		assertEquals(HttpStatus.BAD_REQUEST, resposta.getStatusCode());
	}
	
	@Test
	@Order(3)
	@DisplayName("Alterar um Usuário")
	public void deveAtualizarUmUsuario() {

		Optional<Usuario> usuarioCreate = usuarioService.cadastrarUsuario(new Usuario(0L,"Juliana Andrews", "juliana_andrews@email.com.br", "juliana123", "juliana.jpg"));

		Usuario usuarioUpdate = new Usuario(usuarioCreate.get().getId(),"Juliana Andrews Ramos", "juliana_ramos@email.com.br", "juliana123", "juliana.jpg");
		
		HttpEntity<Usuario> requisicao = new HttpEntity<Usuario>(usuarioUpdate);

		ResponseEntity<Usuario> resposta = testRestTemplate.withBasicAuth("root", "root").exchange("/usuario/atualizar", HttpMethod.PUT, requisicao, Usuario.class);

		assertEquals(HttpStatus.OK, resposta.getStatusCode());
		assertEquals(usuarioUpdate.getNome(), resposta.getBody().getNome());
		assertEquals(usuarioUpdate.getUsuario(), resposta.getBody().getUsuario());
	}
	
	@Test
	@Order(4)
	@DisplayName("Listar todos os Usuário")
	public void deveMostrarTodosUsuario() {
		
		usuarioService.cadastrarUsuario(new Usuario(0L, "Sabrina Sanches", "sabrina@gmail.com", "bina123", "sabrina.jpeg"));
		usuarioService.cadastrarUsuario(new Usuario(0L, "Ricardo Marques", "ricm@gmail.com", "riquinho", "ricardo.jpeg"));

		ResponseEntity<String> resposta = testRestTemplate.withBasicAuth("root", "root").exchange("/usuario/all", HttpMethod.GET, null, String.class);
	
		assertEquals(HttpStatus.OK, resposta.getStatusCode());
	}
	
	

}
