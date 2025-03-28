package org.example.gdal.service;

public interface VectorTileService {

	byte[] selectVectorTile(Integer x,Integer y,Integer z);

    byte[] selectVectorTiles(Integer x, Integer y, Integer z);
}
