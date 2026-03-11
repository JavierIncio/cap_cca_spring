package com.ccsw.tutorial.loan;

import com.ccsw.tutorial.clients.ClientService;
import com.ccsw.tutorial.common.criteria.SearchCriteria;
import com.ccsw.tutorial.game.GameService;
import com.ccsw.tutorial.loan.model.Loan;
import com.ccsw.tutorial.loan.model.LoanDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class LoanServiceImpl implements LoanService {
    private final LoanRepository loanRepository;

    private final ClientService clientService;
    private final GameService gameService;

    @Autowired
    public LoanServiceImpl(LoanRepository loanRepository, ClientService clientService, GameService gameService) {
        this.loanRepository = loanRepository;
        this.clientService = clientService;
        this.gameService = gameService;
    }

    @Override
    public void save(Long id, LoanDTO dto) {
        Loan loan;

        if (id == null)
            loan = new Loan();
        else
            loan = this.loanRepository.findById(id).orElse(null);

        BeanUtils.copyProperties(dto, loan, "id", "client", "game");

        loan.setClient(clientService.get(dto.getClientId()));
        loan.setGame(gameService.get(dto.getGameId()));

        this.loanRepository.save(loan);
    }

    @Override
    public Page<Loan> find(String gameTitle, String clientName, LocalDate loanDate, Pageable pageable) {

        List<Specification<Loan>> specs = new ArrayList<>();

        if (gameTitle != null && !gameTitle.trim().isEmpty()) {
            specs.add(new LoanSpecification(new SearchCriteria("game.title", ":", gameTitle)));
        }

        if (clientName != null && !clientName.trim().isEmpty()) {
            specs.add(new LoanSpecification(new SearchCriteria("client.name", ":", clientName)));
        }

        if (loanDate != null) {
            specs.add(new LoanSpecification(new SearchCriteria("startDate", "<=", loanDate)));
            specs.add(new LoanSpecification(new SearchCriteria("endDate", ">=", loanDate)));
        }

        Specification<Loan> spec = Specification.allOf(specs);

        return loanRepository.findAll(spec, pageable);

    }

    @Override
    public void delete(Long id) {
        this.loanRepository.deleteById(id);
    }

}
