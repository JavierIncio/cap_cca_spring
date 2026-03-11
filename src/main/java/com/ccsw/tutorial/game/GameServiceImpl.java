package com.ccsw.tutorial.game;

import com.ccsw.tutorial.author.AuthorService;
import com.ccsw.tutorial.category.CategoryService;
import com.ccsw.tutorial.common.criteria.SearchCriteria;
import com.ccsw.tutorial.game.model.Game;
import com.ccsw.tutorial.game.model.GameDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;
    private final AuthorService authorService;
    private final CategoryService categoryService;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository, AuthorService authorService, CategoryService categoryService) {
        this.gameRepository = gameRepository;
        this.authorService = authorService;
        this.categoryService = categoryService;
    }

    @Override
    public List<Game> find(String title, Long idCategory) {
        GameSpecification titleSpec = new GameSpecification(new SearchCriteria("title", ":", title));
        GameSpecification categorySpec = new GameSpecification(new SearchCriteria("category.id", ":", idCategory));

        Specification<Game> spec = titleSpec.and(categorySpec);

        return this.gameRepository.findAll(spec);
    }

    @Override
    public void save(Long id, GameDTO dto) {
        Game game;

        if (id == null) {
            game = new Game();
        } else {
            game = this.gameRepository.findById(id).orElse(null);
        }

        BeanUtils.copyProperties(dto, game, "id", "author", "category");

        game.setAuthor(authorService.get(dto.getAuthor().getId()));
        game.setCategory(categoryService.get(dto.getCategory().getId()));

        this.gameRepository.save(game);
    }

    @Override
    public Game get(Long gameId) {
        return this.gameRepository.findById(gameId).orElse(null);
    }
}
