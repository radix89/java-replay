package com.radix;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ScreenShot {

	public static void ofScreenFull() throws Exception {
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		Robot robot = new Robot();
		BufferedImage img = robot.createScreenCapture(new Rectangle((int) screen
				.getWidth(), (int) screen.getHeight()));
		ImageIO.write(img, "jpeg", new File(System.currentTimeMillis()
				+ ".jpeg"));
	}

	public static void ofScreenArea(int x , int y , int width , int height){
		try {
			Robot robot = new Robot();
			BufferedImage img = robot.createScreenCapture(new Rectangle(x, y, width, height));
			ImageIO.write(img, "jpeg", new File(System.currentTimeMillis()
					+ ".jpeg"));
		} catch (AWTException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
