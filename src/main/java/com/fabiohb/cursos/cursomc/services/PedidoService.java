package com.fabiohb.cursos.cursomc.services;

import static com.fabiohb.cursos.cursomc.domain.enums.EstadoPagamento.PENDENTE;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.fabiohb.cursos.cursomc.domain.Cliente;
import com.fabiohb.cursos.cursomc.domain.ItemPedido;
import com.fabiohb.cursos.cursomc.domain.Pagamento;
import com.fabiohb.cursos.cursomc.domain.PagamentoComBoleto;
import com.fabiohb.cursos.cursomc.domain.Pedido;
import com.fabiohb.cursos.cursomc.domain.Produto;
import com.fabiohb.cursos.cursomc.repositories.ItemPedidoRepository;
import com.fabiohb.cursos.cursomc.repositories.PagamentoRepository;
import com.fabiohb.cursos.cursomc.repositories.PedidoRepository;
import com.fabiohb.cursos.cursomc.security.UserSS;
import com.fabiohb.cursos.cursomc.services.exceptions.AuthorizationException;
import com.fabiohb.cursos.cursomc.services.exceptions.ObjectNotFoundException;

@Service
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class PedidoService {

	@Autowired
	private PedidoRepository repository;

	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	@Autowired
	private BoletoService boletoService;

	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private EmailService emailService;

	public Pedido find(Integer id) {
		return repository
			.findById(id)
			.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName())
			);
	}

	public Pedido insert(Pedido pedido) {
		Date dataPedido = new Date();

		pedido.setId(null);
		pedido.setInstante(dataPedido);
		pedido.setCliente(clienteService.find(pedido.getCliente().getId()));

		Pagamento pagamento = pedido.getPagamento();
		pagamento.setEstadoPagamento(PENDENTE);
		pagamento.setPedido(pedido);
		if (pagamento instanceof PagamentoComBoleto) {
			PagamentoComBoleto pgto = (PagamentoComBoleto) pagamento;
			boletoService.preencherPagamentoComBoleto(pgto, dataPedido);
		}

		repository.save(pedido);
		pagamentoRepository.save(pagamento);

		for (ItemPedido item : pedido.getItens()) {
			Produto produto = produtoService.find(item.getProduto().getId());
			
			item.setDesconto(0.0);
			item.setProduto(produto);
			item.setPreco(produto.getPreco());
			item.setPedido(pedido);
		}

		itemPedidoRepository.saveAll(pedido.getItens());

		emailService.sendOrderConfirmationHtmlEmail(pedido);
		
		return pedido;
	}

	public Page<Pedido> findPage(Integer page, Integer size, String orderBy, String direction) {
		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		
		PageRequest pageRequest = PageRequest.of(page, size, Direction.valueOf(direction), orderBy);
		Cliente cliente = clienteService.find(user.getId());
		return repository.findByCliente(cliente, pageRequest);
	}
	
}
