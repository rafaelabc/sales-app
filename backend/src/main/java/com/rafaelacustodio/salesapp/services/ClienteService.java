package com.rafaelacustodio.salesapp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rafaelacustodio.salesapp.domain.Cidade;
import com.rafaelacustodio.salesapp.domain.Cliente;
import com.rafaelacustodio.salesapp.domain.Endereco;
import com.rafaelacustodio.salesapp.domain.enums.TipoCliente;
import com.rafaelacustodio.salesapp.dto.ClienteDTO;
import com.rafaelacustodio.salesapp.dto.ClienteNewDTO;
import com.rafaelacustodio.salesapp.repositories.ClienteRepository;
import com.rafaelacustodio.salesapp.repositories.EnderecoRepository;
import com.rafaelacustodio.salesapp.services.exceptions.DataIntegrityException;
import com.rafaelacustodio.salesapp.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;
    
    @Autowired
    private EnderecoRepository enderecoRepository;

    public Cliente find(Integer id){
    	Optional<Cliente> obj = repository.findById(id);
    	return obj.orElseThrow(() -> new ObjectNotFoundException(
    	 "Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
    }
    @Transactional 
    public Cliente insert (Cliente obj) {
    	obj.setId(null);
    	obj = repository.save(obj);
    	enderecoRepository.saveAll(obj.getEnderecos());
    	return obj;
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
    		throw new DataIntegrityException("Não é possível excluir porque há pedidos relacionados");
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
    
    public Cliente fromDTO(ClienteNewDTO objDto) {
    	Cliente cli =  new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(), TipoCliente.toEnum(objDto.getTipo()));
    	Cidade cidade = new Cidade(objDto.getCidadeId(), null, null);
    	Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cli, cidade);
    	cli.getEnderecos().add(end);
    	cli.getTelefones().add(objDto.getTelefone1()); 
    	if(objDto.getTelefone2() != null) {
    		cli.getTelefones().add(objDto.getTelefone2());
    	}
    	if(objDto.getTelefone3() != null) {
    		cli.getTelefones().add(objDto.getTelefone3());
    	}
    	
    	return cli;
    }
}
