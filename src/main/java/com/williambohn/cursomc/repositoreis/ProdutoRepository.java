package com.williambohn.cursomc.repositoreis;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.williambohn.cursomc.domain.Categoria;
import com.williambohn.cursomc.domain.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
	@Transactional
	@Query("SELECT DISTINCT obj FROM Produto obj INNER JOIN obj.categorias cat WHERE obj.nome LIKE %:nome% AND cat IN :categorias") //Consulta JPQL
	Page<Produto> search(@Param("nome") String nome,@Param("categorias") List<Categoria> categoria, Pageable pageRequest);
	
	/*
	 * Para fazer pesquisas JPQL tbm pode usar padrao d nome, que ele automaticamente vai fazer o query.
	 *  Para esse caso pode-se trocar o nome do método para findDistinctByNomeContainingAndCategoriaIn. Ficaria assim 
	 *  
	 *  @Repository
       public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
	   Page<Produto> findDistinctByNomeContainingAndCategoriaIn ( String nome, List<Categoria> categoria, Pageable pageRequest);
	
	 */
} 
