package com.qwertcardo.springcurso.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.qwertcardo.springcurso.domain.PagamentoComBoleto;

@Service
public class BoletoService {

	public void preencherPagamentoComBoleto(PagamentoComBoleto pagamento, Date instanteDoPedido) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, 7);
		pagamento.setDataVencimento(cal.getTime());
	}
}
