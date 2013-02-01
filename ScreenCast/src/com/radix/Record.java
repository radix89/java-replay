package com.radix;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseListener;
import org.jnativehook.mouse.NativeMouseMotionListener;
import org.jnativehook.mouse.NativeMouseWheelEvent;
import org.jnativehook.mouse.NativeMouseWheelListener;

public class Record implements NativeKeyListener,NativeMouseListener,NativeMouseMotionListener,NativeMouseWheelListener {
	public static int pressToStop;
	
	public static void register(String args){
		try {
			GlobalScreen.registerNativeHook();
		}
		catch (NativeHookException ex) {
			System.err.println("Exception : Unable to set up listener.");
			System.err.println(ex.getMessage());
			System.exit(1);
		}

		pressToStop = NativeKeyEvent.VK_ESCAPE;
		
		if(args.contains("keyboard")){
			GlobalScreen.getInstance().addNativeKeyListener(new Record());
		}

		if(args.contains("mouse")){
			GlobalScreen.getInstance().addNativeMouseListener(new Record());
			GlobalScreen.getInstance().addNativeMouseMotionListener(new Record());
			GlobalScreen.getInstance().addNativeMouseWheelListener(new Record());
		} 
	}

	public void nativeKeyPressed(NativeKeyEvent e) {
		System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));

		if (e.getKeyCode() == pressToStop) {
			GlobalScreen.unregisterNativeHook();
			Action.onStopRecord();
		}
	}

	public void nativeKeyReleased(NativeKeyEvent e) {
		System.out.println("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
	}

	public void nativeKeyTyped(NativeKeyEvent e) {
		System.out.println("Key Typed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
	}

	public void nativeMouseClicked(NativeMouseEvent e) {
		System.out.println("Mouse Clicked: " + e.getClickCount());
	}

	public void nativeMousePressed(NativeMouseEvent e) {
		System.out.println("Mouse Pressed: " + e.getButton());
	}

	public void nativeMouseReleased(NativeMouseEvent e) {
		System.out.println("Mouse Released: " + e.getButton());
	}

	public void nativeMouseMoved(NativeMouseEvent e) {
		System.out.println("Mouse Moved: " + e.getX() + ", " + e.getY());
	}

	public void nativeMouseDragged(NativeMouseEvent e) {
		System.out.println("Mouse Dragged: " + e.getX() + ", " + e.getY());
	}

	public void nativeMouseWheelMoved(NativeMouseWheelEvent e) {
		System.out.println("Mouse Wheel Moved: " + e.getWheelRotation());
	}
}