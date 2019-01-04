package net.namlongadv.specs;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import net.namlongadv.common.SearchCriteria;
import net.namlongadv.entities.User;
import net.namlongadv.utils.StringUtils;

public class UserSpecification implements Specification<User> {
	private SearchCriteria criteria;
	
	public UserSpecification(SearchCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		switch (criteria.getOperation()) {
		case LIKE:
			return cb.like(cb.lower(root.get(criteria.getKey())), "%" + StringUtils.convertStringIgnoreUtf8(criteria.getValue().toString()).toLowerCase() + "%");
		case EQUALITY:
			return cb.equal(cb.lower(root.get(criteria.getKey())), StringUtils.convertStringIgnoreUtf8(criteria.getValue().toString()).toLowerCase());
		default:
			return null;
		}
	}

}
