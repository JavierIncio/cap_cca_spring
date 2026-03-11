package com.ccsw.tutorial.common.pagination;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class PageableRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private int pageNumber;
    private int pageSize;

    private List<SortRequest> sort;

    public PageableRequest() {
        sort = new ArrayList<>();
    }

    public PageableRequest(int pageNumber, int pageSize) {
        this();
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    public PageableRequest(int pageNumber, int pageSize, List<SortRequest> sort) {
        this();
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.sort = sort;
    }

    @JsonIgnore
    public Pageable getPageable() {

        return PageRequest.of(this.pageNumber, this.pageSize, Sort.by(sort.stream().map(e -> new Sort.Order(e.getDirection(), e.getProperty())).collect(Collectors.toList())));
    }

    @Data
    public static class SortRequest implements Serializable {
        private static final long serialVersionUID = 1L;
        private String property;
        private Sort.Direction direction;
    }
}
