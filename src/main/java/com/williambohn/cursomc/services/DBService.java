package com.williambohn.cursomc.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.williambohn.cursomc.domain.Categoria;
import com.williambohn.cursomc.domain.Cidade;
import com.williambohn.cursomc.domain.Cliente;
import com.williambohn.cursomc.domain.Endereco;
import com.williambohn.cursomc.domain.Estado;
import com.williambohn.cursomc.domain.ItemPedido;
import com.williambohn.cursomc.domain.Pagamento;
import com.williambohn.cursomc.domain.PagamentoComBoleto;
import com.williambohn.cursomc.domain.PagamentoComCartao;
import com.williambohn.cursomc.domain.Pedido;
import com.williambohn.cursomc.domain.Produto;
import com.williambohn.cursomc.domain.enums.EstadoPagamento;
import com.williambohn.cursomc.domain.enums.TipoCliente;
import com.williambohn.cursomc.repositoreis.CategoriaRepository;
import com.williambohn.cursomc.repositoreis.CidadeRepository;
import com.williambohn.cursomc.repositoreis.ClienteRepository;
import com.williambohn.cursomc.repositoreis.EnderecoRepository;
import com.williambohn.cursomc.repositoreis.EstadoRepository;
import com.williambohn.cursomc.repositoreis.ItemPedidoRepository;
import com.williambohn.cursomc.repositoreis.PagamentoRepository;
import com.williambohn.cursomc.repositoreis.PedidoRepository;
import com.williambohn.cursomc.repositoreis.ProdutoRepository;

@Service
public class DBService {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private CidadeRepository cidadeRespository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private PagamentoRepository pagamentoRepository;
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	public void instanciateTesteDatabs() throws ParseException {
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		Categoria cat3 = new Categoria(null, "Cama mesa e banho");
		Categoria cat4 = new Categoria(null, "Eletronicos");
		Categoria cat5 = new Categoria(null, "Jardinagem");
		Categoria cat6 = new Categoria(null, "Decoração");
		Categoria cat7 = new Categoria(null, "Perfumaria");

		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.0);

		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));

		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));

		categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));

		// ======================================================

		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");

		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);

		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));

		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRespository.saveAll(Arrays.asList(c1, c2, c3));

		// ======================================================

		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@exemplo.com", "0101010101010", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("000000000", "111111111"));
		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 303", "Jardim", "26172172", cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "sala 800", "Centro", "26172172", cli1, c2);

		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));

		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1, e2));

		// ======================================================

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		Pedido ped1 = new Pedido(null, sdf.parse("21/01/2021 19:45"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("21/01/2021 20:00"), cli1, e2);

		Pagamento pag1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pag1);

		Pagamento pag2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/2/2021 00:00"),
				null);
		ped2.setPagamento(pag2);

		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));

		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pag1, pag2));

		// ======================================================

		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.0, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.0, 2, 80.0);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.0, 1, 800.00);

		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));

		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));

		itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));

	}
}