package com.example.b2023_gr1sw_gimc

class BBaseDatosMemoria {

    //EMPEZAR EL COMPANION
    companion object{
        val arregloBEntrenador = arrayListOf<BEntrenador>()
        init {
            arregloBEntrenador
                .add(
                    BEntrenador(1,"Glender","a@a.com")

                )
            arregloBEntrenador
                .add(
                    BEntrenador(2,"Adrian","b@b.com")

                )
            arregloBEntrenador
                .add(
                    BEntrenador(3,"Sofia","c@c.com")

                )
        }
    }

}