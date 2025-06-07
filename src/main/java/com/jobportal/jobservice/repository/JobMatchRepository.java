package com.jobportal.jobservice.repository;

import com.jobportal.jobservice.model.JobPost;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface JobMatchRepository extends JpaRepository<JobPost, Long>, JpaSpecificationExecutor<JobPost> {
    class Specifications {
        public static Specification<JobPost> hasMatchInColumnsByValue(final List<String> columns, final String value) {
            return (root, criteriaQuery, criteriaBuilder) -> {
                root.fetch("company", JoinType.INNER)
                    .fetch("address", JoinType.INNER);

                List<Expression> expressions = new ArrayList<>();
                columns.forEach(column -> {
                    expressions.add(root.get(column));
                });

                final String splitValues = String.join(" | ", value.split(" "));
                expressions.add(criteriaBuilder.literal(splitValues));

                final Expression[] exp = expressions.toArray(new Expression[expressions.size()]);
                return criteriaBuilder.isTrue(
                        criteriaBuilder.function("fts", Boolean.class, exp));
            };
        }
    }

}
