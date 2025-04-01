package org.example.gdal.mean_std;

import cn.hutool.json.JSONUtil;
import org.gdal.gdal.Band;
import org.gdal.gdal.Dataset;
import org.gdal.gdal.gdal;
import org.gdal.gdalconst.gdalconst;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: 张黎 zliprogram@163.com 15972037154
 * @CreateDate: 2025/4/1 16:40
 * @UpdateUser:
 * @UpdateDate: 2025/4/1 16:40
 * @UpdateRemark:
 * @Version: 1.0
 * Copyright (c) 2025,南方数码
 * All rights reserved.
 */
public class OpenCVDemo {

    public static void main(String[] args) throws Exception {
        String tiffPath = "D:\\mnt\\cephfs\\ispatial\\70.tif";
        Long startTime = System.currentTimeMillis();
        openCV(tiffPath);
        Long endTime = System.currentTimeMillis();
        System.out.println("计算时间======" + (endTime-startTime));
    }

    /**
     * @description openCV 55毫秒
     * @author 张黎 zliprogram@163.com 15972037154
     * @param
     * @param tiffPath
     * @exception
     * @date 2025/4/1 10:55
     * @return
     */
    public static void openCV(String tiffPath) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat image = Imgcodecs.imread(tiffPath, Imgcodecs.IMREAD_ANYDEPTH | Imgcodecs.IMREAD_ANYCOLOR);

        if (image.empty()) {
            System.err.println("无法加载图像");
            return;
        }

//        System.out.println("=== 多波段统计 ===");
//        System.out.printf("尺寸: %dx%d, 波段数: %d, 类型: %s\n",
//                image.width(), image.height(),
//                image.channels(),
//                CvType.typeToString(image.type()));

        List<Mat> bands = new ArrayList<>();
        Core.split(image, bands);

        List<Double> means = new ArrayList<>();
        List<String> stdDevs = new ArrayList<>();
        List<Double> mins = new ArrayList<>();
        List<Double> maxs = new ArrayList<>();

        for (int i = 0; i < bands.size(); i++) {
            Mat band = bands.get(i);
            Mat doubleBand = new Mat();
            band.convertTo(doubleBand, CvType.CV_64F);

            // 计算统计量
            Scalar mean = Core.mean(doubleBand);
            MatOfDouble stdMat = new MatOfDouble();
            Core.meanStdDev(doubleBand, new MatOfDouble(0), stdMat);
            Double stdDev = stdMat.get(0, 0)[0];

            // 计算最小最大值
            Core.MinMaxLocResult minmax = Core.minMaxLoc(doubleBand);

            means.add(mean.val[0]);
            stdDevs.add(stdDev.toString());
            mins.add(minmax.minVal);
            maxs.add(minmax.maxVal);
        }

//        System.out.println(means);
//        System.out.println(stdDevs);
//        System.out.printf("  有效像素数: %d\n", band.total());
    }

}