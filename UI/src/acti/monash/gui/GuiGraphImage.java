package acti.monash.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class GuiGraphImage extends JPanel
{
	private Image graphImage;

	public GuiGraphImage(Image image)
	{
		this.setBackground(Color.WHITE);
		this.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, GuiMain.panelColorGrey));
		this.setImage(image);
	}

	public void setImage(Image image)
	{
		this.graphImage = image;
		if (image != null)
		{
			this.setPreferredSize(new Dimension(image.getWidth(null), image.getHeight(null)));
			this.setMaximumSize(new Dimension(image.getWidth(null), image.getHeight(null)));
			this.setMinimumSize(new Dimension(image.getWidth(null), image.getHeight(null)));
		}
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		if (this.graphImage == null) super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;
		if (this.graphImage != null)
		{
			g2d.drawImage(this.graphImage, 0, 0, this);
		}
	}
}
