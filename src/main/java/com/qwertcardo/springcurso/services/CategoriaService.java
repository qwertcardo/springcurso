package com.qwertcardo.springcurso.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qwertcardo.springcurso.domain.Categoria;
import com.qwertcardo.springcurso.repositories.CategoriaRepository;
import com.qwertcardo.springcurso.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;
	
	public Categoria buscar(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Categoria Não Encontrada Id: " + id + " / Tipo Referenciado: " + Categoria.class.getName()));
	}
}
