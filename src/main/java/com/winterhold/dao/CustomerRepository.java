package com.winterhold.dao;

import com.winterhold.dto.utility.DropdownDTO;
import com.winterhold.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, String> {
    @Query("""
        select new com.winterhold.dto.customer.CustomerRowDTO(
                ct.membershipNumber, 
                concat(ct.firstName,' ', isnull(ct.lastName,' ')),
                ct.membershipExpireDate)
        from Customer as ct
        where concat(ct.firstName,' ',isnull(ct.lastName,' ')) like %:customerName% and ct.membershipNumber like %:membershipNumber%
    """)
    public Page<Object> findAll(@Param("customerName") String customerName, @Param("membershipNumber") String membershipNumber, Pageable pageable);

    @Query("""
         select new com.winterhold.dto.utility.DropdownDTO(cs.membershipNumber, concat(cs.firstName, ' ',isnull(cs.lastName,'')))
         from Customer as cs
         where cs.membershipExpireDate >  getutcdate()
     """)
    public List<DropdownDTO> getActiveMemberDropdownList();
}
