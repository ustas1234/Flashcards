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
            Logs.log += "The card:\n"
            term = scanner.nextLine()
            Logs.log += term + "\n"
            if (isUnique(term, "term")) {
                println("The definition of the card:")
                Logs.log += "The definition of the card:\n"
                definition = scanner.nextLine()
                Logs.log += definition + "\n"
                if (isUnique(definition, "definition")) {
                    desk.add(FlashCard(term, definition))
                    println("The pair (\"$term\":\"$definition\") has been added.")
                    Logs.log += "The pair (\"$term\":\"$definition\") has been added.\n"
                }
            }


        }

        fun isUnique(value: String, field: String): Boolean {
            var result = true

            if (field == "term") {
                for (i in desk.indices) {
                    if (desk[i].term == value) {
                        println("The card \"$value\" already exists.")
                        Logs.log += "The card \"$value\" already exists.\n"
                        result = false
                    }
                }
            } else {
                for (i in desk.indices) {
                    if (desk[i].definition == value) {
                        println("The definition \"$value\" already exists")
                        Logs.log += "The definition \"$value\" already exists\n"
                        result = false
                    }

                }

            }
            return result
        }

        fun checkAnotherTerm(originalIndex: Int, definition: String) {
            var isAnotherTermExists = false
            for (i in desk.indices) {
                if (definition == desk[i].definition) {
                    println("Wrong. The right answer is \"${desk[originalIndex].definition}\", but your definition is correct for \"${desk[i].term}\".")
                    Logs.log += "Wrong. The right answer is \"${desk[originalIndex].definition}\", but your definition is correct for \"${desk[i].term}\".\n"
                    isAnotherTermExists = true
                }
            }
            if (!isAnotherTermExists) {
                println("Wrong. The right answer is \"${desk[originalIndex].definition}\".")
                Logs.log += "Wrong. The right answer is \"${desk[originalIndex].definition}\".\n"
            }
        }

        fun checkCardDefinition(i: Int): String {
            val result: String
            val scanner = Scanner(System.`in`)
            println("Print the definition of \"${desk[i].term}\":")
            Logs.log += "Print the definition of \"${desk[i].term}\":\n"
            val uinput = scanner.nextLine()
            Logs.log += uinput + "\n"
            result = if (uinput == desk[i].definition) "true" else uinput
            return result
        }

        fun removingTheCard() {
            println("The card:")
            Logs.log += "The card:\n"
            val uInput = readLine() ?: throw Exception("WTF")
            Logs.log += uInput + "\n"
            if (desk.find { it.term == uInput } != null) {
                desk.remove(desk.find { it.term == uInput }!!)
                println("The card has been removed.")
                Logs.log += "The card has been removed.\n"
            } else {
                println("Can't remove \"$uInput\": there is no such card.")
                Logs.log += "Can't remove \"$uInput\": there is no such card.\n"
            }
        }

        fun askForRandomCard() {
            println("How many times to ask?")
            Logs.log += "How many times to ask?\n"
            val uInput = readLine() ?: throw Exception("WTF")
            Logs.log += uInput + "\n"
            val numOfQuestions = uInput.toInt()
            var resultOfChecking: String
            var j: Int
            // println("desk.size ${desk.size}")

            repeat(numOfQuestions) {
                j = Random.nextInt(0, desk.size)
                //println(j)


                resultOfChecking = checkCardDefinition(j)
                if (resultOfChecking == "true") {
                    println("Correct!")
                    Logs.log += "Correct!\n"
                } else {
                    desk[j].wrongAnswers += 1
                    checkAnotherTerm(j, resultOfChecking)

                }
            }
        }

        fun exportToFile() {
            println("File name:")
            Logs.log += "File name:\n"
            val uInput = readLine() ?: throw Exception("WTF")
            Logs.log += uInput + "\n"
            val file = File(uInput)

            for (i in desk.indices) {
                var stringFromFlashCard = "$i|${desk[i].term}|${desk[i].definition}|${desk[i].wrongAnswers}"
                if (i == 0) file.writeText(stringFromFlashCard) else file.appendText("\n" + stringFromFlashCard)

            }


            println("${desk.size} cards have been saved.")
            Logs.log += "${desk.size} cards have been saved.\n"
        }

        fun importFromFile() {
            var dataFromFile = MutableList(0) { FlashCard("", "") }
            var stringFromFile = ""
            println("File name:")
            Logs.log += "File name:\n"
            val uInput = readLine() ?: throw Exception("WTF")
            Logs.log += uInput + "\n"
            if (File(uInput).exists()) {
                val file = File(uInput).readLines()
                for (str in file) {
                    stringFromFile = str
                    dataFromFile.add(FlashCard(stringFromFile.split("|")[1], stringFromFile.split("|")[2], stringFromFile.split("|")[3].toInt()))
                }

                for (i in dataFromFile.indices) {
                    if (dataFromFile[i].term == desk.find { it.term == dataFromFile[i].term }?.term) {
                        desk.find { it.term == dataFromFile[i].term }?.definition = dataFromFile[i].definition
                        desk.find { it.term == dataFromFile[i].term }?.wrongAnswers = dataFromFile[i].wrongAnswers
                    } else desk.add(dataFromFile[i])
                }

                println("${dataFromFile.size} cards have been loaded.")
                Logs.log += "${dataFromFile.size} cards have been loaded.\n"

            } else {
                println("File not found.")
                Logs.log += "File not found.\n"
            }


        }

        fun showHardestCard() {
            var maxMistakes = 0
            desk.sortByDescending { it.wrongAnswers }
            if (desk.size > 0) maxMistakes = desk[0].wrongAnswers
            var tempList = MutableList(0) { FlashCard("", "") }
            //desk.forEach { println(it.wrongAnswers) }

            if (maxMistakes == 0) {
                println("There are no cards with errors.")
                Logs.log += "There are no cards with errors.\n"
            } else {
                for (i in 0..desk.lastIndex) {
                    if (desk[i].wrongAnswers == maxMistakes) {
                        //tempList.clear()
                        tempList.add(desk[i])
                    }
                }
                if (tempList.size == 1) {
                    println("The hardest card is \"${tempList[0].term}\". You have ${tempList[0].wrongAnswers} errors answering it.")
                    Logs.log += "The hardest card is \"${tempList[0].term}\". You have ${tempList[0].wrongAnswers} errors answering it.\n"
                } else {
                    val tempStr = tempList.joinToString { "\"${it.term}\"" }
                    println("The hardest cards are $tempStr. You have ${tempList[0].wrongAnswers} errors answering them.")
                    Logs.log += "The hardest cards are $tempStr. You have ${tempList[0].wrongAnswers} errors answering them."
                }
            }


        }

        fun resetStats() {
            desk.forEach { it.wrongAnswers = 0 }
            println("Card statistics has been reset.")
            Logs.log += "Card statistics has been reset.\n"
        }
    }


    class FlashCard(var term: String, var definition: String, var wrongAnswers: Int = 0)
}

