package flashcards

import java.lang.Exception

class FlashCards(val term: String, val definition: String) {

    fun checkDefini

}

fun main() {
    val input = readLine() ?: throw Exception("WTF")
    do {
        val firstCard = FlashCards(readLine() ?: throw Exception("WTF"), readLine()
                ?: throw Exception("WTF"))
        checkAnswer(readLine() ?: throw Exception("WTF"))
    } while (input != "exit")
    println()
}
