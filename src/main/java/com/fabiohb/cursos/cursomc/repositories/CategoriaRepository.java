package com.fabiohb.cursos.cursomc.repositories;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fabiohb.cursos.cursomc.domain.Categoria;

@Repository
@Scope(BeanDefinition.SCOPE_SINGLETON)
public interface CategoriaRepository extends JpaRepository<Categoria, Integer>{

}
