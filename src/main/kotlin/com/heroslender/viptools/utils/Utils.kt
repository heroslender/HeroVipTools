package com.heroslender.viptools.utils

import com.heroslender.viptools.utils.exceptions.InvalidLocationSqlException
import org.bukkit.Bukkit
import org.bukkit.Location
import java.sql.ResultSet

@Throws(InvalidLocationSqlException::class)
fun ResultSet.getLocation(): Location {
    val worldStr = this.getString("world") ?: throw InvalidLocationSqlException("World is not saved!")

    val world = Bukkit.getWorld(worldStr)
            ?: throw InvalidLocationSqlException("The world \"$worldStr\" could not be found.")

//    val yaw = getFloat("yaw")
    return Location(
            world,
            this.getDouble("x"),
            this.getDouble("y"),
            this.getDouble("z"),
            this.getFloat("yaw"),
            this.getFloat("pitch")
    )
}