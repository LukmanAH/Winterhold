package com.winterhold.dto.loan;

import com.winterhold.utility.MapperHelper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoanRowDTO {
    private Long id;
    private String bookTitle;
    private String customerName;
    private LocalDate loanDate;
    private LocalDate dueDate;
    private  LocalDate returnDate;

//    public LoanRowDTO(Long id, String bookTitle, String customerName, LocalDate loanDate, LocalDate dueDate, LocalDate returnDate) {
//        this.id = id;
//        this.bookTitle = bookTitle;
//        this.customerName = customerName;
//        this.loanDate = MapperHelper.getFormatedDate(loanDate);
//        this.dueDate = MapperHelper.getFormatedDate(dueDate);
//        this.returnDate = MapperHelper.getFormatedDate(returnDate);
//    }
}
