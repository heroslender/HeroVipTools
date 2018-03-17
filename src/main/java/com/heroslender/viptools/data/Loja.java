package com.heroslender.viptools.data;

import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class Loja {
    private final String owner;
    private List<String> votos;
    private Location location;

    public Loja(String owner) {
        this.owner = owner;
        votos = new ArrayList<>();
    }

    /**
     * Pegar os votos de uma loja
     *
     * @param player Loja a pesquisar
     * @return int - Numero de votos na loja
     */
    int getVotos(String player) {
        return votos.size();
    }

    public String getOwner() {
        return owner;
    }
}
