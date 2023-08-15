package com.zlgis.mvt.dao;


import com.zlgis.mvt.domain.VectorTile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface VectorTileMapper {
   
    int deleteByPrimaryKey(Integer id);
   
    int insert(VectorTile record);

    VectorTile selectByPrimaryKey(Integer id);

    List<VectorTile> selectAll();

    int updateByPrimaryKey(VectorTile record);
    
    VectorTile selectTile(@Param("xmin")double Xmin, @Param("ymin")double Ymin, @Param("xmax")double Xmax, @Param("ymax")double Ymax, @Param("z")int z);

    VectorTile selectVectorTiles(@Param("x") Integer x,@Param("y") Integer y,@Param("z") Integer z);

}