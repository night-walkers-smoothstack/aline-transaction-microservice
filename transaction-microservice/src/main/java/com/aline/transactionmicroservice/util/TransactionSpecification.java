package com.aline.transactionmicroservice.util;

import com.aline.transactionmicroservice.model.Transaction;
import com.aline.transactionmicroservice.model.Transaction_;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Arrays;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class TransactionSpecification implements Specification<Transaction> {

    private final TransactionCriteria criteria;

    @Override
    public Predicate toPredicate(Root<Transaction> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        String[] searchTerms = criteria.getSearchTerms();

        switch (criteria.getMode()) {
            case ACCOUNT:
                long accountId = criteria.getAccountId();
                return searchByAccount(accountId, searchTerms, root, criteriaBuilder);
            case MEMBER:
                long memberId = criteria.getMemberId();
                return searchByMemberId(memberId, searchTerms, root, criteriaBuilder);
            default:
                return null;
        }
    }

    public Predicate searchByAccount(long accountId, String[] searchTerms, Root<Transaction> root, CriteriaBuilder cb) {
        final Predicate byAccount = cb.equal(root.get("account").get("id"), accountId);
        Predicate[] bySearchTerms = Arrays.stream(searchTerms)
                .map(term -> searchBySearchTerm(term, root, cb))
                .toArray(Predicate[]::new);
        return cb.and(byAccount, cb.or(bySearchTerms));
    }

    public Predicate searchByMemberId(long memberId, String[] searchTerms, Root<Transaction> root, CriteriaBuilder cb) {
        final Predicate byMember = cb.equal(root.get("account").get("members").get("id"), memberId);
        Predicate[] bySearchTerms = Arrays.stream(searchTerms)
                .map(term -> searchBySearchTerm(term, root, cb))
                .toArray(Predicate[]::new);
        return cb.and(byMember, cb.or(bySearchTerms));
    }

    public Predicate searchBySearchTerm(String searchTerm, Root<Transaction> root, CriteriaBuilder cb) {
        String searchPattern = "%" + searchTerm + "%";
        Predicate byDescription = cb.like(cb.lower(root.get("description")), searchPattern);
        Predicate byMerchantName = cb.like(cb.lower(root.get("merchant").get("name")), searchPattern);
        Predicate byMerchantCode = cb.like(cb.lower(root.get("merchant").get("code")), searchPattern);
        Predicate byMerchantDescription = cb.like(cb.lower(root.get("merchant").get("description")), searchPattern);
        Predicate byMerchantAddress = cb.like(cb.lower(root.get("merchant").get("address")), searchPattern);
        Predicate byMerchantCity = cb.like(cb.lower(root.get("merchant").get("city")), searchPattern);
        Predicate byMerchantState = cb.like(cb.lower(root.get("merchant").get("state")), searchPattern);

        return cb.or(
                byDescription,
                byMerchantName,
                byMerchantCode,
                byMerchantDescription,
                byMerchantAddress,
                byMerchantCity,
                byMerchantState
        );
    }

}
