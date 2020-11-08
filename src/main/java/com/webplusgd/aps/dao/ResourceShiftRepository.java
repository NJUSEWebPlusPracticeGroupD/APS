package com.webplusgd.aps.dao;

import com.webplusgd.aps.domain.ResourceShift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceShiftRepository extends JpaRepository<ResourceShift,Integer> {
}
