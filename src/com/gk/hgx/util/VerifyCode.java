package com.gk.hgx.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Component;

@Component
public class VerifyCode {
	private int w = 70;
	private int h = 35;
 	private Random r = new Random();
 	// {"瀹嬩�?", "鍗庢枃妤蜂綋", "榛戜�?", "鍗庢枃鏂伴瓘", "鍗庢枃闅朵功", "寰蒋闆呴粦", "妤蜂綋_GB2312"}
	private String[] fontNames  = {"瀹嬩�?", "鍗庢枃妤蜂綋", "榛戜�?", "寰蒋闆呴粦", "妤蜂綋_GB2312"};
	private String codes  = "23456789abcdefghjkmnopqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ";
	private Color bgColor  = new Color(255, 255, 255);
	private String text ;
	
	private Color randomColor () {
		int red = r.nextInt(150);
		int green = r.nextInt(150);
		int blue = r.nextInt(150);
		return new Color(red, green, blue);
	}
	
	private Font randomFont () {
		int index = r.nextInt(fontNames.length);
		String fontName = fontNames[index];
		int style = r.nextInt(4);
		int size = r.nextInt(5) + 24; 
		return new Font(fontName, style, size);
	}
	
	private void drawLine (BufferedImage image) {
		int num  = 3;
		Graphics2D g2 = (Graphics2D)image.getGraphics();
		for(int i = 0; i < num; i++) {
			int x1 = r.nextInt(w);
			int y1 = r.nextInt(h);
			int x2 = r.nextInt(w);
			int y2 = r.nextInt(h); 
			g2.setStroke(new BasicStroke(1.5F)); 
			g2.setColor(Color.BLUE); 
			g2.drawLine(x1, y1, x2, y2);
		}
	}
	
	private char randomChar () {
		int index = r.nextInt(codes.length());
		return codes.charAt(index);
	}
	
	private BufferedImage createImage () {
		BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB); 
		Graphics2D g2 = (Graphics2D)image.getGraphics(); 
		g2.setColor(this.bgColor);
		g2.fillRect(0, 0, w, h);
 		return image;
	}
	
	public BufferedImage getImage () {
		BufferedImage image = createImage(); 
		Graphics2D g2 = (Graphics2D)image.getGraphics();
		StringBuilder sb = new StringBuilder();
		// 鍚戝浘鐗囦腑鐢�4涓瓧绗�?
		for(int i = 0; i < 4; i++)  {
			String s = randomChar() + ""; 
			sb.append(s); 
			float x = i * 1.0F * w / 4; 
			g2.setFont(randomFont()); 
			g2.setColor(randomColor()); 
			g2.drawString(s, x, h-5); 
		}
		this.text = sb.toString(); 
		drawLine(image); 
		return image;		
	}
	
	public String getText () {
		return text;
	}
	
	public static void output (BufferedImage image, OutputStream out) 
				throws IOException {
		ImageIO.write(image, "JPEG", out);
	}
}


