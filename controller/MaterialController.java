package controller;

import model.Material;
import model.Servico;

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
}
