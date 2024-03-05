package com.example.examenb1

import android.content.Context
import com.example.examen01.models.Actor
import com.example.examen01.models.DAO
import com.google.firebase.firestore.CollectionReference


import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.ktx.firestore



class ActorDAO(context: Context?) : DAO<Actor>(context) {
    private val databaseReference: CollectionReference =  Firebase.firestore.collection("actores")
    companion object{
        var listaLocal= mutableListOf<Actor>()

    }
    override fun add(actor: Actor) {

        actor.setId((System.currentTimeMillis() % 100000).toInt()) // Usar el setter para establecer el ID.
        val actorData = HashMap<String, Any>()
        actorData["name"] = actor.getName()
        actorData["age"] = actor.getAge().toString()
        actorData["hasOscar"] = actor.hasOscar().toString()
        actorData["money"] = actor.getMoney().toString()


        databaseReference.document(actor.getId().toString())
            .set(actorData)
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

    override fun edit(actor: Actor) {

        val actorData = HashMap<String, Any>()
        actorData["name"] = actor.getName()
        actorData["age"] = actor.getAge().toString()
        actorData["hasOscar"] = actor.hasOscar().toString()
        actorData["money"] = actor.getMoney().toString()

        databaseReference.document(actor.getId().toString())
            .set(actorData)
            .addOnSuccessListener {  }
            .addOnFailureListener {  }
    }

    override fun get(id: Int): Actor? {
        var actorAux:Actor?=null
        databaseReference.document(id.toString()).get().addOnSuccessListener{document ->
            val data = document.data
            val actorA= Actor(
                document.id.toInt(),
                data?.get("name") as String,
                (data.get("age") as String).toInt(),
                (data.get("hasOscar") as String).toBoolean(),
                (data.get("money") as String).toDouble()
            )
            actorAux=actorA
        }.addOnFailureListener{

        }

        if (actorAux==null){
            actorAux=listaLocal.find { it.getId()==id }
        }

        return actorAux
    }

    override fun getLista(): MutableList<Actor> {


        val listaActores:MutableList<Actor> = mutableListOf<Actor>()
        databaseReference.get().addOnSuccessListener {
                documents ->
            for (document in documents){
                val data = document.data

                val actorAux= Actor(
                    document.id.toInt(),
                    data?.get("name") as String,
                    (data.get("age") as String).toInt(),
                    (data.get("hasOscar") as String).toBoolean(),
                    (data.get("money") as String).toDouble()
                )

                listaActores.add(actorAux)
            }
            listaLocal=listaActores
        }.addOnFailureListener{ex ->
            println( ex.toString())

        }
        println( listaActores.size)


        return listaLocal
    }

    fun existe(id: Int): Boolean {
        var flag=false;
        databaseReference.document(id.toString())
            .get()
            .addOnSuccessListener { flag=true }
            .addOnFailureListener {  }

        if(!flag){
            flag=listaLocal.any({actor: Actor -> actor.getId()==id})
        }


        return flag
    }


}