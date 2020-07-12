package com.qwertcardo.springcurso.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.qwertcardo.springcurso.DTO.CategoriaDTO;
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
	
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}

	public Categoria insert(Categoria categoria) {
		categoria.setId(null);
		return repo.save(categoria);
	}

	public Categoria update(Categoria categoria) {
		Categoria categoriaToUpdate = findId(categoria.getId());
		updateData(categoriaToUpdate, categoria);
		return repo.save(categoria);
	}
	
	private void updateData(Categoria categoriaToUpdate, Categoria categoria) {
		categoriaToUpdate.setNome(categoria.getNome());
	}

	public void delete(Integer id) {
		findId(id);
		try{
			repo.deleteById(id);
		}
		catch(DataIntegrityViolationException error) {
			throw new DataIntegrityException(
					"Não é possivel deletar uma Categoria que possui Produto(s) associado "
					+ "/Tipo: " + Categoria.class.getName());
		}
	}
	
	public CategoriaDTO toDTO(Categoria categoria) {
		return new CategoriaDTO(categoria.getId(), categoria.getNome());
	}
	
	public Categoria fromDTO(CategoriaDTO categoriaDTO) {
		return new Categoria(categoriaDTO.getId(), categoriaDTO.getNome());
	}
}
