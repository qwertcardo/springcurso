package com.qwertcardo.springcurso.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.qwertcardo.springcurso.domain.Categoria;
import com.qwertcardo.springcurso.repositories.CategoriaRepository;
import com.qwertcardo.springcurso.services.exceptions.DataIntegrityException;
import com.qwertcardo.springcurso.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;

	public Categoria findId(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Categoria Não Encontrada /Id: " + id + " /Tipo Referenciado: " + Categoria.class.getName()));
	}
	
	public List<Categoria> findAll(){
		return repo.findAll();
	}

	public Categoria insert(Categoria categoria) {
		categoria.setId(null);
		return repo.save(categoria);
	}

	public Categoria update(Categoria categoria) {
		findId(categoria.getId());
		return repo.save(categoria);
	}

	public void delete(Integer id) {
		findId(id);
		try{
			repo.deleteById(id);
		}
		catch(DataIntegrityViolationException error) {
			throw new DataIntegrityException(
					"Não é possivel deletar uma Categoria que possui Produto(s) associado"
					+ " /Tipo: " + Categoria.class.getName());
		}
	}
}
