package br.com.alura.loja.testes;

import br.com.alura.loja.dao.CategoriaDao;
import br.com.alura.loja.dao.ClienteDao;
import br.com.alura.loja.dao.PedidoDao;
import br.com.alura.loja.dao.ProdutoDao;
import br.com.alura.loja.modelo.*;
import br.com.alura.loja.util.JPAUtil;
import br.com.alura.loja.vo.RelatorioDeVendasVo;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

public class CadastroDePedido {
    public static void main(String[] args) {
        popularBancoDeDados();

        EntityManager em = JPAUtil.getEntityManager();
        ProdutoDao produtoDao = new ProdutoDao(em);
        ClienteDao clienteDao = new ClienteDao(em);

        Produto produto = produtoDao.buscarPorId(1l);
        Produto produto2 = produtoDao.buscarPorId(2l);
        Produto produto3 = produtoDao.buscarPorId(3l);
        Cliente cliente = clienteDao.buscarPorId(1l);

        em.getTransaction().begin();

        Pedido pedido = new Pedido(cliente);
        Pedido pedido2 = new Pedido(cliente);
        pedido.adicionarItem(new ItemPedido(10, pedido, produto));
        pedido.adicionarItem(new ItemPedido(1, pedido, produto2));
        pedido2.adicionarItem(new ItemPedido(1, pedido2, produto3));

        PedidoDao pedidoDao = new PedidoDao(em);
        pedidoDao.cadastrar(pedido);
        pedidoDao.cadastrar(pedido2);

        em.getTransaction().commit();

        BigDecimal totalVendido = pedidoDao.valorTotalVendido();
        System.out.println("VALOR TOTAL: " + totalVendido);

        List<RelatorioDeVendasVo> relatorio = pedidoDao.relatorioDeVendas();
        relatorio.forEach(System.out::println);

        em.close();


    }

    private static void popularBancoDeDados() {
        Categoria celulares = new Categoria("CELULARES");
        Categoria consoles = new Categoria("CONSOLES");
        Categoria computadores = new Categoria("COMPUTADORES");
        Produto celular = new Produto("Redmi","Muito legal",new BigDecimal("800"), celulares);
        Produto console = new Produto("Playstation","Muito legal",new BigDecimal("1000"), consoles);
        Produto computador = new Produto("Notebook","Muito legal",new BigDecimal("2000"), computadores);

        Cliente cliente = new Cliente("Victor", "123456");

        EntityManager em = JPAUtil.getEntityManager();
        CategoriaDao categoriaDao = new CategoriaDao(em);
        ProdutoDao produtoDao = new ProdutoDao(em);
        ClienteDao clienteDao = new ClienteDao(em);


        em.getTransaction().begin();
        categoriaDao.cadastrar(celulares);
        categoriaDao.cadastrar(consoles);
        categoriaDao.cadastrar(computadores);
        produtoDao.cadastrar(celular);
        produtoDao.cadastrar(console);
        produtoDao.cadastrar(computador);
        clienteDao.cadastrar(cliente);
        em.getTransaction().commit();
        em.close();
    }
}
