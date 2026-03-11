package com.ccsw.tutorial.author.model;

import com.ccsw.tutorial.common.pagination.PageableRequest;
import lombok.Data;

@Data
public class AuthorSearchDTO {
    private PageableRequest pageable;
}
