package com.pragma.traceability.infrastructure.out.mongodb.repository;

import com.pragma.traceability.infrastructure.out.mongodb.document.OrderTraceDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderTraceMongoRepository extends MongoRepository<OrderTraceDocument, String> {

    List<OrderTraceDocument> findByOrderIdOrderByChangedAtAsc(Long orderId);

    List<OrderTraceDocument> findByRestaurantIdOrderByChangedAtAsc(Long restaurantId);
}
