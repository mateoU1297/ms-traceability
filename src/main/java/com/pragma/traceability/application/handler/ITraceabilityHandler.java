package com.pragma.traceability.application.handler;

import com.pragma.traceability.application.dto.TraceResponse;

import java.util.List;

public interface ITraceabilityHandler {

    List<TraceResponse> getOrderTraces(Long orderId);
}
