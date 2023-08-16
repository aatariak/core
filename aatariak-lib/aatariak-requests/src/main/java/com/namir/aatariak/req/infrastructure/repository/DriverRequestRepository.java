package com.namir.aatariak.req.infrastructure.repository;

import com.namir.aatariak.req.domain.entity.DriverRequest;
import com.namir.aatariak.shared.valueObjects.ID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository()
public interface DriverRequestRepository extends CrudRepository<DriverRequest, ID> {
    Optional<DriverRequest> findByEmail(String email);
}
