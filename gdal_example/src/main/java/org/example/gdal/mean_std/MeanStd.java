package org.example.gdal.mean_std;

import cn.hutool.json.JSONUtil;
import org.gdal.gdal.Band;
import org.gdal.gdal.Dataset;
import org.gdal.gdal.gdal;
import org.gdal.gdalconst.gdalconst;
import org.geotools.coverage.GridSampleDimension;
import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.coverage.grid.io.GridCoverage2DReader;
import org.geotools.gce.geotiff.GeoTiffFormat;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.colour.Transforms;

import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import java.awt.image.Raster;
import java.awt.image.RenderedImage;
import java.io.File;
import java.text.DecimalFormat;
import java.util.*;

/**
 * @Description:
 * @Author: 张黎 zliprogram@163.com 15972037154
 * @CreateDate: 2025/3/28 11:32
 * @UpdateUser:
 * @UpdateDate: 2025/3/28 11:32
 * @UpdateRemark:
 * @Version: 1.0
 * Copyright (c) 2025,南方数码
 * All rights reserved.
 */
public class MeanStd {

    public static void main(String[] args) throws Exception {
        String tiffPath = "D:\\mnt\\cephfs\\ispatial\\70.tif";
//        [130.01685959094857,131.94715000187585,120.13810030908289]
//        [25.984884638245422,18.470792874092677,17.319195274320013]

//        均值: ["103.5356","130.4389","55.7369"]
//        标准差: ["47.588","38.0768","34.1736"]

//        getRandomTile(10000,10000,20,100);
        Long startTime = System.currentTimeMillis();
        TiffStatsOpenIMAJ(tiffPath);
        Long endTime = System.currentTimeMillis();
        System.out.println("计算时间======" + (endTime-startTime));

    }

    /**
     * @description 利用geotools生成std和mean，一个波段一个波段计算
     * @author 张黎 zliprogram@163.com 15972037154
     * @param
     * @param tifPath
     * @exception
     * @date 2025/3/28 13:38
     * @return
     */
    public static void geotools1(String tifPath) throws Exception {
        File tifFile = new File(tifPath);
        GeoTiffFormat format = new GeoTiffFormat();
        GridCoverage2DReader reader = format.getReader(tifFile);
        GridCoverage2D coverage = reader.read(null);

        // 获取波段数量
        GridSampleDimension[] sampleDimensions = coverage.getSampleDimensions();
        int numBands = sampleDimensions.length;
        System.out.println("图像包含 " + numBands + " 个波段");

        // 获取图像数据
        RenderedImage image = coverage.getRenderedImage();

        // 为每个波段计算统计信息
        for (int band = 0; band < numBands; band++) {
            List<Double> values = new ArrayList<>();
            double sum = 0;
            int count = image.getWidth() * image.getHeight();

            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    double value = image.getData().getSampleDouble(x, y, band);
                    values.add(value);
                    sum+=value;
                }
            }

            double mean = sum / count;
            double stdDev = calculateSD(mean,values);

            // 获取波段名称(如果有)
            String bandName = sampleDimensions[band].getDescription().toString();

