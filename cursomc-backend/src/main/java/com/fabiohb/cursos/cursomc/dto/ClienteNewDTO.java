package com.fabiohb.cursos.cursomc.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class ClienteNewDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	@NotEmpty(message = "Preencimento obrigatório")
	@Size(min = 5, max = 80, message = "O tamanho de ser entre 5 e 80 caracteres")
	private String nome;

	@NotEmpty(message = "Preencimento obrigatório")
	@Email(message = "Email inválido")
	private String email;

	private String cpfOuCnpj;

	private Integer tipo;

	private String logradouro;

	private String numero;

	private String complemento;

	private String bairro;

	private String cep;

	private Integer cidadeId;

	private String telefone1;

	private String telefone2;

	private String telefone3;

	public ClienteNewDTO() {
		super();
	}

}
