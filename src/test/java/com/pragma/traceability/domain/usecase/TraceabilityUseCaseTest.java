package com.pragma.traceability.domain.usecase;

import com.pragma.traceability.domain.exception.UnauthorizedTraceAccessException;
import com.pragma.traceability.domain.model.EmployeeEfficiency;
import com.pragma.traceability.domain.model.OrderEfficiency;
import com.pragma.traceability.domain.model.OrderTrace;
import com.pragma.traceability.domain.spi.ITraceabilityPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TraceabilityUseCaseTest {

    @Mock
    private ITraceabilityPersistencePort traceabilityPersistencePort;

    @InjectMocks
    private TraceabilityUseCase traceabilityUseCase;

    private LocalDateTime baseTime;

    @BeforeEach
    void setUp() {
        baseTime = LocalDateTime.of(2026, 3, 14, 10, 0, 0);
    }

    @Test
    void save_shouldDelegateToPersistencePort() {
        OrderTrace trace = buildTrace(1L, 5L, 1L, null, null, "PENDING", baseTime);

        traceabilityUseCase.save(trace);

        verify(traceabilityPersistencePort).save(trace);
    }

    @Test
    void getOrderTraces_whenOrderBelongsToClient_shouldReturnTraces() {
        List<OrderTrace> traces = List.of(
                buildTrace(1L, 5L, 1L, null, null, "PENDING", baseTime)
        );
        when(traceabilityPersistencePort.findByOrderId(1L)).thenReturn(traces);

        List<OrderTrace> result = traceabilityUseCase.getOrderTraces(1L, 5L);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void getOrderTraces_whenOrderDoesNotBelongToClient_shouldThrowUnauthorizedTraceAccessException() {
        List<OrderTrace> traces = List.of(
                buildTrace(1L, 5L, 1L, null, null, "PENDING", baseTime)
        );
        when(traceabilityPersistencePort.findByOrderId(1L)).thenReturn(traces);

        assertThrows(UnauthorizedTraceAccessException.class,
                () -> traceabilityUseCase.getOrderTraces(1L, 99L));
    }

    @Test
    void getOrderTraces_whenNoTracesFound_shouldReturnEmptyList() {
        when(traceabilityPersistencePort.findByOrderId(1L)).thenReturn(List.of());

        List<OrderTrace> result = traceabilityUseCase.getOrderTraces(1L, 5L);

        assertTrue(result.isEmpty());
    }

    @Test
    void getOrdersEfficiency_whenOrderHasPendingAndDelivered_shouldCalculateDuration() {
        List<OrderTrace> traces = List.of(
                buildTrace(1L, 5L, 1L, null, null, "PENDING", baseTime),
                buildTrace(1L, 5L, 1L, 20L, "PENDING", "IN_PREPARATION", baseTime.plusMinutes(10)),
                buildTrace(1L, 5L, 1L, 20L, "IN_PREPARATION", "DELIVERED", baseTime.plusMinutes(45))
        );
        when(traceabilityPersistencePort.findByRestaurantId(1L)).thenReturn(traces);

        List<OrderEfficiency> result = traceabilityUseCase.getOrdersEfficiency(1L);

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getOrderId());
        assertEquals(45L, result.get(0).getDurationMinutes());
        assertEquals(baseTime, result.get(0).getStartedAt());
        assertEquals(baseTime.plusMinutes(45), result.get(0).getFinishedAt());
    }

    @Test
    void getOrdersEfficiency_whenOrderIsNotDelivered_shouldBeExcluded() {
        List<OrderTrace> traces = List.of(
                buildTrace(1L, 5L, 1L, null, null, "PENDING", baseTime),
                buildTrace(1L, 5L, 1L, 20L, "PENDING", "IN_PREPARATION", baseTime.plusMinutes(10))
        );
        when(traceabilityPersistencePort.findByRestaurantId(1L)).thenReturn(traces);

        List<OrderEfficiency> result = traceabilityUseCase.getOrdersEfficiency(1L);

        assertTrue(result.isEmpty());
    }

    @Test
    void getOrdersEfficiency_whenMultipleOrders_shouldCalculateEachSeparately() {
        List<OrderTrace> traces = List.of(
                buildTrace(1L, 5L, 1L, null, null, "PENDING", baseTime),
                buildTrace(1L, 5L, 1L, 20L, "PENDING", "DELIVERED", baseTime.plusMinutes(30)),
                buildTrace(2L, 6L, 1L, null, null, "PENDING", baseTime),
                buildTrace(2L, 6L, 1L, 21L, "PENDING", "DELIVERED", baseTime.plusMinutes(60))
        );
        when(traceabilityPersistencePort.findByRestaurantId(1L)).thenReturn(traces);

        List<OrderEfficiency> result = traceabilityUseCase.getOrdersEfficiency(1L);

        assertEquals(2, result.size());
    }

    @Test
    void getOrdersEfficiency_whenNoTraces_shouldReturnEmptyList() {
        when(traceabilityPersistencePort.findByRestaurantId(1L)).thenReturn(List.of());

        List<OrderEfficiency> result = traceabilityUseCase.getOrdersEfficiency(1L);

        assertTrue(result.isEmpty());
    }

    @Test
    void getEmployeesEfficiencyRanking_shouldCalculateAverageDurationPerEmployee() {
        List<OrderTrace> traces = List.of(
                buildTrace(1L, 5L, 1L, 20L, null, "PENDING", baseTime),
                buildTrace(1L, 5L, 1L, 20L, "PENDING", "DELIVERED", baseTime.plusMinutes(40)),
                buildTrace(2L, 6L, 1L, 20L, null, "PENDING", baseTime),
                buildTrace(2L, 6L, 1L, 20L, "PENDING", "DELIVERED", baseTime.plusMinutes(60))
        );
        when(traceabilityPersistencePort.findByRestaurantId(1L)).thenReturn(traces);

        List<EmployeeEfficiency> result = traceabilityUseCase.getEmployeesEfficiencyRanking(1L);

        assertEquals(1, result.size());
        assertEquals(20L, result.get(0).getEmployeeId());
        assertEquals(50.0, result.get(0).getAverageDurationMinutes());
        assertEquals(2L, result.get(0).getTotalOrders());
    }

    @Test
    void getEmployeesEfficiencyRanking_shouldBeSortedAscByAverageDuration() {
        List<OrderTrace> traces = List.of(
                buildTrace(1L, 5L, 1L, 20L, null, "PENDING", baseTime),
                buildTrace(1L, 5L, 1L, 20L, "PENDING", "DELIVERED", baseTime.plusMinutes(60)),

                buildTrace(2L, 6L, 1L, 21L, null, "PENDING", baseTime),
                buildTrace(2L, 6L, 1L, 21L, "PENDING", "DELIVERED", baseTime.plusMinutes(30))
        );
        when(traceabilityPersistencePort.findByRestaurantId(1L)).thenReturn(traces);

        List<EmployeeEfficiency> result = traceabilityUseCase.getEmployeesEfficiencyRanking(1L);

        assertEquals(2, result.size());
        assertEquals(21L, result.get(0).getEmployeeId());
        assertEquals(20L, result.get(1).getEmployeeId());
    }

    @Test
    void getEmployeesEfficiencyRanking_whenNoDeliveredOrders_shouldReturnEmptyList() {
        List<OrderTrace> traces = List.of(
                buildTrace(1L, 5L, 1L, 20L, null, "PENDING", baseTime),
                buildTrace(1L, 5L, 1L, 20L, "PENDING", "IN_PREPARATION", baseTime.plusMinutes(10))
        );
        when(traceabilityPersistencePort.findByRestaurantId(1L)).thenReturn(traces);

        List<EmployeeEfficiency> result = traceabilityUseCase.getEmployeesEfficiencyRanking(1L);

        assertTrue(result.isEmpty());
    }

    @Test
    void getEmployeesEfficiencyRanking_shouldCallPersistencePortTwice() {
        when(traceabilityPersistencePort.findByRestaurantId(1L)).thenReturn(List.of());

        traceabilityUseCase.getEmployeesEfficiencyRanking(1L);

        verify(traceabilityPersistencePort, times(2)).findByRestaurantId(1L);
    }

    private OrderTrace buildTrace(Long orderId, Long clientId, Long restaurantId,
                                  Long employeeId, String previousStatus,
                                  String newStatus, LocalDateTime changedAt) {
        OrderTrace trace = new OrderTrace();
        trace.setOrderId(orderId);
        trace.setClientId(clientId);
        trace.setRestaurantId(restaurantId);
        trace.setEmployeeId(employeeId);
        trace.setPreviousStatus(previousStatus);
        trace.setNewStatus(newStatus);
        trace.setChangedAt(changedAt);
        return trace;
    }
}