//package org.example.flayplan.service;
//
//import org.example.dtos.enums.LogLevel;
//import org.example.flayplan.model.AuditLog;
//import org.example.flayplan.repository.AuditLogRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//
//@Service
//public class AuditLogService {
//    @Autowired
//    private AuditLogRepository auditLogRepository;
////    @Autowired
////    private SimpMessagingTemplate messagingTemplate;
//
//    public void logEvent(String action, String performedBy, String details, LogLevel level) {
//        AuditLog log = new AuditLog(action, LocalDateTime.now(), performedBy, details, level);
//        log.setLevel(level);
//        auditLogRepository.save(log);
//
////        messagingTemplate.convertAndSend("/topic/audit-logs", log);
//    }
//}
package org.example.flayplan.service;

import org.example.dtos.enums.LogLevel;
import org.example.flayplan.model.AuditLog;
import org.example.flayplan.repository.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuditLogService {

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void logEvent(String action, String performedBy, String details, LogLevel level) {
        AuditLog log = new AuditLog(action, LocalDateTime.now(), performedBy, details, level);
        log.setLevel(level);
        auditLogRepository.save(log);

        messagingTemplate.convertAndSend("/topic/audit-logs", log);
    }
}
