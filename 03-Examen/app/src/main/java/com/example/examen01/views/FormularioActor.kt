package com.example.examen01.views

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.examen01.MainActivity
import com.example.examen01.R
import com.example.examen01.bdd.BDD
import com.example.examen01.models.Actor
import com.example.examen01.models.ActorDAO
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class FormularioActor : AppCompatActivity() {
    val actorDAO:ActorDAO= BDD.actorDAO!!



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_actor)
        val id: Int? = intent.getIntExtra("id",-1)

        val botonAceptar=findViewById<Button>(R.id.btn_aceptar)
        val botonCancelar=findViewById<Button>(R.id.btn_cancelar)
        val campoNombre=findViewById<EditText>(R.id.et_name)
        val campoEdad=findViewById<EditText>(R.id.et_edad)
        val campoOscar = findViewById<CheckBox>(R.id.chk_oscar)
        val campoMoney=findViewById<EditText>(R.id.et_money)

        val textoTop=findViewById<TextView>(R.id.tv_top_actor)

        botonCancelar.setOnClickListener {
            irActividad(MainActivity::class.java)
        }

        if (id!=-1){
            val actor=actorDAO.get(id!!)
            textoTop.text = "Editar Actor"


            campoNombre.setText(actor!!.getName())
            campoEdad.setText(actor?.getAge().toString())
            campoOscar.isChecked = actor?.hasOscar() == true
            campoMoney.setText(actor?.getMoney().toString())

            botonAceptar.setOnClickListener {


                try {


                    val name = campoNombre.text.toString()
                    val age = campoEdad.text.toString()
                    val hasOscar = campoOscar.isChecked
                    val money = campoMoney.text.toString()

                    if(name !=""
                        && age !=""
                        && money !=""
                    ){
                        actor.setName(name)
                        actor.setAge(age.toInt())
                        actor.setHasOscar(hasOscar)
                        actor.setMoney(money.toDouble())

                        actorDAO.edit(actor)
                        abrirDialogoYVolver("Actor actualizado correctamente")
                    }else{
                        abrirDialogo("Faltan datos por ingresar")
                    }


                }catch (e:Exception){
                    abrirDialogo("Los datos no estan en el formato correcto")
                }

            }
        }else{

            textoTop.text = "Crear Actor"
            botonAceptar.setOnClickListener {

                try {

                    val name = campoNombre.text.toString()
                    val age = campoEdad.text.toString()
                    val hasOscar = campoOscar.isChecked
                    val money = campoMoney.text.toString()

                    if(name !=""
                        && age !=""
                        && money !=""
                    ){
                        val actor= Actor(0,
                            name,
                            age.toInt(),
                            hasOscar,
                            money.toDouble())

                        actorDAO.add(actor)
                        abrirDialogo("Actor registrado exitosamente")
                        campoNombre.setText("")
                        campoEdad.setText("")
                        campoOscar.isChecked = false
                        campoMoney.setText("")


                    }else{
                        abrirDialogo("Faltan datos por ingresar")
                    }

                }catch (e:Exception){
                    abrirDialogo("Los datos no estan en el formato correcto")
                }

            }


        }
    }

    fun abrirDialogoYVolver(cadena:String){
        val builder = AlertDialog.Builder(this)
        builder.setTitle(cadena)
        builder.setPositiveButton(
            "Aceptar",
            DialogInterface.OnClickListener{ // Callback
                    dialog, which -> irActividad(MainActivity::class.java)
            }
        )

        val dialogo = builder.create()
        dialogo.setCancelable(false)
        dialogo.show()

    }


    fun abrirDialogo(cadena:String){
        val builder = AlertDialog.Builder(this)
        builder.setTitle(cadena)
        builder.setPositiveButton(
            "Aceptar",
            DialogInterface.OnClickListener{ // Callback
                    dialog, which -> dialog.cancel()
            }
        )

        val dialogo = builder.create()
        dialogo.setCancelable(false)
        dialogo.show()

    }

    fun irActividad(
        clase: Class<*>
    ){
        val intent = Intent(this, clase)
        // NO RECIBIMOS RESPUESTA
        startActivity(intent)
        // this.startActivity()
    }

}