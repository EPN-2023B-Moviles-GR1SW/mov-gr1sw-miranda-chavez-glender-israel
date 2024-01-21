package com.example.examen01.models

import java.time.LocalDate

class Actor {
    private var id: Int = 0
    private var name: String = ""
    private var age: Int = 0
    private var hasOscar: Boolean = true
    private var money: Double = 0.0

    constructor(id: Int, name: String, age: Int, hasOscar: Boolean, money: Double) {
        this.id = id
        this.name = name
        this.age = age
        this.hasOscar = hasOscar
        this.money = money
    }


    fun getId(): Int {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun getName(): String {
        return name
    }

    fun setName(name: String) {
        this.name = name
    }

    fun getAge(): Int {
        return age
    }

    fun setAge(age: Int) {
        this.age = age
    }

    fun hasOscar(): Boolean {
        return hasOscar
    }

    fun setHasOscar(hasOscar: Boolean) {
        this.hasOscar = hasOscar
    }

    fun getMoney(): Double {
        return money
    }

    fun setMoney(money: Double) {
        this.money = money
    }



    override fun toString(): String {
        return "Actor(id=$id, name='$name', age=$age, hasOscar=$hasOscar, money=$money)"
    }


}



