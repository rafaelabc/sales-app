package com.rafaelacustodio.salesapp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rafaelacustodio.salesapp.domain.Estado;
import com.rafaelacustodio.salesapp.repositories.EstadoRepository;

@Service
public class EstadoService {
	
	@Autowired
	private EstadoRepository repository;
	
	public List<Estado> findAll(){
		return repository.findAllByOrderByNome();
	}
	
}
