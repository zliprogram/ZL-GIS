//package org.example.geotools;
//
////import org.geotools.api.parameter.GeneralParameterValue;
////import org.geotools.api.parameter.ParameterValue;
////import org.geotools.api.parameter.ParameterValueGroup;
//import it.geosolutions.jaiext.range.NoDataContainer;
//import org.geotools.coverage.grid.GridCoverage2D;
//import org.geotools.coverage.grid.GridCoverageFactory;
//import org.geotools.coverage.grid.io.AbstractGridFormat;
//import org.geotools.coverage.grid.io.GridCoverage2DReader;
//import org.geotools.coverage.grid.io.GridFormatFinder;
//import org.geotools.coverage.grid.io.imageio.geotiff.GeoTiffIIOMetadataEncoder;
//import org.geotools.gce.geotiff.GeoTiffFormat;
//import org.geotools.gce.geotiff.GeoTiffReader;
//import org.geotools.gce.geotiff.GeoTiffWriteParams;
//import org.geotools.gce.geotiff.GeoTiffWriter;
//import org.opengis.coverage.grid.GridCoverage;
//import org.opengis.parameter.GeneralParameterValue;
//
//
//import java.io.File;
//import java.io.IOException;
//
///**
// * @Description:
// * @Author: 张黎 zliprogram@163.com 15972037154
// * @CreateDate: 2024/5/30 14:49
// * @UpdateUser:
// * @UpdateDate: 2024/5/30 14:49
// * @UpdateRemark:
// * @Version: 1.0
// * Copyright (c) 2024,南方数码
// * All rights reserved.
// */
//public class TTTT {
//
//    public static void d1(String[] args) throws IOException {
//        // TIF文件路径
//        String filePath = "path/to/your/file.tif";
//
//        // 读取TIF文件
////        AbstractGridFormat format = GridFormatFactory.getFormat(filePath);
////        GridCoverage2Reader reader = (GridCoverage2Reader) format.getReader(new File(filePath));
////        GridCoverage gridCoverage = reader.read(null);
////
////        // 创建新的NoData容器并设置值为-1
////        NoDataContainer noDataContainer = new NoDataContainer(-1);
////
////        // 获取GridCoverage2D，并设置新的NoData值
////        GridCoverage2D coverage2D = (GridCoverage2D) gridCoverage;
////        coverage2D.setNoDataContainer(noDataContainer);
////
////        // 获取写入器并写出修改后的数据
////        GridCoverageWriter writer = (GridCoverageWriter) format.getWriter(new File(filePath));
////        writer.write(coverage2D, null);
//
//        System.out.println("NoData value modified successfully.");
//    }
//
//    public static void d2(String[] args) throws IOException {
//        // 输入和输出 TIFF 文件路径
//        String inputFilePath = "path/to/your/input.tif";
//        String outputFilePath = "path/to/your/output.tif";
//
//        // 读取输入 TIFF 文件
//        File inputFile = new File(inputFilePath);
//        GridCoverage2DReader reader = new GeoTiffReader(inputFile);
//        GridCoverage2D coverage = (GridCoverage2D) reader.read(null);
//
//        // 获取元数据
//        GeoTiffIIOMetadata metadata = (GeoTiffIIOMetadata) reader.getMetadata();
//        GeoTiffIIOMetadataEncoder metadataEncoder = metadata.getMetadataEncoder();
//
//        // 设置 NoData 值为 -1
//        IIOMetadataNode rootNode = new IIOMetadataNode(GeoTiffIIOMetadata.nativeMetadataFormatName);
//        IIOMetadataNode noDataNode = new IIOMetadataNode("GDALNoData");
//        noDataNode.setAttribute("NoDataValue", "-1");
//        rootNode.appendChild(noDataNode);
//        metadataEncoder.mergeTree(GeoTiffIIOMetadata.nativeMetadataFormatName, rootNode);
//
//        // 创建 GeoTiffWriteParams 并设置元数据
//        GeoTiffWriteParams writeParams = new GeoTiffWriteParams();
//        writeParams.setMetadata(metadataEncoder);
//
//        // 获取输出 TIFF 文件
//        File outputFile = new File(outputFilePath);
//        GeoTiffWriter writer = new GeoTiffWriter(outputFile);
//
//        // 设置写入参数
//        GeneralParameterValue[] params = new GeneralParameterValue[]{
//                GeoTiffFormat.GEOTOOLS_WRITE_PARAMS.createValue().setValue(writeParams)
//        };
//
//        // 写入新的 TIFF 文件
//        writer.write(coverage, params);
//
//        // 关闭资源
//        reader.dispose();
//        writer.dispose();
//
//        System.out.println("NoData value successfully set to -1 in " + outputFilePath);
//    }
//
//
//    public static void d3(String[] args) throws IOException {
//        // 输入GeoTiff文件路径
//        String inputFilePath = "input.tif";
//        // 新的NoData值
//        double newNoDataValue = -1;
//
//        // 读取GeoTiff文件
//        File inputFile = new File(inputFilePath);
//        GridCoverage2DReader reader = new GeoTiffFormat().getReader();
//        GridCoverage2D coverage2D = (GridCoverage2D) reader.read(inputFile.toURI().toURL());
//
//        // 修改NoData值
//        GridCoverage modifiedCoverage = Operations.value(coverage2D, newNoDataValue, "value");
//
//        // 输出文件路径
//        String outputFilePath = "output.tif";
//        // 写出修改后的数据
//        GeoTiffFormat format = new GeoTiffFormat();
//        format.write(new File(outputFilePath), modifiedCoverage, null);
//    }
//
//    public static void d4(String[] args) {
//        File inputFile = new File("path/to/input.tif"); // 输入的GeoTIFF文件
//        File outputFile = new File("path/to/output.tif"); // 输出的GeoTIFF文件
//
//        try {
//            // 查找GeoTIFF读取器
//            GridCoverage2DReader reader = (GridCoverage2DReader) GridFormatFinder.findFormat(inputFile).getReader(inputFile);
//            if (reader == null) {
//                throw new RuntimeException("Unable to find a reader for the input file.");
//            }
//
//            // 读取GeoTIFF
//            GridCoverage2D gridCoverage = reader.read(null);
//
//            // 设置NoData值（这里设置为-1，你需要根据实际情况设置）
//            double noDataValue = -1.0;
//
//            // 创建GeoTiffWriteParams对象并设置NoData值
//            GeoTiffWriteParams wp = new GeoTiffWriteParams();
//            wp.setCompressionMode(GeoTiffWriteParams.MODE_EXPLICIT);
//            wp.setCompressionType("DEFLATE"); // 可选：设置压缩类型
//            wp.setUserDefinedOverviewPolicies(new String[]{"APPROXIMATE_WORLD_FILE"}); // 可选：设置概述策略
//            wp.set
//
//            // 查找GeoTIFF写入器
//            CoverageWriter writer = GridFormatFinder.findFormat(outputFile).getWriter(outputFile);
//            if (writer instanceof GridCoverage2DWriter) {
//                GridCoverage2DWriter gridWriter = (GridCoverage2DWriter) writer;
//
//                // 设置写入参数
//                gridWriter.setWriteParams(wp);
//
//                // 写入GeoTIFF
//                gridWriter.write(gridCoverage, null);
//
//                // 清理资源
//                gridWriter.dispose();
//            }
//
//            // 清理资源
//            reader.dispose();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void main(String[] args) throws IOException {
//        // 输入和输出TIFF文件路径
//        String inputFilePath = "C:\\Users\\zlipr\\Desktop\\test\\70.tif";
//        String outputFilePath = "C:\\Users\\zlipr\\Desktop\\test\\7070.tif";
//
//        File inputFile = new File(inputFilePath);
//        File outputFile = new File(outputFilePath);
//
//        // 读取GeoTiff文件
//        GridCoverage2D coverage = (GridCoverage2D) ImageIO.readGridCoverage(new File(inputFilePath).toURI());
//
//        // 设置NODATA值
//        double noDataValue = -9999; // 假设我们想设置NODATA值为-9999
//        NoDataContainer noDataContainer = Operations.createNoDataContainer(coverage.getGridGeometry(), noDataValue);
//
//        // 应用NODATA值
//        GridCoverage modifiedCoverage = Operations.setNoData(coverage, noDataContainer);
//
//        // 输出文件路径
////        String outputFilePath = "output.tif";
//
//        // 将修改后的覆盖写入新的TIF文件
//        ImageIO.writeGridCoverage(modifiedCoverage, new File(outputFilePath), GeoTiffFormat.getFormat());
//
//
//
////        AbstractGridFormat format = GridFormatFinder.findFormat(inputFile);
////        ParameterValueGroup params = format.getReadParameters();
////        params.parameter("nodata").setValue(-1.0);
//
////        Map<String, Object> hints = new HashMap<>();
////        hints.put(String.valueOf(Hints.DEFAULT_COORDINATE_REFERENCE_SYSTEM), "EPSG:4326");
//
//        try {
//            GeoTiffReader reader = new GeoTiffReader(inputFile);
//            GridCoverage2D coverage = reader.read(null);
//            GridCoverageFactory factory = new GridCoverageFactory();
//            GridCoverage2D modifiedCoverage = factory.create("modified", coverage.getRenderedImage(), coverage.getEnvelope());
//
//
//            // 获取NODATA值
////            String noDataValue = (String) coverage.getProperty("GC_NODATA");
//
//
////            format = GridFormatFinder.findFormat(outputFile);
////            params = format.getWriteParameters();
//
////            ParameterValue<GeoTiffWriteParams> writeParamsParam = GeoTiffFormat.GEOTOOLS_WRITE_PARAMS.createValue().clone();
////            writeParamsParam.setValue(params);
//
//            GeneralParameterValue metadataParam = GeoTiffFormat.WRITE_NODATA.createValue().clone();
//
//            GeneralParameterValue[] params1 = new GeneralParameterValue[]{metadataParam};
//
//
////            File inputFile = new File(inputFilePath);
////            GridCoverage2D coverage = CoverageIO.read(inputFile);
////
////            GeoTiffWriterSpi writerSpi = new GeoTiffWriterSpi();
////            File outputFile = new File(outputFilePath);
////            FileImageOutputStream output = new FileImageOutputStream(outputFile);
////            GeoTiffWriter writer = new GeoTiffWriter(output, writerSpi);
//
////            ParameterValueGroup params = writer.getParams();
////            params.parameter(AbstractGridFormat.WRITE_NODATA.getName().toString()).setValue(-1.0);
//
////            writer.write(coverage, new GeneralParameterValue[]{params});
////            writer.dispose();
////            output.close();
//
//            GeoTiffWriter writer = new GeoTiffWriter(outputFile);
////
//            writer.write(modifiedCoverage,params1);
//
//            System.out.println("Nodata value has been set to -1 in the output file.");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//
////        // 读取输入的 GeoTIFF 文件
////        AbstractGridFormat format = GridFormatFinder.findFormat(new File(inputFilePath));
////        Hints hints = new Hints();
////        if (format instanceof GeoTiffFormat) {
////            hints = new Hints(Hints.FORCE_LONGITUDE_FIRST_AXIS_ORDER, Boolean.TRUE);
////        }
////        GridCoverage2DReader reader = format.getReader(new File(inputFilePath), hints);
////
////        // 获取覆盖对象
////        GridCoverage2D coverage = reader.read(null);
////
////        // 设置 NoData 值
////        ParameterValueGroup params = format.getWriteParameters();
////        GeoTiffWriteParams writeParams = new GeoTiffWriteParams();
////        writeParams.setNoData(new Double(-1));
////
////        params.parameter(AbstractGridFormat.GEOTOOLS_WRITE_PARAMS.getName().toString()).setValue(writeParams);
////
////        // 写入输出的 GeoTIFF 文件
////        GeoTiffWriter writer = new GeoTiffWriter(new File(outputFilePath));
////        writer.write(coverage, (GeneralParameterValue[]) params.values().toArray(new GeneralParameterValue[1]));
////
////        System.out.println("NoData 值已成功设置为 -1。");
//
//
////        File inputFile = new File(inputFilePath);
////        GeoTiffReader reader = new GeoTiffReader(inputFile);
////        GridCoverage2D coverage = reader.read(null);
////
////        // 创建GeoTiffWriteParams
////        GeoTiffWriteParams writeParams = new GeoTiffWriteParams();
////        writeParams.setCompressionMode(GeoTiffWriteParams.MODE_EXPLICIT);
////        writeParams.setCompressionType("LZW");
////
////        // 设置NoData值元数据
////        IIOMetadataNode rootNode = new IIOMetadataNode();
////        IIOMetadataNode noDataNode = new IIOMetadataNode("NoData");
////        noDataNode.setAttribute("value", "-1");
////        rootNode.appendChild(noDataNode);
////
////        GeoTiffIIOMetadataEncoder metadataEncoder = new GeoTiffIIOMetadataEncoder();
////
////        // 创建GeoTiffWriter
////        File outputFile = new File(outputFilePath);
////        GeoTiffWriter writer = new GeoTiffWriter(outputFile);
////
////        // 设置写入参数
////        GeneralParameterValue[] params = new GeneralParameterValue[]{
////
////
////        };
////
////        // 写入新的TIFF文件
////        writer.write(coverage, params);
//
////        File inputFile = new File(inputFilePath);
////        GeoTiffReader reader = new GeoTiffReader(inputFile);
////        GridCoverage2D coverage = reader.read(null);
////
////        // 创建GeoTiffWriteParams并设置NoData值元数据
////        GeoTiffWriteParams writeParams = new GeoTiffWriteParams();
////        writeParams.setTilingMode(GeoTiffWriteParams.MODE_EXPLICIT);
////        writeParams.setCompressionMode(GeoTiffWriteParams.MODE_EXPLICIT);
////        writeParams.setCompressionType("LZW");
////
////        // 设置NoData值
////        ParameterValueGroup params = coverage.getGridGeometry()
////        ParameterValue<Boolean> noData = GeoTiffFormat.WRITE_NODATA.createValue();
////        noData.setValue(-1.0);
////        params.parameter("NoData").setValue(noData);
////
////        // 获取输出TIFF文件
////        File outputFile = new File(outputFilePath);
////        GeoTiffWriter writer = new GeoTiffWriter(outputFile);
////
////        // 写入新的TIFF文件，使用包含NoData值的参数
////        writer.write(coverage, (GeneralParameterValue[]) params.values().toArray(new GeneralParameterValue[0]));
//
//
////        // 读取栅格数据
////        File rasterFile = new File("path/to/your/raster.tif");
////        AbstractGridCoverage2DReader reader = (AbstractGridCoverage2DReader) GridFormatFactoryFinder.findFormat(rasterFile).getReader(rasterFile);
////        GridCoverage2D coverage = reader.read(null);
////
////        // 设置NoData值为-1
////        WritableRaster raster = (WritableRaster) coverage.getRenderedImage().getData();
////        for (int y = 0; y < raster.getHeight(); y++) {
////            for (int x = 0; x < raster.getWidth(); x++) {
////                double value = raster.getSampleDouble(x, y, 0);
////                if (Double.isNaN(value)) {
////                    raster.setSample(x, y, 0, -1);
////                }
////            }
////        }
////        GridCoverageFactory factory = new GridCoverageFactory();
////        coverage = factory.create("title", raster, coverage.getEnvelope());
////
////        // 读取矢量数据
////        File vectorFile = new File("path/to/your/vector.shp");
////        FileDataStore store = FileDataStoreFinder.getDataStore(vectorFile);
////        SimpleFeatureSource featureSource = store.getFeatureSource();
////
////        // 创建地图内容和图层
////        MapContent mapContent = new MapContent();
////        mapContent.setTitle("Raster and Vector Overlay");
////
////        // 栅格图层
////        Style rasterStyle = SLD.createRasterStyle();
////        GridReaderLayer rasterLayer = new GridReaderLayer(reader, rasterStyle);
////        mapContent.addLayer(rasterLayer);
////
////        // 矢量图层
////        Style vectorStyle = SLD.createSimpleStyle(featureSource.getSchema());
////        FeatureLayer vectorLayer = new FeatureLayer(featureSource, vectorStyle);
////        mapContent.addLayer(vectorLayer);
////
////        // 创建缩略图
////        ReferencedEnvelope mapBounds = mapContent.getViewport().getBounds();
////        int imageWidth = 800;
////        int imageHeight = 600;
////        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
////        Graphics2D graphics = image.createGraphics();
////
////        GTRenderer renderer = new StreamingRenderer();
////        renderer.setMapContent(mapContent);
////        renderer.paint(graphics, new Rectangle(imageWidth, imageHeight), mapBounds);
////
////        // 保存缩略图
////        File outputFile = new File("path/to/your/output.png");
////        ImageIO.write(image, "png", outputFile);
////
////        // 释放资源
////        mapContent.dispose();
////        store.dispose();
//    }
//
//
//}