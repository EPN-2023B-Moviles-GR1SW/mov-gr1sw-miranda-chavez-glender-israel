package com.example.examen01.bdd

import com.example.examen01.models.ActorDAO
import com.example.examen01.models.CharacterDAO

class BDD {
    companion object{
        var actorDAO: ActorDAO?=null
        var characterDAO: CharacterDAO?=null
    }
}