package model;

import java.util.ArrayList;

public class Cliente extends Pessoa {
    private ArrayList<Orcamento> orcamentos;
    private ArrayList<Receita> receitas;

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
}
