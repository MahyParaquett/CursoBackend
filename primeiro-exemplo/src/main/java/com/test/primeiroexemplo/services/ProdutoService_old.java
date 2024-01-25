package com.test.primeiroexemplo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.primeiroexemplo.model.Produto;
import com.test.primeiroexemplo.repository.ProdutoRepository_old;

@Service
public class ProdutoService_old {
    
    @Autowired
    private ProdutoRepository_old produtoRepository;

    /**
     * Método para retornar uma lista de produtos
     * @return Lista de produtos
     */
    public List<Produto> obterTodos(){
        return produtoRepository.obterTodos();
    }

     /**
     * Método que retorna o produto encontrado pelo seu id
     * @param id do produto que será localizado.
     * @return retorna um produto caso ele seja encontrado.
     */
    public Optional<Produto> obterPorId(Integer id){
        return produtoRepository.obterPorId(id);
    }

    /**
 * Método para adicionar produto na lista.
 * @param produto que será adionado.
 * @return Retorna o produto que foi adicionado na lista.
 */
public Produto adcionar(Produto produto){
    //poderia ter alguma regra de negocio aqui para validar o produto.
    return produtoRepository.adcionar(produto);
}

/**
     * Método para deletar o produto por id
     * @param id do produto a ser deletados
     */
    public void deletar(Integer id){
       produtoRepository.deletar(id);
    }
    
    /**
 * Método para atualizar o produto na lista
 * @param produto que será atualizado
 * @param id do produto
 * @return Retorna o produto após atualizar a lista.
 */
public Produto atualizar(Integer id, Produto produto){
    //Poderia ter alguma validação no ID.
    produto.setId(id);

    return produtoRepository.atualizar(produto);
}


}
