package com.rafaelacustodio.salesapp.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.rafaelacustodio.salesapp.domain.Cliente;
import com.rafaelacustodio.salesapp.domain.enums.TipoCliente;
import com.rafaelacustodio.salesapp.dto.ClienteNewDTO;
import com.rafaelacustodio.salesapp.repositories.ClienteRepository;
import com.rafaelacustodio.salesapp.resources.exception.FieldMessage;
import com.rafaelacustodio.salesapp.services.validation.utils.BR;
public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {
	 
	@Autowired
	private ClienteRepository repository;
	
	 @Override
	 public void initialize(ClienteInsert ann) {
	 }
	 
	 @Override
	 public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
		 List<FieldMessage> list = new ArrayList<>();

		 // inclua os testes aqui, inserindo erros na lista
		 if(objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !BR.isValidCPF(objDto.getCpfOuCnpj())) {
			 list.add(new FieldMessage("cpfOuCnpj", "CPF Inválido"));
		 }
		 if(objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(objDto.getCpfOuCnpj())) {
			 list.add(new FieldMessage("cpfOuCnpj", "CNPJ Inválido"));
		 }

		 
		 Cliente aux = repository.findByEmail(objDto.getEmail());
		 if(aux != null) {
			 list.add(new FieldMessage("email", "Email já existente"));
		 }
		 for (FieldMessage e : list) {
			 context.disableDefaultConstraintViolation();
			 context.buildConstraintViolationWithTemplate(e.getMessage())
			 .addPropertyNode(e.getFieldName()).addConstraintViolation();
		 }
		 return list.isEmpty();
	}
}