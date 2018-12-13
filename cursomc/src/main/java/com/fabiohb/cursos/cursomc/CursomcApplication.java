package com.fabiohb.cursos.cursomc;

import static java.util.Arrays.asList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fabiohb.cursos.cursomc.domain.Categoria;
import com.fabiohb.cursos.cursomc.domain.Cidade;
import com.fabiohb.cursos.cursomc.domain.Estado;
import com.fabiohb.cursos.cursomc.domain.Produto;
import com.fabiohb.cursos.cursomc.repositories.CategoriaRepository;
import com.fabiohb.cursos.cursomc.repositories.CidadeRepository;
import com.fabiohb.cursos.cursomc.repositories.EstadoRepository;
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
	}
}
