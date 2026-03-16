package com.pragma.traceability.application.handler;

import com.pragma.traceability.application.dto.EmployeeEfficiencyResponse;
import com.pragma.traceability.application.dto.OrderEfficiencyResponse;
import com.pragma.traceability.application.dto.TraceResponse;

import java.util.List;

public interface ITraceabilityHandler {

    List<TraceResponse> getOrderTraces(Long orderId);

    List<OrderEfficiencyResponse> getOrdersEfficiency(Long restaurantId);

    List<EmployeeEfficiencyResponse> getEmployeesEfficiencyRanking(Long restaurantId);
}
