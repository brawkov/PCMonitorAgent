
import io.ktor.util.*
import oshi.util.Util.sleep

import kotlinx.serialization.*
import kotlinx.serialization.json.*

suspend fun main(args: Array<String>) {
    val first = firstclass()

    val socket = Socket()
    val tmp = first.firstfun()
    socket.sendData(9999)
//    while (true) {
//        sleep(2000)
//        socket.sendData(first.firstfun())
//    }

}