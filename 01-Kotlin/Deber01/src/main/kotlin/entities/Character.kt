package entities

import java.io.File
import java.text.SimpleDateFormat
import java.util.*
class Character (
    var id: Int,
    var name: String,
    var age: Int,
    var movie: String,
    var alias: String,
    var firstActingDate: Date
) {
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy")
    private val file = System.getProperty("user.dir")+ "\\src\\main\\kotlin\\sources\\characters.txt"

    fun createCharacter() {
        val character = "${this.id},${this.name},${this.age}," +
                "${this.movie},${this.alias},${dateFormat.format(this.firstActingDate)}\n"
        File(file).appendText(character)
        println("Character $name has been successfully added :D")
    }

    fun updateCharacter(updatedCharacter: Character) {
        val characters = File(file).readLines()
        val newCharacters = characters.map { character ->
            if (character.startsWith("${updatedCharacter.id},")) {
                "${updatedCharacter.id},${updatedCharacter.name},${updatedCharacter.age}," +
                        "${updatedCharacter.movie},${updatedCharacter.alias},${dateFormat.format(updatedCharacter.firstActingDate)}"
            } else {
                character
            }
        }
        File(file).writeText(newCharacters.joinToString("\n"))

    }

    fun deleteCharacter(id: Int) {
        val characters = File(file).readLines()
        val actors = Actor.getActorsByCharacter(id)

        actors.forEach { actor ->
            val charactersWithoutDeletedOne = actor.characters.filter { it.id != id }
            val updatedActor = Actor(
                actor.id,
                actor.name,
                actor.age,
                charactersWithoutDeletedOne,
                actor.hasOscar,
                actor.money
            )
            actor.updateActor(updatedActor)
        }
        val newCharacters = characters.filter { !it.startsWith("$id,") }
        File(file).writeText(newCharacters.joinToString("\n"))
    }

    override fun toString(): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        return "Character(id=$id, name='$name', age=$age, movie='${movie}', alias='$alias', firstActingDate=${dateFormat.format(firstActingDate)})"
    }

    companion object {
        fun getCharacter(id: Int): Character {
            val characters = File(file).readLines()
            val character = characters.find{it.startsWith("$id,")}
            val characterSplit = character?.split(",") ?: throw Exception("There isn't any Character with id: $id")
            return Character(
                characterSplit[0].toInt(),
                characterSplit[1],
                characterSplit[2].toInt(),
                characterSplit[3],
                characterSplit[4],
                dateFormat.parse(characterSplit[5])
            )
        }

        private val file = System.getProperty("user.dir")+ "\\src\\main\\kotlin\\sources\\characters.txt"
        private val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        fun getCharacters(): List<Character> {
            val characters = File(file).readLines()
            return characters.map { character ->
                val characterSplit = character.split(",")
                Character(
                    characterSplit[0].toInt(),
                    characterSplit[1],
                    characterSplit[2].toInt(),
                    characterSplit[3],
                    characterSplit[4],
                    dateFormat.parse(characterSplit[5])
                )
            }
        }
    }

}