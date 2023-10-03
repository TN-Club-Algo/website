package org.algotn.website.utils

import org.springframework.messaging.simp.SimpMessageHeaderAccessor
import org.springframework.messaging.simp.SimpMessagingTemplate

class NotificationUtils {

    companion object {

        private val token = System.getenv("WEBSOCKET_SECRET_TOKEN") ?: "secret_token"

        fun sendNotificationToUser(template: SimpMessagingTemplate?, username: String, message: String) {
            val headerAccessor = SimpMessageHeaderAccessor.create()
            headerAccessor.setHeader("Authorization", "Bearer $token")

            template!!.convertAndSendToUser(
                username,
                "/queue/return/notifications",
                message,
                headerAccessor.messageHeaders
            )
        }
    }
}