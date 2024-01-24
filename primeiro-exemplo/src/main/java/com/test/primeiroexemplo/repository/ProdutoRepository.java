package com.test.primeiroexemplo.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.test.primeiroexemplo.model.Produto;
import com.test.primeiroexemplo.model.exception.ResourceNotFoundException;

@Repository
public class ProdutoRepository {
    
    private List<Produto> produtos = new ArrayList<Produto>();
    private Integer ultimoId = 0;

    /**
     * Método para retornar uma lista de produtos
     * @return Lista de produtos
     */
    public List<Produto> obterTodos(){ 
        return produtos;
    }

    /**
     * Método que retorna o produto encontrado pelo seu id
     * @param id do produto que será localizado.
     * @return retorna um produto caso ele seja encontrado.
     */
    public Optional<Produto> obterPorId(Integer id){
        return produtos
                .stream()
                .filter(produto -> produto.getId() == id)
                .findFirst();
    }

/**
 * Método para adicionar produto na lista.
 * @param produto que será adionado.
 * @return Retorna o produto que foi adicionado na lista.
 */
    public Produto adcionar(Produto produto){

        ultimoId++;

        produto.setId(ultimoId);
        produtos.add(produto);

        return produto;
    }

    /**
     * Método para deletar o produto por id
     * @param id do produto a ser deletados
     */
public void deletar(Integer id){
    produtos.removeIf(produto -> produto.getId()== id);
}

/**
 * Método para atualizar o produto na lista
 * @param produto que será atualizado
 * @return Retorna o produto após atualizar a lista.
 */
public Produto atualizar(Produto produto){
    //Encontrar o produto na lista
    Optional<Produto> produtoEncontrado = obterPorId(produto.getId());

    if(produtoEncontrado.isEmpty()){
        throw new ResourceNotFoundException("Produto não encontrado");
    }

    //Eu tenho que remover o prouto antigo da lista
    deletar(produto.getId());

    //Depois eu ponho o novo produto atualizado na lista.
    produtos.add(produto);

    return produto;
}

}
