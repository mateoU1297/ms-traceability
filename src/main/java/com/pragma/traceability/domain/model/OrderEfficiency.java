package com.pragma.traceability.domain.model;

import java.time.LocalDateTime;

public class OrderEfficiency {
    private Long orderId;
    private LocalDateTime startedAt;
    private LocalDateTime finishedAt;
    private Long durationMinutes;

    public OrderEfficiency() {
    }

    public OrderEfficiency(Long orderId, LocalDateTime finishedAt, LocalDateTime startedAt, Long durationMinutes) {
        this.orderId = orderId;
        this.finishedAt = finishedAt;
        this.startedAt = startedAt;
        this.durationMinutes = durationMinutes;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public LocalDateTime getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(LocalDateTime finishedAt) {
        this.finishedAt = finishedAt;
    }

    public Long getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(Long durationMinutes) {
        this.durationMinutes = durationMinutes;
    }
}