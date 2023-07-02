package com.winterhold.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="Book")
public class Book {
    @Id
    @Column(name="Code")
    private String code;

    @Column(name="Title")
    private String title;

    @Column(name="CategoryName")
    private String categoryName;

    @ManyToOne
    @JoinColumn(name = "CategoryName", insertable = false, updatable = false)
    private Category category;

    @Column(name="AuthorId")
    private Long authorId;

    @ManyToOne
    @JoinColumn(name = "AuthorId", insertable = false, updatable = false)
    private Author author;

    @Column(name="IsBorrowed")
    private Boolean isBorrowed;

    @Column(name="Summary")
    private String summary;

    @Column(name="ReleaseDate")
    private LocalDate releaseDate;

    @Column(name="TotalPage")
    private Integer totalPage;

    public Book(String code, String title, String categoryName, Long authorId, Boolean isBorrowed, String summary, LocalDate releaseDate, Integer totalPage) {
        this.code = code;
        this.title = title;
        this.categoryName = categoryName;
        this.authorId = authorId;
        this.isBorrowed = isBorrowed != null ? isBorrowed : false;
        this.summary = summary;
        this.releaseDate = releaseDate;
        this.totalPage = totalPage;
    }
}
