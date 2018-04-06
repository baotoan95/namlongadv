//package net.namlongadv.config;
//
//import javax.persistence.EntityManager;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import net.namlongadv.services.HibernateSearchService;
//
//@Configuration
//public class HibernateSearchConfiguration {
//	@Autowired
//	private EntityManager entityManager;
//
//	@Bean
//	HibernateSearchService hibernateSearchService() {
//		HibernateSearchService hibernateSearchService = new HibernateSearchService(entityManager);
//		hibernateSearchService.initializeHibernateSearch();
//		return hibernateSearchService;
//	}
//}