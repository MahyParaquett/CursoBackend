package com.example.projetotdd.model;

public class Comissao {
    
    public Double calcular(Double valorVenda){
        return (valorVenda > 1000.00) ? valorVenda*0.15 : valorVenda*0.10;
    }
}
