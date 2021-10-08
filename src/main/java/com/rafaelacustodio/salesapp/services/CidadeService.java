package com.rafaelacustodio.salesapp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rafaelacustodio.salesapp.domain.Cidade;
import com.rafaelacustodio.salesapp.repositories.CidadeRepository;

@Service
public class CidadeService {
	
	@Autowired
	private CidadeRepository repository;
	
	public List<Cidade> findByEstado(Integer estadoId){
		return repository.findCidades(estadoId);
	}
}
