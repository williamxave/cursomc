package com.williambohn.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.williambohn.cursomc.domain.Categoria;
import com.williambohn.cursomc.repositoreis.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired // instancia automaticamente
	private CategoriaRepository repo;

	public Categoria buscar(Integer id) {// Busca no banco de dados um objeto pelo ID, se nao tiver instacia do msmo
		// retorna null
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElse(null);
	}
}
