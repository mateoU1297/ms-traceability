package com.pragma.traceability.domain.spi;

import com.pragma.traceability.domain.model.OrderTrace;

import java.util.List;

public interface ITraceabilityPersistencePort {

    void save(OrderTrace orderTrace);

    List<OrderTrace> findByOrderId(Long orderId);

    List<OrderTrace> findByRestaurantId(Long restaurantId);
}
