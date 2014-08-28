package com.lge.boardgame;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;

public class Main {

	public static void main(String[] args) throws InvocationTargetException,
			InterruptedException {
		SwingUtilities.invokeAndWait(new Runnable() {
			@Override
			public void run() {
				JFrame frame = new JFrame("BoardGame");
				frame.add(Games.reversi());
				frame.setSize(new Dimension(600, 600));
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
				// frame.setResizable(false);
			}
		});
	}

}
