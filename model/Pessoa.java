package model;

abstract class Pessoa {
    private String nome;
    private String sobreNome;
    private String telefone;
    private String email;
    private String endereco;
    private String bilheteIdentidade;

    public Pessoa(String nome, String sobreNome, String telefone, String email, String endereco, String bilheteIdentidade) {
        this.nome = nome;
        this.sobreNome = sobreNome;
        this.telefone = telefone;
        this.email = email;
        this.endereco = endereco;
        this.bilheteIdentidade = bilheteIdentidade;
    }

    public Pessoa() {

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobreNome() {
        return sobreNome;
    }

    public void setSobreNome(String sobreNome) {
        this.sobreNome = sobreNome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getBilheteIdentidade() {
        return bilheteIdentidade;
    }

    public void setBilheteIdentidade(String bilheteIdentidade) {
        this.bilheteIdentidade = bilheteIdentidade;
    }
}
