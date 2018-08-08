package com.heroslender.viptools

import org.bukkit.Location

import java.util.ArrayList

class Loja(val owner: String,
           var location: Location,
           val votos: List<String> = mutableListOf(),
           val placas: List<Location> = mutableListOf())
