package com.example.projetotdd.controller;


import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.projetotdd.model.Produto;
import com.example.projetotdd.service.ProdutoService;
import com.fasterxml.jackson.databind.ObjectMapper;



@WebMvcTest
public class ProdutoControllerTest {
    //MockMvc
    //Mockito
    //Mock
    @Autowired
    private ProdutoController produtoController;

    @MockBean
    private ProdutoService produtoService;

    @Autowired
    private MockMvc MockMvc;

    @BeforeEach
    public void setup(){
    //aqui será executado antes de cada caso de teste
        this.MockMvc = MockMvcBuilders.standaloneSetup(produtoController).build();
    }

    @Test
    public void deve_retornar_status_200_OK_ao_chamar_o_metodo_obter_todos_os_produtos() throws Exception{
        //Arrenge
        var requestBuilder = MockMvcRequestBuilders.get("/api/produtos");
        List<Produto> produtos = new ArrayList<Produto>();
        when(this.produtoService.listarTodos()).thenReturn(produtos);

        //Act
        this.MockMvc.perform(requestBuilder)
        
        //Assert
        .andExpect(MockMvcResultMatchers.status()
                     .isOk());

    }

    @Test
    public void deve_retornar_o_produto_por_id() throws Exception{
        //Arrenge
            //Criei um produto
        Produto  produto = new Produto();
        produto.setId(2l);
        produto.setNome("Martelo");
        produto.setQuantidade(10);

            //Transformei em um optional
        Optional<Produto> optProduto = Optional.of(produto);

            //Fiz a requisição
        var requestBuilder = MockMvcRequestBuilders.get("/api/produtos/2");
        
             //Mockei o service
        when(this.produtoService.listarPorId(2l)).thenReturn(optProduto);

        //Act
        this.MockMvc.perform(requestBuilder)
        
        //Assert
        //Compara se o valor do id que ta vindo no Json é o mesmo que o valor que eu passei
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(2l));
    }

    @Test
    public void deve_adicionar_o_produto() throws Exception{
        //Arrenge  
        //Criando o produto a ser adicionado
        Produto  produto = new Produto();
        produto.setNome("Martelo");
        produto.setQuantidade(10);

        //To pegando o produto acima e to transformando em json          
        String json = new ObjectMapper().writeValueAsString(produto);

        //Fazendo a requisição e passando o body
        var requestBuilder = MockMvcRequestBuilders.post("/api/produtos")
        .content(json)
        .contentType(MediaType.APPLICATION_JSON);
        
        //Adicionando o id ao produto que iremos retornar
        produto.setId(2l);
        when(this.produtoService.adicionar(produto)).thenReturn(produto);

        //Act
        this.MockMvc.perform(requestBuilder)
        
        //Assert
        //Compara se o valor do id que ta vindo no Json é o mesmo que o valor que eu passei
        .andExpect(MockMvcResultMatchers.status().isCreated());
    }
}
