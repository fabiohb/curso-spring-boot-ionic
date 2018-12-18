package com.fabiohb.cursos.cursomc.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fabiohb.cursos.cursomc.domain.Cliente;

import lombok.Data;

@Data
public class ClienteDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	@NotEmpty(message = "Preencimento obrigatório")
	@Size(min = 5, max = 80, message = "O tamanho de ser entre 5 e 80 caracteres")
	private String nome;
	
	@NotEmpty(message = "Preencimento obrigatório")
	@Email(message = "Email inválido")
	private String email;
	
	public ClienteDTO() {
		super();
	}
	
	public ClienteDTO(Cliente cliente) {
		this();
		this.id = cliente.getId();
		this.nome = cliente.getNome();
		this.email = cliente.getEmail();
	}
	
}
