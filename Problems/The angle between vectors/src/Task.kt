import java.util.Scanner
import kotlin.math.acos
import kotlin.math.sqrt

fun main() {
    val scanner = Scanner(System.`in`)
    val (ax, ay, bx, by) = DoubleArray(4) { scanner.nextDouble() }
 //   println("$ax $ay $bx $by")

    val temp1: Double = ax * bx + ay * by
 //   println(temp1)
    val temp2: Double = sqrt(ax * ax + ay * ay) * sqrt(bx * bx + by * by)
 //   println(temp2)

    println(Math.toDegrees(acos(temp1 / temp2)).toInt())

}