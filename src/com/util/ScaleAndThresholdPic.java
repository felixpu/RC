/**     
 * 
 *  缩略图实现，将图片(jpg、bmp、png、gif等等)真实的变成想要的大小    
 *  */
package com.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 1.缩略图类（通用） 本java类能将jpg、bmp、png、gif图片文件，进行等比或非等比的大小转换。 2.图形图像二值化
 * 
 * @author puguanxu
 * 
 */
public class ScaleAndThresholdPic {

	/**
	 * 等比列缩放偏移量
	 */
	private double flag = 2.5;

	public static ScaleAndThresholdPic getInstence() {
		return innerTextParse.temp;
	}

	public static class innerTextParse {
		private static final ScaleAndThresholdPic temp = new ScaleAndThresholdPic();
	}

	public void setFlag(double flag) {
		this.flag = flag;
	}

	/**
	 * 图片处理器
	 * 
	 * @param file
	 *            处理前文件
	 * @param tpath
	 *            临时存放路径
	 * @return 处理后的文件
	 */
	public File doPituer(File file, String tpath) {
		return scalePic(file, tpath);
	}

	/**
	 * 图片缩放
	 * 
	 * @return
	 */
	private File scalePic(File file, String tpath) {
		try {
			Image img = ImageIO.read(file);
			// 这里是从本地读图片文件，如果是执行上传图片的话， Formfile formfile=获得表单提交的Formfile ,然后 ImageIO.read 方法里参数放
			// formfile.getInputStream()
			// 判断图片格式是否正确
			if (img.getWidth(null) != -1) {
				// 为等比缩放计算输出的图片宽度及高度
				double oldWidth = (double) img.getWidth(null);
				double oldHeight = (double) img.getHeight(null);
				// 根据缩放比率大的进行缩放控制
				int newWidth = (int) (oldWidth * flag);
				int newHeight = (int) (oldHeight * flag);
				BufferedImage tag = new BufferedImage((int) newWidth, (int) newHeight, BufferedImage.TYPE_3BYTE_BGR);
				// Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢
				// ImageIO.write((tag), "jpg", new File(tpath +"temp"+ file.getName()+".jpg"));
				Graphics graphics=tag.getGraphics();
//				Graphics2D graphics2D = (Graphics2D)graphics;
				graphics.drawImage(img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);
				
				BufferedImage bi = thresholding(tag);
				
				ImageIO.write(bi, "jpg", new File(tpath + "temp" + file.getName() + ".jpg"));
				file = new File(tpath + "temp" + file.getName() + ".jpg");
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return file;

	}

//	private BufferedImage setKangJuChi(BufferedImage bi) {
//		Graphics2D g2d = (Graphics2D) bi.getGraphics();
//		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);// 设置抗锯齿效果
//		return bi;
//	}

	/**
	 * 二值化图像
	 * 
	 * @param bi
	 * @return
	 */
	public BufferedImage thresholding(BufferedImage bi) {
		int h = bi.getHeight();// 获取图像的高
		int w = bi.getWidth();// 获取图像的宽
		// int rgb = bi.getRGB(0, 0);// 获取指定坐标的ARGB的像素值
		int[][] gray = new int[w][h];
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				gray[x][y] = getGray(bi.getRGB(x, y));
			}
		}

		BufferedImage nbi = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_BINARY);
		int SW = 160;
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				if (getAverageColor(gray, x, y, w, h) > SW) {
					int max = new Color(255, 255, 255).getRGB();
					nbi.setRGB(x, y, max);
				} else {
					int min = new Color(0, 0, 0).getRGB();
					nbi.setRGB(x, y, min);
				}
			}
		}

		return nbi;
	}

	public static int getGray(int rgb) {
		String str = Integer.toHexString(rgb);
		int r = Integer.parseInt(str.substring(2, 4), 16);
		int g = Integer.parseInt(str.substring(4, 6), 16);
		int b = Integer.parseInt(str.substring(6, 8), 16);
		// or 直接new个color对象
		Color c = new Color(rgb);
		r = c.getRed();
		g = c.getGreen();
		b = c.getBlue();
		int top = (r + g + b) / 3;
		return (int) (top);
	}

	/**
	 * 自己加周围8个灰度值再除以9，算出其相对灰度值
	 * 
	 * @param gray
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @return
	 */
	public static int getAverageColor(int[][] gray, int x, int y, int w, int h) {
		int rs = gray[x][y] + (x == 0 ? 255 : gray[x - 1][y]) + (x == 0 || y == 0 ? 255 : gray[x - 1][y - 1])
				+ (x == 0 || y == h - 1 ? 255 : gray[x - 1][y + 1]) + (y == 0 ? 255 : gray[x][y - 1])
				+ (y == h - 1 ? 255 : gray[x][y + 1]) + (x == w - 1 ? 255 : gray[x + 1][y])
				+ (x == w - 1 || y == 0 ? 255 : gray[x + 1][y - 1])
				+ (x == w - 1 || y == h - 1 ? 255 : gray[x + 1][y + 1]);
		return rs / 9;
	}
}
