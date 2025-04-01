package org.example.gdal.mean_std;

import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.colour.Transforms;

import java.io.File;

/**
 * @Description:
 * @Author: 张黎 zliprogram@163.com 15972037154
 * @CreateDate: 2025/4/1 17:07
 * @UpdateUser:
 * @UpdateDate: 2025/4/1 17:07
 * @UpdateRemark:
 * @Version: 1.0
 * Copyright (c) 2025,南方数码
 * All rights reserved.
 */
public class OpenIMAJ {

    public static void main(String[] args) throws Exception {
        String tiffPath = "D:\\mnt\\cephfs\\ispatial\\70.tif";
        Long startTime = System.currentTimeMillis();
        TiffStatsOpenIMAJ(tiffPath);
        Long endTime = System.currentTimeMillis();
        System.out.println("计算时间======" + (endTime-startTime));
    }

    public static void TiffStatsOpenIMAJ(String tiffPath) throws Exception {
        MBFImage image = ImageUtilities.readMBF(new File(tiffPath));
        FImage gray = Transforms.calculateIntensity(image);

        float sum = 0;
        float sumSquared = 0;
        int pixelCount = gray.getWidth() * gray.getHeight();

        for (int y = 0; y < gray.getHeight(); y++) {
            for (int x = 0; x < gray.getWidth(); x++) {
                float value = gray.pixels[y][x];
                sum += value;
                sumSquared += value * value;
            }
        }

        float mean = sum / pixelCount;
        float std = (float) Math.sqrt((sumSquared / pixelCount) - (mean * mean));

        System.out.println("Mean: " + mean);
        System.out.println("Standard Deviation: " + std);
    }


}