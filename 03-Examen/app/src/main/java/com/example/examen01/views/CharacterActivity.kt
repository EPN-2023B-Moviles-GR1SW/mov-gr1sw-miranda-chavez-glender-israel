package com.example.examen01.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.example.examen01.R
import com.example.examen01.bdd.BDD
import com.example.examen01.models.Character
import com.example.examen01.models.CharacterDAO

class CharacterActivity : AppCompatActivity() {
    lateinit var adaptador: ArrayAdapter<Character>
    lateinit var listView:ListView
    var idItemSeleccionado = 0
    val arrayAux=arrayListOf<Character>()
    lateinit var characterDAO : CharacterDAO


    var id: Int? = -1
    val callback = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character)

        characterDAO= BDD.characterDAO!!

        id = intent.getIntExtra("id", -1)
        listView = findViewById<ListView>(R.id.lv_characters)

        if (id != -1) {

            adaptador = ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                characterDAO.getLista(id!!)
            )
            listView.adapter = adaptador

            val botonCrear = findViewById<Button>(R.id.btn_crear_character)
            botonCrear.setOnClickListener {
                irActividad(FormularioCharacter::class.java)
            }

            registerForContextMenu(listView)
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_character, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        idItemSeleccionado = id
    }

    fun abrirDialogoEliminar() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("¿Está seguro de que desea eliminar el character?")
        builder.setPositiveButton("Sí") { dialog, which ->
            val characterEliminado = characterDAO.getLista()[idItemSeleccionado]
            if (characterDAO.delete(characterEliminado.getId())) {

                adaptador = ArrayAdapter(
                    this,
                    android.R.layout.simple_list_item_1,
                    characterDAO.getLista(id!!)
                )
                listView.adapter = adaptador
                adaptador.notifyDataSetChanged()
            }
        }
        builder.setNegativeButton("No", null)

        val dialog = builder.create()
        dialog.show()
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mi_editar -> {
                abrirActividadConParametros(FormularioCharacter::class.java)
                true
            }
            R.id.mi_eliminar -> {
                abrirDialogoEliminar()
                true
            }
            else -> {
                super.onContextItemSelected(item)
            }
        }
    }

    override fun onRestart() {
        super.onRestart()
        adaptador.clear()
        adaptador.addAll(characterDAO.getLista(id!!))
        adaptador.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        adaptador.clear()
        adaptador.addAll(characterDAO.getLista(id!!))
        adaptador.notifyDataSetChanged()
    }

    fun irActividad(clase: Class<*>) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }

    fun abrirActividadConParametros(clase: Class<*>) {
        val intentExplicito = Intent(this, clase)
        intentExplicito.putExtra("id", characterDAO.getLista(id!!).get(idItemSeleccionado).getId())
        intentExplicito.putExtra("idActor", id)
        callback.launch(intentExplicito)
    }

    fun abrirDialogo(cadena: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(cadena)
        builder.setPositiveButton("Aceptar") { dialog, which -> dialog.dismiss() }

        val dialogo = builder.create()
        dialogo.setCancelable(false)
        dialogo.show()
    }
}

