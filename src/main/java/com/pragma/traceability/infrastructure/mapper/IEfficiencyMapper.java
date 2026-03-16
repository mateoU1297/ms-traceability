package com.pragma.traceability.infrastructure.mapper;

import com.pragma.traceability.application.dto.EmployeeEfficiencyResponse;
import com.pragma.traceability.application.dto.OrderEfficiencyResponse;
import com.pragma.traceability.domain.model.EmployeeEfficiency;
import com.pragma.traceability.domain.model.OrderEfficiency;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IEfficiencyMapper {

    OrderEfficiencyResponse toResponse(OrderEfficiency orderEfficiency);

    EmployeeEfficiencyResponse toEmployeeResponse(EmployeeEfficiency employeeEfficiency);
}
