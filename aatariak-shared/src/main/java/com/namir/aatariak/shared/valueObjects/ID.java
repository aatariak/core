package com.namir.aatariak.shared.valueObjects;




import com.namir.aatariak.shared.libs.ValueObjectBaseInterface;

import java.util.UUID;


public class ID implements ValueObjectBaseInterface {
    private UUID id;

    public ID(String id) {
        if (id == null) {
            this.id = UUID.randomUUID();
        } else {
            this.id = UUID.fromString(id);
        }
    }

    public ID()
    {
        this.id = UUID.randomUUID();
    }

    public String getValue()
    {
        return this.id.toString();
    }

    @Override
    public String toString() {
        return this.id.toString();
    }
}
