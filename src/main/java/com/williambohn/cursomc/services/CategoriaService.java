package com.williambohn.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.williambohn.cursomc.domain.Categoria;
import com.williambohn.cursomc.repositoreis.CategoriaRepository;
import com.williambohn.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired // instancia automaticamente
	private CategoriaRepository repo;

	/*
	 * Busca no banco de dados um objeto pelo ID, se nao tiver instacia do msmo
	 * retorna null
	 */
	public Categoria buscar(Integer id) {   
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				 "Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}
}