class Logs() {
    companion object {
        var log: String = ""

        fun saveLogToFile() {
            println("File name:")
            log += "File name:\n"

            val uInput = readLine() ?: throw Exception("WTF")
            log += uInput + '\n'

            val file = File(uInput)
            println("The log has been saved.")
            log += "The log has been saved.\n"

            file.writeText(log)
        }
    }
}

fun main() {
    do {
        println("Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):")
        Logs.log = "Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):\n"

        val uInput = readLine() ?: throw Exception("WTF")

        when (uInput) {
            "add" -> {
                Logs.log += "add\n"
                CardDesk.addingTheCard()
            }
            "remove" -> {
                Logs.log += "remove\n"
                CardDesk.removingTheCard()
            }
            "ask" -> {
                Logs.log += "ask\n"
                CardDesk.askForRandomCard()
            }
            "import" -> {
                Logs.log += "import\n"
                CardDesk.importFromFile()
            }
            "export" -> {
                Logs.log += "export\n"
                CardDesk.exportToFile()
            }
            "hardest card" -> {
                Logs.log += "hardest card\n"
                CardDesk.showHardestCard()
            }
            "log" -> {
                Logs.log += "log\n"
                Logs.saveLogToFile()
            }
            "reset stats" -> {
                Logs.log += "reset stats\n"
                CardDesk.resetStats()
            }
            "exit" -> {
                Logs.log += "exit\n"
                println("Bye bye!")
                Logs.log += "Bye bye!\n"
                exitProcess(-1)
            }

        }

        println("")
        Logs.log += "\n"
    } while (true)


}