package acti.monash.gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class JRoundedPanel extends JPanel
{
	public JRoundedPanel()
	{
		super();
		this.setOpaque(false);
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		// super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(this.getBackground());
		g2d.fillRoundRect(0, 0, this.getPreferredSize().width - 1, this.getPreferredSize().height - 1, 10, 10);
	}
}
