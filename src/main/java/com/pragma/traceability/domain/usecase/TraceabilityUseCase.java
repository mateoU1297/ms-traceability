package com.pragma.traceability.domain.usecase;

import com.pragma.traceability.domain.api.ITraceabilityServicePort;
import com.pragma.traceability.domain.exception.UnauthorizedTraceAccessException;
import com.pragma.traceability.domain.model.EmployeeEfficiency;
import com.pragma.traceability.domain.model.OrderEfficiency;
import com.pragma.traceability.domain.model.OrderTrace;
import com.pragma.traceability.domain.spi.ITraceabilityPersistencePort;

import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TraceabilityUseCase implements ITraceabilityServicePort {

    private final ITraceabilityPersistencePort traceabilityPersistencePort;

    private static final String PENDING = "PENDING";
    private static final String DELIVERED = "DELIVERED";

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

    @Override
    public List<OrderEfficiency> getOrdersEfficiency(Long restaurantId) {
        List<OrderTrace> traces = traceabilityPersistencePort.findByRestaurantId(restaurantId);

        Map<Long, List<OrderTrace>> tracesByOrder = traces.stream()
                .collect(Collectors.groupingBy(OrderTrace::getOrderId));

        return tracesByOrder.entrySet().stream()
                .map(entry -> buildOrderEfficiency(entry.getKey(), entry.getValue()))
                .filter(e -> e.getFinishedAt() != null)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeEfficiency> getEmployeesEfficiencyRanking(Long restaurantId) {
        List<OrderEfficiency> ordersEfficiency = getOrdersEfficiency(restaurantId);

        List<OrderTrace> traces = traceabilityPersistencePort.findByRestaurantId(restaurantId);

        Map<Long, Long> orderEmployee = traces.stream()
                .filter(t -> t.getEmployeeId() != null)
                .collect(Collectors.toMap(
                        OrderTrace::getOrderId,
                        OrderTrace::getEmployeeId,
                        (existing, replacement) -> existing
                ));

        Map<Long, List<OrderEfficiency>> byEmployee = ordersEfficiency.stream()
                .filter(e -> orderEmployee.containsKey(e.getOrderId()))
                .collect(Collectors.groupingBy(e -> orderEmployee.get(e.getOrderId())));

        return byEmployee.entrySet().stream()
                .map(entry -> {
                    Long employeeId = entry.getKey();
                    List<OrderEfficiency> orders = entry.getValue();

                    double avg = orders.stream()
                            .mapToLong(OrderEfficiency::getDurationMinutes)
                            .average()
                            .orElse(0);

                    EmployeeEfficiency efficiency = new EmployeeEfficiency();
                    efficiency.setEmployeeId(employeeId);
                    efficiency.setAverageDurationMinutes(avg);
                    efficiency.setTotalOrders((long) orders.size());
                    return efficiency;
                })
                .sorted(Comparator.comparingDouble(EmployeeEfficiency::getAverageDurationMinutes))
                .collect(Collectors.toList());
    }

    private OrderEfficiency buildOrderEfficiency(Long orderId, List<OrderTrace> traces) {
        OrderEfficiency efficiency = new OrderEfficiency();
        efficiency.setOrderId(orderId);

        traces.forEach(trace -> {
            if (PENDING.equals(trace.getNewStatus()))
                efficiency.setStartedAt(trace.getChangedAt());
            if (DELIVERED.equals(trace.getNewStatus()))
                efficiency.setFinishedAt(trace.getChangedAt());
        });

        if (efficiency.getStartedAt() != null && efficiency.getFinishedAt() != null) {
            efficiency.setDurationMinutes(
                    ChronoUnit.MINUTES.between(efficiency.getStartedAt(), efficiency.getFinishedAt())
            );
        }

        return efficiency;
    }
}
