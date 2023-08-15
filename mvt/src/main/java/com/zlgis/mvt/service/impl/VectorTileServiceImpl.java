package com.zlgis.mvt.service.impl;

import com.zlgis.mvt.utils.VectorTileUtil;
import com.zlgis.mvt.dao.VectorTileMapper;
import com.zlgis.mvt.domain.TileBox;
import com.zlgis.mvt.domain.VectorTile;
import com.zlgis.mvt.service.VectorTileService;
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
