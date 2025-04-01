package org.example.gdal.mean_std;

import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import java.awt.image.Raster;

/**
 * @Description:
 * @Author: 张黎 zliprogram@163.com 15972037154
 * @CreateDate: 2025/4/1 16:47
 * @UpdateUser:
 * @UpdateDate: 2025/4/1 16:47
 * @UpdateRemark:
 * @Version: 1.0
 * Copyright (c) 2025,南方数码
 * All rights reserved.
 */
public class JAIDemo {

    public static void main(String[] args) throws Exception {
        String tifPath = "D:\\mnt\\cephfs\\ispatial\\70.tif";
        Long startTime = System.currentTimeMillis();
        TiffStatsJAI(tifPath);
        Long endTime = System.currentTimeMillis();
        System.out.println("计算时间======" + (endTime-startTime));
    }

    /**
     * @description openCV 450毫秒
     * @author 张黎 zliprogram@163.com 15972037154
     * @param tiffPath
     * @exception
     * @date 2025/4/1 10:55
     * @return
     */
    public static void TiffStatsJAI(String tiffPath) {
        PlanarImage image = JAI.create("fileload", tiffPath);
        Raster raster = image.getData();

        int bands = raster.getNumBands();
        double[] sum = new double[bands];
        double[] sumSquared = new double[bands];
        int pixelCount = raster.getWidth() * raster.getHeight();

        for (int y = 0; y < raster.getHeight(); y++) {
            for (int x = 0; x < raster.getWidth(); x++) {
                double[] pixel = raster.getPixel(x, y, (double[]) null);
                for (int b = 0; b < bands; b++) {
                    double value = pixel[b];
                    sum[b] += value;
                    sumSquared[b] += value * value;
                }
            }
        }

//        System.out.println("Band\tMean\tStdDev");
        for (int b = 0; b < bands; b++) {
            double mean = sum[b] / pixelCount;
            double std = Math.sqrt((sumSquared[b] / pixelCount) - (mean * mean));
//            System.out.printf("%d\t%.2f\t%.2f%n", b+1, mean, std);
        }
    }
}