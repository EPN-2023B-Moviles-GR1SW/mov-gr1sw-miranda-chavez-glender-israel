package com.example.examen01

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
import com.example.examen01.bdd.BDD
import com.example.examen01.models.Actor
import com.example.examen01.models.ActorDAO
import com.example.examen01.models.CharacterDAO
import com.example.examen01.views.CharacterActivity
import com.example.examen01.views.FormularioActor

class MainActivity : AppCompatActivity() {
    lateinit var adaptador: ArrayAdapter<Actor>
    var idItemSeleccionado = 0
    lateinit var listView: ListView

    lateinit var actorDAO: ActorDAO
    val callback=  registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
            result ->
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        BDD.actorDAO=ActorDAO(this)
        BDD.characterDAO= CharacterDAO(this)

        this.actorDAO= BDD.actorDAO!!


        listView = findViewById<ListView>(R.id.lv_actores)
        adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            actorDAO.getLista()
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()

        val botonCrear = findViewById<Button>(R.id.btn_crear_actor)
        botonCrear.setOnClickListener {

            irActividad(FormularioActor::class.java)
        }

        registerForContextMenu(listView)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        idItemSeleccionado = id
    }

    fun abrirDialogoEliminar() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Esta seguro que desea eliminar el actor?")
        builder.setPositiveButton("Si") { dialog, which ->
            val actorEliminado = actorDAO.getLista()[idItemSeleccionado]
            if (actorDAO.delete(actorEliminado.getId())) {

                adaptador = ArrayAdapter(
                    this,
                    android.R.layout.simple_list_item_1,
                    actorDAO.getLista()
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
        return when(item.itemId) {
            R.id.mi_editar -> {
                abrirActividadConParametros(FormularioActor::class.java)
                true
            }

            R.id.mi_eliminar -> {
                abrirDialogoEliminar()
                true
            }

            R.id.mi_ver_characters -> {
                abrirActividadConParametros(CharacterActivity::class.java)
                true
            }

            else -> {
                super.onContextItemSelected(item)
            }
        }

    }

    override fun onRestart() {
        super.onRestart()
        adaptador.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        adaptador.notifyDataSetChanged()
    }

    fun irActividad(
        clase: Class<*>
    ){
        val intent = Intent(this, clase)
        startActivity(intent)
    }

    fun abrirActividadConParametros(
        clase: Class<*>
    ){
        val intentExplicito = Intent(this, clase)
        intentExplicito.putExtra("id", actorDAO.getLista().get(idItemSeleccionado).getId())
        callback.launch(intentExplicito)
    }
}


