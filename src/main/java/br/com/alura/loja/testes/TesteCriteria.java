package br.com.alura.loja.testes;

import br.com.alura.loja.dao.CategoriaDao;
import br.com.alura.loja.dao.ProdutoDao;
import br.com.alura.loja.modelo.*;
import br.com.alura.loja.util.JPAUtil;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;

public class TesteCriteria {

    public static void main(String[] args) {
        popularBancoDeDados();
        EntityManager em = JPAUtil.getEntityManager();
        ProdutoDao produtoDao = new ProdutoDao(em);
        produtoDao.buscarPorParamentrosComCriteria("PS5", null, LocalDate.now());

    }

    private static void popularBancoDeDados() {
        Categoria celulares = new Categoria("CELULARES");
        Categoria consoles = new Categoria("CONSOLES");
        Categoria computadores = new Categoria("COMPUTADORES");
        Produto celular = new Produto("Redmi","Muito legal",new BigDecimal("800"), celulares);
        Produto console = new Produto("Playstation","Muito legal",new BigDecimal("1000"), consoles);
        Produto computador = new Produto("Notebook","Muito legal",new BigDecimal("2000"), computadores);


        EntityManager em = JPAUtil.getEntityManager();
        CategoriaDao categoriaDao = new CategoriaDao(em);
        ProdutoDao produtoDao = new ProdutoDao(em);

        em.getTransaction().begin();
        categoriaDao.cadastrar(celulares);
        categoriaDao.cadastrar(consoles);
        categoriaDao.cadastrar(computadores);
        produtoDao.cadastrar(celular);
        produtoDao.cadastrar(console);
        produtoDao.cadastrar(computador);
        em.getTransaction().commit();
        em.close();
    }
}
