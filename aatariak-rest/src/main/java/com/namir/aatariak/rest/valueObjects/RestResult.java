package com.namir.aatariak.rest.valueObjects;

import java.util.List;

public class RestResult<T> {

    private List<T> results;

    private PaginationData pagination;

    public RestResult(
            List<T> results,
            PaginationData pagination
    )
    {
        this.results = results;
        this.pagination = pagination;
    }

}
