package com.bookmaster.specification;

import org.springframework.data.jpa.domain.Specification;

import com.bookmaster.repository.model.CustomerDetails;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class CustomerSpecification implements Specification<CustomerDetails> {

	@Override
	public Predicate toPredicate(Root<CustomerDetails> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		Predicate like = criteriaBuilder.like(root.get(CustomerDetails.class.getName()), "%"+ "a" +"%");
		return null;
	}
}