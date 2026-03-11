package com.ccsw.tutorial.category;

import com.ccsw.tutorial.category.model.Category;
import com.ccsw.tutorial.category.model.CategoryDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Category", description = "API of Category")
@RequestMapping(value = "/category")
@RestController
@CrossOrigin(origins = "*")
public class CategoryController {
    private final CategoryService categoryService;
    private final ModelMapper mapper;

    @Autowired
    public CategoryController(CategoryService categoryService, ModelMapper mapper) {
        this.categoryService = categoryService;
        this.mapper = mapper;
    }

    /**
     * Método para recuperar todas las categorias
     *
     * @return {@link List} de {@link CategoryDTO}
     */
    @Operation(summary = "Find", description = "Method that return a list of Categories")
    @GetMapping()
    public List<CategoryDTO> findAll() {
        List<Category> categories = this.categoryService.findAll();
        return categories.stream().map(c -> mapper.map(c, CategoryDTO.class)).collect(Collectors.toList());
    }

    /**
     * Método para crear o actualizar una categoria
     *
     * @param id  PK de la entidad
     * @param dto datos de la entidad
     */
    @Operation(summary = "Save or Update", description = "Method that saves or updates a Category")
    @RequestMapping(path = { "", "/{id}" }, method = RequestMethod.PUT)
    public void save(@PathVariable(required = false) Long id, @RequestBody CategoryDTO dto) {
        this.categoryService.save(id, dto);
    }

    /**
     * Método para borrar una categoria
     *
     * @param id PK de la entidad
     */
    @Operation(summary = "Delete", description = "Method that deletes a Category")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) throws Exception {
        this.categoryService.delete(id);
    }

}
