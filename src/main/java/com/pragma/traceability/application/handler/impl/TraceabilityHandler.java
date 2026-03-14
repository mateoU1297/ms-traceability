package com.pragma.traceability.application.handler.impl;

import com.pragma.traceability.application.dto.TraceResponse;
import com.pragma.traceability.application.handler.ITraceabilityHandler;
import com.pragma.traceability.application.mapper.ITraceResponseMapper;
import com.pragma.traceability.domain.api.ITraceabilityServicePort;
import com.pragma.traceability.domain.model.OrderTrace;
import com.pragma.traceability.domain.spi.ISecurityContextPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TraceabilityHandler implements ITraceabilityHandler {

    private final ITraceabilityServicePort traceabilityServicePort;
    private final ITraceResponseMapper traceResponseMapper;
    private final ISecurityContextPort securityContextPort;

    @Override
    public List<TraceResponse> getOrderTraces(Long orderId) {
        Long clientId = securityContextPort.getAuthenticatedUserId();
        List<OrderTrace> traces = traceabilityServicePort.getOrderTraces(orderId, clientId);
        return traceResponseMapper.toResponseList(traces);
    }
}
