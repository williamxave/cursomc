package com.williambohn.cursomc.resources;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.williambohn.cursomc.domain.Categoria;
import com.williambohn.cursomc.domain.Pedido;
import com.williambohn.cursomc.dto.CategoriaDTO;
import com.williambohn.cursomc.services.PedidoService;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoResource {

	@Autowired
	private PedidoService service;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Pedido> find(@PathVariable Integer id) { // Para que o Spring saiba que o ID da url vai vir do ID do metodo precisa usar a anotacao @PathVariable
		Pedido obj = service.find(id);
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
		
		@RequestMapping(method = RequestMethod.POST)
		public ResponseEntity<Void> insert(@Valid @RequestBody Pedido obj){
			obj = service.insert(obj);
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
						.path("/{id}").buildAndExpand(obj.getId()).toUri();
			return ResponseEntity.created(uri).build();
		}
}

