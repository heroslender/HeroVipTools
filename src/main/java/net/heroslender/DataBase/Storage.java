package net.heroslender.DataBase;

import net.heroslender.Loja;
import org.bukkit.Location;

import java.util.List;
import java.util.Map;

/**
 * Created by Heroslender.
 */
public interface Storage {

    /**
     * Pegar todas as lojas do servidor
     * @return Todas as lojas do servidor
     */
    Map<String, Location> getLojas();

    List<Loja> getTopLojas();

    /**
     * Pegar os votos de uma loja
     * @param player ModuloLoja a pesquisar
     * @return int - Numero de votos na loja
     */
    int getVotos(String player);

    /**
     * Votar em uma loja
     * @param loja ModuloLoja onde vai votar
     * @param votador Player que vai votar
     * @return int - numero de votos da loja
     */
    int votar(String loja, String votador);

    void setLoja(String jogador, Location location);

    void delLoja(String player);
}
