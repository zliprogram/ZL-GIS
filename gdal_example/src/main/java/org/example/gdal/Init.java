package org.example.gdal;

import org.gdal.gdal.Dataset;
import org.gdal.gdal.gdal;
import org.gdal.gdalconst.gdalconst;

/**
 * @Description:
 * @Author: 张黎 zliprogram@163.com 15972037154
 * @CreateDate: 2025/3/28 14:06
 * @UpdateUser:
 * @UpdateDate: 2025/3/28 14:06
 * @UpdateRemark:
 * @Version: 1.0
 * Copyright (c) 2025,南方数码
 * All rights reserved.
 */
public class Init {

    public static void main(String[] args) throws Exception {
        String tifPath = "C:\\Users\\zlipr\\Desktop\\测试数据\\70.tif";
        getBasicInfo(tifPath);
    }

    private static void getBasicInfo(String tifPath) {
        gdal.AllRegister();
        System.out.println("success");
        gdal.SetConfigOption("GDAL_FILENAME_IS_UTF8", "YES");
        Dataset dataset = gdal.Open(tifPath, gdalconst.GA_ReadOnly);
        int xSize = dataset.getRasterXSize();
        int ySize = dataset.getRasterYSize();
        System.out.println(String.format("Size: %d, %d", xSize, ySize));
        System.out.println("Coordinate System is:");
        System.out.println(dataset.GetProjection());
    }

}