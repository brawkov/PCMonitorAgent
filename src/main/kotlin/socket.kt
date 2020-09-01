import io.ktor.client.*
import io.ktor.client.features.websocket.*
import io.ktor.http.*
import io.ktor.http.cio.websocket.*
import io.ktor.util.*
import kotlinx.coroutines.isActive
import kotlinx.serialization.Contextual
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.lang.Thread.sleep
import kotlinx.serialization.Serializable

class Socket() {
    @KtorExperimentalAPI
    val client = HttpClient {
        install(WebSockets)
    }

    suspend fun sendData(pcId: Int) {

        try {
//            host = "127.0.0.1",
//            port = 8080,

            client.ws(
                method = HttpMethod.Get,
//                host = "127.0.0.1",

                host = "whispering-bayou-77904.herokuapp.com",
                path = "/sendStatePC?pcId=3",
            ) {
//                timeoutMillis = 10000
                maxFrameSize = 600000


                @Serializable
                data class Message(val type: String, val data: Int)

                @Serializable
                data class UpdateMessage(val type: String, val data: firstclass.StatePC)

//                val pcId = 9999
//                Собщение после подключения
                send(Json.encodeToString(Message("join", pcId)))

//                while (true) {
//                val tmp = first.firstfun()
                    // Send text frame.
//               send(first.firstfun())
                    println("sending")
//                    send(Json.encodeToString(UpdateMessage("update", firstclass().firstfun())))

//                    sleep(5000)
//                // Receive frame.
//                val frame = incoming.receive()
//                when (frame) {
//                    is Frame.Text -> println(frame.readText())
//                    is Frame.Binary -> println(frame.readBytes())
//                }
//                }
            }
        } catch (e: Exception) {
            println("Errors: $e")
        }
    }
}


