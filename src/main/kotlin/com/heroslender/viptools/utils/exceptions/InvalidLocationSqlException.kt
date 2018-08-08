package com.heroslender.viptools.utils.exceptions

class InvalidLocationSqlException(message: String) : HeroSqlException("Failed to load location from database. $message")