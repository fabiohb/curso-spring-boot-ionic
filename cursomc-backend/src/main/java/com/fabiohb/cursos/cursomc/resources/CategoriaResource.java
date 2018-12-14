package com.fabiohb.cursos.cursomc.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fabiohb.cursos.cursomc.domain.Categoria;
import com.fabiohb.cursos.cursomc.services.CategoriaService;

@RestController
@RequestMapping("/categorias")
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class CategoriaResource {

	@Autowired
	private CategoriaService service;
	
	@GetMapping
	@RequestMapping("/{id}")
	public ResponseEntity<Categoria> listar(@PathVariable Integer id) {
		Categoria categoria = service.buscar(id);
		
		return ResponseEntity.ok(categoria);
	}
	
	@PostMapping
	public ResponseEntity<Void> insert(Categoria categoria) {
		Categoria categoriaInserida = service.insert(categoria);
		URI uri = ServletUriComponentsBuilder
					.fromCurrentRequest()
					.path("/{id}")
					.buildAndExpand(categoriaInserida.getId())
					.toUri();
		return ResponseEntity.created(uri).build();
	}
}
