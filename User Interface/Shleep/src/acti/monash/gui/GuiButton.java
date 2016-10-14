package acti.monash.gui;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class GuiButton extends JPanel implements MouseListener
{
	private BufferedImage image;
	private Dimension dimension;

	private JLabel textLabel;

	private boolean mouseHovering = false;
	public boolean hoverEffectTranparancy = false;

	public GuiButton()
	{
		super();
		this.setLayout(new BorderLayout(0, 0));
		this.setBackground(new Color(0, 0, 0, 0));
		this.addMouseListener(this);
		this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		this.setBackground(GuiMain.panelColor);
		this.textLabel = new JLabel();
		this.textLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		this.textLabel.setHorizontalAlignment(SwingConstants.CENTER);
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		if (this.image == null) super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;
		if (this.isRounded)
		{
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setColor(this.backgroundColor);
			g2d.fillRoundRect(0, 0, this.getPreferredSize().width - 1, this.getPreferredSize().height - 1, 10, 10);
		}

		if (this.image != null)
		{
			if (this.mouseHovering && this.hoverEffectTranparancy) g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
			g2d.drawImage(this.image, 0, 0, this);
		}
	}

	public void setImage(BufferedImage image)
	{
		this.image = image;
	}

	public void setTransparant(boolean transparant)
	{
		if (transparant)
		{
			this.textLabel.setForeground(new Color(this.textLabel.getForeground().getRed(), this.textLabel.getForeground().getGreen(), this.textLabel.getForeground().getBlue(), 100));
			this.setBackground(new Color(this.getBackground().getRed(), this.getBackground().getGreen(), this.getBackground().getBlue(), 100));
			if (this.backgroundColor != null) this.backgroundColor = new Color(this.backgroundColor.getRed(), this.backgroundColor.getGreen(), this.backgroundColor.getBlue(), 100);
		}
		else
		{
			this.textLabel.setForeground(new Color(this.textLabel.getForeground().getRed(), this.textLabel.getForeground().getGreen(), this.textLabel.getForeground().getBlue(), 255));
			this.setBackground(new Color(this.getBackground().getRed(), this.getBackground().getGreen(), this.getBackground().getBlue(), 255));
			if (this.backgroundColor != null) this.backgroundColor = new Color(this.backgroundColor.getRed(), this.backgroundColor.getGreen(), this.backgroundColor.getBlue(), 255);

		}
	}

	public void setText(String text, Font font, Color color)
	{
		this.textLabel.setText(text);
		this.textLabel.setFont(font);
		this.textLabel.setForeground(color);
		this.add(this.textLabel, BorderLayout.CENTER);
	}

	private boolean isRounded = false;
	private Color backgroundColor;

	public void setRounded(Color color)
	{
		this.isRounded = true;
		this.backgroundColor = color;
	}

	public void setDimension(Dimension dimension)
	{
		this.setPreferredSize(dimension);
		this.setMinimumSize(dimension);
		this.setMaximumSize(dimension);
		this.dimension = dimension;
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{

	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
		this.mouseHovering = true;
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		this.mouseHovering = false;
	}

	@Override
	public void mousePressed(MouseEvent e)
	{

	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		this.onClick();
	}

	public void onClick()
	{

	}
}
