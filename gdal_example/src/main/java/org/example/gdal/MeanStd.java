package org.example.gdal;

import cn.hutool.json.JSONUtil;
import org.gdal.gdal.Band;
import org.gdal.gdal.Dataset;
import org.gdal.gdal.gdal;
import org.gdal.gdalconst.gdalconst;
import org.geotools.coverage.GridSampleDimension;
import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.coverage.grid.io.GridCoverage2DReader;
import org.geotools.gce.geotiff.GeoTiffFormat;

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
        String tifPath = "C:\\Users\\zlipr\\Desktop\\测试数据\\70.tif";
//        [130.01685959094857,131.94715000187585,120.13810030908289]
//        [25.984884638245422,18.470792874092677,17.319195274320013]

//        均值: ["103.5356","130.4389","55.7369"]
//        标准差: ["47.588","38.0768","34.1736"]

        getRandomTile(10000,10000,20,100);
    }

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

    public static void geotools3(String tifPath) throws Exception {
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
        int width = image.getWidth();
        int height = image.getHeight();

        // 为每个波段计算统计信息
        List<Double> values1 = new ArrayList<>();
        List<Double> values2 = new ArrayList<>();
        List<Double> values3 = new ArrayList<>();
        double sum1 = 0;
        double sum2 = 0;
        double sum3 = 0;

        int count = width * height;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double value1 = image.getData().getSampleDouble(x, y, 0);
                double value2 = image.getData().getSampleDouble(x, y, 1);
                double value3 = image.getData().getSampleDouble(x, y, 2);

                values1.add(value1);
                values2.add(value2);
                values3.add(value3);

                sum1+=value1;
                sum2+=value2;
                sum3+=value3;
            }
        }

        double mean1 = sum1 / count;
        double stdDev1 = calculateSD(mean1,values1);

        double mean2 = sum2 / count;
        double stdDev2 = calculateSD(mean2,values2);

        double mean3 = sum3 / count;
        double stdDev3 = calculateSD(mean3,values3);

        String [] means = {round(mean1, 4),round(mean2, 4),round(mean3, 4)};
        String [] stdDevs = {round(stdDev1, 4),round(stdDev2, 4),round(stdDev3, 4)};

        System.out.println("均值: " + JSONUtil.parseArray(means));
        System.out.println("标准差: " + JSONUtil.parseArray(stdDevs));

        reader.dispose();
    }

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


    private static String round(double value, int decimals) {
        DecimalFormat df = new DecimalFormat("#.####");
        return df.format(value);
    }

    private static void gdalMeta(String tifPath){
        gdal.AllRegister();
        System.out.println("success");
        gdal.SetConfigOption("GDAL_FILENAME_IS_UTF8", "YES");
        String tifName = "/mnt/cephfs/ispatial/zhangli/FW3.tif";
        Dataset dataset = gdal.Open(tifName, gdalconst.GA_ReadOnly);
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
                System.out.println(JSONUtil.parseArray(meanArray));
                System.out.println(JSONUtil.parseArray(stdArray));
                System.out.println("++++++++++++++++++++++++++++");
            }
        }
    }



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

    // 检查新区域是否与已选区域重叠
    private static boolean isOverlapping(Set<String> selectedAreas, int x, int y,int SMALL_SIZE) {
        for (String area : selectedAreas) {
            String[] parts = area.split(",");
            int existingX = Integer.parseInt(parts[0]);
            int existingY = Integer.parseInt(parts[1]);

            // 检查两个矩形是否重叠
            if (x < existingX + SMALL_SIZE &&
                    x + SMALL_SIZE > existingX &&
                    y < existingY + SMALL_SIZE &&
                    y + SMALL_SIZE > existingY) {
                return true;
            }
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