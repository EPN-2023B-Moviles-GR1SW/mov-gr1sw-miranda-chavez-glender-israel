package com.example.examenb1

import android.content.Context
import com.example.examen01.models.DAO
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDate
import com.example.examen01.models.Character

class CharacterDAO(context: Context?) : DAO<Character>(context) {

    private val databaseReference: CollectionReference =  Firebase.firestore.collection("characters")
    companion object{
        var listaLocalCharacters= mutableListOf<Character>()
        var listaLocalCharacterActor= mutableListOf<Character>()
    }

    override fun add(character: Character) {
        character.setId((System.currentTimeMillis() % 100000).toInt()) // Usar el setter para establecer el ID.
        val characterData = HashMap<String, Any>()
        characterData["name"] = character.getName()
        characterData["age"] = character.getAge().toString()
        characterData["movie"] = character.getMovie()
        characterData["alias"] = character.getAlias()
        characterData["firstActingDate"] = character.getFirstActingDate().toString()
        characterData["idActor"] = character.getIdActor().toString()


        databaseReference.document(character.getId().toString())
            .set(characterData)
            .addOnSuccessListener {  }
            .addOnFailureListener {  }
    }

    override fun delete(id: Int): Boolean {
        var flag=false;
        databaseReference.document(id.toString())
            .delete()
            .addOnSuccessListener { flag=true }
            .addOnFailureListener {  }
        return true
    }

    override fun edit(character: Character) {
        val characterData = HashMap<String, Any>()
        characterData["name"] = character.getName()
        characterData["age"] = character.getAge().toString()
        characterData["movie"] = character.getMovie()
        characterData["alias"] = character.getAlias()
        characterData["firstActingDate"] = character.getFirstActingDate().toString()
        characterData["idActor"] = character.getIdActor().toString()


        databaseReference.document(character.getId().toString())
            .set(characterData)
            .addOnSuccessListener {  }
            .addOnFailureListener {  }
    }

    override fun get(id: Int): Character? {
        var characterAux:Character?=null
        databaseReference.document(id.toString()).get().addOnSuccessListener{document ->
            val data = document.data
            val characterA= Character(
                document.id.toInt(),
                data?.get("name") as String,
                (data.get("age") as String).toInt(),
                data?.get("movie") as String,
                data?.get("alias") as String,
                LocalDate.parse(data.get("firstActingDate") as String),
                (data.get("idActor") as String).toInt()
            )
            characterAux=characterA
        }.addOnFailureListener{

        }

        if (characterAux==null){
            characterAux= listaLocalCharacters.find { it.getId()==id }
        }

        if (characterAux==null){
            characterAux= listaLocalCharacterActor.find { it.getId()==id }
        }

        return characterAux
    }

    override fun getLista(): MutableList<Character> {
        val listaCharacters:MutableList<Character> = mutableListOf<Character>()
        databaseReference.get().addOnSuccessListener {
                documents ->
            for (document in documents){
                val data = document.data
                val characterAux= Character(
                    document.id.toInt(),
                    data?.get("name") as String,
                    (data.get("age") as String).toInt(),
                    data?.get("movie") as String,
                    data?.get("alias") as String,
                    LocalDate.parse(data.get("firstActingDate") as String),
                    (data.get("idActor") as String).toInt()
                )

                listaCharacters.add(characterAux)
            }
            listaLocalCharacters =listaCharacters
        }.addOnFailureListener{ex ->
            println( ex.toString())

        }

        return listaLocalCharacters
    }

    fun getLista(actorId: Int): List<Character> {
        val listaCharacters:MutableList<Character> = mutableListOf<Character>()
        databaseReference.whereEqualTo("idActor",actorId.toString()).get().addOnSuccessListener {
                documents ->
            for (document in documents){
                val data = document.data
                val characterAux= Character(
                    document.id.toInt(),
                    data?.get("name") as String,
                    (data.get("age") as String).toInt(),
                    data?.get("movie") as String,
                    data?.get("alias") as String,
                    LocalDate.parse(data.get("firstActingDate") as String),
                    (data.get("idActor") as String).toInt()
                )

                listaCharacters.add(characterAux)
            }
            listaLocalCharacterActor =listaCharacters
        }.addOnFailureListener{ex ->
            println( ex.toString())

        }

        return listaLocalCharacterActor
    }
}
