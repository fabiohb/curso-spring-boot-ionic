package com.fabiohb.cursos.cursomc.services.validation;

import static com.fabiohb.cursos.cursomc.domain.enums.TipoCliente.*;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.fabiohb.cursos.cursomc.dto.ClienteNewDTO;
import com.fabiohb.cursos.cursomc.resources.exception.FieldMessage;
import com.fabiohb.cursos.cursomc.services.validation.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

	@Override
	public boolean isValid(ClienteNewDTO value, ConstraintValidatorContext context) {

		List<FieldMessage> errors = new ArrayList<>();

		if (value.getTipo().equals(PESSOA_FISICA.getCod())
				&& !BR.isValidCPF(value.getCpfOuCnpj())) {
			errors.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
		}
		if (value.getTipo().equals(PESSOA_JURIDICA.getCod())
				&& !BR.isValidCNPJ(value.getCpfOuCnpj())) {
			errors.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
		}

		// Inclua os testes aqui, inserindo erros na lista
		for (FieldMessage error : errors) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(error.getMessage())
				.addPropertyNode(error.getFieldName())
				.addConstraintViolation();
		}

		return errors.isEmpty();
	}

}
