package com.fabiohb.cursos.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.fabiohb.cursos.cursomc.domain.Cliente;
import com.fabiohb.cursos.cursomc.domain.Pedido;
import com.fabiohb.cursos.cursomc.repositories.PedidoRepository;
import com.fabiohb.cursos.cursomc.services.exceptions.ObjectNotFoundException;

@Service
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class PedidoService {

	@Autowired
	private PedidoRepository repository;
	
	public Pedido buscar(Integer id) {
		return repository
			.findById(id)
			.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName())
			);
	}
	
}
