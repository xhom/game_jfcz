package com.visy.view;

import java.awt.Graphics;
import javax.swing.JPanel;
import com.visy.controller.Controller;

public class DrawPanel extends JPanel{
	private static final long serialVersionUID = 1L;

	@Override
	public void paint(Graphics g) {
		Controller.getInstance().paint(g);
	}
}
