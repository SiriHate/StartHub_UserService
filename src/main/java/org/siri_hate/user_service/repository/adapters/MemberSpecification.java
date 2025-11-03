package org.siri_hate.user_service.repository.adapters;

import org.siri_hate.user_service.model.entity.Member;
import org.springframework.data.jpa.domain.Specification;

public class MemberSpecification {

    public static Specification<Member> usernameStartsWith(String username) {
        return (root, query, criteriaBuilder) -> {
            if (username == null || username.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(root.get("username"), username + "%");
        };
    }

    public static Specification<Member> hasSpecialization(String specialization) {
        return (root, query, criteriaBuilder) -> {
            if (specialization == null || specialization.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("specialization").get("name"), specialization);
        };
    }

    public static Specification<Member> profileIsNotHidden() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isFalse(root.get("profileHiddenFlag"));
    }

    public static Specification<Member> nameStartsWithIgnoreCase(String name) {
        return (root, query, criteriaBuilder) -> {
            if (name == null || name.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("name")),
                    name.toLowerCase() + "%"
            );
        };
    }

    public static Specification<Member> usernameStartsWithIgnoreCase(String username) {
        return (root, query, criteriaBuilder) -> {
            if (username == null || username.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("username")),
                    "%" + username.toLowerCase() + "%"
            );
        };
    }
}
