package com.pragma.traceability.infrastructure.mapper;

import com.pragma.traceability.domain.model.OrderTrace;
import com.pragma.traceability.infrastructure.out.mongodb.document.OrderTraceDocument;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IOrderTraceDocumentMapper {

    OrderTraceDocument toDocument(OrderTrace orderTrace);

    OrderTrace toDomain(OrderTraceDocument document);

    List<OrderTrace> toDomainList(List<OrderTraceDocument> documents);
}
