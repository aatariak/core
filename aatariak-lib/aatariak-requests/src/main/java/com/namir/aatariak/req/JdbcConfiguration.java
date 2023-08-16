package com.namir.aatariak.req;

import com.namir.aatariak.req.infrastructure.converter.AddressReadingConverter;
import com.namir.aatariak.req.infrastructure.converter.AddressWritingConverter;
import com.namir.aatariak.req.infrastructure.converter.IdPapersReadingConverter;
import com.namir.aatariak.req.infrastructure.converter.IdPapersWritingConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class JdbcConfiguration extends AbstractJdbcConfiguration {

    @Override
    protected List<?> userConverters() {
        return Arrays.asList(new AddressWritingConverter(), new AddressReadingConverter(), new IdPapersWritingConverter(), new IdPapersReadingConverter());
//        return Arrays.asList(new AddressWritingConverter(), new AddressReadingConverter());
    }
}
