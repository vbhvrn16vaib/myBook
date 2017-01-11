package com.book.Controller;

import com.book.Entity.Book;
import com.book.Entity.BookDao;
import com.book.Exceptions.StorageFileNotFoundException;
import com.book.Storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sun.misc.IOUtils;

import java.io.IOException;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


/**
 * Created by vaibhav.rana on 12/31/16.
 */
@CrossOrigin(origins = {"*","http://localhost:8080"})
@RestController
public class BookController {
    @Autowired
    private BookDao bookDao;

    private final StorageService storageService;


    @Autowired
    public BookController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping(value="/books/{productId}",produces = APPLICATION_JSON_VALUE)
    public Book getBook(@PathVariable String productId){
//        return bookDao.findByproductid(productId);
        return new Book();
    }

    @GetMapping(value="/books",produces = APPLICATION_JSON_VALUE)
    public List<Book> getAllBooks(){
        return (List<Book>) bookDao.findAll();
    }

    @PostMapping(value="/addbook",produces = APPLICATION_JSON_VALUE)
    public List<Book> addBooks(@RequestBody Book book){

        bookDao.save(book);
        return getAllBooks();
        }

    @PostMapping(value="/editbook/{id}",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Book> editBooks(@RequestBody Book newbook,@PathVariable long id){

        Book book = bookDao.findByid(id);

//        book.setImageurl(newbook.getImageurl());
//        book.setBooktitle(newbook.getBooktitle());
//        book.setProductid(newbook.getProductid());
//
//        bookDao.save(book);
        return new ResponseEntity<>(book,HttpStatus.OK);
    }

    @GetMapping(value="/find/{search:.+}",produces = APPLICATION_JSON_VALUE)
    public List<Book> searchBooks(@PathVariable String search){
        if("all".contains(search)){
            return getAllBooks();
        }
        return bookDao.findByclasses(Integer.parseInt(search));
    }

    @DeleteMapping(value="/delete/{id}",produces = APPLICATION_JSON_VALUE)
    public List<Book>  deleteBook(@PathVariable Long id){
        Book book = bookDao.findByid(id);
        bookDao.delete(book);
        return getAllBooks();
    }

//    @GetMapping(value="/files",produces = APPLICATION_JSON_VALUE)
//    public String listUploadedFiles(Model model) throws IOException {
//
//        model.addAttribute("files", storageService
//                .loadAll()
//                .map(path ->
//                        MvcUriComponentsBuilder
//                                .fromMethodName(BookController.class, "showFile", path.getFileName().toString())
//                                .build().toString())
//                .collect(Collectors.toList()));
//
//        return model.toString();
//    }

//    @GetMapping(value="/files/{filename:.+}",produces = MediaType.ALL_VALUE)
//    @ResponseBody
//    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
//
//        Resource file = storageService.loadAsResource(filename);
//        return ResponseEntity
//                .ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+file.getFilename()+"\"")
//                .body(file);
//    }

    @GetMapping(value = "/peek/{filename:.+}",produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public ResponseEntity<byte[]> showFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        //InputStream in = servletContext.getResourceAsStream(file);
        try {
            return ResponseEntity
                    .ok()
                    .body(IOUtils.readFully(file.getInputStream(),-1,true));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/addFiles")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {

        storageService.store(file);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "/files/"+file.getOriginalFilename();
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
}
