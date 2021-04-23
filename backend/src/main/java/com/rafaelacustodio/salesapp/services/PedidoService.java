package com.rafaelacustodio.salesapp.services;

import com.rafaelacustodio.salesapp.domain.Pedido;
import com.rafaelacustodio.salesapp.exceptions.ObjectNotFoundException;
import com.rafaelacustodio.salesapp.repositories.PedidoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repository;

    public Pedido buscar(Integer id){
    	Optional<Pedido> obj = repository.findById(id);
    	return obj.orElseThrow(() -> new ObjectNotFoundException(
    	 "Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
    }
}
