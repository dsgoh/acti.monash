package acti.monash.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

public class GuiGraphBox extends JPanel
{
	private JLabel titleLabel;
	public GuiGraphImage[] images = new GuiGraphImage[3];
	public GuiGraphImage image;

	public JPanel graphPanel;

	public GuiGraphBox(String title, Image graphImage)
	{
		this.setOpaque(false);
		this.setLayout(new BorderLayout(0, 0));
		// this.setAlignmentX(Component.LEFT_ALIGNMENT);
		this.setBorder(new EmptyBorder(10, 10, 10, 10));

		JPanel top = new JPanel();
		this.add(top, BorderLayout.NORTH);
		top.setOpaque(false);
		top.setPreferredSize(new Dimension(0, 25));
		top.setLayout(new BorderLayout(0, 0));

		this.titleLabel = new JLabel("");
		this.titleLabel.setForeground(Color.BLACK);
		this.titleLabel.setBorder(new EmptyBorder(10, 0, 10, 0));
		top.add(this.titleLabel, BorderLayout.WEST);

		JPanel main = new JPanel();
		main.setOpaque(false);
		this.add(main, BorderLayout.CENTER);
		main.setLayout(new BorderLayout(0, 0));

		JPanel graphContainer = new JPanel();
		graphContainer.setOpaque(false);
		main.add(graphContainer, BorderLayout.CENTER);
		graphContainer.setLayout(new BorderLayout(0, 0));

		JScrollPane graphScrollPane = new JScrollPane();
		graphScrollPane.setOpaque(false);
		graphScrollPane.getViewport().setOpaque(false);
		graphScrollPane.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, GuiMain.panelColorGrey));
		graphScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		graphScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		graphScrollPane.setWheelScrollingEnabled(false);
		graphScrollPane.removeMouseWheelListener(graphScrollPane.getMouseWheelListeners()[0]);
		graphContainer.add(graphScrollPane, BorderLayout.CENTER);

		this.graphPanel = new JPanel();
		this.graphPanel.setLayout(new BorderLayout(0, 0));
		this.graphPanel.setBackground(Color.WHITE);
		graphScrollPane.setViewportView(this.graphPanel);
		graphScrollPane.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);

		this.image = new GuiGraphImage(graphImage);
		this.graphPanel.add(this.image, BorderLayout.CENTER);

		if (graphImage != null)
		{
			int width = this.image.getPreferredSize().width + 30;
			int height = this.image.getPreferredSize().height + 40 + top.getPreferredSize().height;
			this.setPreferredSize(new Dimension(0, height));
			this.setMaximumSize(new Dimension(width, height));
			this.setMinimumSize(new Dimension(width, height));
		}
		else this.setPreferredSize(new Dimension(0, 200));

		boolean showButtons = false;
		JPanel graphButtons = new JPanel();
		graphButtons.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
		graphButtons.setBackground(GuiMain.panelColorGrey);
		graphButtons.setOpaque(false);
		if (showButtons) main.add(graphButtons, BorderLayout.SOUTH);
		graphButtons.setBorder(new EmptyBorder(5, -5, 0, 0));
		graphButtons.setPreferredSize(new Dimension(0, 30));

		JComboBox viewComboBox = new JComboBox(new String[] {"Light Data", "Other Data", "Other Data 2"})
		{
			@Override
			public void setSelectedIndex(int anIndex)
			{
				super.setSelectedIndex(anIndex);
				GuiGraphBox.this.setImageInView(anIndex);
			}
		};
		viewComboBox.setPreferredSize(new Dimension(90, 23));
		viewComboBox.setFocusable(false);
		graphButtons.add(viewComboBox);

		JButton buttonViewAll = new JButton();
		buttonViewAll.setPreferredSize(new Dimension(75, 25));
		buttonViewAll.setText("View All");
		buttonViewAll.setFocusable(false);

		// buttonViewAll.setDimension(new Dimension(75, 25));
		// buttonViewAll.setRounded(GuiMain.greenColor);
		// buttonViewAll.setText("View All", GuiMain.smallTextFont,
		// Color.WHITE);
		graphButtons.add(buttonViewAll);

		this.setTitle(title, GuiMain.smallTextFontBold);

		this.setImageInView(0);
	}

	public void setImageInView(int id)
	{
		// this.graphPanel.removeAll();
		// if (this.images[id] != null) this.graphPanel.add(this.images[id]);
	}

	public void setTitle(String text, Font font)
	{
		this.titleLabel.setText(text);
		this.titleLabel.setFont(font);
	}
}
