package com.winterhold.sevice.implementation;

import com.winterhold.dao.BookRepository;
import com.winterhold.dao.CustomerRepository;
import com.winterhold.dao.LoanRepository;
import com.winterhold.dto.utility.DropdownDTO;
import com.winterhold.entity.Customer;
import com.winterhold.entity.Loan;
import com.winterhold.sevice.abstraction.CrudService;
import com.winterhold.utility.MapperHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Scope("singleton")
@Service("loanMenu")
public class LoanService implements CrudService {

    @Autowired
    LoanRepository loanRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    CustomerRepository customerRepository;

    private final Integer rowsInPage = 10;

    @Override
    public <T> Page<Object> getAll(Integer page, T filter) {
        var pagination = PageRequest.of(page - 1, rowsInPage, Sort.by("id"));
        var customerName = MapperHelper.getStringField(filter, "customerName");
        var bookTitle = MapperHelper.getStringField(filter, "bookTitle");
        var data = loanRepository.findAll(customerName, bookTitle, pagination);
        return data;
    }

    @Override
    public Object getSingle(Object id) {
        var entity = loanRepository.findById((Long) id);
        return entity.isPresent() ? entity.get() : null;
    }

    @Override
    public Object save(Object dto) {
        var entity = new Loan(
                MapperHelper.getLongField(dto, "id"),
                MapperHelper.getStringField(dto, "customerNumber"),
                MapperHelper.getStringField(dto, "bookCode"),
                MapperHelper.getLocalDateField(dto,"loanDate"),
                MapperHelper.getLocalDateField(dto,"dueDate"),
                MapperHelper.getLocalDateField(dto,"returnDate"),
                MapperHelper.getStringField(dto, "note")
        );
        var dueDate = entity.getDueDate() != null? entity.getDueDate() : entity.getLoanDate().plusDays(5);
        entity.setDueDate(dueDate);

        var book = bookRepository.findById(entity.getBookCode()).get();
        book.setIsBorrowed(true);

        bookRepository.save(book);

        return loanRepository.save(entity);
    }

    @Override
    public Boolean delete(Object id) {
        return false;
    }

    @Override
    public Boolean isExist(Object id) {
        return loanRepository.existsById((Long) id);
    }

    public Object returnBook(Long id){
        var loan = loanRepository.findById(id).get();
        var book = loan.getBook();

        loan.setReturnDate(LocalDate.now());
        book.setIsBorrowed(false);

        bookRepository.save(book);

        return loanRepository.save(loan);
    }

    public List<DropdownDTO> getActiveMemberDropdownList(){
        return customerRepository.getActiveMemberDropdownList();
    }

    public List<DropdownDTO> getAvailableBookDropdownList(){
        return bookRepository.getAvailableBookDropdownList();
    }
}
