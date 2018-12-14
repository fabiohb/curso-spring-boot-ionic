package com.fabiohb.cursos.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.fabiohb.cursos.cursomc.domain.Categoria;
import com.fabiohb.cursos.cursomc.repositories.CategoriaRepository;
import com.fabiohb.cursos.cursomc.services.exceptions.ObjectNotFoundException;

@Service
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class CategoriaService {

	@Autowired
	private CategoriaRepository repository;
	
	public Categoria find(Integer id) {
		return repository
			.findById(id)
			.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName())
			);
	}
	
	public Categoria insert(Categoria categoria) {
		categoria.setId(null);
		return repository.save(categoria);
	}

	public Categoria update(Categoria categoria) {
		find(categoria.getId());
		return repository.save(categoria);
	}
}
