package net.namlongadv.specs;

import java.text.ParseException;
import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.data.jpa.domain.Specification;

import net.namlongadv.common.SearchCriteria;
import net.namlongadv.entities.Advertisement;
import net.namlongadv.utils.DateUtils;
import net.namlongadv.utils.StringUtils;

public class AdvertSpecification implements Specification<Advertisement> {
	private SearchCriteria criteria;
	
	public AdvertSpecification(SearchCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public Predicate toPredicate(Root<Advertisement> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		switch (criteria.getOperation()) {
		case LIKE:
			return cb.like(cb.lower(root.get(criteria.getKey())), "%" + StringUtils.convertStringIgnoreUtf8(criteria.getValue().toString()).toLowerCase() + "%");
		case EQUALITY:
			if(criteria.getKey().equalsIgnoreCase("createdBy")) {
				return cb.equal(cb.lower(root.get("createdBy").get("username")), criteria.getValue().toString().toLowerCase());
			}
			return cb.equal(cb.lower(root.get(criteria.getKey())), StringUtils.convertStringIgnoreUtf8(criteria.getValue().toString()).toLowerCase());
		case BETWEEN:
			if(criteria.getKey().equalsIgnoreCase("createdDate")) {
				JSONObject obj = null;
				Date from = null;
				Date to = null;
				try {
					obj = new JSONObject(criteria.getValue().toString());
					from = DateUtils.convertStringToDate(obj.getString("start"));
					to = DateUtils.increaseDay(DateUtils.convertStringToDate(obj.getString("end")), 0);
				} catch (ParseException | JSONException e) {
					try {
						from = DateUtils.convertStringToDate("01/01/2018");
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
					to = DateUtils.increaseDay(new Date(), 1);
				}
				return cb.between(root.get(criteria.getKey()), from, to);
			}
		default:
			return null;
		}
	}

}
