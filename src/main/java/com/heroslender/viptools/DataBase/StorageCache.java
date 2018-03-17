package com.heroslender.viptools.DataBase;

import com.heroslender.viptools.data.Loja;

import java.util.HashMap;
import java.util.Map;

public abstract class StorageCache implements Storage {
    // Cache
    private final Map<String, Loja> cache = new HashMap<>();

    @Override
    public Loja get(String dono) {
        return cache.get(dono);
    }
}
