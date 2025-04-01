package org.example.gdal.mean_std;

import cn.hutool.json.JSONUtil;
import org.geotools.coverage.GridSampleDimension;
import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.coverage.grid.io.GridCoverage2DReader;
import org.geotools.gce.geotiff.GeoTiffFormat;

import java.awt.image.RenderedImage;
import java.io.File;

/**
 * @Description:
 * @Author: 张黎 zliprogram@163.com 15972037154
 * @CreateDate: 2025/4/1 16:43
 * @UpdateUser:
 * @UpdateDate: 2025/4/1 16:43
 * @UpdateRemark:
 * @Version: 1.0
 * Copyright (c) 2025,南方数码
 * All rights reserved.
 */
public class GeotoolsDemo {

    public static void main(String[] args) throws Exception {
        String tifPath = "D:\\mnt\\cephfs\\ispatial\\70.tif";
        Long startTime = System.currentTimeMillis();
        geotools3(tifPath);
        Long endTime = System.currentTimeMillis();
        System.out.println("计算时间======" + (endTime-startTime));
    }

    /**
     * @description openCV 66165毫秒
     * @author 张黎 zliprogram@163.com 15972037154
     * @param tifPath
     * @exception
     * @date 2025/4/1 10:55
     * @return
     */
    public static void geotools3(String tifPath) throws Exception {
        File tifFile = new File(tifPath);
        GeoTiffFormat format = new GeoTiffFormat();
        GridCoverage2DReader reader = format.getReader(tifFile);
        GridCoverage2D coverage = reader.read(null);

        // 获取波段数量
        GridSampleDimension[] sampleDimensions = coverage.getSampleDimensions();
        int numBands = sampleDimensions.length;

        // 获取图像数据
        RenderedImage image = coverage.getRenderedImage();
        int width = image.getWidth();
        int height = image.getHeight();

        // 为每个波段计算统计信息
        double sum1 = 0;
        double sum2 = 0;
        double sum3 = 0;
        double sumOfSquares1 = 0;
        double sumOfSquares2 = 0;
        double sumOfSquares3 = 0;
        int count = width * height;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double value1 = image.getData().getSampleDouble(x, y, 0);
                double value2 = image.getData().getSampleDouble(x, y, 1);
                double value3 = image.getData().getSampleDouble(x, y, 2);

                sum1 += value1;
                sumOfSquares1 += value1 * value1;

                sum2 += value2;
                sumOfSquares2 += value2 * value2;

                sum3 += value3;
                sumOfSquares3 += value3 * value3;
            }
        }

        double mean1 = sum1 / count;
        double variance1 = (sumOfSquares1 / count) - (mean1 * mean1);
        double stdDev1 = Math.sqrt(variance1);

        double mean2 = sum2 / count;
        double variance2 = (sumOfSquares2 / count) - (mean2 * mean2);
        double stdDev2 = Math.sqrt(variance2);

        double mean3 = sum3 / count;
        double variance3 = (sumOfSquares3 / count) - (mean3 * mean3);
        double stdDev3 = Math.sqrt(variance3);

        String [] means = {mean1+"",mean2+"",mean3+""};
        String [] stdDevs = {stdDev1+"",stdDev2+"",stdDev3+""};

//        System.out.println("均值: " + JSONUtil.parseArray(means));
//        System.out.println("标准差: " + JSONUtil.parseArray(stdDevs));

        reader.dispose();
    }

}