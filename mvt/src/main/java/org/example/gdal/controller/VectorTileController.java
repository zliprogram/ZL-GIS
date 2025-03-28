package org.example.gdal.controller;

import javax.servlet.http.HttpServletResponse;

import org.example.gdal.service.VectorTileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/vector")
public class VectorTileController {

	@Autowired
	private VectorTileService vectorTileService;

	@CrossOrigin
	@RequestMapping("/tile/{z}/{x}/{y}")
	public Object getVectorTile(@PathVariable("z") Integer z, @PathVariable("x") Integer x,@PathVariable("y") Integer y,HttpServletResponse response){
		response.setContentType("application/x-protobuf;type=mapbox-vector;chartset=UTF-8");
		return vectorTileService.selectVectorTile(x, y, z);
	}

	@CrossOrigin
	@RequestMapping("/tiles/{z}/{x}/{y}")
	public Object getVectorTiles(@PathVariable("z") Integer z, @PathVariable("x") Integer x,@PathVariable("y") Integer y,HttpServletResponse response){
		response.setContentType("application/x-protobuf;type=mapbox-vector;chartset=UTF-8");
		return vectorTileService.selectVectorTiles(x, y, z);
	}

}
