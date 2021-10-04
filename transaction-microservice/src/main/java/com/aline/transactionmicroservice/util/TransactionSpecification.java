package com.aline.transactionmicroservice.util;

import com.aline.transactionmicroservice.model.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@RequiredArgsConstructor
public class TransactionSpecification implements Specification<Transaction> {

    private final TransactionCriteria criteria;

    @Override
    public Predicate toPredicate(Root<Transaction> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        String searchTerm = criteria.getSearchTerm();

        switch (criteria.getMode()) {
            case ACCOUNT:
                long accountId = criteria.getAccountId();
                return searchByAccount(accountId, searchTerm, root, criteriaBuilder);
            case MEMBER:
                long memberId = criteria.getMemberId();
                return searchByMemberId(memberId, searchTerm, root, criteriaBuilder);
            default:
                return null;
        }
    }

    public Predicate searchByAccount(long accountId, String searchTerm, Root<Transaction> root, CriteriaBuilder cb) {
        return cb.equal(root.get("account").get("id"), accountId);
    }

    public Predicate searchByMemberId(long memberId, String searchTerm, Root<Transaction> root, CriteriaBuilder cb) {
        return cb.equal(root.get("members").get("id"), memberId);
    }

}
