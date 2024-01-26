package com.test.primeiroexemplo.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.primeiroexemplo.model.Produto;
import com.test.primeiroexemplo.model.exception.ResourceNotFoundException;
import com.test.primeiroexemplo.repository.ProdutoRepository;
import com.test.primeiroexemplo.shared.ProdutoDTO;

@Service
public class ProdutoService {
    
    @Autowired
    private ProdutoRepository produtoRepository;

    /**
     * Método para retornar uma lista de produtos
     * @return Lista de produtos
     */
    public List<ProdutoDTO> obterTodos(){
        //Retorna uma lista de produto model
       List<Produto> produtos = produtoRepository.findAll();

       //Varre a lista com o stream, pega da varredura o que é produto pelo map 
       //e converte para objeto produtoDTO, pega e transforma en lista.
       return produtos.stream()
       .map(produto -> new ModelMapper().map(produto, ProdutoDTO.class))
       .collect(Collectors.toList());
    }

     /**
     * Método que retorna o produto encontrado pelo seu id
     * @param id do produto que será localizado.
     * @return retorna um produto caso ele seja encontrado.
     */
    public Optional<ProdutoDTO> obterPorId(Integer id){
        //Obtendo optional produto pelo id
        Optional<Produto>produto = produtoRepository.findById(id);

        //Se não encontrar lanca una exception
        if(produto.isEmpty()){
            throw new ResourceNotFoundException("Produto com o id " + id + " não encontrado");
        }

        //Convertendo meu optional de produto de produto em produtoDTO
        ProdutoDTO dto = new ModelMapper().map(produto.get(), ProdutoDTO.class);

        //Criando e retornando um optional de produtoDTO
        return Optional.of(dto);
    }

    /**
 * Método para adicionar produto na lista.
 * @param produto que será adionado.
 * @return Retorna o produto que foi adicionado na lista.
 */
public ProdutoDTO adcionar(ProdutoDTO produtoDto){
    //poderia ter alguma regra de negocio aqui para validar o produto.
    //Zerar o id pra cadastrar
    produtoDto.setId(null);

    //Criar um objeto de mapeamento
   ModelMapper mapper = new ModelMapper();

   //Converter o ProdutoDTO em um produto
   Produto produto = mapper.map(produtoDto, Produto.class);

   //Salvar o produto no banco
    produto = produtoRepository.save(produto);

    produtoDto.setId(produto.getId());

   //retornar o produtoDTO atualizado
    return produtoDto;
}

/**
     * Método para deletar o produto por id
     * @param id do produto a ser deletados
     */
    public void deletar(Integer id){
        //verificar se o produto existe
        Optional<Produto>produto =  produtoRepository.findById(id);

        //Se não existir lança uma exception
        if(produto.isEmpty()){
            throw new ResourceNotFoundException("Não foi possivel deletar o produto com o id: "+ id + " - Produto não existe.");
        }
        //deletar o produto pelo id
       produtoRepository.deleteById(id);
    }
    
    /**
 * Método para atualizar o produto na lista
 * @param produto que será atualizado
 * @param id do produto
 * @return Retorna o produto após atualizar a lista.
 */
public ProdutoDTO atualizar(Integer id, ProdutoDTO produtoDto){
  //passar o id para o produtoDto
produtoDto.setId(id);

  //Criar um objeto de mapeamento
ModelMapper mapper = new ModelMapper();

  //Converter o produtoDto em um Produto
Produto produto = mapper.map(produtoDto, Produto.class);

  //Atualizar o produto no Banco de dados
produtoRepository.save(produto);

  //Retornar o produtoDto atualizado
return produtoDto;
}


}
