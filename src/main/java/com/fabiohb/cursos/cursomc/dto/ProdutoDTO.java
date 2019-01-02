package com.fabiohb.cursos.cursomc.dto;

import java.io.Serializable;

import com.fabiohb.cursos.cursomc.domain.Produto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProdutoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String nome;

	private Double preco;

	public ProdutoDTO() {
		super();
	}

	public ProdutoDTO(Produto produto) {
		this(
			produto.getId(),
			produto.getNome(),
			produto.getPreco()
		);
	}

}
