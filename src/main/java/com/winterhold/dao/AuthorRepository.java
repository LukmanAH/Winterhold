package com.winterhold.dao;

import com.winterhold.dto.utility.DropdownDTO;
import com.winterhold.entity.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query("""
			SELECT new  com.winterhold.dto.author.AuthorRowDTO(
				at.id,
				concat(at.title,' ', at.firstName, ' ',isnull(at.lastName,'')),
				at.birthDate,
				at.deceasedDate,
				at.education 
			)
			FROM Author AS at
			WHERE at.firstName LIKE %:name%	 OR at.lastName LIKE %:name%
			""")
    public Page<Object> findAll(@Param("name") String name, Pageable pageable);

	@Query("""
         SELECT new com.winterhold.dto.utility.DropdownDTO(at.id, concat(at.title,' ', at.firstName, ' ',isnull(at.lastName,'')))
         FROM Author AS at
     """)
	public List<DropdownDTO> getDropdownList();
}
