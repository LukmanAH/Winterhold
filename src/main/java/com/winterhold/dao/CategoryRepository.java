package com.winterhold.dao;


import com.winterhold.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, String> {

    @Query("""
        select new com.winterhold.dto.category.CategoryRowDTO(
                ct.name, 
                ct.floor, 
                ct.isle, 
                ct.bay, 
                count(bk.code)
           )
        from Category as ct
        	left join Book as bk on bk.categoryName = ct.name
        where ct.name like %:name%
        group by ct.name, ct.floor, ct.isle, ct.bay
    """)
public Page<Object> findAll(@Param("name") String name, Pageable pageable);

    @Query("""
			SELECT COUNT(ct.id)
			FROM Category AS ct
			WHERE ct.name = :name """)
    public Long countByName(@Param("name") String name);


}
