package acti.monash.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class GuiGraphGroup extends JPanel
{
	public GuiGraphGroup(String title, GuiGraphImage[] images)
	{
		this.setLayout(new BorderLayout(0, 0));
		this.setBorder(new EmptyBorder(10, 10, 10, 10));
		this.setOpaque(false);

		JPanel sidePanelInner = new JPanel();
		// sidePanelInner.setOpaque(false);
		sidePanelInner.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		sidePanelInner.setBackground(GuiMain.panelColor);
		sidePanelInner.setLayout(new BorderLayout(0, 0));
		sidePanelInner.setBorder(BorderFactory.createMatteBorder(0, 0, 5, 0, GuiMain.darkGreenColor));
		this.add(sidePanelInner, BorderLayout.CENTER);

		JPanel overviewTitleHolder = new JPanel();
		overviewTitleHolder.setPreferredSize(new Dimension(200, 50));
		overviewTitleHolder.setBackground(GuiMain.greenColor);
		overviewTitleHolder.setBorder(BorderFactory.createMatteBorder(0, 0, 5, 0, GuiMain.darkGreenColor));
		sidePanelInner.add(overviewTitleHolder, BorderLayout.NORTH);
		overviewTitleHolder.setLayout(new BorderLayout(0, 0));

		JLabel lblOverview = new JLabel(title);
		lblOverview.setHorizontalAlignment(SwingConstants.LEFT);
		lblOverview.setForeground(Color.WHITE);
		lblOverview.setFont(GuiMain.largeTitleFont);
		lblOverview.setPreferredSize(new Dimension(100, 40));
		lblOverview.setBorder(new EmptyBorder(0, 10, 0, 0));
		overviewTitleHolder.add(lblOverview, BorderLayout.CENTER);
	}
}
