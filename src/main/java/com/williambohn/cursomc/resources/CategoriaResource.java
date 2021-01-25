package com.williambohn.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.williambohn.cursomc.domain.Categoria;
import com.williambohn.cursomc.dto.CategoriaDTO;
import com.williambohn.cursomc.services.CategoriaService;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaService service;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Categoria> find(@PathVariable Integer id) { // Para que o Spring saiba que o ID da url vai vir do ID
		Categoria obj = service.find(id);
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
	 * ResponseEntity<Void>  retorna um corpo vazion com for inserido com sucesso
	 */
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody CategoriaDTO objDto){
		Categoria obj = service.fromDTO(objDto);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
					.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	/*
	 * PUT atualiza um dado
	 * 
	 * 
	 */
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT )
	public ResponseEntity<Void> update(@Valid @RequestBody CategoriaDTO objDto,@PathVariable Integer id){
		Categoria obj = service.fromDTO(objDto);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	// Deleta uma categoria pelo ID
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	/*Retorna todas Categorias
	 *  
	 *  stream()  Recurso do java 8 para percorer uma lista
	 *  map() Faz uma operação para cada elemento da lista
	 *  -> arrow function; ou seja uma operação anônima que recebe um OBJ e o transforma em new CategoriaDTO(obj) e recebe o obj como argumento
	 *  
	 *  para passar o list.stream().map(obj -> new CategoriaDTO(obj)) para o tipo lista para isso usa-se  collect(Collectors.toList())
	 *  com isso em uma linha dapra converter uma lista para outra em uma linha
	 */
	
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<CategoriaDTO>> findAll(){
		List<Categoria> list = service.findAll();
		List<CategoriaDTO> listDto = list.stream().map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}
	
	// Método de paginação
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ResponseEntity<Page<CategoriaDTO>> findPage( 
		@RequestParam(value = "page", defaultValue = "0") 			Integer page,
		@RequestParam(value = "linesPerPage", defaultValue = "24") 	Integer linesPerPage,
		@RequestParam(value = "orderBy", defaultValue = "nome")		String orderBy ,
		@RequestParam(value = "direction", defaultValue = "ASC")	String direction){
		Page<Categoria> list = service.findPage(page, linesPerPage, direction, orderBy);
		Page<CategoriaDTO> listDTO = list.map(obj -> new CategoriaDTO(obj));
				return ResponseEntity.ok().body(listDTO);
	}
	
	
	
}
