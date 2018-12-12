package com.fabiohb.cursos.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.fabiohb.cursos.cursomc.domain.Categoria;
import com.fabiohb.cursos.cursomc.repositories.CategoriaRepository;

@Service
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class CategoriaService {

	@Autowired
	private CategoriaRepository repository;
	
	public Categoria buscar(Integer id) {
		return repository
				.findById(id)
				.orElse(null);
	}
	
}
