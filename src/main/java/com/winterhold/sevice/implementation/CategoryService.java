package com.winterhold.sevice.implementation;


import com.winterhold.dao.AuthorRepository;
import com.winterhold.dao.BookRepository;
import com.winterhold.dao.CategoryRepository;
import com.winterhold.dto.book.BookByCategoryDTO;
import com.winterhold.dto.category.InsertCategoryDTO;
import com.winterhold.dto.utility.DropdownDTO;
import com.winterhold.entity.Book;
import com.winterhold.entity.Category;
import com.winterhold.sevice.abstraction.CrudService;
import com.winterhold.utility.MapperHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Scope("singleton")
@Service("categoryMenu")
public class CategoryService implements CrudService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    private final Integer rowsInPage = 10;

    @Override
    public <T> Page<Object> getAll(Integer page, T filter) {
        var pagination = PageRequest.of(page - 1, rowsInPage, Sort.by("floor"));
        var name = MapperHelper.getStringField(filter, "name");
        var data = categoryRepository.findAll(name, pagination);
        return data;
    }

    @Override
    public Object getSingle(Object id) {
        var entity = categoryRepository.findById((String) id);
        return entity.isPresent() ? entity.get() : null;
    }

    @Override
    public Object save(Object dto) {
        var entity = new Category(
                MapperHelper.getStringField(dto, "name"),
                MapperHelper.getIntegerField(dto, "floor"),
                MapperHelper.getStringField(dto, "isle"),
                MapperHelper.getStringField(dto, "bay")
        );
        return categoryRepository.save(entity);
    }

    @Override
    public Boolean delete(Object id) {
        try{
            categoryRepository.deleteById((String) id);
            return true;
        }catch (Exception exception){
            return false;
        }
    }

    @Override
    public Boolean isExist(Object id) {
        return categoryRepository.existsById((String)id);
    }

    public <T> List<BookByCategoryDTO> getBooksByCategoryName(String name, T filter){
        var authorName = MapperHelper.getStringField(filter, "authorName");
        var title = MapperHelper.getStringField(filter, "title");
        return bookRepository.getBookByCategoryName(name,authorName, title);
    }

    public Object getSingleBook(String code) {
        var entity = bookRepository.findById(code);
        return entity.isPresent() ? entity.get() : null;
    }

    public Object saveBook(Object dto) {
        var entity = new Book(
                MapperHelper.getStringField(dto, "code"),
                MapperHelper.getStringField(dto, "title"),
                MapperHelper.getStringField(dto, "categoryName"),
                MapperHelper.getLongField(dto, "authorId"),
                MapperHelper.getBooleanField(dto, "isBorrowed"),
                MapperHelper.getStringField(dto, "summary"),
                MapperHelper.getLocalDateField(dto, "releaseDate"),
                MapperHelper.getIntegerField(dto, "totalPage")
        );
        return bookRepository.save(entity);
    }

    public Boolean deleteBook(String code) {
        try{
            bookRepository.deleteById(code);
            return true;
        }catch (Exception exception){
            return false;
        }
    }


    public Integer totalDependentBook(String categoryName){
        return bookRepository.countBookByCategoryName(categoryName);
    }

    public Boolean checkExistingCategory(String name) {
        Long totalExistingCategory = categoryRepository.countByName(name);
        return (totalExistingCategory > 0) ? true : false;
    }

    public List<DropdownDTO> getAuthorDropdownList(){
        return authorRepository.getDropdownList();
    }

    public Boolean isExistBook(String code) {
        return bookRepository.existsById(code);
    }

    public Boolean isExistAuthor(Long id) {
        return authorRepository.existsById(id);
    }
}
