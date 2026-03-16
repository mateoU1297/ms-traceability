package com.pragma.traceability.infrastructure.out.mongodb.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Document(collection = "order_traces")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderTraceDocument {

    @Id
    private String id;

    @Field("order_id")
    private Long orderId;

    @Field("client_id")
    private Long clientId;

    @Field("restaurant_id")
    private Long restaurantId;

    @Field("employee_id")
    private Long employeeId;

    @Field("previous_status")
    private String previousStatus;

    @Field("new_status")
    private String newStatus;

    @Field("changed_at")
    private LocalDateTime changedAt;
}