package com.modsen.libraryservice.repository;

import com.modsen.libraryservice.entities.BookInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookInventoryRepository extends JpaRepository<BookInventory, Long> {
    List<BookInventory> findByBorrowedAtIsNullOrReturnByBefore(LocalDateTime now);
}
