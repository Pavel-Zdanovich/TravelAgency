package com.zdanovich.web.controller.system;

import com.zdanovich.core.entity.AbstractEntity;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.hibernate.search.SearchFactory;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.AllContext;
import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.FacetContext;
import org.hibernate.search.query.dsl.FuzzyContext;
import org.hibernate.search.query.dsl.MoreLikeThisContext;
import org.hibernate.search.query.dsl.PhraseContext;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.hibernate.search.query.dsl.QueryContextBuilder;
import org.hibernate.search.query.dsl.RangeContext;
import org.hibernate.search.query.dsl.SimpleQueryStringContext;
import org.hibernate.search.query.dsl.SpatialContext;
import org.hibernate.search.query.dsl.TermContext;
import org.hibernate.search.query.dsl.TermMatchingContext;
import org.hibernate.search.query.dsl.Unit;
import org.hibernate.search.query.dsl.WildcardContext;
import org.hibernate.search.query.dsl.sort.SortContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Log4j2
@RestController
@RequestMapping(path = SearchController.PATH)
@Transactional
public class SearchController {

    public static final String PATH = "/search";

    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;

    @GetMapping
    public ResponseEntity<?> search(@RequestBody Map<String, Object> map) {
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(this.entityManager);
        SearchFactory searchFactory = fullTextEntityManager.getSearchFactory();
        QueryContextBuilder queryContextBuilder = searchFactory.buildQueryBuilder();

        Class<?> clazz = AbstractEntity.class;
        if (map.containsKey("class")) {
            try {
                clazz = Class.forName(String.format("com.zdanovich.core.entity.%s", map.get("class")));
            } catch (ClassNotFoundException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
        }

        QueryBuilder queryBuilder = queryContextBuilder.forEntity(clazz).get();
        Query query = null;
        if (!map.containsKey("context")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Field 'context' is missing");
        }
        String context = (String) map.get("context");
        switch (context) {
            case "keyword": {
                Map<String, Object> termMap = (Map<String, Object>) map.get("keyword");
                TermContext termContext = queryBuilder.keyword();
                if (termMap.containsKey("fuzzy")) {
                    FuzzyContext fuzzyContext = termContext.fuzzy();
                }
                if (termMap.containsKey("wildcard")) {
                    WildcardContext wildcardContext = termContext.wildcard();
                }
                if (termMap.containsKey("fields")) {
                    List<String> fields = (List<String>) termMap.get("fields");
                    String[] fieldsArray = ArrayUtils.toStringArray(fields.toArray());
                    TermMatchingContext termMatchingContext = termContext.onFields(fieldsArray);
                    if (termMap.containsKey("matching")) {
                        query = termMatchingContext.matching(termMap.get("matching")).createQuery();
                        break;
                    } else {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Field 'matching' is missing");
                    }
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Field 'fields' is missing");
                }
            }
            case "range": {
                Map<String, Object> rangeMap = (Map<String, Object>) map.get("range");
                RangeContext rangeContext = queryBuilder.range();
                query = rangeContext.onField("").above("").createQuery();
                break;
            }
            case "phrase": {
                Map<String, Object> phraseMap = (Map<String, Object>) map.get("phrase");
                PhraseContext phraseContext = queryBuilder.phrase();
                query = phraseContext.onField("").sentence("").createQuery();
                break;
            }
            case "simple": {
                Map<String, Object> simpleQueryStringMap = (Map<String, Object>) map.get("simple");
                SimpleQueryStringContext simpleQueryStringContext = queryBuilder.simpleQueryString();
                query = simpleQueryStringContext.onFields("").matching("").createQuery();
                break;
            }
            case "bool": {
                Map<String, Object> booleanMap = (Map<String, Object>) map.get("bool");
                BooleanJunction<?> booleanJunction = queryBuilder.bool();
                query = booleanJunction.must(null).createQuery();
                break;
            }
            case "all": {
                Map<String, Object> allMap = (Map<String, Object>) map.get("all");
                AllContext allContext = queryBuilder.all();
                query = allContext.createQuery();
                break;
            }
            case "facet": {
                Map<String, Object> facetMap = (Map<String, Object>) map.get("facet");
                FacetContext facetContext = queryBuilder.facet();
                query = null;//facetContext.name().onField();
                break;
            }
            case "spatial": {
                Map<String, Object> spatialMap = (Map<String, Object>) map.get("spatial");
                SpatialContext spatialContext = queryBuilder.spatial();
                query = spatialContext.onField("").within(1, Unit.KM).ofLatitude(1).andLongitude(1).createQuery();
                break;
            }
            case "more": {
                Map<String, Object> moreLikeThisMap = (Map<String, Object>) map.get("more");
                MoreLikeThisContext moreLikeThisContext = queryBuilder.moreLikeThis();
                query = moreLikeThisContext.comparingAllFields().toEntity(null).createQuery();
                break;
            }
            case "sort": {
                Map<String, Object> sortMap = (Map<String, Object>) map.get("sort");
                SortContext sortContext = queryBuilder.sort();
                Sort sort = sortContext.byField("").createSort();
                break;
            }
            default: {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(String.format("Invalid context: %s", context));
            }
        }

        /*try {
            query = new QueryParser("id", new StandardAnalyzer()).parse("text");
        } catch (ParseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }*/

        return ResponseEntity.ok(fullTextEntityManager.createFullTextQuery(query).getResultList());
    }
}
