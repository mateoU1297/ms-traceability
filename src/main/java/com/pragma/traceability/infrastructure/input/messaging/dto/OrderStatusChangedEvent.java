package com.pragma.traceability.infrastructure.input.messaging.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusChangedEvent {
    private Long orderId;
    private Long clientId;
    private String previousStatus;
    private String newStatus;
    private LocalDateTime changedAt;
}
