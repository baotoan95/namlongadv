package net.namlongadv.specs;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import net.namlongadv.common.SearchCriteria;
import net.namlongadv.entities.Advertisement;

public class AdvertSpecification implements Specification<Advertisement> {
	private SearchCriteria criteria;
	
	public AdvertSpecification(SearchCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public Predicate toPredicate(Root<Advertisement> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		switch (criteria.getOperation()) {
		case LIKE:
			return cb.like(cb.lower(root.get(criteria.getKey())), "%" + criteria.getValue() + "%");
		case EQUALITY:
			return cb.equal(root.get(criteria.getKey()), criteria.getValue());
		default:
			return null;
		}
	}

}
