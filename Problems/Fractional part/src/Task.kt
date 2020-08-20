import java.util.Scanner
import kotlin.math.floor
import kotlin.math.round

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)
    val uInput =scanner.nextDouble()
/*    println(floor(uInput * 10))
    println(round(floor(uInput * 10)))*/

    var res = (round(floor(uInput * 10))) % 10
    while (res > 10) {
        res /= 10
    }
    println(res)
}