package com.fabiohb.cursos.cursomc.repositories;

import java.util.List;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fabiohb.cursos.cursomc.domain.Categoria;
import com.fabiohb.cursos.cursomc.domain.Produto;

@Repository
@Transactional(readOnly = true)
@Scope(BeanDefinition.SCOPE_SINGLETON)
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

	// https://docs.spring.io/spring-data/jpa/docs/current/reference/html/
	@Query("SELECT DISTINCT obj "
			+ " FROM Produto obj "
			+ "INNER JOIN obj.categorias cat "
			+ "WHERE obj.nome LIKE %:nome% "
			+ "  AND cat IN :categorias")
	Page<Produto> serch(@Param("nome") String nome,
			@Param("categorias") List<Categoria> categorias, Pageable pageRequest);

	Page<Produto> findDistinctByNomeContainingAndCategoriasIn(
			String nome, List<Categoria> categorias, Pageable pageRequest);

}
