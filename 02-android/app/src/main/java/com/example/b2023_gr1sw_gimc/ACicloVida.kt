package com.example.b2023_gr1sw_gimc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar

class ACicloVida : AppCompatActivity() {

    var textoGlobal = ""

    fun mostrarSnackbar(texto:String){
        textoGlobal = textoGlobal + " " + texto
        Snackbar
            .make(
                findViewById(R.id.cl_ciclo_vida), //view
            textoGlobal, //texto
            Snackbar.LENGTH_INDEFINITE //tiempo
            )
            .show()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aciclo_vida)
        mostrarSnackbar("onCreate")
    }

    override fun onStart() {
        super.onStart()
        setContentView(R.layout.activity_aciclo_vida)
        mostrarSnackbar("onCreate")
    }

    override fun onResume() {
        super.onResume()
        setContentView(R.layout.activity_aciclo_vida)
        mostrarSnackbar("onCreate")
    }

    override fun onRestart() {
        super.onRestart()
        setContentView(R.layout.activity_aciclo_vida)
        mostrarSnackbar("onCreate")
    }

    override fun onPause() {
        super.onPause()
        setContentView(R.layout.activity_aciclo_vida)
        mostrarSnackbar("onCreate")
    }

    override fun onStop() {
        super.onStop()
        setContentView(R.layout.activity_aciclo_vida)
        mostrarSnackbar("onCreate")
    }

    override fun onDestroy() {
        super.onDestroy()
        setContentView(R.layout.activity_aciclo_vida)
        mostrarSnackbar("onCreate")
    }

    override fun onSaveInstanceState(outState:Bundle) {
        outState.run {
        // GUARDAR LAS VARIABLES
        // PRIMITIVOS
            putString("textoGuardado", textoGlobal)
        //putInt( "numeroGuardado" , numero)
        }
        super.onSaveInstanceState(outState)
    }


    override fun onRestoreInstanceState(
        savedInstanceState: Bundle
    ){
        super.onRestoreInstanceState(savedInstanceState)
// RECUPERAR LAS VARIABLES
// PRIMITIVOS
        val textoRecuperado:String? = savedInstanceState
            .getString ("textoGuardado")
// val textoRecuperado:Int? = savedInstanceState
// .getInt("numeroGuardado")
        if(textoRecuperado!= null){
            mostrarSnackbar(textoRecuperado)
            textoGlobal = textoRecuperado
        }
    }




}