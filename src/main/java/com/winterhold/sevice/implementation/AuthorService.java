package com.winterhold.sevice.implementation;

import com.winterhold.dao.AuthorRepository;
import com.winterhold.dao.BookRepository;
import com.winterhold.dto.author.UpsertAuthorDTO;
import com.winterhold.dto.book.BookByAuthorDTO;
import com.winterhold.entity.Author;
import com.winterhold.sevice.abstraction.CrudService;
import com.winterhold.utility.MapperHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Scope("singleton")
@Service("authorMenu")
public class AuthorService implements CrudService {

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    BookRepository bookRepository;

    private final Integer rowsInPage = 10;

    @Override
    public <T> Page<Object> getAll(Integer page, T filter) {
        var pagination = PageRequest.of(page - 1, rowsInPage, Sort.by("id"));
        var name = MapperHelper.getStringField(filter, "name");
        var data = authorRepository.findAll(name, pagination);
        return data;
    }

    @Override
    public Object getSingle(Object id) {
        var entity = authorRepository.findById((Long)id);
        return entity.isPresent() ? entity.get() : null;
    }

    @Override
    public Object save(Object dto) {
        var upsertDTO = (UpsertAuthorDTO) dto;
        var entity = new Author(
                upsertDTO.getId(),
                upsertDTO.getTitle(),
                upsertDTO.getFirstName(),
                upsertDTO.getLastName(),
                upsertDTO.getBirthDate(),
                upsertDTO.getDeceasedDate(),
                upsertDTO.getEducation(),
                upsertDTO.getSummary()
        );
        return authorRepository.save(entity);
    }

    @Override
    public Boolean delete(Object id) {
        try{
            authorRepository.deleteById((Long)id);
            return true;
        }catch (Exception exception){
            return false;
        }
    }

    @Override
    public Boolean isExist(Object id) {
        return authorRepository.existsById((Long)id);
    }

    public List<BookByAuthorDTO> getBooksByAuthorId(Long id){
        var data = bookRepository.getBookByAuthorId(id);
        return data;
    }

    public Long totalDependentBook(Long id){
        return bookRepository.countBookByAuthorId(id);
    }

}
