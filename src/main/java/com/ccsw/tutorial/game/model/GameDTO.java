package com.ccsw.tutorial.game.model;

import com.ccsw.tutorial.author.model.AuthorDTO;
import com.ccsw.tutorial.category.model.CategoryDTO;
import lombok.Data;

@Data
public class GameDTO {
    private Long id;

    private String title;

    private String age;

    private CategoryDTO category;

    private AuthorDTO author;
}
