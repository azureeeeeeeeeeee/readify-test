package com.test.backend.services;

import com.test.backend.DTO.BookDTO;
import com.test.backend.DTO.JsonResponse;
import com.test.backend.models.Book;
import com.test.backend.models.CustomUser;
import com.test.backend.permissions.BookPermissions;
import com.test.backend.repositories.BookRepository;
import com.test.backend.utilities.UserUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServices {
    private final UserUtils userUtils;
    private final BookRepository bookRepository;
    private final BookPermissions bookPermissions;

    public BookServices(UserUtils userUtils, BookRepository bookRepository, BookPermissions bookPermissions) {
        this.userUtils = userUtils;
        this.bookRepository = bookRepository;
        this.bookPermissions = bookPermissions;
    }


    // Create Book
    public ResponseEntity<JsonResponse<Void>> create(Book book) {
        CustomUser user = userUtils.getUser();
        JsonResponse<Void> response = new JsonResponse<>();

        // Authorization
        if (!bookPermissions.checkCreate(user)) {
            response.setMessage("You are not authorized");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }

        book.setAddedBy(user);
        bookRepository.save(book);
        response.setMessage("Book created");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }



    // Update Book
    public ResponseEntity<JsonResponse<Object>> update(String isbn, Book book) {
        Optional<Book> checkBook = bookRepository.findById(isbn);
        JsonResponse<Object> response = new JsonResponse<>();

        // Check if book is available
        if (checkBook.isEmpty()) {
            response.setMessage("Book with isbn " + isbn + " is not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        Book currBook = checkBook.get();

        // Authorization
        if (!bookPermissions.checkUpdate(userUtils.getUserId(), book)) {
            response.setMessage("You are not authorized");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }

        currBook.setTitle(book.getTitle());
        currBook.setDescription(book.getDescription());
        currBook.setImageURL(book.getImageURL());
        BookDTO data = new BookDTO(bookRepository.save(currBook));
        response.setData(data);
        response.setMessage("Book with isbn " + isbn + " successfully updated");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }



    // Delete Book
    public ResponseEntity<JsonResponse<Object>> delete(String isbn) {
        Optional<Book> checkBook = bookRepository.findById(isbn);
        JsonResponse<Object> response = new JsonResponse<>();

        if (checkBook.isEmpty()) {
            response.setMessage("Book with isbn " + isbn + " is not found");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        }

        Book book = checkBook.get();

        // Authorization
        if (!bookPermissions.checkDelete(book)) {
            response.setMessage("You are not authorized");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }

        bookRepository.delete(book);
        response.setMessage("Book with isbn " + isbn + " successfully deleted");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    // Find One
    public ResponseEntity<JsonResponse<Object>> findOne(String isbn) {
        Optional<Book> book = bookRepository.findById(isbn);
        JsonResponse<Object> response = new JsonResponse<>();

        if (book.isEmpty()) {
            response.setMessage("Book with isbn " + isbn + " is not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        response.setMessage("Book with isbn " + isbn + " fetched");
        response.setData(new BookDTO(book.get()));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    // Find all
    public ResponseEntity<JsonResponse<Object>> findAll() {
        JsonResponse<Object> response = new JsonResponse<>();
        List<Book> books = bookRepository.findAll();
        List<BookDTO> bookDTOs = books.stream().map(BookDTO::new).toList();

        response.setMessage("All books fetched");
        response.setData(bookDTOs);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    // Search
    public ResponseEntity<JsonResponse<Object>> search(String title, String description) {
        JsonResponse<Object> response = new JsonResponse<>();

        System.out.println(title + description);

        List<Book> allBooks = bookRepository.findByTitleContainingOrDescriptionContaining(title, description);
        List<BookDTO> bookDTOs = allBooks.stream().map(BookDTO::new).toList();

        System.out.println(allBooks);
        System.out.println(bookDTOs);

        response.setMessage("Search result for keyword " + title);
        response.setData(bookDTOs);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
