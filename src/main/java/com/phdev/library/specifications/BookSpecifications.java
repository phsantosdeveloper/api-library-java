package com.phdev.library.specifications;

import com.phdev.library.dto.BookFilter;
import com.phdev.library.entities.Book;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public final class BookSpecifications {

    private BookSpecifications() {
    }

    public static Specification<Book> withFilters(BookFilter filter) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.title() != null) {
                predicates.add(containsIgnoreCase(builder, root.get("title"), filter.title()));
            }
            if (filter.authorId() != null) {
                predicates.add(builder.equal(root.get("author").get("id"), filter.authorId()));
            }
            if (filter.authorName() != null) {
                predicates.add(containsIgnoreCase(builder, root.get("author").get("name"), filter.authorName()));
            }
            if (filter.categoryId() != null) {
                predicates.add(builder.equal(root.get("category").get("id"), filter.categoryId()));
            }
            if (filter.isbn() != null) {
                predicates.add(builder.equal(root.get("isbn"), filter.isbn()));
            }

            return builder.and(predicates.toArray(Predicate[]::new));
        };
    }

    public static Specification<Book> available() {
        return (root, query, builder) -> builder.greaterThan(root.get("availableQuantity"), 0);
    }

    private static Predicate containsIgnoreCase(CriteriaBuilder builder, Expression<String> field, String value) {
        return builder.greaterThan(builder.locate(builder.lower(field), value.toLowerCase(Locale.ROOT)), 0);
    }
}
