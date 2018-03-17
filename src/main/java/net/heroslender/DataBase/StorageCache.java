package net.heroslender.DataBase;

import net.heroslender.data.Loja;

import java.util.HashMap;
import java.util.Map;

public abstract class StorageCache implements Storage {
    // Cache
    private final Map<String, Loja> cache = new HashMap<>();

    public Loja get(String dono) {
        return cache.get(dono);
    }
}
