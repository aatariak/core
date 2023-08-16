package com.namir.aatariak.req.domain.entity;

import com.namir.aatariak.req.domain.valueObject.IdentificationImage;
import com.namir.aatariak.req.domain.valueObject.IdentificationImageList;
import com.namir.aatariak.shared.valueObjects.ID;
import lombok.Data;
import org.springframework.data.annotation.Id;
import com.namir.aatariak.req.domain.valueObject.Address;
import com.namir.aatariak.shared.valueObjects.EmailAddress;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Embedded;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Table("driver_requests")
public class DriverRequest {
    @Id
    private UUID id;

    @Column("first_name")
    private String firstName;

    @Column("last_name")
    private String lastName;

    @Column("email")
    @Embedded(onEmpty = Embedded.OnEmpty.USE_NULL)
    private EmailAddress email;

    @Column("address")
    private Address address;

    private String routes;

    @Column("id_papers")
    private IdentificationImageList idPapers;

    @Column("plate_nb")
    private String plateNb;

    @Column("date_created")
    private Date dateCreated;
}
