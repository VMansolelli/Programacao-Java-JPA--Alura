package br.com.alura.loja.testes;

import br.com.alura.loja.dao.CategoriaDao;
import br.com.alura.loja.dao.ClienteDao;
import br.com.alura.loja.dao.PedidoDao;
import br.com.alura.loja.dao.ProdutoDao;
import br.com.alura.loja.modelo.*;
import br.com.alura.loja.util.JPAUtil;

import javax.persistence.EntityManager;
import java.math.BigDecimal;

public class PerformaceConsultas {
    public static void main(String[] args) {
        popularBancoDeDados();
        EntityManager em = JPAUtil.getEntityManager();

        PedidoDao pedidoDao = new PedidoDao(em);
        Pedido pedido = pedidoDao.buscarPedidoComCliente(1l);

        em.close();

        System.out.println(pedido.getCliente().getNome());

    }

    private static void popularBancoDeDados() {
        Categoria celulares = new Categoria("CELULARES");
        Categoria consoles = new Categoria("CONSOLES");
        Categoria computadores = new Categoria("COMPUTADORES");
        Produto celular = new Produto("Redmi","Muito legal",new BigDecimal("800"), celulares);
        Produto console = new Produto("Playstation","Muito legal",new BigDecimal("1000"), consoles);
        Produto computador = new Produto("Notebook","Muito legal",new BigDecimal("2000"), computadores);

        Cliente cliente = new Cliente("Victor", "123456");

        Pedido pedido = new Pedido(cliente);
        pedido.adicionarItem(new ItemPedido(10, pedido, celular));
        pedido.adicionarItem(new ItemPedido(1, pedido, console));

        Pedido pedido2 = new Pedido(cliente);
        pedido2.adicionarItem(new ItemPedido(1, pedido2, computador));

        EntityManager em = JPAUtil.getEntityManager();
        CategoriaDao categoriaDao = new CategoriaDao(em);
        ProdutoDao produtoDao = new ProdutoDao(em);
        ClienteDao clienteDao = new ClienteDao(em);
        PedidoDao pedidoDao = new PedidoDao(em);

        em.getTransaction().begin();
        categoriaDao.cadastrar(celulares);
        categoriaDao.cadastrar(consoles);
        categoriaDao.cadastrar(computadores);
        produtoDao.cadastrar(celular);
        produtoDao.cadastrar(console);
        produtoDao.cadastrar(computador);
        clienteDao.cadastrar(cliente);
        pedidoDao.cadastrar(pedido);
        pedidoDao.cadastrar(pedido2);
        em.getTransaction().commit();
        em.close();
    }
}
