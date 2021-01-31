package com.williambohn.cursomc.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.williambohn.cursomc.domain.Produto;
import com.williambohn.cursomc.dto.ProdutoDTO;
import com.williambohn.cursomc.resources.utils.Url;
import com.williambohn.cursomc.services.ProdutoService;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoResource {

	@Autowired
	private ProdutoService service;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Produto> find(@PathVariable Integer id) { 
		Produto obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Page<ProdutoDTO>> findPage( 
		@RequestParam(value = "nome", defaultValue = "")            String nome,
		@RequestParam(value = "categorias", defaultValue = "") 	    String categorias,
		@RequestParam(value = "page", defaultValue = "0") 			Integer page,
		@RequestParam(value = "linesPerPage", defaultValue = "24") 	Integer linesPerPage,
		@RequestParam(value = "orderBy", defaultValue = "nome")		String orderBy ,
		@RequestParam(value = "direction", defaultValue = "ASC")	String direction){
		String nomeDecoded = Url.decodeParam(nome);
		List<Integer> ids = Url.decodeIntList(categorias);
		Page<Produto> list = service.search(nomeDecoded, ids, page, linesPerPage, direction, orderBy);
		Page<ProdutoDTO> listDTO = list.map(obj -> new ProdutoDTO(obj));
				return ResponseEntity.ok().body(listDTO);
	}

}
