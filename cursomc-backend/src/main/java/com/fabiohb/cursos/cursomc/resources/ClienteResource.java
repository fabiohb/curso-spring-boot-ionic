package com.fabiohb.cursos.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

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

import com.fabiohb.cursos.cursomc.domain.Cliente;
import com.fabiohb.cursos.cursomc.dto.ClienteDTO;
import com.fabiohb.cursos.cursomc.services.ClienteService;

@RestController
@RequestMapping("/clientes")
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class ClienteResource {

	@Autowired
	private ClienteService service;
	
	@GetMapping
	@RequestMapping("/{id}")
	public ResponseEntity<Cliente> find(@PathVariable Integer id) {
		Cliente cliente = service.find(id);
		
		return ResponseEntity.ok(cliente);
	}
	
	@GetMapping
	public ResponseEntity<List<ClienteDTO>> findAll() {
		List<Cliente> clientes = service.findAll();
		
		List<ClienteDTO> categorioasDTO = clientes.stream()
			.map(ClienteDTO::new)
			.collect(Collectors.toList());
		
		return ResponseEntity.ok(categorioasDTO);
	}
	
	@GetMapping("/page")
	public ResponseEntity<Page<ClienteDTO>> findPage(
			@RequestParam(value = "page", defaultValue = "0") Integer page, 
			@RequestParam(value = "size", defaultValue = "24") Integer size, 
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy, 
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		
		Page<Cliente> clientes = service.findPage(page, size, orderBy, direction);
		Page<ClienteDTO> categorioasDTO = clientes.map(ClienteDTO::new);
		
		return ResponseEntity.ok(categorioasDTO);
	}
	
	@PostMapping
	public ResponseEntity<Void> insert(@Valid @RequestBody ClienteDTO clienteDTO) {
		Cliente cliente = service.fromDTO(clienteDTO);
		
		Cliente clienteInserida = service.insert(cliente );

		URI uri = ServletUriComponentsBuilder
					.fromCurrentRequest()
					.path("/{id}")
					.buildAndExpand(clienteInserida.getId())
					.toUri();

		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO clienteDTO, @PathVariable Integer id) {
		Cliente cliente = service.fromDTO(clienteDTO);
		
		cliente.setId(id);
		
		service.update(cliente);

		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		
		return ResponseEntity.noContent().build();
	}
}
