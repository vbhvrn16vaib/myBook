package com.book.Entity;


import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by vaibhav.rana on 12/31/16.
 */

@Entity
@Table(name = "BOOK")
public @Data class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @NotNull
    private int classes;

    @NotNull
    private int price;

    @NotNull
    private String bookname;

    @NotNull
    private String image;

    @NotNull
    private String description;

}


