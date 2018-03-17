package net.heroslender.DataBase;

import net.heroslender.data.Loja;

/**
 * Created by Heroslender.
 */
public interface Storage {
    void load();

    void unload();

    /**
     * Procurar a loja de um player pelo nome dele
     *
     * @param dono Nome do player
     * @return Loja do player
     */
    Loja get(String dono);

    void save(Loja loja);

    void saveVoto(Loja loja, String votador);

    void delete(Loja loja);
}
