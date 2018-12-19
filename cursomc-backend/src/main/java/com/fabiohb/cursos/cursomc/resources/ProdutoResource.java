package com.fabiohb.cursos.cursomc.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fabiohb.cursos.cursomc.domain.Produto;
import com.fabiohb.cursos.cursomc.dto.ProdutoDTO;
import com.fabiohb.cursos.cursomc.resources.utils.URL;
import com.fabiohb.cursos.cursomc.services.ProdutoService;

@RestController
@RequestMapping("/produtos")
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class ProdutoResource {

	@Autowired
	private ProdutoService service;

	@GetMapping
	@RequestMapping("/{id}")
	public ResponseEntity<Produto> find(@PathVariable Integer id) {
		Produto pedido = service.find(id);

		return ResponseEntity.ok(pedido);
	}

	@GetMapping
	public ResponseEntity<Page<ProdutoDTO>> findPage(
			@RequestParam(value = "nome", defaultValue = "") String nome,
			@RequestParam(value = "categorias", defaultValue = "") String categorias,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "size", defaultValue = "24") Integer size,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		String nomeDecoded = URL.decodeParam(nome);
		List<Integer> ids = URL.decodeIntList(categorias);
		Page<Produto> produtos = service.search(nomeDecoded, ids, page, size, orderBy, direction);
		Page<ProdutoDTO> produtosDTO = produtos.map(ProdutoDTO::new);

		return ResponseEntity.ok(produtosDTO);
	}
}
