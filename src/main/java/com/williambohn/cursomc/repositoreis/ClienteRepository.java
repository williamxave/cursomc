package com.williambohn.cursomc.repositoreis;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.williambohn.cursomc.domain.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
	
	@Transactional(readOnly = true) 
    Cliente findByEmail(String email);
	/*
	 * readOnly = true faz com que nao nessecite ser envolvida com uma transacao de bando de dados fica mais rapido e evita loking
	 * 
	 * O Spring data tem o pradrao de nomes
	 * ele já tem um metodo chamado findBy
	 * e se voc por um Email dps ele automaticamente 
	 * sabe que voc quer fazer uma pesquisa por email  -> "findByEmail";
	 * 
	 */
	
}

