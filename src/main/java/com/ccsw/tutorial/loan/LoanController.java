package com.ccsw.tutorial.loan;

import com.ccsw.tutorial.loan.model.Loan;
import com.ccsw.tutorial.loan.model.LoanDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Loan", description = "API of Loan")
@RequestMapping(value = "/loan")
@RestController
@CrossOrigin(origins = "*")
public class LoanController {

    private final LoanService loanService;
    private final ModelMapper modelMapper;

    @Autowired
    public LoanController(LoanService loanService, ModelMapper modelMapper) {
        this.loanService = loanService;
        this.modelMapper = modelMapper;
    }

    @Operation(summary = "Find", description = "Method that return a filtered list of Loans")
    @GetMapping
    public List<LoanDTO> find(@RequestParam(required = false) String gameTitle, @RequestParam(required = false) String clientName, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate loanDate,
            Pageable pageable) {

        Page<Loan> loans = loanService.find(gameTitle, clientName, loanDate, pageable);
        return loans.stream().map(e -> modelMapper.map(e, LoanDTO.class)).collect(Collectors.toList());
    }

    @Operation(summary = "Save or Update", description = "Method that saves or updates a Loan")
    @RequestMapping(path = { "", "/{id}" }, method = RequestMethod.PUT)
    public void save(@PathVariable(name = "id", required = false) Long id, @RequestBody LoanDTO dto) {
        loanService.save(id, dto);
    }

    @Operation(summary = "Delete", description = "Method that deletes a Loan")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        this.loanService.delete(id);
    }

}
