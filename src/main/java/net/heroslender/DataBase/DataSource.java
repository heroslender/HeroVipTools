package net.heroslender.DataBase;

import org.bukkit.Location;

import java.util.Map;

/**
 * Created by Heroslender.
 */
public interface DataSource {

    /**
     * Pegar todas as lojas do servidor
     * @return Todas as lojas do servidor
     */
    Map<String, Location> getLojas();

    /**
     * Pegar os votos de uma loja
     * @param player Loja a pesquisar
     * @return int - Numero de votos na loja
     */
    int getVotos(String player);

    /**
     * Votar em uma loja
     * @param loja Loja onde vai votar
     * @param votador Player que vai votar
     * @return int - numero de votos da loja
     */
    int votar(String loja, String votador);

    void setLoja(String player, Location location);

    void delLoja(String player);
}
