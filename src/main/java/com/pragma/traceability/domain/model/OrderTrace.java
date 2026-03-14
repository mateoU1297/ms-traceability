package com.pragma.traceability.domain.model;

import java.time.LocalDateTime;

public class OrderTrace {
    private String id;
    private Long orderId;
    private Long clientId;
    private String previousStatus;
    private String newStatus;
    private LocalDateTime changedAt;

    public OrderTrace() {
    }

    public OrderTrace(String id, Long orderId, Long clientId, String previousStatus, String newStatus,
                      LocalDateTime changedAt) {
        this.id = id;
        this.orderId = orderId;
        this.clientId = clientId;
        this.previousStatus = previousStatus;
        this.newStatus = newStatus;
        this.changedAt = changedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getPreviousStatus() {
        return previousStatus;
    }

    public void setPreviousStatus(String previousStatus) {
        this.previousStatus = previousStatus;
    }

    public String getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(String newStatus) {
        this.newStatus = newStatus;
    }

    public LocalDateTime getChangedAt() {
        return changedAt;
    }

    public void setChangedAt(LocalDateTime changedAt) {
        this.changedAt = changedAt;
    }
}
