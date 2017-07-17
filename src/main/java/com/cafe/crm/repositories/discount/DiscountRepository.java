package com.cafe.crm.repositories.discount;

import com.cafe.crm.models.discount.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DiscountRepository extends JpaRepository<Discount, Long> {

    @Query("SELECT c FROM Discount c WHERE c.isOpen = true")
    List<Discount> getAllOpen();

}