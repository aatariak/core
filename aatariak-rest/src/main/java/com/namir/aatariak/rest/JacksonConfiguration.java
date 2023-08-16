package com.namir.aatariak.rest;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.namir.aatariak.rest.serializer.EmailAddressSerializer;
import com.namir.aatariak.rest.serializer.IdSerializer;
import com.namir.aatariak.rest.serializer.PermissionSerializer;
import com.namir.aatariak.rest.serializer.RoleSerializer;
import com.namir.aatariak.shared.valueObjects.EmailAddress;
import com.namir.aatariak.shared.valueObjects.ID;
import com.namir.aatariak.user.domain.entity.Role;
import com.namir.aatariak.user.domain.valueObject.Permission;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfiguration {
    @Bean
    public SimpleModule valueObjectModule() {
        SimpleModule module = new SimpleModule();
        module.addSerializer(EmailAddress.class, new EmailAddressSerializer());
        module.addSerializer(Permission.class, new PermissionSerializer());
        module.addSerializer(ID.class, new IdSerializer());
        module.addSerializer(Role.class, new RoleSerializer());
        return module;
    }
}
