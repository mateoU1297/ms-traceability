package com.pragma.traceability.infrastructure.input.rest;

import com.pragma.traceability.application.dto.TraceResponse;
import com.pragma.traceability.application.handler.ITraceabilityHandler;
import com.pragma.traceability.infrastructure.adapter.in.rest.api.TraceabilityApi;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TraceabilityController implements TraceabilityApi {

    private final ITraceabilityHandler traceabilityHandler;

    @Override
    public ResponseEntity<List<TraceResponse>> getOrderTraces(Long orderId) {
        return ResponseEntity.ok(traceabilityHandler.getOrderTraces(orderId));
    }
}