package com.qwertcardo.springcurso.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.qwertcardo.springcurso.domain.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer>{

}
