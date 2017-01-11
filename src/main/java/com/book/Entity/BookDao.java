package com.book.Entity;

import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by vaibhav.rana on 12/31/16.
 */
@Transactional
public interface BookDao extends CrudRepository<Book, Long>{
//    public Book findByproductid(String productid);
    Book findByid(Long id);
    List<Book> findByclasses(int query);
}
