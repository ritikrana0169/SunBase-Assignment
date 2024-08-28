package com.sunbase.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sunbase.model.User;

public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User>,
		PagingAndSortingRepository<User, String> {
	default Page<User> findByColumnNameAndKeyword(String columnName, String searchKeyword, Pageable pageable) {
		return findAll((root, query, criteriaBuilder) -> {
			if (columnName != null && searchKeyword != null) {
				return criteriaBuilder.like(criteriaBuilder.lower(root.get(columnName)),
						"%" + searchKeyword.toLowerCase() + "%");
			}
			return null;
		}, pageable);
	}

}