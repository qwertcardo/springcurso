package com.qwertcardo.springcurso.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.qwertcardo.springcurso.domain.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

	@Transactional(readOnly = true)
	public Cliente findByEmail(String email);
}
