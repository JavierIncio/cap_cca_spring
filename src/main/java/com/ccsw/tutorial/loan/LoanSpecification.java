package com.ccsw.tutorial.loan;

import com.ccsw.tutorial.common.criteria.SearchCriteria;
import com.ccsw.tutorial.loan.model.Loan;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class LoanSpecification implements Specification<Loan> {

    private static final long serialVersionUID = 1L;

    private final SearchCriteria criteria;

    public LoanSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Loan> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        Path<?> path = getPath(root);

        if (":".equalsIgnoreCase(criteria.getOperation())) {
            if (path.getJavaType() == String.class) {
                return builder.like(builder.lower(path.as(String.class)), "%" + criteria.getValue().toString().toLowerCase() + "%");
            }
            return builder.equal(path, criteria.getValue());
        }

        if (path.getJavaType().isAssignableFrom(LocalDate.class) && criteria.getValue() instanceof LocalDate) {
            LocalDate loanDate = (LocalDate) criteria.getValue();
            switch (criteria.getOperation()) {
            case ">":
                return builder.greaterThan(path.as(LocalDate.class), loanDate);
            case ">=":
                return builder.greaterThanOrEqualTo(path.as(LocalDate.class), loanDate);
            case "<":
                return builder.lessThan(path.as(LocalDate.class), loanDate);
            case "<=":
                return builder.lessThanOrEqualTo(path.as(LocalDate.class), loanDate);
            default:
                return null;
            }
        }

        return null;
    }

    private Path<String> getPath(Root<Loan> root) {
        String key = criteria.getKey();
        String[] split = key.split("[.]", 0);

        Path<String> expression = root.get(split[0]);
        for (int i = 1; i < split.length; i++) {
            expression = expression.get(split[i]);
        }

        return expression;
    }
}
