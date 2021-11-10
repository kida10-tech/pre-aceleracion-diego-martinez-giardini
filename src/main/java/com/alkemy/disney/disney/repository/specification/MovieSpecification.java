package com.alkemy.disney.disney.repository.specification;

import com.alkemy.disney.disney.dto.MovieDTOFilter;
import com.alkemy.disney.disney.entity.GenreEntity;
import com.alkemy.disney.disney.entity.MovieEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Component
public class MovieSpecification {

    public Specification<MovieEntity> getByFilters(MovieDTOFilter filterDTO){

        // Lambda Function:
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // == Name ==
            if(StringUtils.hasLength(filterDTO.getName())) {
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("name")),
                                "%" + filterDTO.getName().toLowerCase() + "%"
                        )
                );
            }

            if(!CollectionUtils.isEmpty(filterDTO.getGenre())) {
                Join<MovieEntity, GenreEntity> join = root.join("movieGenres", JoinType.INNER);
                Expression<String> genresId = join.get("id");
                predicates.add(genresId.in(filterDTO.getGenre()));
            }

            query.distinct(true);

            String orderByField = "name";
            query.orderBy(
                    filterDTO.isASC() ?
                            criteriaBuilder.asc(root.get(orderByField)) :
                            criteriaBuilder.desc(root.get(orderByField))
            );


            // MAIN RETURN:
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
