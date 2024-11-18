//package org.example.flayplan.messaging;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.example.flayplan.service.dtos.ApprovalDTO;
//import org.springframework.amqp.core.AmqpTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//@Component
//public class ApprovalProducer {
//
//    @Autowired
//    private AmqpTemplate amqpTemplate;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Value("${spring.rabbitmq.exchange}")
//    private String exchangeName;
//
//    public void sendApprovalRequest(ApprovalDTO approvalDTO) {
//        try {
//            String message = objectMapper.writeValueAsString(approvalDTO);
//            amqpTemplate.convertAndSend(exchangeName, "approval.requests", message);
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to send approval request message", e);
//        }
//    }
//}
