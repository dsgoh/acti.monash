package acti.monash.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class GuiViewGraph extends JPanel
{
	private GuiFrame frame;

	public GuiViewGraph(String title, Image graphImage)
	{
		GuiGraphBox graphBox = new GuiGraphBox(title, graphImage, false, false);
		this.frame = new GuiFrame();
		Dimension frameSize = new Dimension(graphBox.getPreferredSize().width + 25, graphBox.getPreferredSize().height + 74);
		this.frame.setMinimumSize(frameSize);
		this.frame.setSize(frameSize);
		this.frame.setPreferredSize(frameSize);
		this.frame.setResizable(false);

		this.setLayout(new BorderLayout(0, 0));
		this.setBorder(new EmptyBorder(10, 10, 10, 10));
		this.setOpaque(false);
		this.frame.addToFrame(this);

		JPanel panel = new JPanel();
		panel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		panel.setBackground(GuiMain.panelColor);
		panel.setLayout(new BorderLayout(0, 0));
		panel.setBorder(BorderFactory.createMatteBorder(0, 0, 5, 0, GuiMain.darkGreenColor));
		this.add(panel, BorderLayout.CENTER);
		panel.add(graphBox, BorderLayout.CENTER);
		this.add(panel);

		JPanel overviewTitleHolder = new JPanel();
		overviewTitleHolder.setPreferredSize(new Dimension(200, 50));
		overviewTitleHolder.setBackground(GuiMain.greenColor);
		overviewTitleHolder.setBorder(BorderFactory.createMatteBorder(0, 0, 5, 0, GuiMain.darkGreenColor));
		panel.add(overviewTitleHolder, BorderLayout.NORTH);
		overviewTitleHolder.setLayout(new BorderLayout(0, 0));

		JLabel lblOverview = new JLabel(title);
		lblOverview.setHorizontalAlignment(SwingConstants.LEFT);
		lblOverview.setForeground(Color.WHITE);
		lblOverview.setFont(GuiMain.largeTitleFont);
		lblOverview.setPreferredSize(new Dimension(100, 40));
		lblOverview.setBorder(new EmptyBorder(0, 10, 0, 0));
		overviewTitleHolder.add(lblOverview, BorderLayout.CENTER);

		this.revalidate();
		this.repaint();
	}

	public void showWindow()
	{
		this.frame.setVisible(true);
	}
}
