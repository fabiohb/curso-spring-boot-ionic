package com.fabiohb.cursos.cursomc;

import static java.util.Arrays.asList;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fabiohb.cursos.cursomc.domain.Categoria;
import com.fabiohb.cursos.cursomc.domain.Cidade;
import com.fabiohb.cursos.cursomc.domain.Cliente;
import com.fabiohb.cursos.cursomc.domain.Endereco;
import com.fabiohb.cursos.cursomc.domain.Estado;
import com.fabiohb.cursos.cursomc.domain.Pagamento;
import com.fabiohb.cursos.cursomc.domain.PagamentoComBoleto;
import com.fabiohb.cursos.cursomc.domain.PagamentoComCartao;
import com.fabiohb.cursos.cursomc.domain.Pedido;
import com.fabiohb.cursos.cursomc.domain.Produto;
import com.fabiohb.cursos.cursomc.domain.enums.EstadoPagamento;
import com.fabiohb.cursos.cursomc.domain.enums.TipoCliente;
import com.fabiohb.cursos.cursomc.repositories.CategoriaRepository;
import com.fabiohb.cursos.cursomc.repositories.CidadeRepository;
import com.fabiohb.cursos.cursomc.repositories.ClienteRepository;
import com.fabiohb.cursos.cursomc.repositories.EnderecoRepository;
import com.fabiohb.cursos.cursomc.repositories.EstadoRepository;
import com.fabiohb.cursos.cursomc.repositories.PagamentoRepository;
import com.fabiohb.cursos.cursomc.repositories.PedidoRepository;
import com.fabiohb.cursos.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Categoria categoria1 = new Categoria(null, "Informática");
		Categoria categoria2 = new Categoria(null, "Escritório");
		
		Produto produto1 = new Produto(null, "Computador", 2000.00);
		Produto produto2 = new Produto(null, "Impressora", 800.00);
		Produto produto3 = new Produto(null, "Mouse", 80.00);
		
		categoria1.getProdutos().addAll(asList(produto1, produto2, produto3));
		categoria2.getProdutos().add(produto2);
		
		produto1.getCategorias().add(categoria1);
		produto2.getCategorias().addAll(asList(categoria1, categoria2));
		produto3.getCategorias().add(categoria1);
		
		categoriaRepository.saveAll(asList(categoria1, categoria2));
		produtoRepository.saveAll(asList(produto1, produto2, produto3));
	
		Estado estado1 = new Estado(null, "Minas Gerais");
		Estado estado2 = new Estado(null, "São Paulo");
		
		Cidade cidade1 = new Cidade(null, "Uberlândia", estado1);
		Cidade cidade2 = new Cidade(null, "São Paulo", estado2);
		Cidade cidade3 = new Cidade(null, "Campinas", estado2);
		
		estado1.getCidades().add(cidade1);
		estado2.getCidades().addAll(asList(cidade2, cidade3));
		
		estadoRepository.saveAll(asList(estado1, estado2));
		cidadeRepository.saveAll(asList(cidade1, cidade2, cidade3));	
		
		Cliente cliente1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "32165498770", TipoCliente.PESSOA_FISICA);
		cliente1.getTelefones().addAll(asList("321321321321", "351321321"));
		
		Endereco endereco1 = new Endereco(null, "Rua Flores", "300", "Apto 303", "Jardim", "38220834", cliente1, cidade1);
		Endereco endereco2 = new Endereco(null, "Av. Matos", "105", "Sala 800", "Centro", "38777012", cliente1, cidade2);
		
		cliente1.getEnderecos().addAll(asList(endereco1, endereco2));
		
		clienteRepository.save(cliente1);
		enderecoRepository.saveAll(asList(endereco1, endereco2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido pedido1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cliente1, endereco1);
		Pedido pedido2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cliente1, endereco2);
		
		cliente1.getPedidos().addAll(Arrays.asList(pedido1, pedido2));
		
		Pagamento pagamento1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, pedido1, 6);
		pedido1.setPagamento(pagamento1);
		Pagamento pagamento2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, pedido2, sdf.parse("20/10/2017 00:00"), null);
		pedido2.setPagamento(pagamento2);
		
		pedidoRepository.saveAll(asList(pedido1, pedido2));
		pagamentoRepository.saveAll(asList(pagamento1, pagamento2));
	}
}
