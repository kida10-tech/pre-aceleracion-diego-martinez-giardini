package com.alkemy.disney.disney.repository.specification;

import com.alkemy.disney.disney.dto.CharacterDTOFilter;
import com.alkemy.disney.disney.entity.CharacterEntity;
import com.alkemy.disney.disney.entity.MovieEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CharacterSpecification {

    public Specification<CharacterEntity> getByFilters(CharacterDTOFilter filterDTO){

        //Lambda function
        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            //Creating a dynamic query, hasLength() checks if it exist.
            if(StringUtils.hasLength(filterDTO.getName())) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + filterDTO.getName().toLowerCase() + "%"));
            }

            //Date
//            if(StringUtils.hasLength(filterDTO.getName())) {
//
//                //String to LocalDate
//                String dateToParse = filterDTO.getDate();
//                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
//                LocalDate date = LocalDate.parse(dateToParse, formatter);
//
//                predicates.add(criteriaBuilder.equal(root.<LocalDate>get("created"),date));
//            }

            if(!CollectionUtils.isEmpty(filterDTO.getMovies())) { //if CountryId not empty
                Join<MovieEntity, CharacterEntity> toBeJoined = root.join("movies", JoinType.INNER);
                Expression<String> paisesId = toBeJoined.get("id");

                predicates.add(paisesId.in(filterDTO.getMovies()));
            }

            //Remove duplicates
            query.distinct(true);

            //Order by filtering
            String orderByField = "name";
            query.orderBy(
                    filterDTO.isASC() ?
                            criteriaBuilder.asc(root.get(orderByField))
                            :
                            criteriaBuilder.desc(root.get(orderByField))
            );

            return criteriaBuilder.and(predicates.toArray(new Predicate[0])); // Return the generated predicate as a list
        };
    }
}
