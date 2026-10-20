package com.example.camellando.data.mock

data class ChatMessage(
    val id: String,
    val senderId: String,
    val senderName: String,
    val text: String,
    val timestamp: String,
    val isFromMe: Boolean
)

data class ChatPreview(
    val id: String,
    val providerName: String,
    val profession: String,
    val lastMessage: String,
    val time: String,
    val unreadCount: Int,
    val messages: MutableList<ChatMessage>
)

val initialMockChats = listOf(
    ChatPreview(
        id = "chat_juan",
        providerName = "Juan Álvarez",
        profession = "Electricista Matriculado",
        lastMessage = "Perfecto, estaré allí a las 9:00 AM.",
        time = "10:45 AM",
        unreadCount = 1,
        messages = mutableListOf(
            ChatMessage(
                id = "m1",
                senderId = "juan",
                senderName = "Juan Álvarez",
                text = "Hola, vi tu solicitud para la instalación eléctrica.",
                timestamp = "10:30 AM",
                isFromMe = false
            ),
            ChatMessage(
                id = "m2",
                senderId = "me",
                senderName = "Tú",
                text = "¿Podría ser este martes por la mañana?",
                timestamp = "10:35 AM",
                isFromMe = true
            ),
            ChatMessage(
                id = "m3",
                senderId = "juan",
                senderName = "Juan Álvarez",
                text = "Perfecto, estaré allí a las 9:00 AM.",
                timestamp = "10:45 AM",
                isFromMe = false
            )
        )
    ),
    ChatPreview(
        id = "chat_maria",
        providerName = "María Gómez",
        profession = "Diseñadora de Interiores",
        lastMessage = "Me encantan los tonos que sugieres.",
        time = "Ayer",
        unreadCount = 0,
        messages = mutableListOf(
            ChatMessage(
                id = "m201",
                senderId = "maria",
                senderName = "María Gómez",
                text = "¿Qué te parece la propuesta de paleta de colores?",
                timestamp = "Ayer",
                isFromMe = false
            ),
            ChatMessage(
                id = "m202",
                senderId = "me",
                senderName = "Tú",
                text = "Me encantan los tonos que sugieres.",
                timestamp = "Ayer",
                isFromMe = true
            )
        )
    ),
    ChatPreview(
        id = "chat_carlos",
        providerName = "Carlos Ruiz",
        profession = "Plomero Profesional",
        lastMessage = "Quedó solucionada la fuga de agua.",
        time = "18 Oct",
        unreadCount = 0,
        messages = mutableListOf(
            ChatMessage(
                id = "m301",
                senderId = "carlos",
                senderName = "Carlos Ruiz",
                text = "Quedó solucionada la fuga de agua.",
                timestamp = "18 Oct",
                isFromMe = false
            )
        )
    ),
    ChatPreview(
        id = "chat_ana",
        providerName = "Ana Torres",
        profession = "Técnica en Refrigeración",
        lastMessage = "Revisaré el mantenimiento del aire acondicionado.",
        time = "15 Oct",
        unreadCount = 0,
        messages = mutableListOf(
            ChatMessage(
                id = "m401",
                senderId = "ana",
                senderName = "Ana Torres",
                text = "Revisaré el mantenimiento del aire acondicionado.",
                timestamp = "15 Oct",
                isFromMe = false
            )
        )
    )
)
