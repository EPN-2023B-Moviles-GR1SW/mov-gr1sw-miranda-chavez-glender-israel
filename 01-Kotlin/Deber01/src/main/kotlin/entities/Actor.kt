package entities

import java.io.File
import java.util.*

class Actor (
    var id: Int,
    var name: String,
    var age: Int,
    var characters: List<Character>,
    var hasOscar: Boolean,
    var money: Double

){
    private val files = System.getProperty("user.dir")+ "\\src\\main\\kotlin\\sources\\actors.txt"

    fun createActor() {
        val idCharacters = this.characters.map { it.id.toString() }.joinToString("-")
        val actor = "${this.id},${this.name},${this.age}," +
                "[$idCharacters],${this.hasOscar},${this.money}\n"

        File(files).appendText(actor)
        println("Actor $name has been successfully added :D")
    }

    fun updateActor(updatedActor: Actor) {
        val idCharacters = updatedActor.characters.joinToString("-") { it.id.toString() }
        val actors = File(files).readLines()
        val newActors = actors.map { actor ->
            if (actor.startsWith("${updatedActor.id},")) {
                "${updatedActor.id},${updatedActor.name},${updatedActor.age}," +
                        "[$idCharacters],${updatedActor.hasOscar},${updatedActor.money}"
            } else {
                actor
            }
        }
        File(files).writeText(newActors.joinToString("\n"))
    }

    fun deleteActor(id: Int) {
        val actors = File(files).readLines()
        val newActors = actors.filter { !it.startsWith("$id,") }
        File(files).writeText(newActors.joinToString("\n"))
    }

    override fun toString(): String {
        val characterNames = characters.joinToString(", ") { it.name }
        return "Actor(id=$id, name='$name', age=$age, characters=[$characterNames], hasOscar=$hasOscar, money=$money)"
    }


    companion object {
        private val files = System.getProperty("user.dir")+ "\\src\\main\\kotlin\\sources\\actors.txt"

        fun getActorsByCharacter(id: Int): List<Actor>{
            val actors = File(files).readLines()
            val matchedActors = mutableListOf<Actor>()
            actors.forEach { actor ->
                val idCharacters = actor.substringAfter("[").substringBefore("]")
                val characters = idCharacters.split("-")
                println(characters)
                if (characters.contains(id.toString())) {
                    val idCharacters = actor.substringAfter("[").substringBefore("]")
                    val charactersl = idCharacters.split("-").map { Character.getCharacter(it.toInt()) }
                    val values = actor.split(",")
                    matchedActors.add(Actor(values[0].toInt(), values[1], values[2].toInt(), charactersl, values[4].toBoolean(), values[5].toDouble()))
                }
            }
            println(matchedActors)
            return matchedActors
        }
        fun getActor(id: Int): Actor{
            val actors = File(files).readLines()
            val actor = actors.find { it.startsWith("$id,")}
            val values = actor?.split(",") ?: throw Exception("There isn't any Actor with id: $id")

            return Actor(
                values[0].toInt(),
                values[1],
                values[2].toInt(),
                listOf(),
                values[4].toBoolean(),
                values[5].toDouble())
        }
        fun getActors(): List<Actor>{
            val actors = File(files).readLines()
            val actorsList = mutableListOf<Actor>()
            actors.forEach { actor ->
                val idCharacters = actor.substringAfter("[").substringBefore("]")
                val characters = idCharacters.split("-").map { Character.getCharacter(it.toInt()) }
                val values = actor.split(",")
                actorsList.add(Actor(values[0].toInt(), values[1], values[2].toInt(), characters, values[4].toBoolean(), values[5].toDouble()))
            }
            return actorsList
        }
    }
}