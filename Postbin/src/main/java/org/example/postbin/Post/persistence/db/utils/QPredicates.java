package org.example.postbin.Post.persistence.db.utils;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class QPredicates {
    private final List<Predicate> predicates = new ArrayList<>();

    public static QPredicates getInstance() {
        return new QPredicates();
    }

    public <T> QPredicates add(T object, Function<T, Predicate> function) {
        if (object != null) {
            predicates.add(function.apply(object));
        }

        return this;
    }

    public Predicate buildAnd(){
        return Optional.ofNullable(ExpressionUtils.allOf(predicates)).
                orElseGet(() -> Expressions.asBoolean(true).isTrue());
    }

    public Predicate buildOr(){
        return Optional.ofNullable(ExpressionUtils.anyOf(predicates)).
                orElseGet(() -> Expressions.asBoolean(true).isTrue());
    }
}
