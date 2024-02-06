package br.com.teste.animaisms.view.model;

public class AnimalModeloAlteracao {
    private Integer id;
    private Integer nome;
    private Integer idade;
    private String raca;
    private Boolean vivo;

    //#region Get / Set
   
    

    public Integer getIdade() {
        return idade;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNome() {
        return nome;
    }

    public void setNome(Integer nome) {
        this.nome = nome;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public Boolean getVivo() {
        return vivo;
    }

    public void setVivo(Boolean vivo) {
        this.vivo = vivo;
    }
    //#endregion
}
