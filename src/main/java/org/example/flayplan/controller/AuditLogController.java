package org.example.flayplan.controller;

import org.example.flayplan.model.AuditLog;
import org.example.flayplan.service.AuditLogService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/auditlogs")
public class AuditLogController {
    private final AuditLogService service;

    public AuditLogController(AuditLogService service) {
        this.service = service;
    }

    @GetMapping("/by-time-range")
    public List<AuditLog> getLogsByTimeRange(@RequestParam LocalDateTime startTime, @RequestParam LocalDateTime endTime) {
        return service.getLogsByTimeRange(startTime, endTime);
    }
    //http://localhost:8080/api/auditlogs/by-time-range?startTime=2023-10-07T00:00:00&endTime=2028-10-07T23:59:59
}
