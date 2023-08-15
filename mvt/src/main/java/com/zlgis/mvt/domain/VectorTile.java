package com.zlgis.mvt.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class VectorTile implements Serializable {
    private static final long serialVersionUID = 1L;
	 
    private Integer id;
   
    private String name;
   
    private Object geom;

    private byte[] tile;

}