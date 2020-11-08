package com.webplusgd.aps.dao;

import com.webplusgd.aps.domain.Bom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BomRepository extends JpaRepository<Bom,Integer> {
}
