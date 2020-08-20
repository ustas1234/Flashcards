import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)
    val (i1, i2, i3, i4) = IntArray(4) { scanner.nextInt() }

    print(Math.max((Math.max(i1, i2)), Math.max(i3, i4)))
}