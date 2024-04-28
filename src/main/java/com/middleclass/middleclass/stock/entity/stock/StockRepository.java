package com.middleclass.middleclass.stock.entity.stock;

import com.middleclass.middleclass.stock.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository<Entity 클래스, PK 타입>
public interface StockRepository extends JpaRepository <Stock, Long>{
}
