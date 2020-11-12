package com.webplusgd.aps.dao;

import com.webplusgd.aps.domain.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ResourceRepository extends JpaRepository<Resource,String> {
    /**
     * 清空Resource数据表
     */
    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query(value = "truncate table `resource`",nativeQuery = true)
    void truncateTable();
}
