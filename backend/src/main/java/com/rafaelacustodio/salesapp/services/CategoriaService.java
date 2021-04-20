package com.rafaelacustodio.salesapp.services;

import com.rafaelacustodio.salesapp.domain.Categoria;
import com.rafaelacustodio.salesapp.exceptions.ObjectNotFoundException;
import com.rafaelacustodio.salesapp.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repository;

    public Categoria buscar(Integer id){
    	Optional<Categoria> obj = repository.findById(id);
    	return obj.orElseThrow(() -> new ObjectNotFoundException(
    	 "Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
    }
}
