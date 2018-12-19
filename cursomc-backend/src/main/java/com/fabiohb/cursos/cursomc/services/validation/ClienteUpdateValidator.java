package com.fabiohb.cursos.cursomc.services.validation;

import static org.springframework.web.servlet.HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import com.fabiohb.cursos.cursomc.domain.Cliente;
import com.fabiohb.cursos.cursomc.dto.ClienteDTO;
import com.fabiohb.cursos.cursomc.repositories.ClienteRepository;
import com.fabiohb.cursos.cursomc.resources.exception.FieldMessage;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private ClienteRepository repository;

	@Override
	public boolean isValid(ClienteDTO value, ConstraintValidatorContext context) {

		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) request.getAttribute(URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer uriId = Integer.parseInt(map.get("id"));


		List<FieldMessage> errors = new ArrayList<>();

		Cliente cliente = repository.findByEmail(value.getEmail());
		if (cliente != null && !cliente.getId().equals(uriId)) {
			errors.add(new FieldMessage("email", "Email já existente"));
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
