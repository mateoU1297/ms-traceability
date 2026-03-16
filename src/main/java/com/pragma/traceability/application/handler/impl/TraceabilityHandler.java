package com.pragma.traceability.application.handler.impl;

import com.pragma.traceability.application.dto.EmployeeEfficiencyResponse;
import com.pragma.traceability.application.dto.OrderEfficiencyResponse;
import com.pragma.traceability.application.dto.TraceResponse;
import com.pragma.traceability.application.handler.ITraceabilityHandler;
import com.pragma.traceability.application.mapper.ITraceResponseMapper;
import com.pragma.traceability.domain.api.ITraceabilityServicePort;
import com.pragma.traceability.domain.model.OrderTrace;
import com.pragma.traceability.domain.spi.ISecurityContextPort;
import com.pragma.traceability.infrastructure.mapper.IEfficiencyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TraceabilityHandler implements ITraceabilityHandler {

    private final ITraceabilityServicePort traceabilityServicePort;
    private final ITraceResponseMapper traceResponseMapper;
    private final ISecurityContextPort securityContextPort;
    private final IEfficiencyMapper efficiencyMapper;

    @Override
    public List<TraceResponse> getOrderTraces(Long orderId) {
        Long clientId = securityContextPort.getAuthenticatedUserId();
        List<OrderTrace> traces = traceabilityServicePort.getOrderTraces(orderId, clientId);
        return traceResponseMapper.toResponseList(traces);
    }

    @Override
    public List<OrderEfficiencyResponse> getOrdersEfficiency(Long restaurantId) {
        return traceabilityServicePort.getOrdersEfficiency(restaurantId)
                .stream()
                .map(efficiencyMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeEfficiencyResponse> getEmployeesEfficiencyRanking(Long restaurantId) {
        return traceabilityServicePort.getEmployeesEfficiencyRanking(restaurantId)
                .stream()
                .map(efficiencyMapper::toEmployeeResponse)
                .collect(Collectors.toList());
    }
}
