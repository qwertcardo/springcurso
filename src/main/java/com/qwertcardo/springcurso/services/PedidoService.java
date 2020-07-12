package com.qwertcardo.springcurso.services;

import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qwertcardo.springcurso.domain.ItemPedido;
import com.qwertcardo.springcurso.domain.PagamentoComBoleto;
import com.qwertcardo.springcurso.domain.Pedido;
import com.qwertcardo.springcurso.domain.enums.EstadoPagamento;
import com.qwertcardo.springcurso.repositories.ItemPedidoRepository;
import com.qwertcardo.springcurso.repositories.PagamentoRepository;
import com.qwertcardo.springcurso.repositories.PedidoRepository;
import com.qwertcardo.springcurso.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo;
	@Autowired
	private PagamentoRepository pagamentoRepository;
	@Autowired
	private ItemPedidoRepository itemRepository;
	
	@Autowired
	private ProdutoService produtoService;
	@Autowired
	private BoletoService boletoService;
	
	public Pedido findId(Integer id) {
		Optional<Pedido> pedido = repo.findById(id);
		return pedido.orElseThrow(() -> new ObjectNotFoundException(
				"Pedido Não Encontrado /Id: " + id + " /Tipo Referenciado: " + Pedido.class.getName()));
	}
	
	@Transactional
	public Pedido insert(Pedido pedido) {
		pedido.setId(null);
		pedido.setInstante(new Date());
		pedido.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		pedido.getPagamento().setPedido(pedido);
		
		if(pedido.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagamento = (PagamentoComBoleto) pedido.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagamento, pedido.getInstante());
		}
		pedido = repo.save(pedido);
		pagamentoRepository.save(pedido.getPagamento());
		
		for(ItemPedido itens : pedido.getItens()) {
			itens.setDesconto(0.0);
			itens.setPreco(produtoService.findId(itens.getProduto().getId()).getPreco());
			itens.setPedido(pedido);
		}
		itemRepository.saveAll(pedido.getItens());
		return pedido;
	}
}
