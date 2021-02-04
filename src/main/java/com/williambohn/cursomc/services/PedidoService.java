package com.williambohn.cursomc.services;

import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.williambohn.cursomc.domain.ItemPedido;
import com.williambohn.cursomc.domain.PagamentoComBoleto;
import com.williambohn.cursomc.domain.Pedido;
import com.williambohn.cursomc.domain.enums.EstadoPagamento;
import com.williambohn.cursomc.repositoreis.ClienteRepository;
import com.williambohn.cursomc.repositoreis.ItemPedidoRepository;
import com.williambohn.cursomc.repositoreis.PagamentoRepository;
import com.williambohn.cursomc.repositoreis.PedidoRepository;
import com.williambohn.cursomc.repositoreis.ProdutoRepository;
import com.williambohn.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo;

	@Autowired
	private BoletoService boletoService;

	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ClienteService clienteService;

	public Pedido find(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + ItemPedido.class.getName()));
	}

	@Transactional
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setData(new Date());
		obj.setCliente(clienteService.find(obj.getCliente().getId()));
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getData());
		}

		obj = repo.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		
		for (ItemPedido p : obj.getItens()) {
			p.setDesconto(0.0);
			p.setProduto(produtoService.find(p.getProduto().getId()));
			p.setPreco(p.getProduto().getPreco());
			p.setPedido(obj);
		}
		
		itemPedidoRepository.saveAll(obj.getItens());
		System.out.println(obj);

		return obj;

	}
}
