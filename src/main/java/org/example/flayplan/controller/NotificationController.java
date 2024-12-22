package org.example.flayplan.controller;

import org.example.dtos.ApprovalDTO;
import org.springframework.stereotype.Controller;

@Controller
public class NotificationController {

    private final NotificationWebSocketHandler webSocketHandler;

    public NotificationController(NotificationWebSocketHandler webSocketHandler) {
        this.webSocketHandler = webSocketHandler;
    }

    public void sendNotification(ApprovalDTO approvalDTO) {
        String notificationMessage = String.format(
                "Approval ID: %s updated to status: %s",
                approvalDTO.getId(),
                approvalDTO.getStatus()
        );
        webSocketHandler.sendMessageToAll(notificationMessage);
    }
}
