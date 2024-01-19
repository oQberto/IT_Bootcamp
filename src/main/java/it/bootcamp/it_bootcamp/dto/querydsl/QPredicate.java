package it.bootcamp.it_bootcamp.dto.querydsl;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class QPredicate {
    private final List<Predicate> predicates = new ArrayList<>();

    public static QPredicate builder() {
        return new QPredicate();
    }

    public <T> QPredicate add(T object, Function<T, Predicate> function) {
        if (object != null) {
            predicates.add(function.apply(object));
        }

        return this;
    }

    public <T extends Comparable<T>> QPredicate orderBy(OrderSpecifier<T> specifier) {
        predicates.add((Predicate) ExpressionUtils.orderBy(List.of(specifier)));
        return this;
    }

    public Predicate build() {
        return ExpressionUtils.allOf(predicates);
    }
}
