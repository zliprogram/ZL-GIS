package org.example.gdal.service.impl;

import org.example.gdal.utils.VectorTileUtil;
import org.example.gdal.dao.VectorTileMapper;
import org.example.gdal.domain.TileBox;
import org.example.gdal.domain.VectorTile;
import org.example.gdal.service.VectorTileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class VectorTileServiceImpl implements VectorTileService {

	@Autowired
	private VectorTileMapper vectorTileDao;
	
	@Override
	public byte[] selectVectorTile(Integer x, Integer y, Integer z) {
		TileBox tileBox = VectorTileUtil.tile2boundingBox(x, y, z);
		VectorTile vTile = vectorTileDao.selectTile(tileBox.getXmin(),tileBox.getYmin(),tileBox.getXmax(),tileBox.getYmax(),z);
		return vTile.getTile();
	}

	@Override
	public byte[] selectVectorTiles(Integer x, Integer y, Integer z) {
		VectorTile vTile=vectorTileDao.selectVectorTiles(x,y,z);
		return vTile.getTile();
	}
}
