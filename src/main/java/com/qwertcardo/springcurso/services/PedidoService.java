package com.qwertcardo.springcurso.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qwertcardo.springcurso.domain.Pedido;
import com.qwertcardo.springcurso.repositories.PedidoRepository;
import com.qwertcardo.springcurso.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo;
	
	public Pedido find(Integer id) {
		Optional<Pedido> pedido = repo.findById(id);
		return pedido.orElseThrow(() -> new ObjectNotFoundException(
				"Pedido Não Encontrado /Id: " + id + " /Tipo Referenciado: " + Pedido.class.getName()));
	}
}
