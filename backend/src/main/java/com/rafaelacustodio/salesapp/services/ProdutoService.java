package com.rafaelacustodio.salesapp.services;

import com.rafaelacustodio.salesapp.domain.Categoria;
import com.rafaelacustodio.salesapp.domain.Produto;
import com.rafaelacustodio.salesapp.repositories.CategoriaRepository;
import com.rafaelacustodio.salesapp.repositories.ProdutoRepository;
import com.rafaelacustodio.salesapp.services.exceptions.ObjectNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;
    
    @Autowired 
    private CategoriaRepository categoriaRepository;

    public Produto find(Integer id){
    	Optional<Produto> obj = repository.findById(id);
    	return obj.orElseThrow(() -> new ObjectNotFoundException(
    	 "Objeto não encontrado! Id: " + id + ", Tipo: " + Produto.class.getName()));
    }
    
    public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction){
    	PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction),orderBy);
		List<Categoria> categorias = categoriaRepository.findAllById(ids);
    	return repository.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);

    	
    }
}
