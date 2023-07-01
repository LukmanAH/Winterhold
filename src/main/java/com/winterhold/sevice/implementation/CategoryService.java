package com.winterhold.sevice.implementation;


import com.winterhold.dao.BookRepository;
import com.winterhold.dao.CategoryRepository;
import com.winterhold.dto.book.BookByCategoryDTO;
import com.winterhold.dto.category.InsertCategoryDTO;
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

    public <T> List<BookByCategoryDTO> getBooksByCategoryName(String name, T filter){
        var authorName = MapperHelper.getStringField(filter, "authorName");
        var title = MapperHelper.getStringField(filter, "title");
        var data = bookRepository.getBookByCategoryName(name,authorName, title);
        return data;
    }

    public Integer totalDependentBook(String categoryName){
        return bookRepository.countBookByCategoryName(categoryName);
    }

    public Boolean checkExistingCategory(String name) {
        Long totalExistingCategory = categoryRepository.countByName(name);
        return (totalExistingCategory > 0) ? true : false;
    }
}
