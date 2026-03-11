package com.ccsw.tutorial.author;

import com.ccsw.tutorial.author.model.Author;
import com.ccsw.tutorial.author.model.AuthorDTO;
import com.ccsw.tutorial.author.model.AuthorSearchDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AuthorService {
    Author get(Long id);

    List<Author> findAll();

    Page<Author> findPage(AuthorSearchDTO dto);

    void save(Long id, AuthorDTO dto);

    void delete(Long id) throws Exception;
}
