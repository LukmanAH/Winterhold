package com.winterhold.sevice.implementation;

import com.winterhold.dao.CustomerRepository;
import com.winterhold.dao.LoanRepository;
import com.winterhold.dto.utility.DropdownDTO;
import com.winterhold.entity.Category;
import com.winterhold.entity.Customer;
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
@Service("customerMenu")
public class CustomerService implements CrudService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    LoanRepository loanRepository;

    private final Integer rowsInPage = 10;

    @Override
    public <T> Page<Object> getAll(Integer page, T filter) {
        var pagination = PageRequest.of(page - 1, rowsInPage, Sort.by("membershipNumber"));
        var customerName = MapperHelper.getStringField(filter, "fullName");
        var membershipNumber = MapperHelper.getStringField(filter, "membershipNumber");
        var data = customerRepository.findAll(customerName, membershipNumber, pagination);
        return data;
    }

    @Override
    public Object getSingle(Object id) {
        var entity = customerRepository.findById((String) id);
        return entity.isPresent() ? entity.get() : null;
    }

    @Override
    public Object save(Object dto) {
        var entity = new Customer(
                MapperHelper.getStringField(dto, "membershipNumber"),
                MapperHelper.getStringField(dto, "firstName"),
                MapperHelper.getStringField(dto, "lastName"),
                MapperHelper.getLocalDateField(dto,"birthDate"),
                MapperHelper.getStringField(dto, "gender"),
                MapperHelper.getStringField(dto, "phone"),
                MapperHelper.getStringField(dto, "address"),
                MapperHelper.getLocalDateField(dto,"membershipExpireDate")
        );
        return customerRepository.save(entity);
    }

    @Override
    public Boolean delete(Object id) {
        try{
            customerRepository.deleteById((String) id);
            return true;
        }catch (Exception exception){
            return false;
        }
    }

    @Override
    public Boolean isExist(Object id) {
        return customerRepository.existsById((String)id);
    }

    public Object extendMembership(String membershipNumber){
        var entity = customerRepository.findById(membershipNumber).get();
        var newExpireDate = entity.getMembershipExpireDate().plusYears(2);
        entity.setMembershipExpireDate(newExpireDate);
        return customerRepository.save(entity);
    }

    public LocalDate setMembershipExpire(){
        return LocalDate.now().plusYears(2);
    }

    public Integer totalDependentLoan(String customerNumber){
        return loanRepository.countLoanByCustomerNumber(customerNumber);
    }

}
