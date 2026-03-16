package com.pragma.traceability.infrastructure.out.mongodb.adapter;

import com.pragma.traceability.domain.model.OrderTrace;
import com.pragma.traceability.domain.spi.ITraceabilityPersistencePort;
import com.pragma.traceability.infrastructure.mapper.IOrderTraceDocumentMapper;
import com.pragma.traceability.infrastructure.out.mongodb.repository.OrderTraceMongoRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class TraceabilityMongoAdapter implements ITraceabilityPersistencePort {

    private final OrderTraceMongoRepository orderTraceMongoRepository;
    private final IOrderTraceDocumentMapper orderTraceDocumentMapper;

    @Override
    public void save(OrderTrace orderTrace) {
        orderTraceMongoRepository.save(
                orderTraceDocumentMapper.toDocument(orderTrace)
        );
    }

    @Override
    public List<OrderTrace> findByOrderId(Long orderId) {
        return orderTraceDocumentMapper.toDomainList(
                orderTraceMongoRepository.findByOrderIdOrderByChangedAtAsc(orderId)
        );
    }

    @Override
    public List<OrderTrace> findByRestaurantId(Long restaurantId) {
        return orderTraceDocumentMapper.toDomainList(
                orderTraceMongoRepository.findByRestaurantIdOrderByChangedAtAsc(restaurantId)
        );
    }
}