package com.webplusgd.aps.dao;

import com.webplusgd.aps.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface OrderRepository extends JpaRepository<Order,Integer> {
    /**
     * 清空Order数据表
     */
    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query(value = "truncate table `order`",nativeQuery = true)
    void truncateTable();
}
