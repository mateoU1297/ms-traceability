package com.pragma.traceability.application.mapper;

import com.pragma.traceability.application.dto.TraceResponse;
import com.pragma.traceability.domain.model.OrderTrace;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ITraceResponseMapper {
    TraceResponse toResponse(OrderTrace orderTrace);

    List<TraceResponse> toResponseList(List<OrderTrace> traces);
}