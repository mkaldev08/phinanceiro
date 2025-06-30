package controller;

import model.Material;
import model.Material;
import model.Material;

import java.util.ArrayList;
import java.util.List;

public class MaterialController {
    private List<Material> materiais = new ArrayList<>();
    private static int lastId = 0;

    public Material buscarMaterial(int id) {
        return materiais.stream()
                .filter(m -> m.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void cadastrarMaterial(Material novoMaterial) {
        validarMaterial(novoMaterial);
        lastId += 1;
        novoMaterial.setId(lastId);
        this.materiais.add(novoMaterial);
        lastId = novoMaterial.getId();
    }

    public List<Material> listarTodosMateriais() {
        return new ArrayList<>(materiais);
    }

    public void validarMaterial(Material material) {
        if (material == null)
            throw new IllegalArgumentException("Material não pode ser nulo");
        else if (material.getDescricao() == null || material.getPrecoUnitario() < 0)
            throw new IllegalArgumentException("Verifique os dados");
    }

    public void atualizarMaterial(Material materialAtualizado) {
        validarMaterial(materialAtualizado);
        Material materialExistente = buscarMaterial(materialAtualizado.getId());
        if (materialExistente == null) {
            throw new IllegalArgumentException("Material não encontrado para atualização");
        }

        materialExistente.setDescricao(materialAtualizado.getDescricao());
        materialExistente.setPrecoUnitario(materialAtualizado.getPrecoUnitario());

    }

    public void removerMaterial(int id) {
        Material material = buscarMaterial(id);
        if (material != null) {
            materiais.remove(material);
        }

    }

}
