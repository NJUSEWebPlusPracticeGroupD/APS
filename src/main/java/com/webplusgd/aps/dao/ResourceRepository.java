package com.webplusgd.aps.dao;

import com.webplusgd.aps.domain.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Repository
public interface ResourceRepository extends JpaRepository<Resource,String> {
    /**
     * 清空Resource数据表
     */
    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query(value = "truncate table `resource`",nativeQuery = true)
    void truncateTable();

    /**
     * 根据resource_id列表查找Resource
     * @param resourceIds 资源id列表
     * @return 资源列表
     */
    @Query(value = "select * from resource r where r.resource_id in(:resourceIds)", nativeQuery = true)
    List<Resource> findByResourceIdIn(@Param("resourceIds") Collection<String> resourceIds);
}
