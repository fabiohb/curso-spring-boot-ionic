package com.fabiohb.cursos.cursomc.resources;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fabiohb.cursos.cursomc.domain.Pedido;
import com.fabiohb.cursos.cursomc.services.PedidoService;

@RestController
@RequestMapping("/pedidos")
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class PedidoResource {

	@Autowired
	private PedidoService service;

	@GetMapping
	@RequestMapping("/{id}")
	public ResponseEntity<Pedido> find(@PathVariable Integer id) {
		Pedido pedido = service.find(id);

		return ResponseEntity.ok(pedido);
	}

	@PostMapping
	public ResponseEntity<Void> insert(@Valid @RequestBody Pedido pedido) {

		Pedido pedidoInserido = service.insert(pedido);

		URI uri = ServletUriComponentsBuilder
					.fromCurrentRequest()
					.path("/{id}")
					.buildAndExpand(pedidoInserido.getId())
					.toUri();

		return ResponseEntity.created(uri).build();
	}
}
