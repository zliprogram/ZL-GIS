package org.example.gdal.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class TileBox implements Serializable {

	 private static final long serialVersionUID = 1L;
	 
	 private Double xmin;
	 
	 private Double ymin;
	 
	 private Double xmax;
	 
	 private Double ymax;

}
