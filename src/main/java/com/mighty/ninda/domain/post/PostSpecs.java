package com.mighty.ninda.domain.post;

import com.mighty.ninda.domain.user.User;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class PostSpecs {

    public static Specification<Post> searchPost(Map<String, Object> searchKeyword) {
        return (Specification<Post>) ((root, query, builder) -> {

            List<Predicate> predicates = new ArrayList<>();

            root.fetch("user", JoinType.LEFT);

            searchKeyword.forEach((key, value) -> {

                String likeValue = "%" + value + "%";

                switch (key) {
                    case "board":
                        predicates.add(builder.equal(root.get(key), value.toString()));
                        break;
                    case "title":
                    case "context":
                        predicates.add(builder.like(root.get(key), likeValue));
                        break;
                    case "title_context":
                        predicates.add(builder.or(builder.like(root.get("title"), likeValue), builder.like(root.get("context"), likeValue)));
                        break;
                    case "userId":
                        predicates.add(builder.equal(root.get("user").get("id"), Long.valueOf(value.toString())));
                        break;
                    case "userName":
                        predicates.add(builder.equal(root.get("user").get("nickname"), value.toString()));
                        break;
                }

            });

            query.orderBy(builder.desc(root.get("id")));

            return builder.and(predicates.toArray(new Predicate[0]));
        });
    }
}