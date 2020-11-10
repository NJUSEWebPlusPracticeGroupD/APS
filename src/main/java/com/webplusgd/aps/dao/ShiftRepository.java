package com.webplusgd.aps.dao;

import com.webplusgd.aps.domain.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ShiftRepository extends JpaRepository<Shift,Integer> {
    /**
     * 清空Shift数据表
     */
    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query(value = "truncate table `shift`",nativeQuery = true)
    void truncateTable();
}
