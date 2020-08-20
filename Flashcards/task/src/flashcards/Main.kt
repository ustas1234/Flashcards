package flashcards


import java.io.File
import java.lang.Exception
import java.util.*
import kotlin.random.Random
import kotlin.system.exitProcess


class CardDesk {
    companion object {
        var desk = MutableList(0) { FlashCard("", "") }

        fun addingTheCard() {
            val scanner = Scanner(System.`in`)
            var term: String
            var definition: String

            println("The card:")
            term = scanner.nextLine()
            if (isUnique(term, "term")) {
                println("The definition of the card:")
                definition = scanner.nextLine()
                if (isUnique(definition, "definition")) {
                    desk.add(FlashCard(term, definition))
                    println("The pair (\"$term\":\"$definition\") has been added.")
                }
            }


        }

        fun isUnique(value: String, field: String): Boolean {
            var result = true

            if (field == "term") {
                for (i in desk.indices) {
                    if (desk[i].term == value) {
                        println("The card \"$value\" already exists.")
                        result = false
                    }
                }
            } else {
                for (i in desk.indices) {
                    if (desk[i].definition == value) {
                        println("The definition \"$value\" already exists")
                        result = false
                    }

                }

            }
            return result
        }

        /*fun checkAllCards() {
            var resultOfChecking: String
            for (i in desk.indices) {
                resultOfChecking = checkCardDefinition(i)
                if (resultOfChecking == "true") {
                    println("Correct!")
                } else {
                    checkAnotherTerm(i, resultOfChecking)

                }
            }
        }*/

        fun checkAnotherTerm(originalIndex: Int, definition: String) {
            var isAnotherTermExists = false
            for (i in desk.indices) {
                if (definition == desk[i].definition) {
                    println("Wrong. The right answer is \"${desk[originalIndex].definition}\", but your definition is correct for \"${desk[i].term}\".")
                    isAnotherTermExists = true
                }
            }
            if (!isAnotherTermExists) println("Wrong. The right answer is \"${desk[originalIndex].definition}\".")
        }

        fun checkCardDefinition(i: Int): String {
            val result: String
            val scanner = Scanner(System.`in`)
            println("Print the definition of \"${desk[i].term}\":")
            val uinput = scanner.nextLine()
            result = if (uinput == desk[i].definition) "true" else uinput
            return result
        }

        fun removingTheCard() {
            println("The card:")
            val uInput = readLine() ?: throw Exception("WTF")
            if (desk.find { it.term == uInput } != null) {
                desk.remove(desk.find { it.term == uInput })
                println("The card has been removed.")
            } else println("Can't remove \"$uInput\": there is no such card.")
        }

        fun askForRandomCard() {
            println("How many times to ask?")
            val uInput = readLine() ?: throw Exception("WTF")
            val numOfQuestions = uInput.toInt()
            var resultOfChecking: String
            var j: Int
            println("desk.size ${desk.size}")

            repeat(numOfQuestions) {
                j = Random.nextInt(0, desk.size)
                println(j)


                resultOfChecking = checkCardDefinition(j)
                if (resultOfChecking == "true") {
                    println("Correct!")
                } else {
                    checkAnotherTerm(j, resultOfChecking)

                }
            }
        }

        fun exportToFile() {
            println("File name:")
            val uInput = readLine() ?: throw Exception("WTF")
            val file = File(uInput)


            for (i in desk.indices) {
                var stringFromFlashCard = "$i|${desk[i].term}|${desk[i].definition}"
                if (i == 0) file.writeText(stringFromFlashCard) else file.appendText("\n" + stringFromFlashCard)

            }


            println("${desk.size} cards have been saved.")
        }

        fun importFromFile() {
            var dataFromFile = MutableList(0) { FlashCard("", "") }
            var stringFromFile = ""
            println("File name:")
            val uInput = readLine() ?: throw Exception("WTF")
            if (File(uInput).exists()) {
                val file = File(uInput).readLines()
                for (str in file) {
                    stringFromFile = str
                    dataFromFile.add(FlashCard(stringFromFile.split("|")[1], stringFromFile.split("|")[2]))
                }

                for (i in dataFromFile.indices) {
                    for (j in desk.indices) {
                        if (dataFromFile[i].term == desk[j].term) {
                            desk[j].definition = dataFromFile[i].definition
                        } else {
                            desk.add(FlashCard(dataFromFile[i].term,dataFromFile[i].definition))
                        }
                    }
                }

                println("${dataFromFile.size} cards have been loaded.")

            } else println("File not found.")


        }
    }


    class FlashCard(var term: String, var definition: String)
}

fun main() {
    do {
        println("Input the action (add, remove, import, export, ask, exit):")

        val uInput = readLine() ?: throw Exception("WTF")

        when (uInput) {
            "add" -> CardDesk.addingTheCard()
            "remove" -> CardDesk.removingTheCard()
            "ask" -> CardDesk.askForRandomCard()
            "import" -> CardDesk.importFromFile()
            "export" -> CardDesk.exportToFile()
            "exit" -> {
                println("Bye bye!")
                exitProcess(-1)
            }

        }

        println("")
    } while (true)


}


