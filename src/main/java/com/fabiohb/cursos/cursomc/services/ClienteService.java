package com.fabiohb.cursos.cursomc.services;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fabiohb.cursos.cursomc.domain.Cidade;
import com.fabiohb.cursos.cursomc.domain.Cliente;
import com.fabiohb.cursos.cursomc.domain.Endereco;
import com.fabiohb.cursos.cursomc.domain.enums.TipoCliente;
import com.fabiohb.cursos.cursomc.dto.ClienteDTO;
import com.fabiohb.cursos.cursomc.dto.ClienteNewDTO;
import com.fabiohb.cursos.cursomc.repositories.ClienteRepository;
import com.fabiohb.cursos.cursomc.repositories.EnderecoRepository;
import com.fabiohb.cursos.cursomc.services.exceptions.DataIntegrityException;
import com.fabiohb.cursos.cursomc.services.exceptions.ObjectNotFoundException;

@Service
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class ClienteService {

	@Autowired
	private ClienteRepository repository;

	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public Cliente find(Integer id) {
		return repository
			.findById(id)
			.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName())
			);
	}

	public List<Cliente> findAll() {
		return repository.findAll();
	}

	public Page<Cliente> findPage(Integer page, Integer size, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, size, Direction.valueOf(direction), orderBy);
		return repository.findAll(pageRequest);
	}

	@Transactional
	public Cliente insert(Cliente cliente) {
		cliente.setId(null);
		Cliente novoCliente = repository.save(cliente);
		enderecoRepository.saveAll(cliente.getEnderecos());
		return novoCliente;
	}

	public Cliente update(Cliente cliente) {
		Cliente clienteAtual = find(cliente.getId());
		updateData(clienteAtual, cliente);
		return repository.save(clienteAtual);
	}

	public void delete(Integer id) {
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há pedidos relacionados");
		}
	}

	public Cliente fromDTO(ClienteDTO clienteDTO) {
		return new Cliente(
			clienteDTO.getId(),
			clienteDTO.getNome(),
			clienteDTO.getEmail(),
			null,
			null,
			null);
	}

	private void updateData(Cliente oldObj, Cliente newObj) {
		oldObj.setNome(newObj.getNome());
		oldObj.setEmail(newObj.getEmail());
	}

	public Cliente fromDTO(@Valid ClienteNewDTO clienteNewDTO) {
		Cliente cliente = new Cliente(
			null,
			clienteNewDTO.getNome(),
			clienteNewDTO.getEmail(),
			clienteNewDTO.getCpfOuCnpj(),
			TipoCliente.toEnum(clienteNewDTO.getTipo()),
			passwordEncoder.encode(clienteNewDTO.getSenha())
		);

		Endereco endereco = new Endereco(
			null,
			clienteNewDTO.getLogradouro(),
			clienteNewDTO.getNumero(),
			clienteNewDTO.getComplemento(),
			clienteNewDTO.getBairro(),
			clienteNewDTO.getCep(),
			cliente,
			new Cidade(clienteNewDTO.getCidadeId(), null, null)
		);

		cliente.getEnderecos().add(endereco);
		cliente.getTelefones().add(clienteNewDTO.getTelefone1());
		if (clienteNewDTO.getTelefone2() != null) {
			cliente.getTelefones().add(clienteNewDTO.getTelefone2());
		}
		if (clienteNewDTO.getTelefone3() != null) {
			cliente.getTelefones().add(clienteNewDTO.getTelefone3());
		}
		return cliente;
	}
}
