package com.example.examen01.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.example.examen01.R
import com.example.examen01.bdd.BDD
import com.example.examen01.models.ActorDAO
import com.example.examen01.models.Character
import com.example.examen01.models.CharacterDAO
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class FormularioCharacter : AppCompatActivity() {
    val characterDAO:CharacterDAO= BDD.characterDAO!!
    val actorDAO: ActorDAO = BDD.actorDAO!!
    var id: Int? =null
    var idActor: Int? =null
    val callback = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_character)
        id=intent.getIntExtra("id", -1)
        idActor=intent.getIntExtra("idActor", -1)


        val botonAceptar = findViewById<Button>(R.id.btn_aceptar_character)
        val botonCancelar = findViewById<Button>(R.id.btn_cancelar_character)
        val campoNombreCharacter = findViewById<EditText>(R.id.et_nombre_character)
        val campoEdadCharacter = findViewById<EditText>(R.id.et_edad_character)
        val campoPeliculaCharacter = findViewById<EditText>(R.id.et_pelicula_character)
        val campoAliasCharacter = findViewById<EditText>(R.id.et_alias_character)
        val campoFADateCharacter = findViewById<EditText>(R.id.et_FADate_character)
        val campoActor = findViewById<EditText>(R.id.et_actor_character)

        val textoTop = findViewById<TextView>(R.id.tv_top_character)

        botonCancelar.setOnClickListener {
            onBackPressed()
        }

        if (id != -1) {
            val character = characterDAO.get(id!!)
            if (character != null) {
                textoTop.text = "Editar Character"

                campoNombreCharacter.setText(character.getName())
                campoEdadCharacter.setText(character.getAge().toString())
                campoPeliculaCharacter.setText(character.getMovie())
                campoAliasCharacter.setText(character.getAlias())
                campoFADateCharacter.setText(
                    (character.getFirstActingDate()?.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))))

                campoActor.setText(character.getIdActor().toString())

                botonAceptar.setOnClickListener {
                    try {
                        val nombreCharacter = campoNombreCharacter.text.toString()
                        val edadCharacter = campoEdadCharacter.text.toString()
                        val peliculaCharacter = campoPeliculaCharacter.text.toString()
                        val aliasCharacter = campoAliasCharacter.text.toString()
                        val FADateCharacter = campoFADateCharacter.text.toString()
                        val idActor = campoActor.text.toString()



                        if (nombreCharacter.isNotBlank() &&
                            edadCharacter.isNotBlank() &&
                            peliculaCharacter.isNotBlank() &&
                            aliasCharacter.isNotBlank() &&
                            FADateCharacter.isNotBlank() &&
                            idActor.isNotBlank() &&
                            actorDAO.existe(idActor.toInt())
                        ) {
                            character.setName(nombreCharacter)
                            character.setAge(edadCharacter.toInt())
                            character.setMovie(peliculaCharacter)
                            character.setAlias(aliasCharacter)
                            character.setFirstActingDate(
                                LocalDate.parse(
                                    FADateCharacter,
                                    DateTimeFormatter.ofPattern("dd/MM/yyyy")
                                )
                            )


                            character.setIdActor(idActor.toInt())
                            characterDAO.edit(character)
                            abrirDialogoYVolver("Character actualizado correctamente")
                        } else {
                            if (!actorDAO.existe(idActor.toInt())) {
                                abrirDialogo("El actor no existe")
                            } else {
                                abrirDialogo("Faltan datos por ingresar")
                            }
                        }
                    } catch (e: Exception) {
                        abrirDialogo("Los datos no están en el formato correcto")
                    }
                }
            } else {
                abrirDialogo("El character no existe")
            }
        } else {
            textoTop.text = "Crear Character"

            botonAceptar.setOnClickListener {
                try {
                    val nombreCharacter = campoNombreCharacter.text.toString()
                    val edadCharacter = campoEdadCharacter.text.toString()
                    val peliculaCharacter = campoPeliculaCharacter.text.toString()
                    val aliasCharacter = campoAliasCharacter.text.toString()
                    val FADateCharacter = campoFADateCharacter.text.toString()
                    val idActor = campoActor.text.toString()

                    if (nombreCharacter.isNotBlank() &&
                        edadCharacter.isNotBlank() &&
                        peliculaCharacter.isNotBlank() &&
                        aliasCharacter.isNotBlank() &&
                        FADateCharacter.isNotBlank() &&
                        idActor.isNotBlank() &&
                        actorDAO.existe(idActor.toInt())
                    ) {
                        val character = Character(
                            0,
                            nombreCharacter,
                            edadCharacter.toInt(),
                            peliculaCharacter,
                            aliasCharacter,
                            LocalDate.parse(FADateCharacter, DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                            idActor.toInt()

                        )

                        characterDAO.add(character)
                        abrirDialogo("Character registrado exitosamente")

                        campoNombreCharacter.setText("")
                        campoEdadCharacter.setText("")
                        campoPeliculaCharacter.setText("")
                        campoAliasCharacter.setText("")
                        campoFADateCharacter.setText("")
                        campoActor.setText("")

                    } else {
                        if (!actorDAO.existe(idActor.toInt())) {
                            abrirDialogo("El actor no existe")
                        } else {
                            abrirDialogo("Faltan datos por ingresar")
                        }
                    }
                } catch (e: Exception) {
                    abrirDialogo("Los datos no están en el formato correcto")
                }
            }
        }
    }

    private fun abrirDialogoYVolver(cadena: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(cadena)
        builder.setPositiveButton("Aceptar") { dialog, _ ->
            abrirActividadConParametros(CharacterActivity::class.java)
            dialog.dismiss()
        }

        val dialogo = builder.create()
        dialogo.setCancelable(false)
        dialogo.show()
    }

    private fun abrirDialogo(cadena: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(cadena)
        builder.setPositiveButton("Aceptar") { dialog, _ ->
            dialog.dismiss()
        }

        val dialogo = builder.create()
        dialogo.setCancelable(false)
        dialogo.show()
    }

    fun abrirActividadConParametros(clase: Class<*>) {
        val intentExplicito = Intent(this, clase)
        intentExplicito.putExtra("id", idActor)
        callback.launch(intentExplicito)
    }
}

