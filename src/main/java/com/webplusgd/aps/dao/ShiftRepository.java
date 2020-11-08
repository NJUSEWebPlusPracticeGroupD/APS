package com.webplusgd.aps.dao;

import com.webplusgd.aps.domain.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShiftRepository extends JpaRepository<Shift,String> {
}
