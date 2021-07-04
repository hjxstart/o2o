package com.imooc.o2o.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;

import com.imooc.o2o.dto.ImageHolder;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

/**
 * 该工具类用于处理预先定义好的图片
 * @author jxh
 *
 */
public class ImageUtil {
	// 获取classpath的绝对值路径
	private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
	
	private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyMMddHHmmss");
	private static final Random r = new Random();
	
	// 缩略图
	public static String generateThumbnail(ImageHolder thumbnail, String targetAddr) {
		// 自定义图片名称，由于用户可能有重复的图片名称
		String realFileName = getRandomFileName();
		// 获取图片的后缀
		String extension = getFileExtension(thumbnail.getImageName());
		System.out.print("=================================" + extension);
		// 用于处理路径不存在的情况
		makeDirPath(targetAddr);
		// 图片相对路径
		String relativeAddr = targetAddr + realFileName + extension;
		// 文件：相对路径 + 文件组成
		File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
		
		// 创建缩略图
		try {
			Thumbnails.of(thumbnail.getImage()).size(200, 200)
			.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "/watermark.png")), 0.25f)
			.outputQuality(0.8f).toFile(dest); // 压缩和输出路径
		} catch(IOException e) {
			e.printStackTrace();
		}
		return relativeAddr;
	}
	
	/**
	 * 创建目标路径所涉及的目录，即 /home/work/hjxstart/xxx.jpg, 那么 home work hjxstart 这三个文件夹都得自动创建
	 * @param targetAddr
	 */
	private static void makeDirPath(String targetAddr) {
		// TODO Auto-generated method stub
		String realFileParentPath = PathUtil.getImgBasePath() + targetAddr;
		File dirPath = new File(realFileParentPath);
		if(!dirPath.exists()) {
			dirPath.mkdirs();
		}
	}

	/**
	 * 获取输入文件流的拓展名,获取最后一个.的字符
	 * @param thumbnail
	 * @return
	 */
	private static String getFileExtension(String fileName) {
	    
	    return fileName.substring(fileName.lastIndexOf("."));
	}

	/**
	 * 生成随机文件名，当前年月日小时分钟秒钟 + 五位随机数
	 * @return 随机文件名称
	 */
	public static String getRandomFileName() {
		// 获取随机的五位数
		int rannum = r.nextInt(89999) + 10000;
		String nowTimeStr = sDateFormat.format(new Date());
		return nowTimeStr + rannum;
	}
	

	public static void main(String[] args) throws IOException {
		// 获取classpath的绝对值路径，由于这个方法是通过线程去执行的，可以通过线程逆推到类加载器，从而从类加载器得到资源路径
		String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		Thumbnails.of(new File("/Users/jxh/Downloads/src.jpeg")) // 需要处理的文件
		.size(200, 200) // 处理后的文件大小
		.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "/hjxstart.png")), 0.25f) // 添
		.outputQuality(0.8f).toFile("/Users/jxh/Downloads/det.jpeg");
	}
	
	/**
	 * storePath是文件的路径还是目录的路径，
	 * 如果storePath是文件路径则删除改文件
	 * 如果storePath是目录路径则删除改目录下的所有文件
	 */
	public static void deleteFileOrPath(String storePath) {
		File fileOrPath = new File(PathUtil.getImgBasePath() + storePath);
		if(fileOrPath.exists()) {
			if(fileOrPath.isDirectory()) {
				File files[] = fileOrPath.listFiles();
				for(int i = 0; i < files.length; i++) {
					files[i].delete();
				}
			}
			fileOrPath.delete();
		}
	}

	public static String generateNormalImg(ImageHolder thumbnail, String targetAddr) {
		// 自定义图片名称，由于用户可能有重复的图片名称
		String realFileName = getRandomFileName();
		// 获取图片的后缀
		String extension = getFileExtension(thumbnail.getImageName());
		System.out.print("current relativeAddr is:" + extension);
		// 用于处理路径不存在的情况
		makeDirPath(targetAddr);
		// 图片相对路径
		String relativeAddr = targetAddr + realFileName + extension;
		// 文件：相对路径 + 文件组成
		File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
		
		// 创建缩略图
		try {
			Thumbnails.of(thumbnail.getImage()).size(337, 640)
			.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "/watermark.png")), 0.25f)
			.outputQuality(0.9f).toFile(dest); // 压缩和输出路径
		} catch(IOException e) {
			e.printStackTrace();
		}
		return relativeAddr;
	}
}
