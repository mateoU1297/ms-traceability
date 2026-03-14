package com.pragma.traceability.domain.usecase;

import com.pragma.traceability.domain.api.ITraceabilityServicePort;
import com.pragma.traceability.domain.exception.UnauthorizedTraceAccessException;
import com.pragma.traceability.domain.model.OrderTrace;
import com.pragma.traceability.domain.spi.ITraceabilityPersistencePort;

import java.util.List;

public class TraceabilityUseCase implements ITraceabilityServicePort {

    private final ITraceabilityPersistencePort traceabilityPersistencePort;

    public TraceabilityUseCase(ITraceabilityPersistencePort traceabilityPersistencePort) {
        this.traceabilityPersistencePort = traceabilityPersistencePort;
    }

    @Override
    public void save(OrderTrace orderTrace) {
        traceabilityPersistencePort.save(orderTrace);
    }

    @Override
    public List<OrderTrace> getOrderTraces(Long orderId, Long clientId) {
        List<OrderTrace> traces = traceabilityPersistencePort.findByOrderId(orderId);

        if (traces.isEmpty()) return traces;

        boolean belongsToClient = traces.stream()
                .anyMatch(t -> t.getClientId().equals(clientId));

        if (!belongsToClient)
            throw new UnauthorizedTraceAccessException(
                    String.format("Order %d does not belong to client %d", orderId, clientId)
            );

        return traces;
    }
}
