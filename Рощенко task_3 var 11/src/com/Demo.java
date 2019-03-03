package com;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;

import javax.swing.JApplet;

public class Demo extends JApplet {
	private static final long serialVersionUID = 1L;
	private static final int CX = 600, CY = 600;

	public void init() {
		System.out.println("init(): begin");
		setSize(CX, CY);
		setLayout(null);
		System.out.println("init(): end");
	}

	public void paint(Graphics g) {
		System.out.println("paint(): begin");
		Graphics2D g2d = (Graphics2D)g;
		Shape temp = new Quadratrix(200, 0, 0, 1, 1);
		g2d.translate(CX/2, CY/2);
		Stroke stroke = new QStroke(2, 10, 10);
		g2d.setColor(Color.RED);
		g2d.setStroke(stroke);
		g2d.draw(temp);

	}


}