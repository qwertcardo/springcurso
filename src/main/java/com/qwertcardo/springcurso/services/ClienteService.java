package com.qwertcardo.springcurso.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qwertcardo.springcurso.domain.Cliente;
import com.qwertcardo.springcurso.repositories.ClienteRepository;
import com.qwertcardo.springcurso.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;
	
	public Cliente find(Integer id) {
		Optional<Cliente> cliente = repo.findById(id);
		return cliente.orElseThrow(() -> new ObjectNotFoundException(
				"Cliente Não Encontrado /Id: " + id + " /Tipo Referenciado: " + Cliente.class.getName()));
	}
}
