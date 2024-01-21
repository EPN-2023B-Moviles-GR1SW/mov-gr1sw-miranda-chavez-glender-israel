package com.example.examen01.models

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate

class CharacterDAO(context: Context?) : DAO<Character>(context) {

    override fun onCreate(db: SQLiteDatabase?) {

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    override fun add(character: Character) {
        val baseDatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("id", character.getId())
        valoresAGuardar.put("name", character.getName())
        valoresAGuardar.put("age", character.getAge())
        valoresAGuardar.put("movie", character.getMovie())
        valoresAGuardar.put("alias", character.getAlias())
        valoresAGuardar.put("firstActingDate", character.getFirstActingDate().toString())

        baseDatosEscritura.insert("CHARACTER", null, valoresAGuardar)
        baseDatosEscritura.close()
    }

    override fun delete(id: Int): Boolean {
        val conexionEscritura = writableDatabase
        val parametrosConsultaDelete = arrayOf(id.toString())
        val resultadoEliminacion = conexionEscritura.delete(
            "CHARACTER",
            "id=?",
            parametrosConsultaDelete
        )
        conexionEscritura.close()
        return resultadoEliminacion != -1
    }

    override fun edit(character: Character) {
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("id", character.getId())
        valoresAActualizar.put("name", character.getName())
        valoresAActualizar.put("age", character.getAge())
        valoresAActualizar.put("movie", character.getMovie())
        valoresAActualizar.put("alias", character.getAlias())
        valoresAActualizar.put("firstActingDate", character.getFirstActingDate().toString())

        val parametrosConsultaActualizar = arrayOf(character.getId().toString())
        val resultadoActualizacion = conexionEscritura.update(
            "CHARACTER",
            valoresAActualizar,
            "id=?",
            parametrosConsultaActualizar
        )
        conexionEscritura.close()
    }


    override fun get(id: Int): Character? {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = """
            SELECT * FROM CHARACTER WHERE id = ?
        """.trimIndent()
        val parametrosConsultaLectura = arrayOf(id.toString())
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultaLectura,
            parametrosConsultaLectura
        )

        val characterEncontrado: Character?
        if (resultadoConsultaLectura.moveToFirst()) {
            val id = resultadoConsultaLectura.getInt(0)
            val name = resultadoConsultaLectura.getString(1)
            val age = resultadoConsultaLectura.getInt(2)
            val movie = resultadoConsultaLectura.getString(3)
            val alias = resultadoConsultaLectura.getString(4)
            val fechaActingStr = resultadoConsultaLectura.getString(5)
            val idActor = resultadoConsultaLectura.getInt(6)

            val firstActingDate = LocalDate.parse(fechaActingStr)
            characterEncontrado = Character(id, name, age, movie, alias, firstActingDate, idActor)
        } else {
            characterEncontrado = null
        }

        resultadoConsultaLectura.close()
        baseDatosLectura.close()

        return characterEncontrado
    }


    override fun getLista(): List<Character> {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = """
            SELECT * FROM CHARACTER
        """.trimIndent()

        val resultadoConsultaLectura = baseDatosLectura.rawQuery(scriptConsultaLectura, null)

        val arreglo = arrayListOf<Character>()

        if (resultadoConsultaLectura.moveToFirst()) {
            do {
                val id = resultadoConsultaLectura.getInt(0)
                val name = resultadoConsultaLectura.getString(1)
                val age = resultadoConsultaLectura.getInt(2)
                val movie = resultadoConsultaLectura.getString(3)
                val alias = resultadoConsultaLectura.getString(4)
                val fechaActingStr = resultadoConsultaLectura.getString(5)
                val idActor = resultadoConsultaLectura.getInt(6)

                val firstActingDate = LocalDate.parse(fechaActingStr)
                val characterEncontrado = Character(id, name, age, movie, alias, firstActingDate, idActor)
                arreglo.add(characterEncontrado)
            } while (resultadoConsultaLectura.moveToNext())
        }

        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return arreglo
    }


    fun getLista(actorId: Int): List<Character> {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = """
            SELECT * FROM CHARACTER WHERE idActor = ?
        """.trimIndent()

        val parametrosConsultaLectura = arrayOf(actorId.toString())
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultaLectura, parametrosConsultaLectura
        )

        val arreglo = arrayListOf<Character>()

        if (resultadoConsultaLectura.moveToFirst()) {
            do {
                val id = resultadoConsultaLectura.getInt(0)
                val name = resultadoConsultaLectura.getString(1)
                val age = resultadoConsultaLectura.getInt(2)
                val movie = resultadoConsultaLectura.getString(3)
                val alias = resultadoConsultaLectura.getString(4)
                val fechaActingStr = resultadoConsultaLectura.getString(5)
                val idActor = resultadoConsultaLectura.getInt(6)

                val firstActingDate = LocalDate.parse(fechaActingStr)
                val characterEncontrado = Character(id, name, age, movie, alias, firstActingDate, idActor)
                arreglo.add(characterEncontrado)
            } while (resultadoConsultaLectura.moveToNext())
        }

        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return arreglo
    }
}
