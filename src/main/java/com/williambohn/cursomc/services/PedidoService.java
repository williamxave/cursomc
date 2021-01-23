package com.williambohn.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.williambohn.cursomc.domain.ItemPedido;
import com.williambohn.cursomc.domain.Pedido;
import com.williambohn.cursomc.repositoreis.PedidoRepository;
import com.williambohn.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository repo;
	
	public Pedido find(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " +id+ ", Tipo: " + ItemPedido.class.getName()));
				
	}
	

}
