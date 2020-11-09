package com.webplusgd.aps.dao;

import com.webplusgd.aps.domain.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceRepository extends JpaRepository<Resource,String> {
}
