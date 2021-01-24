package com.williambohn.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.williambohn.cursomc.domain.Categoria;
import com.williambohn.cursomc.dto.CategoriaDTO;
import com.williambohn.cursomc.repositoreis.CategoriaRepository;
import com.williambohn.cursomc.services.exceptions.DataIntegrityException;
import com.williambohn.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired // instancia automaticamente
	
	private CategoriaRepository repo;

	/*
	 * Busca no banco de dados um objeto pelo ID, se nao tiver instacia do msmo
	 * retorna null
	 */
	
	public Categoria find(Integer id) {   
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				 "Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}
	
	//Insere uma categoria no banco de dados
	
	public Categoria insert(Categoria obj) {
		obj.setId(null); // Verifica se o id nao esta null, se estiver null, é feita o insert, se o id nao for null, o sistema considera uma atualizacao
		return repo.save(obj);
	}
	
	/*
	 * metodo para atualizar;
	 * o metodo save() serve para inserir e atualizar
	 */
	
	public Categoria update(Categoria obj) {
		find(obj.getId());
		return repo.save(obj);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
		repo.deleteById(id);
		}
		catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel excluir uma categoria que possui produtos");
		}
	}
	
	// Retorna todas Categorias
	public List<Categoria> findAll(){
		return repo.findAll();
	}
	
	
	/*Page é uma classe do Spring, para paginar os resultados
	 * ex: se voc tiver 1M de categorias e precisa retornar todas, vai usar muito processamento, entao voc pagina isso 
	 * como por ex: 20 categorias por pagina que fica safe
	 */
	public Page<Categoria> findPage(Integer page,Integer linesPerPage, String direction, String orderBy){
		
		/*Para retornar uma pagina de dados usamos PageRequesttbm do spring data, ele vai preparar os informações para a pesquisa 
		 * para isso usar PageRequest.of; a direçao vem em forma de String para converter usa-se Direction.valueOf(direction) que tbm é do Spring data e converte para o tipo direction
		 * 
		 * e retorna repo.findAll(pageRequest) automaticamente o metodo findAll vai considerar  pageRequest como argumento(uma sobrecarga de metodo) e vai retornar a pagina
		 * 
		 */
		PageRequest pageRequest = PageRequest.of(page, linesPerPage,Direction.valueOf(direction), orderBy );
		return repo.findAll(pageRequest);
	}
	
	
	// Metodo auxiliar que instancia uma categoria apartir de um DTO
	public Categoria fromDTO(CategoriaDTO objDto) {
		return new Categoria(objDto.getId(), objDto.getNome());
	}
	
}
