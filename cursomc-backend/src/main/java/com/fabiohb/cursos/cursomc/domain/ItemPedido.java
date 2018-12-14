package com.fabiohb.cursos.cursomc.domain;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class ItemPedido implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	@EqualsAndHashCode.Include
	@JsonIgnore
	private ItemPedidoPK id = new ItemPedidoPK();

	private Integer quantidade;

	private Double desconto;

	private Double preco;

	public ItemPedido() {
		super();
	}

	public ItemPedido(Pedido pedido, Produto produto, Integer quantidade, Double desconto, Double preco) {
		super();
		this.id.setPedido(pedido);
		this.id.setProduto(produto);
		this.quantidade = quantidade;
		this.desconto = desconto;
		this.preco = preco;
	}

	@JsonIgnore
	public Pedido getPedido() {
		return id.getPedido();
	}
	
	public Produto getProduto() {
		return id.getProduto();
	}
	
}
