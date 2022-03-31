package com.longnp.lnsocial.presentation.main.inbox.message

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.longnp.lnsocial.business.domain.models.inbox.InboxModel
import com.longnp.lnsocial.business.domain.models.inbox.Message
import com.longnp.lnsocial.business.domain.models.inbox.toMap
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class InboxMessageViewModel
@Inject
constructor(
    private val savedStateHandle: SavedStateHandle,
): ViewModel(){

    private val TAG = "AppDebug"

    val state: MutableLiveData<InboxMessageState> = MutableLiveData(InboxMessageState())

    private var database: DatabaseReference = Firebase.database.reference

    private lateinit var messageRef : DatabaseReference

    init {
        savedStateHandle.get<InboxModel>("model")?.let { data ->
            onTriggerEvent(InboxMessageEvents.OnUpdateInboxModel(data))
        }
    }

    fun onTriggerEvent(events: InboxMessageEvents) {
        when (events) {
            is InboxMessageEvents.OnUpdateInboxModel -> {
                onUpdateInboxModel(events.inboxModel)
            }
            is InboxMessageEvents.GetMessage -> {
                getMessage(events.id)
            }
            is InboxMessageEvents.Send -> {
                send()
            }
            is InboxMessageEvents.OnUpdateMessages -> {
                onUpdateMessages(events.messages)
            }
            is InboxMessageEvents.OnUpdateValueMessage -> {
                onUpdateValueMessage(events.message)
            }
            is InboxMessageEvents.OnSuccessSend -> {
                onSuccessSend()
            }
        }
    }

    private fun onUpdateInboxModel(inboxModel: InboxModel) {
        state.value?.let { state ->
            this.state.value = state.copy(inboxModel = inboxModel)
            onTriggerEvent(InboxMessageEvents.GetMessage(inboxModel.id))
        }
    }

    private fun getMessage(id: String) {
        messageRef = database.root.child(id)
        Log.d(TAG, "getMessage: $id")
        val messageListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val messages: ArrayList<Message> = ArrayList()
                    for (sn in snapshot.children) {
                        val message: Message? = sn.getValue(Message::class.java)
                        if (message != null) messages.add(message)
                        else {
                            Log.d(TAG, "onDataChange: null message error")
                            return
                        }
                    }

                    onTriggerEvent(InboxMessageEvents.OnUpdateMessages(messages))
                    for (message in messages) {
                        Log.d(TAG, "onDataChange: " + message.value)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(TAG, "loadPost:onCancelled", error.toException())
            }
        }
        messageRef.addValueEventListener(messageListener)
    }

    private fun onUpdateMessages(messages: List<Message>?) {
        if (messages != null) {
            state.value?.let { state ->
                this.state.value = state.copy(messages = messages)
            }
        }
    }

    private fun onUpdateValueMessage(message: String) {
        state.value?.let { state ->
            this.state.value = state.copy(valueMessage = message)
        }
    }

    private fun onSuccessSend() {
        onUpdateValueMessage("")
    }

    private fun send() {
        state.value?.let { state ->
            val inboxModel = state.inboxModel!!
            val key = messageRef.push().key
            if (key == null) {
                Log.w(TAG, "Couldn't get push key for posts")
                return
            }
            val message = Message(
                id = inboxModel.idAuth,
                value = state.valueMessage,
                date = state.messages.size,
                type = 1
            )
            val messageValue = message.toMap()
            val childUpdates = hashMapOf<String, Any>(
                "$key" to messageValue
            )
            messageRef.updateChildren(childUpdates)
            onTriggerEvent(InboxMessageEvents.GetMessage(inboxModel.id))
            onTriggerEvent(InboxMessageEvents.OnSuccessSend)
        }
    }
}