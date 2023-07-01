package com.winterhold.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="Category")
public class Category {
   @Id
   @Column(name="Name")
   private String name;

   @Column(name="Floor")
   private Integer floor;

   @Column(name="Isle")
   private String isle;

   @Column(name="Bay")
   private String bay;

}
