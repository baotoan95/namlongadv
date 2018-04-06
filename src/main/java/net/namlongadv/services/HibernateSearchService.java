//package net.namlongadv.services;
//
//import java.util.List;
//
//import javax.persistence.EntityManager;
//import javax.persistence.NoResultException;
//import javax.transaction.Transactional;
//
//import org.apache.lucene.search.Query;
//import org.hibernate.search.jpa.FullTextEntityManager;
//import org.hibernate.search.jpa.Search;
//import org.hibernate.search.query.dsl.QueryBuilder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import lombok.extern.slf4j.Slf4j;
//import net.namlongadv.models.Advertisement;
//
//@Service
//@Slf4j
//public class HibernateSearchService  {
//    private final EntityManager entityManager;
// 
//    @Autowired
//    public HibernateSearchService(EntityManager entityManager) {
//        super();
//        this.entityManager = entityManager;
//    }
// 
// 
//    public void initializeHibernateSearch() {
// 
//        try {
//            FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
//            fullTextEntityManager.createIndexer().startAndWait();
//        } catch (InterruptedException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
//    
//    @SuppressWarnings("unchecked")
//	@Transactional
//    public List<Advertisement> fuzzySearch(String searchTerm){
//        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
//        QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Advertisement.class).get();
//        Query luceneQuery = qb.keyword().fuzzy().withEditDistanceUpTo(1).withPrefixLength(1).onFields("addressSearching").matching(searchTerm).createQuery();
// 
//        javax.persistence.Query jpaQuery = fullTextEntityManager.createFullTextQuery(luceneQuery, Advertisement.class);
// 
//        // execute search
//        List<Advertisement> result = null;
//        try {
//        	result  = jpaQuery.getResultList();
//        } catch (NoResultException nre) {
//            log.warn("No result found");
//        }
// 
//        return result;
//    }
//}