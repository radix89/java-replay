package com.radix;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.MalformedURLException;
import java.util.Scanner;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.imageio.spi.RegisterableService;
import javax.media.MediaLocator;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class ScreenCast{
	public static int screenWidth = (int) Toolkit.getDefaultToolkit()
			.getScreenSize().getWidth();
	public static int screenHeight = (int) Toolkit.getDefaultToolkit()
			.getScreenSize().getHeight();

	public static int captureInterval = 50;

	public static String store = "tmp";

	public static boolean record = false;

	public static void startRecord() {
		setUpInterface();
		record = true;
		Thread recordThread = new Thread() {
			@Override
			public void run() {
				Robot robot;
				int cnt = 0;
				try {
					robot = new Robot();
					while (cnt == 0 || record) {
						BufferedImage img = robot.createScreenCapture(new Rectangle(screenWidth,screenHeight));
						ImageIO.write(img, "jpeg", new File("./"+store+"/"+ System.currentTimeMillis() + ".jpeg"));
						if (cnt == 0) {
							record = true;
							cnt = 1;
						}
						Thread.sleep(captureInterval);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		recordThread.start();
	}

	public static void makeVideo(String movFile) throws MalformedURLException {
		System.out
		.println("#### Easy Capture making video, please wait!!! ####");
		JpegImagesToMovie imageToMovie = new JpegImagesToMovie();
		Vector<String> imgLst = new Vector<String>();
		File f = new File(store);
		File[] fileLst = f.listFiles();
		for (int i = 0; i < fileLst.length; i++) {
			imgLst.add(fileLst[i].getAbsolutePath());
		}

		MediaLocator oml;
		if ((oml = JpegImagesToMovie.createMediaLocator(movFile)) == null) {
			System.err.println("Cannot build media locator from: " + movFile);
			System.exit(0);
		}
		imageToMovie.doIt(screenWidth, screenHeight, (1000 / captureInterval),
				imgLst, oml);

	}

	public static void setUpInterface(){
		Record.register("keyboard mouse");
	}

	public static void main(String[] args) throws Exception {
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		System.out.println("Your Screen [Width,Height]:" + "["
				+ screen.getWidth() + "," + screen.getHeight() + "]");
		Scanner sc = new Scanner(System.in);
		System.out.println("Rate 20 Frames/Per Sec.");
		System.out.print("Do you wanna change the screen capture area (y/n) ? ");
		if (sc.next().equalsIgnoreCase("y")) {
			System.out.print("Enter the width:");
			screenWidth = sc.nextInt();
			System.out.print("Enter the Height:");
			screenHeight = sc.nextInt();
			System.out.println("Your Screen [Width,Height]:" + "["
					+ screen.getWidth() + "," + screen.getHeight() + "]");
		}
		System.out
		.print("Now move to the screen you want to record");
		for(int i=0;i<5;i++){
			System.out.print(".");
			Thread.sleep(1000);
		}
		File tempDirectory = new File(store);
		if(!tempDirectory.exists()){
			tempDirectory.mkdir();
		}
		startRecord();

		while(record){
			
		}
		
		makeVideo(System.currentTimeMillis()+".mov");
		sc.close();
	}
}
