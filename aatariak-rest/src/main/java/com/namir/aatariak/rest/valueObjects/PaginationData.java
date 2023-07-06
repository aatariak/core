package com.namir.aatariak.rest.valueObjects;


import org.springframework.data.domain.Page;

public class PaginationData {

    public Integer currentPage;
    public Integer pageSize;
    public Integer currentPageSize;
    public Integer totalPages;
    public Long totalSize;

    public PaginationData(
            Integer currentPage,
            Integer pageSize,
            Integer currentPageSize,
            Integer totalPages,
            Long totalSize
    )
    {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.currentPageSize = currentPageSize;
        this.totalPages = totalPages;
        this.totalSize = totalSize;
    }

    public static PaginationData fromPage(Page page)
    {
        PaginationData paginationData = new PaginationData(
                page.getNumber() + 1,
                page.getSize(),
                page.getNumberOfElements(),
                page.getTotalPages(),
                page.getTotalElements()
        );

        return paginationData;
    }
}
