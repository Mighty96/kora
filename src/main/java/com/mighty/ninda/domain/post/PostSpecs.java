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

            Join<Post, User> joinUser = root.join("user", JoinType.INNER);

            searchKeyword.forEach((key, value) -> {

                switch (key) {
                    case "board":
                        predicates.add(builder.equal(root.get(key), value.toString()));
                        break;
                    case "title":
                    case "context":
                        predicates.add(builder.like(root.get(key), value.toString()));
                        break;
                    case "userId":
                        predicates.add(builder.equal(joinUser.get("id"), Long.valueOf(value.toString())));
                        break;
                    case "userName":
                        predicates.add(builder.equal(joinUser.get("nickname"), value.toString()));
                        break;
                }

            });
            return builder.and(predicates.toArray(new Predicate[0]));
        });
    }
}