            System.out.println("\n波段 " + (band + 1) + " (" + bandName + ") 统计:");
            System.out.println("均值: " + round(mean, 4));
            System.out.println("标准差: " + round(stdDev, 4));
        }

        reader.dispose();
    }

    /**
     * @description 利用geotools生成std和mean，一个波段一个波段计算 61382
     * @author 张黎 zliprogram@163.com 15972037154
     * @param
     * @param tifPath
     * @exception
     * @date 2025/3/28 13:38
     * @return
     */
    public static void geotools2(String tifPath) throws Exception {
        File tifFile = new File(tifPath);
        GeoTiffFormat format = new GeoTiffFormat();
        GridCoverage2DReader reader = format.getReader(tifFile);
        GridCoverage2D coverage = reader.read(null);

        // 获取波段数量
        GridSampleDimension[] sampleDimensions = coverage.getSampleDimensions();
        int numBands = sampleDimensions.length;
        System.out.println("图像包含 " + numBands + " 个波段");

        // 获取图像数据
        RenderedImage image = coverage.getRenderedImage();

        // 为每个波段计算统计信息
        for (int band = 0; band < numBands; band++) {
            double sum = 0;
            double sumOfSquares = 0;
            int count = image.getWidth() * image.getHeight();

            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    double value = image.getData().getSampleDouble(x, y, band);
                    sum += value;
                    sumOfSquares += value * value;
                }
            }

            double mean = sum / count;
            double variance = (sumOfSquares / count) - (mean * mean);
            double stdDev = Math.sqrt(variance);

            // 获取波段名称(如果有)
            String bandName = sampleDimensions[band].getDescription().toString();

            System.out.println("\n波段 " + (band + 1) + " (" + bandName + ") 统计:");
            System.out.println("均值: " + round(mean, 4));
            System.out.println("标准差: " + round(stdDev, 4));
        }

        reader.dispose();
    }

    /**
     * @description 利用geotools生成std和mean，一个波段一个波段计算 70511
     * @author 张黎 zliprogram@163.com 15972037154
     * @param
     * @param tifPath
     * @exception
     * @date 2025/3/28 13:38
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

        String [] means = {round(mean1, 4),round(mean2, 4),round(mean3, 4)};
        String [] stdDevs = {round(stdDev1, 4),round(stdDev2, 4),round(stdDev3, 4)};

        System.out.println("均值: " + JSONUtil.parseArray(means));
        System.out.println("标准差: " + JSONUtil.parseArray(stdDevs));

        reader.dispose();
    }

    /**
     * @description 利用geotools生成std和mean，一个波段一个波段计算
     * @author 张黎 zliprogram@163.com 15972037154
     * @param
     * @param tifPath
     * @exception
     * @date 2025/3/28 13:38
     * @return
     */
    public static double calculateSD(Double mean,List<Double> array) {
        // 计算方差
        double variance = 0.0;
        for (double num : array) {
            variance += Math.pow(num - mean, 2);
        }
        variance /= array.size();

        // 标准差是方差的平方根
        return Math.sqrt(variance);
    }

    /**
     * @description 利用geotools生成std和mean，一个波段一个波段计算
     * @author 张黎 zliprogram@163.com 15972037154
     * @param
     * @param tifPath
     * @exception
     * @date 2025/3/28 13:38
     * @return
     */
    private static String round(double value, int decimals) {
        DecimalFormat df = new DecimalFormat("#.####");
        return df.format(value);
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
        System.out.println("success");
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
        System.out.println(JSONUtil.parseArray(meanArray));
        System.out.println(JSONUtil.parseArray(stdArray));
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

//        Mat image = Imgcodecs.imread(tiffPath, Imgcodecs.IMREAD_UNCHANGED);
        Mat image = Imgcodecs.imread(tiffPath, Imgcodecs.IMREAD_ANYDEPTH | Imgcodecs.IMREAD_ANYCOLOR);

        if (image.empty()) {
            System.err.println("无法加载图像");
            return;
        }

        System.out.println("=== 多波段统计 ===");
        System.out.printf("尺寸: %dx%d, 波段数: %d, 类型: %s\n",
                image.width(), image.height(),
                image.channels(),
                CvType.typeToString(image.type()));

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

        System.out.println(means);
        System.out.println(stdDevs);
//        System.out.printf("  有效像素数: %d\n", band.total());
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

        System.out.println("Band\tMean\tStdDev");
        for (int b = 0; b < bands; b++) {
            double mean = sum[b] / pixelCount;
            double std = Math.sqrt((sumSquared[b] / pixelCount) - (mean * mean));
            System.out.printf("%d\t%.2f\t%.2f%n", b+1, mean, std);
        }
    }

    public static void TiffStatsOpenIMAJ (String tiffPath) throws Exception {
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


    /**
     * @description 获取瓦片范围
     * @author 张黎 zliprogram@163.com 15972037154
     * @param
     * @param tifPath
     * @exception
     * @date 2025/3/28 13:38
     * @return
     */
    public static void getRandomTile(Integer width,int height,Integer selectionsNum,Integer smallSize) {
        // 假设这是你的大数组
        int[][] bigArray = new int[width][height];

        // 存储已选区域的左上角坐标
        Set<String> selectedAreas = new HashSet<>();
        List<int[][]> selectedSubArrays = new ArrayList<>();

        Random random = new Random();

        while (selectedSubArrays.size() < selectionsNum) {
            // 随机生成左上角坐标
            int x = random.nextInt(width - smallSize + 1);
            int y = random.nextInt(height - smallSize + 1);

            // 检查是否与已选区域重叠
            if (!isOverlapping(selectedAreas, x, y, smallSize)) {
                // 记录这个区域
                selectedAreas.add(x + "," + y);

                // 提取子数组 (这里只是示例，实际操作取决于你的需求)
                int[][] subArray = extractSubArray(bigArray, x, y, smallSize);
                selectedSubArrays.add(subArray);

                System.out.println("Selected sub-array " + selectedSubArrays.size() +
                        " at position (" + x + ", " + y + ")");
            }
        }

        System.out.println("Successfully selected " + selectionsNum + " non-overlapping sub-arrays.");
    }

    /**
     * @description 检查新区域是否与已选区域存在重叠
     * @author 张黎 zliprogram@163.com 15972037154
     * @param selectedAreas
     * @param x
     * @param y
     * @param smallSize
     * @exception
     * @date 2025/3/28 13:38
     * @return
     */
    private static boolean isOverlapping(Set<String> selectedAreas, int x, int y,int smallSize) {
        for (String area : selectedAreas) {
            String[] parts = area.split(",");
            int existingX = Integer.parseInt(parts[0]);
            int existingY = Integer.parseInt(parts[1]);

            // 检查两个矩形是否重叠
            // 计算重叠宽度
            int overlapWidth = Math.max(0, Math.min(x + 100, existingX + 100) - Math.max(x, existingX));
            // 计算重叠高度
            int overlapHeight = Math.max(0, Math.min(y + 100, existingY + 100) - Math.max(y, existingY));
            // 判断是否 > 5000
            return overlapWidth > 50 && overlapHeight > 50;
        }
        return false;
    }

    // 从大数组中提取子数组
    private static int[][] extractSubArray(int[][] bigArray, int x, int y, int size) {
        int[][] subArray = new int[size][size];
        for (int i = 0; i < size; i++) {
            System.arraycopy(bigArray[x + i], y, subArray[i], 0, size);
        }
        return subArray;
    }

}