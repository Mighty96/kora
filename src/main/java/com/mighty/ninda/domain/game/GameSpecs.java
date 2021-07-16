package com.mighty.ninda.domain.game;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class GameSpecs {

    public static Specification<Game> searchGame(Map<String, Object> searchKeyword) {
        return (Specification<Game>) ((root, query, builder) -> {

            List<Predicate> predicates = new ArrayList<>();

            query.orderBy(builder.desc(root.get("reLike")), (builder.desc(root.get("title"))));

            searchKeyword.forEach((key, value) -> {

                String likeValue = "%" + value + "%";

                switch (key) {
                    case "q":
                        predicates.add(builder.like(root.get("title"), likeValue));
                        break;
                    case "list":
                        if (value.equals("released")) {
                            predicates.add(builder.lessThanOrEqualTo(root.get("releasedDate"), LocalDate.now()));
                        }
                        break;
                    case "onSale":
                        predicates.add(builder.equal(root.get(key), value));
                        break;
                    case "order":
                        log.info(value.toString());
                        query.orderBy(builder.desc(root.get(value.toString())), (builder.desc(root.get("title"))));
                        break;
                }

            });

            return builder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
