package net.namlongadv.specs;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

import net.namlongadv.common.SearchCriteria;
import net.namlongadv.entities.Advertisement;

public class AdvertSpecificationBuilder {
	private List<SearchCriteria> params;
	
	public AdvertSpecificationBuilder(List<SearchCriteria> params) {
		this.params = params;
	}
	
	public Specification<Advertisement> build() {
		if(Objects.isNull(params) || params.size() == 0) {
			return null;
		}
		List<Specification<Advertisement>> specs = new ArrayList<>();
		for(SearchCriteria criteria: params) {
			specs.add(new AdvertSpecification(criteria));
		}
		Specification<Advertisement> result = specs.get(0);
		for(Specification<Advertisement> spec: specs) {
			result = Specifications.where(result).and(spec);
		}
		return result;
	}
}
