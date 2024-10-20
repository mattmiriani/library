package com.api.library.domain.repository;

import com.api.library.domain.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {

    @Query(value = """
            select b.*
            from book b
            where b.category in (select distinct bk.category
                                 from loan e
                                     join book bk on e.book_id = bk.id
                                 where e.library_user_id = :libraryUserId
                                 )
              and b.id not in (select e.book_id
                               from loan e
                               where e.library_user_id = :libraryUserId
                               )
            order by b.category, b.title
            """, nativeQuery = true)
    Optional<List<Book>> findRecomendations(@Param("libraryUserId") UUID libraryUserId);
}
