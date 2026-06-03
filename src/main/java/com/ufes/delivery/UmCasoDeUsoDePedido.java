package com.ufes.delivery;

import com.ufes.delivery.desconto.pedido.AplicadorCupomPedidoService;
import com.ufes.delivery.desconto.taxa.entrega.CalculadoraTaxaDescontoPedidoService;
import com.ufes.delivery.model.Cliente;
import com.ufes.delivery.model.CupomDescontoPedido;
import com.ufes.delivery.model.Item;
import com.ufes.delivery.model.Pedido;
import com.ufes.delivery.repository.CupomRepositoryEmMemoria;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import ufes.br.auditoria.model.LogRegistro;
import ufes.br.auditoria.service.ILogService;
import ufes.br.auditoria.service.JsonlLogService;
import ufes.br.auditoria.util.UsuarioLogadoService;

public class UmCasoDeUsoDePedido {

    public static void main(String[] args) {
        Cliente cliente = new Cliente("Maria", "Ouro", 1, "Limoeiro", "Cidade Maravilhosa", "Castelo");

        LocalDateTime dataPedido = LocalDateTime.now();
        Long codigoPedido = 1L;
        Pedido pedido = new Pedido(codigoPedido, dataPedido, cliente);

        Item item1 = new Item("Caderno", 2, 10.50, "Educacao");
        Item item2 = new Item("Borracha", 5, 4.25, "Educacao");
        Item item3 = new Item("Biscoito", 4, 5.80, "Alimentacao");
        Item item4 = new Item("Pao", 2, 1.50, "Alimentacao");
        Item item5 = new Item("Livro", 2, 40.20, "Lazer");
        Item item6 = new Item("Jogo", 1, 45.90, "Lazer");

        pedido.adicionarItem(item1);
        pedido.adicionarItem(item2);
        pedido.adicionarItem(item3);
        pedido.adicionarItem(item4);
        pedido.adicionarItem(item5);
        pedido.adicionarItem(item6);

        CalculadoraTaxaDescontoPedidoService calculadoraDeDesconto = new CalculadoraTaxaDescontoPedidoService();
        calculadoraDeDesconto.calcularDesconto(pedido);

        CupomRepositoryEmMemoria cupomRepository = new CupomRepositoryEmMemoria();
        cupomRepository.adicionarCupom(
                new CupomDescontoPedido("VALIDOHOJE", 15.0, dataPedido.minusDays(1), dataPedido.plusDays(1)));

        AplicadorCupomPedidoService aplicadorCupomService = new AplicadorCupomPedidoService(cupomRepository);

        LocalDateTime dataHoraAplicacaoCupom = LocalDateTime.now();

        aplicadorCupomService.aplicarCupom(pedido, "VALIDOHOJE", dataHoraAplicacaoCupom);

        try {
            aplicadorCupomService.aplicarCupom(pedido, "CUPOMINEXISTENTE", dataHoraAplicacaoCupom);
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }

        pedido.calcularValorTotal();

        ILogService logService = new JsonlLogService();
        
        /*
        ILogService logService = new CsvLogService();
        ILogService logService = new XmlLogService();
        
        */

        LogRegistro registro
                = new LogRegistro(
                        UsuarioLogadoService.getNomeUsuario(),
                        LocalDate.now().format(
                                DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                        LocalTime.now().format(
                                DateTimeFormatter.ofPattern("HH:mm:ss")),
                        pedido.getCodigoPedido(),
                        "Calculo do valor total do pedido (calcularValorTotal)",
                        pedido.getCliente().getNome()
                );

        logService.registrar(registro);

        System.out.println(pedido);

    }
}
