import entities.Actor
import entities.Character
import java.text.SimpleDateFormat
import java.util.*


fun main(){
    val scanner = Scanner(System.`in`)
    var choice: Int

    do {
        println("============================================")
        println("        Actors and Characters Manager       ")
        println("============================================")
        println("1. Add Actor")
        println("2. Add Character")
        println("3. List Actors")
        println("4. List Characters")
        println("5. Update Actor")
        println("6. Update Character")
        println("7. Delete Actor")
        println("8. Delete Character")
        println("9. Exit")

        print("Type your choice: ")
        choice = scanner.nextInt()


        when (choice) {
            1 -> {
                addActor()
            }
            2 -> {
                addCharacter()
            }
            3 -> {
                listActors()
            }
            4 -> {
                listCharacters()
            }
            5 -> {
                updateActor()
            }
            6 -> {
                updateCharacter()
            }
            7 -> {
                deleteActor()
            }
            8 -> {
                deleteCharacter()
            }
            9 -> {
                println("Finishing...")
            }
            else -> println("Invalid Choice, try again")
        }

    } while (choice != 9)

}

fun addActor() {
    println("============================================")
    println("                 Add Actor                  ")
    println("============================================")
    print("Type Actor's id: ")
    val id = readLine()?.toIntOrNull() ?: throw IllegalArgumentException("Invalid id")

    print("Type Actor's name: ")
    val name = readLine() ?: throw IllegalArgumentException("Invalid name")

    print("Type Actor's age: ")
    val age = readLine()?.toIntOrNull() ?: throw IllegalArgumentException("Invalid age")

    print("Type character's id the actor has roled by comma split <,>: ")
    val characterString = readLine() ?: throw IllegalArgumentException("Invalid characters")

    print("Has the actor won an Oscar Price? <true/false>: ")
    val hasOscarString = readLine()?.toLowerCase() ?: throw IllegalArgumentException("Invalid Value")
    val hasOscar = hasOscarString == "true"

    print("Type how much money the actor have: ")
    val money = readLine()?.toDoubleOrNull() ?: throw IllegalArgumentException("Invalid Value")

    val characterIndex = characterString.split(",")
        .filter { it.isNotEmpty() }
        .map { it.trim().toInt() }


    val character = List(characterIndex.size) { Character.getCharacter(characterIndex[it]) }
    val actor = Actor(id, name, age, character, hasOscar, money)
    actor.createActor()
}

fun listActors() {
    println("============================================")
    println("               List of Actors               ")
    println("============================================")
    val actors = Actor.getActors()
    actors.forEach { println(it) }


}

fun updateActor(){
    println("============================================")
    println("               Uodate Actors               ")
    println("============================================")
    print("Type Actor's id: ")
    val id = readLine()?.toIntOrNull() ?: return println("Id inválido.")

    val actor = Actor.getActor(id)
    if (actor != null) {
        print("Type Actor's name: ")
        val name = readLine() ?: throw IllegalArgumentException("Invalid name")

        print("Type Actor's age: ")
        val age = readLine()?.toIntOrNull() ?: throw IllegalArgumentException("Invalid age")

        print("Type character's id the actor has roled by comma split <,>: ")
        val characterString = readLine() ?: throw IllegalArgumentException("Invalid Characters")

        print("Has the actor won an Oscar Price? <true/false>: ")
        val hasOscarString = readLine()?.toLowerCase() ?: throw IllegalArgumentException("Invalid value")
        val hasOscar = hasOscarString == "true"

        print("Type how much money the actor have: ")
        val money = readLine()?.toDoubleOrNull() ?: throw IllegalArgumentException("Invalid value")

        val characterIndex = characterString.split(",")
            .filter { it.isNotEmpty() }
            .map { it.trim().toInt() }

        val character = List(characterIndex.size) { Character.getCharacter(characterIndex[it]) }
        val newActor = Actor(id, name, age, character, hasOscar, money)
        actor.updateActor(newActor)
    } else {
        println("There isn't any Character with id: $id")
    }
}

fun deleteActor() {
    println("============================================")
    println("               Delete Actor               ")
    println("============================================")
    print("Type Actor's id: ")
    val id = readLine()?.toIntOrNull() ?: return println("Invalid id")

    val actor = Actor.getActor(id)
    if (actor != null) {
        actor.deleteActor(id)
    } else {
        println("There isn't any Actor with id: $id")
    }
}


fun addCharacter() {
    println("============================================")
    println("               Add Character               ")
    println("============================================")
    print("Type Character's id: ")
    val id = readLine()?.toIntOrNull() ?: throw IllegalArgumentException("Invalid id")

    print("Type Character's name: ")
    val name = readLine() ?: throw IllegalArgumentException("Invalid name")

    print("Type Character's age: ")
    val age = readLine()?.toIntOrNull() ?: throw IllegalArgumentException("Invalid age")

    print("Type movie's name where character appears: ")
    val movie = readLine() ?: throw IllegalArgumentException("Invalid value")

    print("Type character's alias: ")
    val alias = readLine() ?: throw IllegalArgumentException("Invalid alias.")

    print("Type the character's first appearing date  (dd/MM/yyyy): ")
    val firstActingDate = readLine() ?: throw IllegalArgumentException("Invalid date")

    val dateFormat = SimpleDateFormat("dd/MM/yyyy")
    val date = dateFormat.parse(firstActingDate)
    val character = Character(id, name, age, alias, movie, date)
    character.createCharacter()
}

fun listCharacters() {
    println("============================================")
    println("               List Characters               ")
    println("============================================")
    val character = Character.getCharacters()
    character.forEach { println(it) }
}

fun updateCharacter() {
    println("============================================")
    println("               Update Character             ")
    println("============================================")
    print("Type Character's id: ")
    val id = readLine()?.toIntOrNull() ?: return println("Invalid id")

    val character = Character.getCharacter(id)
    if (character != null) {
        print("Type Character's name: ")
        val name = readLine() ?: throw IllegalArgumentException("Invalid name")

        print("Type Character's age: ")
        val age = readLine()?.toIntOrNull() ?: throw IllegalArgumentException("Invalid age")

        print("Type movie's name where character appears: ")
        val movie = readLine() ?: throw IllegalArgumentException("Invalid value")

        print("Type character's alias: ")
        val alias = readLine() ?: throw IllegalArgumentException("Invalid alias.")

        print("Type the character's first appearing date  (dd/MM/yyyy): ")
        val firstActingDate = readLine() ?: throw IllegalArgumentException("Invalid date")

        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        val date = dateFormat.parse(firstActingDate)
        val newCharacter = Character(id, name, age, alias, movie, date)
        character.updateCharacter(newCharacter)
    } else {
        println("There isn't any Character with id: $id")
    }
}

fun deleteCharacter() {
    println("============================================")
    println("               Delete Character             ")
    println("============================================")
    print("Type Character's id: ")
    val id = readLine()?.toIntOrNull() ?: return println("Invalid id")

    val character = Character.getCharacter(id)
    if (character != null) {
        character.deleteCharacter(id)
    } else {
        println("There isn't any Character with id: $id")
    }
}