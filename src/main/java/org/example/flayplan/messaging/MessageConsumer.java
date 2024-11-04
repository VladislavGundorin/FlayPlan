package org.example.flayplan.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.flayplan.conf.RabbitMQConfig;
import org.example.flayplan.service.FlightPlanService;
import org.example.flayplan.service.dtos.FlightPlanDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MessageConsumer {

    private final FlightPlanService flightPlanService;
    private final ObjectMapper objectMapper;

    public MessageConsumer(FlightPlanService flightPlanService, ObjectMapper objectMapper) {
        this.flightPlanService = flightPlanService;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void receiveMessage(String message) {
        try {
            System.out.println("Received message: " + message);

            FlightPlanDTO flightPlanDTO = objectMapper.readValue(message, FlightPlanDTO.class);

            FlightPlanDTO createdPlan = flightPlanService.createFlightPlan(flightPlanDTO);
            System.out.println("Created Flight Plan: " + createdPlan);

        } catch (IOException e) {
            System.err.println("Failed to parse message: " + e.getMessage());
        } catch (RuntimeException e) {
            System.err.println("Error during message processing: " + e.getMessage());
        }
    }
}
