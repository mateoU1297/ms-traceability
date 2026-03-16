package com.pragma.traceability.domain.api;

import com.pragma.traceability.domain.model.EmployeeEfficiency;
import com.pragma.traceability.domain.model.OrderEfficiency;
import com.pragma.traceability.domain.model.OrderTrace;

import java.util.List;

public interface ITraceabilityServicePort {

    void save(OrderTrace orderTrace);

    List<OrderTrace> getOrderTraces(Long orderId, Long clientId);

    List<OrderEfficiency> getOrdersEfficiency(Long restaurantId);

    List<EmployeeEfficiency> getEmployeesEfficiencyRanking(Long restaurantId);
}
