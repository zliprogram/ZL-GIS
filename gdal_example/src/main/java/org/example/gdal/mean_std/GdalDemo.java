package org.example.gdal.mean_std;

import cn.hutool.json.JSONUtil;
import org.gdal.gdal.Band;
import org.gdal.gdal.Dataset;
import org.gdal.gdal.gdal;
import org.gdal.gdalconst.gdalconst;

/**
 * @Description:
 * @Author: 张黎 zliprogram@163.com 15972037154
 * @CreateDate: 2025/4/1 15:58
 * @UpdateUser:
 * @UpdateDate: 2025/4/1 15:58
 * @UpdateRemark:
 * @Version: 1.0
 * Copyright (c) 2025,南方数码
 * All rights reserved.
 */
public class GdalDemo {

    public static void main(String[] args) throws Exception {
        String tifPath = "D:\\mnt\\cephfs\\ispatial\\70.tif";
        Long startTime = System.currentTimeMillis();
        gdalMeta(tifPath);
        Long endTime = System.currentTimeMillis();
        System.out.println("计算时间======" + (endTime-startTime));
    }

    /**
     * @description 利用geotools生成std和mean，一个波段一个波段计算 60
     * @author 张黎 zliprogram@163.com 15972037154
     * @param
     * @param tifPath
     * @exception
     * @date 2025/3/28 13:38
     * @return
     */
    private static void gdalMeta(String tifPath){
        gdal.AllRegister();
        gdal.SetConfigOption("GDAL_FILENAME_IS_UTF8", "YES");
        Dataset dataset = gdal.Open(tifPath, gdalconst.GA_ReadOnly);
        Integer bandNum = dataset.getRasterCount();
        Double [] meanArray = null;
        Double [] stdArray = null;
        if (meanArray == null || stdArray == null) {
            meanArray = new Double[bandNum];
            stdArray = new Double[bandNum];
            for (int j = 0; j < bandNum; j++) {
                Band band = dataset.GetRasterBand(j + 1);
                Double[] noDataValue = new Double[1];
                band.GetNoDataValue(noDataValue);
                if (noDataValue[0] == null) {
                    band.SetNoDataValue(0);
                }
                double[] meanValue = new double[1];
                double[] stddevValue = new double[1];
                band.ComputeStatistics(true, new double[1], new double[1], meanValue, stddevValue);
                meanArray[j] = meanValue[0];
                stdArray[j] = stddevValue[0];
            }
        }
//        System.out.println(JSONUtil.parseArray(meanArray));
//        System.out.println(JSONUtil.parseArray(stdArray));
    }

}