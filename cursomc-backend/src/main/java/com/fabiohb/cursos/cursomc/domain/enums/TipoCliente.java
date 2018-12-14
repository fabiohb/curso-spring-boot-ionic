package com.fabiohb.cursos.cursomc.domain.enums;

public enum TipoCliente {

	PESSOA_FISICA(1, "Pessoa Física"), 
	
	PESSOA_JURIDICA(2, "Pessoa Jurídica");
	
	private int cod;
	
	private String descricao;

	private TipoCliente(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public int getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}

	public static TipoCliente toEnum(Integer id) {
		if (id == null) {
			return null;
		}
		
		for (TipoCliente tipo : values()) {
			if (id.equals(tipo.getCod())) {
				return tipo;
			}
		}
		
		throw new IllegalArgumentException("Id inválido " + id);
	}
}
