package com.winterhold.dao;

import com.winterhold.dto.book.BookByAuthorDTO;
import com.winterhold.dto.book.BookByCategoryDTO;
import com.winterhold.dto.utility.DropdownDTO;
import com.winterhold.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, String> {

    @Query("""
        select new com.winterhold.dto.book.BookByAuthorDTO(
                bk.code,
                bk.title,
                bk.categoryName,
                bk.isBorrowed,
                bk.releaseDate,
                bk.totalPage
        )
        from Book as bk 
        where bk.authorId = :id
    """)
    public List<BookByAuthorDTO> getBookByAuthorId(@Param("id") Long id);

    @Query("""
        select count(bk.code)
        from Book as bk 
        where bk.authorId = :id
    """)
    public Long countBookByAuthorId(@Param("id") Long id);

    @Query("""
        select count(bk.code)
        from Book as bk 
        where bk.categoryName = :name
    """)
    public Integer countBookByCategoryName(@Param("name") String name);
    @Query("""
        select new com.winterhold.dto.book.BookByCategoryDTO(
                bk.code,
                bk.title,
                at.title,
                at.firstName,
                at.lastName,
                bk.isBorrowed,
                bk.releaseDate,
                bk.totalPage
        )
        from Book as bk 
           inner join bk.author as at
        where bk.categoryName = :categoryName and bk.title like %:title% and concat(at.firstName,' ', at.lastName) like %:authorName% 
    """)
    public List<BookByCategoryDTO> getBookByCategoryName(@Param("categoryName") String categoryName,
                                                             @Param("authorName") String authorName,
                                                             @Param("title") String title);

    @Query("""
         select new com.winterhold.dto.utility.DropdownDTO(bk.code, bk.title)
         from Book as bk
         where bk.isBorrowed = false
     """)
    public List<DropdownDTO> getAvailableBookDropdownList();
}
