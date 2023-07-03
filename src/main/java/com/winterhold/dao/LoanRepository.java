package com.winterhold.dao;

import com.winterhold.entity.Loan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    @Query("""
        select new com.winterhold.dto.loan.LoanRowDTO(
            lo.id,
            bk.title,
            concat(ct.firstName,' ', isnull(ct.lastName,'')),
            lo .loanDate,
            lo.dueDate,
            lo.returnDate
        )        
        from Loan as lo
            join lo.book as bk
            join lo.customer as ct
        where concat(ct.firstName,' ',isnull(ct.lastName,' ')) like %:customerName% and bk.title like %:bookTitle%
    """)
    public Page<Object> findAll(@Param("customerName") String customerName, @Param("bookTitle") String bookTitle, Pageable pageable);

    @Query("""
        select count(lo.id)
        from Loan as lo 
        where lo.customerNumber = :customerNumber
    """)
    public Integer countLoanByCustomerNumber(@Param("customerNumber") String customerNumber);

    @Query("""
        select count(lo.id)
        from Loan as lo 
        where lo.bookCode = :code
    """)
    public Integer countLoanByBookCode(@Param("code") String code);
}
