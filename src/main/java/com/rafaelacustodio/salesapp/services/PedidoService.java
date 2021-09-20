package com.rafaelacustodio.salesapp.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rafaelacustodio.salesapp.domain.ItemPedido;
import com.rafaelacustodio.salesapp.domain.PagamentoComBoleto;
import com.rafaelacustodio.salesapp.domain.Pedido;
import com.rafaelacustodio.salesapp.domain.enums.EstadoPagamento;
import com.rafaelacustodio.salesapp.repositories.ItemPedidoRepository;
import com.rafaelacustodio.salesapp.repositories.PagamentoRepository;
import com.rafaelacustodio.salesapp.repositories.PedidoRepository;
import com.rafaelacustodio.salesapp.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repository;
    
    @Autowired 
    private BoletoService boletoService;
    
    @Autowired 
    private PagamentoRepository pagamentoRepository;
    @Autowired
	private ProdutoService produtoService;

    @Autowired
	private ItemPedidoRepository itemPedidoRepository;
    
    public Pedido find(Integer id){
    	Optional<Pedido> obj = repository.findById(id);
    	return obj.orElseThrow(() -> new ObjectNotFoundException(
    	 "Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
    }
    
    @Transactional
    public Pedido insert(Pedido obj) {
    	obj.setId(null);
    	obj.setInstante(new Date());
    	obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
    	obj.getPagamento().setPedido(obj);
    	if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
    	pagamentoRepository.save(obj.getPagamento());
		for (ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setProduto(produtoService.find(ip.getProduto().getId()));
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(obj);
		}
		itemPedidoRepository.saveAll(obj.getItens());
		return obj;


    }
}