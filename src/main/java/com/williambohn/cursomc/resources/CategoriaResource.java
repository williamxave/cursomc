package com.williambohn.cursomc.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import com.williambohn.cursomc.domain.Categoria;
import com.williambohn.cursomc.services.CategoriaService;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaService service;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id) { // Para que o Spring saiba que o ID da url vai vir do ID
																// do metodo precisa usar a anotacao @PathVariable
		Categoria obj = service.buscar(id);
		return ResponseEntity.ok().body(obj);

		/*
		 * ResponseEntity ele encapsula uma resposta HTTP pra um serviço REST <?> é para
		 * retorna qualquer tipo, pode ou nao encontrar a resposta
		 * 
		 * Para retornar a consulta usa-se >> ResponseEntity.ok() << que vai retorna um
		 * ok, que a busca foi feita com sucesso, e tbm usa-se >> body(obj) << que vai
		 * retorna no corpo da consulta o objeto pesquisado
		 * 
		 */

	}
	
	/*
	 * Metodo para inserir uma categoria no banco de dados
	 * 
	 * @RequestBody transforma o objeto java em json automaticamente
	 * 
	 * Por padrao a requisicao Http deve ser montada assim, como resposta no corpo deve aparecer a URI do novo obj regitraddo alem do codio http 201(CREATED)
	 * para pegar essa nova URI usa-se URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
	 * 
	 * e para mostrar o codigo de criacao usa-se o ResponseEntity.created(aqui dentro vem a sua uri ex: uri) e o . build(); builda para voc
	 * 
	 */
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@RequestBody Categoria obj){
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
					.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

}
