package com.rafaelacustodio.salesapp.services;

import com.rafaelacustodio.salesapp.domain.Cliente;
import com.rafaelacustodio.salesapp.dto.ClienteDTO;
import com.rafaelacustodio.salesapp.repositories.ClienteRepository;
import com.rafaelacustodio.salesapp.services.exceptions.DataIntegrityException;
import com.rafaelacustodio.salesapp.services.exceptions.ObjectNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    public Cliente find(Integer id){
    	Optional<Cliente> obj = repository.findById(id);
    	return obj.orElseThrow(() -> new ObjectNotFoundException(
    	 "Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
    }
    
    public Cliente update (Cliente obj) {
    	Cliente newObj = find(obj.getId());
    	updateData(newObj, obj);
    	return repository.save(obj);
    }
    
    public void delete(Integer id) {
    	find(id);
    	try {
    		repository.deleteById(id);
    	}catch(DataIntegrityViolationException e) {
    		throw new DataIntegrityException("Não é possível excluir porque há entidades relacionadas");
    	}
    }
    
    public List<Cliente> findAll(){
    	return repository.findAll();
    }
    public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
    	PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction),orderBy);

    	return repository.findAll(pageRequest);
    }
    
    public Cliente fromDTO(ClienteDTO objDto) {
    	return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null);
    }
    
    private void updateData(Cliente newObj, Cliente obj) {
    	newObj.setNome(obj.getNome());
    	newObj.setEmail(obj.getEmail());
    }
}
