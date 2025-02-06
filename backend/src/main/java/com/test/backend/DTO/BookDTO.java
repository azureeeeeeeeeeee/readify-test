package com.test.backend.DTO;

import com.test.backend.models.Book;
import com.test.backend.models.CustomUser;

public class BookDTO {
    public String isbn;
    public String title;
    public String description;
    public CustomUserDTO addedBy;

    public BookDTO(Book book) {
        this.isbn = book.getIsbn();
        this.title = book.getTitle();
        this.description = book.getDescription();
        this.addedBy = new CustomUserDTO(book.getAddedBy());
    }
}
