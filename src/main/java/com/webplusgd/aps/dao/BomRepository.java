package com.webplusgd.aps.dao;

import com.webplusgd.aps.domain.Bom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface BomRepository extends JpaRepository<Bom,Integer> {
    /**
     * 根据MaterialIdList查找BOM
     * @param materialIdList 物料id列表
     * @return BOMList
     */
    List<Bom> findByMaterialIdIn(Collection<Integer> materialIdList);

    /**
     * 根据MaterialId查找BOM
     * @param materialId 物料id
     * @return BomList
     */
    List<Bom> findByMaterialId(Integer materialId);
}
