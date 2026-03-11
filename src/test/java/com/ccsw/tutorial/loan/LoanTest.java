package com.ccsw.tutorial.loan;

import com.ccsw.tutorial.clients.ClientService;
import com.ccsw.tutorial.clients.model.Client;
import com.ccsw.tutorial.game.GameService;
import com.ccsw.tutorial.game.model.Game;
import com.ccsw.tutorial.loan.model.Loan;
import com.ccsw.tutorial.loan.model.LoanDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.*;

class LoanTest {

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private ClientService clientService;

    @Mock
    private GameService gameService;

    @InjectMocks
    private LoanServiceImpl loanService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save_WhenIdIsNull_ShouldCreateNewLoan() {
        LoanDTO dto = new LoanDTO();
        dto.setClientId(1L);
        dto.setGameId(2L);

        when(clientService.get(1L)).thenReturn(new Client());
        when(gameService.get(2L)).thenReturn(new Game());

        loanService.save(null, dto);

        verify(loanRepository, times(1)).save(any(Loan.class));
    }

    @Test
    void save_WhenIdExists_ShouldUpdateLoan() {
        Loan existing = new Loan();
        when(loanRepository.findById(10L)).thenReturn(Optional.of(existing));

        LoanDTO dto = new LoanDTO();
        dto.setClientId(1L);
        dto.setGameId(2L);

        when(clientService.get(1L)).thenReturn(new Client());
        when(gameService.get(2L)).thenReturn(new Game());

        loanService.save(10L, dto);

        verify(loanRepository).save(existing);
    }

    @Test
    void find_WithFilters_ShouldCallRepository() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Loan> emptyPage = new PageImpl<>(java.util.Collections.emptyList());

        when(loanRepository.findAll(any(), eq(pageable))).thenReturn(emptyPage);

        loanService.find("Zelda", "Pepe", LocalDate.now(), pageable);

        verify(loanRepository, times(1)).findAll(any(), eq(pageable));
    }

    @Test
    void delete_ShouldCallRepositoryDelete() {
        loanService.delete(5L);

        verify(loanRepository, times(1)).deleteById(5L);
    }
}