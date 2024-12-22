package org.example.flayplan.messaging;

import org.example.dtos.ApprovalDTO;
import org.example.dtos.enums.LogLevel;
import org.example.flayplan.controller.NotificationController;
import org.example.flayplan.service.ApprovalService;
import org.example.flayplan.service.AuditLogService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ApprovalUpdateListener {

    @Autowired
    private ApprovalService approvalService;

    @Autowired
    private AuditLogService auditLogService;

    @Autowired
    private NotificationController notificationController;

    @RabbitListener(queues = "approval.update.queue")
    public void receiveApprovalUpdate(ApprovalDTO approvalDTO) {
        try {
            System.out.println("Received approval update: " + approvalDTO);

            auditLogService.logEvent(
                    "Received Approval Update",
                    approvalDTO.getApprovedBy(),
                    "Approval ID: " + approvalDTO.getId() + ", New Status: " + approvalDTO.getStatus(),
                    LogLevel.INFO
            );

            approvalService.updateApprovalStatus(approvalDTO);
            notificationController.sendNotification(approvalDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
