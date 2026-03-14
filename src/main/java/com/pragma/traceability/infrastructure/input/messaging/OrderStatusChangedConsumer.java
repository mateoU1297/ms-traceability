package com.pragma.traceability.infrastructure.input.messaging;

import com.pragma.traceability.domain.api.ITraceabilityServicePort;
import com.pragma.traceability.domain.model.OrderTrace;
import com.pragma.traceability.infrastructure.config.RabbitMQConfig;
import com.pragma.traceability.infrastructure.input.messaging.dto.OrderStatusChangedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderStatusChangedConsumer {

    private final ITraceabilityServicePort traceabilityServicePort;

    @RabbitListener(queues = RabbitMQConfig.ORDER_STATUS_CHANGED_QUEUE)
    public void handleOrderStatusChanged(OrderStatusChangedEvent event) {
        log.info("Received status change for order: {}", event.getOrderId());

        OrderTrace trace = new OrderTrace();
        trace.setOrderId(event.getOrderId());
        trace.setClientId(event.getClientId());
        trace.setPreviousStatus(event.getPreviousStatus());
        trace.setNewStatus(event.getNewStatus());
        trace.setChangedAt(event.getChangedAt());

        traceabilityServicePort.save(trace);
    }
}
