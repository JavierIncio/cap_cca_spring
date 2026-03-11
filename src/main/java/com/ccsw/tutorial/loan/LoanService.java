package com.ccsw.tutorial.loan;

import com.ccsw.tutorial.loan.model.Loan;
import com.ccsw.tutorial.loan.model.LoanDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface LoanService {
    void save(Long id, LoanDTO dto);

    Page<Loan> find(String gameTitle, String clientName, LocalDate loanDate, Pageable pageable);

    void delete(Long id);
}
