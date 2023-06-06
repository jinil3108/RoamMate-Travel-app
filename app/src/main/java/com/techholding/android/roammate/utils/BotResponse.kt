package com.techholding.android.roammate.utils

import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat

object BotResponse {
    fun basicResponses(_message: String): String {
        val random = (0..2).random()
        val message =_message.toLowerCase()
        return when {
            //Flips a coin
            message.contains("flip") && message.contains("coin") -> {
                val r = (0..1).random()
                val result = if (r == 0) "heads" else "tails"
                "I flipped a coin and it landed on $result"
            }
            message.contains("about us") || message.contains("About us") || message.contains("About Us") -> {
                "This App is a sample app just to know about some functionalities of android application"
            }
            //Hello
            message.contains("hello") || message.contains("hi") || message.contains("namaste") || message.contains("hola") || message.contains("Hey")-> {
                when (random) {
                    0 -> "Hello there!"
                    1 -> "Sup"
                    2 -> "Good morning!"
                    else -> "error" }
            }
            message.contains("open explore fragment") || message.contains("open explore")|| message.contains("go to explore") || message.contains("goto explore")-> {
                OPEN_Explore
            }
            message.contains("open plan fragment") || message.contains("open plan")|| message.contains("go to plan") || message.contains("goto plan")-> {
                OPEN_Plan
            }
            message.contains("open settings fragment") || message.contains("open settings")|| message.contains("go to settings") || message.contains("goto settings")-> {
                OPEN_Settings
            }
            //How are you?
            message.contains("how are you") -> {
                when (random) {
                    0 -> "I'm doing fine, thanks!"
                    1 -> "I'm hungry..."
                    2 -> "Pretty good! How about you?"
                    else -> "error"
                }
            }
            //What time is it?
            message.contains("time") && message.contains("?")-> {
                val timeStamp = Timestamp(System.currentTimeMillis())
                val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm")
                val date = sdf.format(Date(timeStamp.time))
                date.toString()
            }
            //Open Google
            message.contains("open") && message.contains("google")-> {
                OPEN_GOOGLE
            }
            //Search on the internet
            message.contains("search")-> {
                OPEN_SEARCH
            }
            //When the programme doesn't understand...
            else -> {
                when (random) {
                    0 -> "I don't understand..."
                    1 -> "Try asking me something different"
                    2 -> "Idk"
                    else -> "error"
                }
            }
        }
    }
}