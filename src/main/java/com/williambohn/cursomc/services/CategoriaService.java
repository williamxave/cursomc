package com.williambohn.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.williambohn.cursomc.domain.Categoria;
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
}
