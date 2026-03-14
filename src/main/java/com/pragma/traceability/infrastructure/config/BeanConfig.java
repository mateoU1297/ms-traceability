package com.pragma.traceability.infrastructure.config;

import com.pragma.traceability.domain.api.ITraceabilityServicePort;
import com.pragma.traceability.domain.spi.ISecurityContextPort;
import com.pragma.traceability.domain.spi.ITraceabilityPersistencePort;
import com.pragma.traceability.domain.usecase.TraceabilityUseCase;
import com.pragma.traceability.infrastructure.mapper.IOrderTraceDocumentMapper;
import com.pragma.traceability.infrastructure.out.mongodb.adapter.TraceabilityMongoAdapter;
import com.pragma.traceability.infrastructure.out.mongodb.repository.OrderTraceMongoRepository;
import com.pragma.traceability.infrastructure.out.security.adapter.SecurityContextAdapter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfig {

    private final OrderTraceMongoRepository orderTraceMongoRepository;
    private final IOrderTraceDocumentMapper orderTraceDocumentMapper;

    private final HttpServletRequest httpServletRequest;

    @Bean
    public ITraceabilityPersistencePort traceabilityPersistencePort() {
        return new TraceabilityMongoAdapter(orderTraceMongoRepository, orderTraceDocumentMapper);
    }

    @Bean
    public ITraceabilityServicePort traceabilityServicePort() {
        return new TraceabilityUseCase(traceabilityPersistencePort());
    }

    @Bean
    public ISecurityContextPort securityContextPort() {
        return new SecurityContextAdapter(httpServletRequest);
    }
}
