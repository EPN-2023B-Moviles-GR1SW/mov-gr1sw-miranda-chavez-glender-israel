package com.example.examen01.models

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import java.time.LocalDate

class ActorDAO(context: Context?) : DAO<Actor>(context) {

    override fun onCreate(db: SQLiteDatabase?) {
        val scriptSQLCrearTablaActor =
            """
                CREATE TABLE ACTOR(
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name VARCHAR(50),
                    age INTEGER,
                    hasOscar INTEGER,
                    money REAL
                )
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaActor)
        val crearTablaCharacter =
            """
                CREATE TABLE CHARACTER(
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name VARCHAR(50),
                    age INTEGER,
                    movie VARCHAR(50),
                    alias VARCHAR(50),
                    firstActingDate VARCHAR(50),
                    idActor INTEGER,
                    FOREIGN KEY(idActor) REFERENCES ACTOR(id) ON DELETE CASCADE
                )
            """.trimIndent()
        db?.execSQL(crearTablaCharacter)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Lógica de actualización de base de datos (si es necesario)
    }

    override fun add(actor: Actor) {
        val baseDatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("name", actor.getName())
        valoresAGuardar.put("age", actor.getAge())
        valoresAGuardar.put("hasOscar", if (actor.hasOscar()) 1 else 0)
        valoresAGuardar.put("money", actor.getMoney())

        baseDatosEscritura.insert("ACTOR", null, valoresAGuardar)
        baseDatosEscritura.close()
    }

    override fun delete(id: Int): Boolean {
        val conexionEscritura = writableDatabase
        val parametrosConsultaDelete = arrayOf(id.toString())
        val resultadoEliminacion = conexionEscritura.delete(
            "ACTOR",
            "id=?",
            parametrosConsultaDelete
        )
        conexionEscritura.close()
        return resultadoEliminacion != -1
    }

    override fun edit(actor: Actor) {
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("name", actor.getName())
        valoresAActualizar.put("age", actor.getAge())
        valoresAActualizar.put("hasOscar", if (actor.hasOscar()) 1 else 0)
        valoresAActualizar.put("money", actor.getMoney())

        val parametrosConsultaActualizar = arrayOf(actor.getId().toString())
        val resultadoActualizacion = conexionEscritura.update(
            "ACTOR",
            valoresAActualizar,
            "id=?",
            parametrosConsultaActualizar
        )
        conexionEscritura.close()
    }

    override fun get(id: Int): Actor? {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = """
            SELECT * FROM ACTOR WHERE id = ?
        """.trimIndent()
        val parametrosConsultaLectura = arrayOf(id.toString())
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultaLectura,
            parametrosConsultaLectura
        )

        val actorEncontrado: Actor?
        if (resultadoConsultaLectura.moveToFirst()) {
            val id = resultadoConsultaLectura.getInt(0)
            val name = resultadoConsultaLectura.getString(1)
            val age = resultadoConsultaLectura.getInt(2)
            val hasOscar = resultadoConsultaLectura.getInt(3) != 0
            val money = resultadoConsultaLectura.getDouble(4)

            actorEncontrado = Actor(id, name, age, hasOscar, money)
        } else {
            actorEncontrado = null
        }

        resultadoConsultaLectura.close()
        baseDatosLectura.close()

        return actorEncontrado
    }

    override fun getLista(): List<Actor> {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = """
            SELECT * FROM ACTOR
        """.trimIndent()

        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultaLectura,
            null
        )

        val listaActores = mutableListOf<Actor>()

        while (resultadoConsultaLectura.moveToNext()) {
            val id = resultadoConsultaLectura.getInt(0)
            val name = resultadoConsultaLectura.getString(1)
            val age = resultadoConsultaLectura.getInt(2)
            val hasOscar = resultadoConsultaLectura.getInt(3) != 0
            val money = resultadoConsultaLectura.getDouble(4)


            val autor = Actor(id, name, age, hasOscar, money)
            listaActores.add(autor)
        }

        resultadoConsultaLectura.close()
        baseDatosLectura.close()

        return listaActores
    }

    fun existe(id: Int): Boolean {
        return get(id) != null
    }
}