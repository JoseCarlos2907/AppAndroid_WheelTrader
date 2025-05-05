package com.iesfernandoaguilar.perezgonzalez.wheeltrader.utils

import com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.Mensaje

class Serializador {
    companion object{
        fun codificarMensaje(msg: Mensaje): String {
            return msg.getTipo() + ";" + java.lang.String.join(";", msg.getParams())
        }

        fun decodificarMensaje(cadena: String): Mensaje {
            val msg = Mensaje()

            val cadenaSplit = cadena.split(";".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray()

            msg.setTipo(cadenaSplit[0])

            if (cadenaSplit.size > 1) {
                for (i in 1 until cadenaSplit.size) {
                    msg.addParam(cadenaSplit[i])
                }
            }

            return msg
        }
    }
}