package com.techholding.android.roammate.ui.chat

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.techholding.android.roammate.data.MessageAdapter
import com.techholding.android.roammate.databinding.FragmentChatBinding
import com.techholding.android.roammate.model.Message
import com.techholding.android.roammate.utils.*
import kotlinx.coroutines.*

class ChatFragment : Fragment() {

    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: MessageAdapter
    var messagesList = mutableListOf<Message>()
    private val botList = listOf("RoamMater", "Traveller", "Maverick", "Globetrotter")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        recyclerView()
        clickEvents()
        val random = (0..3).random()
        customMessage("Hello! Today you are speaking with ${botList[random]}, how may I help you?")
        return binding.root
    }
    private fun customMessage(message: String) {
        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main) {
                val timeStamp = Utils.timeStamp()
                messagesList.add(Message(message, RECEIVE_ID, timeStamp))
                adapter.insertMessage(Message(message, RECEIVE_ID, timeStamp))
                binding.rvmessages.scrollToPosition(adapter.itemCount - 1)
            }
        }
    }
    private fun recyclerView() {
        binding.rvmessages.layoutManager = LinearLayoutManager(context)
        adapter = MessageAdapter()
        binding.rvmessages.adapter = adapter
    }

    private fun sendMessage() {
        val message = binding.etmessage.text.toString()
        binding.etmessage.text.clear()
        val timeStamp = Utils.timeStamp()
        if (message.isNotEmpty()) {
            messagesList.add(Message(message, SEND_ID, timeStamp))
            adapter.insertMessage(Message(message, SEND_ID, timeStamp))
            binding.rvmessages.scrollToPosition(adapter.itemCount - 1)
            botResponse(message)
        }
    }
    private fun botResponse(message: String) {
        val timeStamp = Utils.timeStamp()
        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main) {
                val response = BotResponse.basicResponses(message)
                messagesList.add(Message(response, RECEIVE_ID, timeStamp))
                adapter.insertMessage(Message(response, RECEIVE_ID, timeStamp))
                binding.rvmessages.scrollToPosition(adapter.itemCount - 1)

                when (response) {
                    OPEN_GOOGLE -> {
                        val site = Intent(Intent.ACTION_VIEW)
                        site.data = Uri.parse("https://www.google.com")
                        startActivity(site)
                    }

                    OPEN_SEARCH -> {
                        val site = Intent(Intent.ACTION_VIEW)
                        val searchTerm: String? = message.substringAfter("search")
                        site.data = Uri.parse("https://www.google.com/search?&q=$searchTerm")
                        startActivity(site)
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()

        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main) {
                binding.rvmessages.scrollToPosition(adapter.itemCount - 1)
            }
        }
    }
    private fun clickEvents() {

        binding.btnsend.setOnClickListener {
            Log.d("jinilmsg", "jinilmsg")
            sendMessage()
        }
        binding.etmessage.setOnClickListener {
            GlobalScope.launch {
                delay(1000)
                withContext(Dispatchers.Main) {
                    binding.rvmessages.scrollToPosition(adapter.itemCount - 1)
                }
            }
        }
    }
}