package net.namlongadv.specs;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

import net.namlongadv.common.SearchCriteria;
import net.namlongadv.entities.User;

public class UserSpecificationBuilder {
	private List<SearchCriteria> params;
	
	public UserSpecificationBuilder(List<SearchCriteria> params) {
		this.params = params;
	}
	
	public Specification<User> build() {
		if(Objects.isNull(params) || params.size() == 0) {
			return null;
		}
		List<Specification<User>> specs = new ArrayList<>();
		for(SearchCriteria criteria: params) {
			specs.add(new UserSpecification(criteria));
		}
		Specification<User> result = specs.get(0);
		for(Specification<User> spec: specs) {
			result = Specifications.where(result).and(spec);
		}
		return result;
	}
}
