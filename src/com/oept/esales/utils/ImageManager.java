package com.oept.esales.utils;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
/**
 * @author mwan
 * Version: 1.0
 * Date: 2015/12/18
 * Description:Image utility.
 * Copyright (c) 2015 �Ϻ���ԯ�Ƽ����޹�˾. All rights reserved.
 */
public class ImageManager {

	/**
	 * �ı�ͼƬ�Ĵ�С����Ϊsize��Ȼ������ſ�ȱ����仯
	 * @param is �ϴ���ͼƬ��������
	 * @param os �ı���ͼƬ�Ĵ�С�󣬰�ͼƬ���������Ŀ��OutputStream
	 * @param size ��ͼƬ�Ŀ�
	 * @param format ��ͼƬ�ĸ�ʽ
	 * @throws IOException
	 */
	public static void resizeImage(InputStream is, OutputStream os, int size, String format) throws IOException {
		BufferedImage prevImage = ImageIO.read(is);
		double width = prevImage.getWidth();
		double height = prevImage.getHeight();
		double percent = size/width;
		int newWidth = (int)(width * percent);
		int newHeight = (int)(height * percent);
		BufferedImage image = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_BGR);
		Graphics graphics = image.createGraphics();
		graphics.drawImage(prevImage, 0, 0, newWidth, newHeight, null);
		ImageIO.write(image, format, os);
		os.flush();
		is.close();
		os.close();
	}
}
