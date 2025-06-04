package model;

import java.util.ArrayList;

public class Cliente extends Pessoa {
    private int id;
    private ArrayList<Orcamento> orcamentos;
    private ArrayList<Receita> receitas;

    public Cliente(String nome, String sobreNome, String telefone,
                   String email, String endereco, String bilheteIdentidade) {
        super(nome, sobreNome, telefone, email, endereco, bilheteIdentidade);
    }

    public Cliente() {
        super();
    }

    public ArrayList<Orcamento> getOrcamentos() {
        return orcamentos;
    }

    public void setOrcamentos(ArrayList<Orcamento> orcamentos) {
        this.orcamentos = orcamentos;
    }

    public ArrayList<Receita> getReceitas() {
        return receitas;
    }

    public void setReceitas(ArrayList<Receita> receitas) {
        this.receitas = receitas;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
