package com.pragma.traceability.infrastructure.out.security.adapter;

import com.pragma.traceability.domain.spi.ISecurityContextPort;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SecurityContextAdapter implements ISecurityContextPort {

    private final HttpServletRequest httpServletRequest;

    @Override
    public Long getAuthenticatedUserId() {
        String userId = httpServletRequest.getHeader("X-User-Id");
        if (userId == null)
            throw new RuntimeException("X-User-Id header not found");
        return Long.parseLong(userId);
    }
}