package flashcards


import java.util.Scanner


class CardDesk {
    companion object {
        lateinit var desk: Array<FlashCard>


        fun createDesk(numOfCards: Int) {
            desk = Array(numOfCards) { FlashCard("", "") }

            for (i in desk.indices) {
                fillDataToCard(i)
            }
        }

        private fun fillDataToCard(i: Int) {
            val scanner = Scanner(System.`in`)
            var term: String
            var definition: String

            println("The card #${i + 1}:")

            do {
                term = scanner.nextLine()
            } while (!isUnique(term, "term"))

            println("The definition of the card #${i + 1}:")

            do {
                definition = scanner.nextLine()
            } while (!isUnique(definition, "definition"))



            desk[i].term = term
            desk[i].definition = definition

        }

        private fun isUnique(value: String, field: String): Boolean {
            var result = true

            if (field == "term") {
                for (i in desk.indices) {
                    if (desk[i].term == value) {
                        println("The card \"$value\" already exists. Try again:")
                        result = false
                    }
                }
            } else {
                for (i in desk.indices) {
                    if (desk[i].definition == value) {
                        println("The definition \"$value\" already exists. Try again:")
                        result = false
                    }

                }

            }
            return result
        }

        fun checkAllCards() {
            var resultOfChecking: String
            for (i in desk.indices) {
                resultOfChecking = checkCardDefinition(i)
                if (resultOfChecking == "true") {
                    println("Correct!")
                } else {
                    checkAnotherTerm(i, resultOfChecking)
                    //println("Wrong. The right answer is \"${desk[i].definition}\".")
                }
            }
        }

        private fun checkAnotherTerm(originalIndex: Int, definition: String) {
            for (i in desk.indices) {
                if (definition == desk[i].definition) println("Wrong. The right answer is \"${desk[i].term}\", but your definition is correct for \"${desk[originalIndex].term}\".")
            }
        }

        private fun checkCardDefinition(i: Int): String {
            val result: String
            val scanner = Scanner(System.`in`)
            val uinput = scanner.nextLine()
            println("Print the definition of \"${desk[i].term}\":")
            result = if (uinput == desk[i].definition) "true" else uinput
            return result
        }
    }


    class FlashCard(var term: String, var definition: String)
}

fun main() {
    val scanner = Scanner(System.`in`)

    println("Input the number of cards:")
    var uInput = scanner.nextLine()

    CardDesk.createDesk(uInput.toInt())
    CardDesk.checkAllCards()

/*    do {
        uInput = scanner.next()
        when (uInput) {
            "" -> {

            }
        }

    } while (uInput != "exit")*/


}
