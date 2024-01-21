package com.example.examen01.models

import java.time.LocalDate
import java.util.Date

class Character {
    private var id: Int = 0
    private var name: String = ""
    private var age: Int = 0
    private var movie: String = ""
    private var alias: String = ""
    private var firstActingDate: LocalDate? = null
    private var idActor: Int = 0

    constructor(
        id: Int,
        name: String,
        age: Int,
        movie: String,
        alias: String,
        firstActingDate: LocalDate?,
        idActor: Int
    ) {
        this.id = id
        this.name = name
        this.age = age
        this.movie = movie
        this.alias = alias
        this.firstActingDate = firstActingDate
        this.idActor = idActor
    }


    // Métodos set y get para id
    fun getId(): Int {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }

    // Métodos set y get para name
    fun getName(): String {
        return name
    }

    fun setName(name: String) {
        this.name = name
    }

    // Métodos set y get para age
    fun getAge(): Int {
        return age
    }

    fun setAge(age: Int) {
        this.age = age
    }

    // Métodos set y get para movie
    fun getMovie(): String {
        return movie
    }

    fun setMovie(movie: String) {
        this.movie = movie
    }

    // Métodos set y get para alias
    fun getAlias(): String {
        return alias
    }

    fun setAlias(alias: String) {
        this.alias = alias
    }

    // Métodos set y get para firstActingDate
    fun getFirstActingDate(): LocalDate? {
        return firstActingDate
    }

    fun setFirstActingDate(firstActingDate: LocalDate?) {
        this.firstActingDate = firstActingDate
    }

    // Métodos set y get para idActor
    fun getIdActor(): Int {
        return idActor
    }

    fun setIdActor(idActor: Int) {
        this.idActor = idActor
    }

    override fun toString(): String {
        return "============" +
                "id=$id \nname=$name \nage=$age \nmovie=$movie \nalias=$alias \nfirstActingDate=$firstActingDate)" +
                "\n============"
    }


}