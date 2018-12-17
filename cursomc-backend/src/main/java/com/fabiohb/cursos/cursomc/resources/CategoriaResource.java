package com.fabiohb.cursos.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fabiohb.cursos.cursomc.domain.Categoria;
import com.fabiohb.cursos.cursomc.dto.CategoriaDTO;
import com.fabiohb.cursos.cursomc.services.CategoriaService;

@RestController
@RequestMapping("/categorias")
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class CategoriaResource {

	@Autowired
	private CategoriaService service;
	
	@GetMapping("/{id}")
	public ResponseEntity<Categoria> find(@PathVariable Integer id) {
		Categoria categoria = service.find(id);
		
		return ResponseEntity.ok(categoria);
	}
	
	@GetMapping
	public ResponseEntity<List<CategoriaDTO>> findAll() {
		List<Categoria> categorias = service.findAll();
		
		List<CategoriaDTO> categorioasDTO = categorias.stream()
			.map(CategoriaDTO::new)
			.collect(Collectors.toList());
		
		return ResponseEntity.ok(categorioasDTO);
	}
	
	@GetMapping("/page")
	public ResponseEntity<Page<CategoriaDTO>> findPage(
			@RequestParam(value = "page", defaultValue = "0") Integer page, 
			@RequestParam(value = "size", defaultValue = "24") Integer size, 
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy, 
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		
		Page<Categoria> categorias = service.findPage(page, size, orderBy, direction);
		Page<CategoriaDTO> categorioasDTO = categorias.map(CategoriaDTO::new);
		
		return ResponseEntity.ok(categorioasDTO);
	}
	
	@PostMapping
	public ResponseEntity<Void> insert(@RequestBody Categoria categoria) {
		Categoria categoriaInserida = service.insert(categoria);

		URI uri = ServletUriComponentsBuilder
					.fromCurrentRequest()
					.path("/{id}")
					.buildAndExpand(categoriaInserida.getId())
					.toUri();

		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@RequestBody Categoria categoria, @PathVariable Integer id) {
		categoria.setId(id);
		
		service.update(categoria);

		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		
		return ResponseEntity.noContent().build();
	}
}
