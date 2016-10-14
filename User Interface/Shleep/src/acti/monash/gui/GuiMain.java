package acti.monash.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class GuiMain extends JPanel
{
	public static Color panelColor = new Color(255, 255, 255, 230);
	public static Color panelColorGrey = new Color(230, 230, 230, 230);
	public static Color panelColorLightGrey = new Color(240, 240, 240, 230);
	public static Color greenColor = new Color(50, 150, 0, 255);
	public static Color darkGreenColor = new Color(10, 120, 0, 255);
	public static Font smallTextFont = new Font("Segoe UI", Font.PLAIN, 13);
	public static Font smallTextFontBold = new Font("Segoe UI", Font.BOLD, 13);
	public static Font largeTitleFont = new Font("Segoe UI", Font.PLAIN, 25);

	public GuiButton[] tabButtons = new GuiButton[3];
	public JPanel[] tabPanels = new JPanel[3];

	public JPanel mainGraphPanel;

	public static GuiMain instance;

	public GuiMain()
	{
		instance = this;
		this.setOpaque(false);
		this.setLayout(new BorderLayout(0, 0));

		JMenuBar menuBar = new JMenuBar();
		JMenu file = new JMenu("File");
		file.setMnemonic(KeyEvent.VK_F);
		menuBar.add(file);
		JMenu view = new JMenu("View");
		view.setMnemonic(KeyEvent.VK_F);
		menuBar.add(view);
		// GuiFrame.frame.setJMenuBar(menuBar);

		JPanel sidePanel = new JPanel();
		this.add(sidePanel, BorderLayout.WEST);
		sidePanel.setPreferredSize(new Dimension(220, 0));
		sidePanel.setMaximumSize(new Dimension(256, 0));
		sidePanel.setMinimumSize(new Dimension(256, 0));
		sidePanel.setOpaque(false);
		sidePanel.setLayout(new BorderLayout(0, 0));
		sidePanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		JPanel sidePanelInner = new JPanel();
		sidePanelInner.setOpaque(false);
		sidePanelInner.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		sidePanelInner.setBackground(panelColor);
		sidePanelInner.setLayout(new BorderLayout(0, 0));
		sidePanelInner.setBorder(BorderFactory.createMatteBorder(0, 0, 5, 0, darkGreenColor));
		sidePanel.add(sidePanelInner, BorderLayout.CENTER);

		JPanel overviewTitleHolder = new JPanel();
		overviewTitleHolder.setPreferredSize(new Dimension(sidePanel.getPreferredSize().width - 20, 50));
		overviewTitleHolder.setBackground(greenColor);
		overviewTitleHolder.setBorder(BorderFactory.createMatteBorder(0, 0, 5, 0, darkGreenColor));
		sidePanelInner.add(overviewTitleHolder, BorderLayout.NORTH);
		overviewTitleHolder.setLayout(new BorderLayout(0, 0));

		JLabel lblOverview = new JLabel("Overview");
		lblOverview.setHorizontalAlignment(SwingConstants.LEFT);
		lblOverview.setForeground(Color.WHITE);
		lblOverview.setFont(largeTitleFont);
		lblOverview.setPreferredSize(new Dimension(100, 40));
		lblOverview.setBorder(new EmptyBorder(0, 10, 0, 0));
		overviewTitleHolder.add(lblOverview, BorderLayout.CENTER);

		JScrollPane overviewInfoScroll = new JScrollPane();
		overviewInfoScroll.setOpaque(false);
		overviewInfoScroll.setBorder(null);
		overviewInfoScroll.getViewport().setOpaque(false);
		overviewInfoScroll.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
		sidePanelInner.add(overviewInfoScroll, BorderLayout.CENTER);

		JPanel overviewInfoPanel = new JPanel();
		overviewInfoScroll.setBackground(panelColor);
		overviewInfoPanel.setBorder(new EmptyBorder(10, 5, 0, 0));
		BoxLayout layout = new BoxLayout(overviewInfoPanel, BoxLayout.Y_AXIS);
		overviewInfoPanel.setLayout(new BorderLayout(0, 0));
		overviewInfoScroll.setViewportView(overviewInfoPanel);
		// this.setupOverviewPanel(overviewInfoPanel);

		JLabel label = new JLabel("<html>Information about our graphs will go here, so that the users know what the graphs are showing.</html>");
		label.setVerticalAlignment(SwingConstants.TOP);
		label.setOpaque(false);
		label.setMinimumSize(new Dimension(170, 0));
		label.setPreferredSize(new Dimension(170, 0));
		label.setMaximumSize(new Dimension(170, 10000));
		label.setAlignmentY(Component.TOP_ALIGNMENT);
		overviewInfoPanel.add(label, BorderLayout.CENTER);

		JPanel overviewBottom = new JPanel();
		overviewBottom.setPreferredSize(new Dimension(0, 100));
		overviewBottom.setBackground(panelColorGrey);
		BoxLayout layout2 = new BoxLayout(overviewBottom, BoxLayout.Y_AXIS);
		overviewBottom.setLayout(layout2);
		overviewBottom.setBorder(new EmptyBorder(16, 0, 0, 0));
		sidePanelInner.add(overviewBottom, BorderLayout.SOUTH);

		GuiButton importButton = new GuiButton()
		{
			@Override
			public void onClick()
			{
				super.onClick();
				int returnVal = GuiImport.instance.dataChooser.showOpenDialog(GuiMain.this);
				if (returnVal == JFileChooser.APPROVE_OPTION)
				{
					File file = GuiImport.instance.dataChooser.getSelectedFile();
					GuiImport.instance.pythonRunner.execute();
					GuiImport.copyFile(file);
					GuiFrame.frame.clear();
					GuiFrame.frame.getContentPane().add(new GuiMain());
					GuiFrame.frame.revalidate();
					GuiFrame.frame.repaint();
				}
			}
		};
		importButton.setOpaque(false);
		importButton.setMaximumSize(new Dimension(128, 32));
		importButton.setPreferredSize(new Dimension(128, 32));
		importButton.setRounded(greenColor);
		importButton.setText("Import Data", smallTextFont, Color.WHITE);
		overviewBottom.add(importButton);

		Component verticalGlue = Box.createVerticalGlue();
		verticalGlue.setMaximumSize(new Dimension(0, 5));
		overviewBottom.add(verticalGlue);

		GuiButton exportButton = new GuiButton();
		exportButton.setOpaque(false);
		exportButton.setMaximumSize(new Dimension(128, 32));
		exportButton.setPreferredSize(new Dimension(128, 32));
		exportButton.setRounded(greenColor);
		exportButton.setText("Export Data", smallTextFont, Color.WHITE);
		overviewBottom.add(exportButton);

		JPanel panel_2 = new JPanel();
		panel_2.setLayout(new BorderLayout(0, 0));
		panel_2.setOpaque(false);
		panel_2.setBorder(new EmptyBorder(10, 0, 10, 10));
		this.add(panel_2, BorderLayout.CENTER);

		JPanel tabPanel = new JPanel();
		tabPanel.setOpaque(false);
		tabPanel.setPreferredSize(new Dimension(0, 50));
		tabPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 5, 0, darkGreenColor));
		tabPanel.setLayout(new BorderLayout(0, 0));
		panel_2.add(tabPanel, BorderLayout.NORTH);

		JPanel tabs = new JPanel();
		tabs.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		tabs.setOpaque(false);
		tabs.setPreferredSize(new Dimension(0, 30));
		tabPanel.add(tabs, BorderLayout.NORTH);

		for (int i = 0; i < this.tabButtons.length; i++)
		{
			final int tabID = i;
			this.tabButtons[i] = new GuiButton()
			{
				@Override
				public void onClick()
				{
					super.onClick();
					GuiMain.this.setTab(tabID);
				}
			};
			this.tabButtons[i].setOpaque(true);
			this.tabButtons[i].setDimension(new Dimension(100, 25));
		}
		this.tabButtons[0].setText("General Data", smallTextFont, Color.WHITE);
		this.tabButtons[1].setText("Misc Data", smallTextFont, Color.WHITE);
		this.tabButtons[2].setText("Raw Data", smallTextFont, Color.WHITE);
		tabs.add(this.tabButtons[0]);
		tabs.add(this.tabButtons[1]);

		JPanel tabBar = new JPanel();
		tabBar.setBackground(greenColor);
		tabBar.setPreferredSize(new Dimension(0, 20));
		tabPanel.add(tabBar, BorderLayout.SOUTH);

		JPanel mainGraphPanelContainer = new JPanel();
		mainGraphPanelContainer.setBackground(panelColor);
		mainGraphPanelContainer.setOpaque(false);
		panel_2.add(mainGraphPanelContainer, BorderLayout.CENTER);
		mainGraphPanelContainer.setLayout(new BorderLayout(0, 0));

		this.mainGraphPanel = new JPanel();
		mainGraphPanelContainer.add(this.mainGraphPanel, BorderLayout.CENTER);
		this.mainGraphPanel.setOpaque(false);
		this.mainGraphPanel.setLayout(new BorderLayout(0, 0));

		JPanel[] scrollPaneViews = new JPanel[3];
		for (int i = 0; i < this.tabPanels.length; i++)
		{
			this.tabPanels[i] = new JPanel();
			this.tabPanels[i].setOpaque(false);
			this.tabPanels[i].setBackground(panelColor);
			this.tabPanels[i].setLayout(new BorderLayout(0, 0));

			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setOpaque(false);
			scrollPane.setBorder(null);
			scrollPane.getViewport().setOpaque(false);
			scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			scrollPane.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
			scrollPane.getVerticalScrollBar().setUnitIncrement(10);
			this.tabPanels[i].add(scrollPane, BorderLayout.CENTER);

			scrollPaneViews[i] = new JPanel();
			scrollPaneViews[i].setBackground(panelColor);
			BoxLayout boxLayout = new BoxLayout(scrollPaneViews[i], BoxLayout.Y_AXIS);
			scrollPaneViews[i].setLayout(boxLayout);
			scrollPane.setViewportView(scrollPaneViews[i]);
		}

		JPanel graphPanelBottom = new JPanel();
		graphPanelBottom.setLayout(new BorderLayout(0, 0));
		graphPanelBottom.setBackground(panelColorGrey);
		graphPanelBottom.setPreferredSize(new Dimension(0, 5));
		graphPanelBottom.setBorder(BorderFactory.createMatteBorder(0, 0, 5, 0, darkGreenColor));
		mainGraphPanelContainer.add(graphPanelBottom, BorderLayout.SOUTH);

		this.graphBoxes = new ArrayList<GuiGraphBox>();
		this.addGraphBoxes(scrollPaneViews[0], 10);
		scrollPaneViews[1].add(new GuiGraphBox("White Count Asleep", GraphImages.whiteCountAsleep));
		scrollPaneViews[1].add(new GuiGraphBox("White Probability", GraphImages.whiteProbability));
		scrollPaneViews[1].add(new GuiGraphBox("Red Count Asleep", GraphImages.redCountAsleep));
		scrollPaneViews[1].add(new GuiGraphBox("Red Probability", GraphImages.redProbability));
		scrollPaneViews[1].add(new GuiGraphBox("Green Count Asleep", GraphImages.greenCountAsleep));
		scrollPaneViews[1].add(new GuiGraphBox("Green Probability", GraphImages.greenProbability));
		scrollPaneViews[1].add(new GuiGraphBox("Blue Count Asleep", GraphImages.blueCountAsleep));
		scrollPaneViews[1].add(new GuiGraphBox("Blue Probability", GraphImages.blueProbability));
		this.setTab(0);
	}

	private ArrayList<GuiGraphBox> graphBoxes;

	public void addGraphBoxes(JComponent component, int count)
	{
		for (int i = 0; i < count; i++)
		{
			GuiGraphBox graphBox = new GuiGraphBox("Day " + (i + 1), null);
			component.add(graphBox);
			this.graphBoxes.add(graphBox);
		}
	}

	public void addMiscGraphBox(JComponent component, String title, BufferedImage image)
	{

	}

	public void setTab(int tab)
	{
		this.mainGraphPanel.removeAll();
		this.mainGraphPanel.setLayout(new BorderLayout(0, 0));

		for (int i = 0; i < this.tabButtons.length; i++)
		{
			if (i == tab)
			{
				this.tabButtons[i].setBackground(greenColor);
				this.mainGraphPanel.add(this.tabPanels[i], BorderLayout.CENTER);
				this.tabPanels[i].revalidate();
				this.tabPanels[i].repaint();
			}
			else
			{
				this.tabButtons[i].setBackground(darkGreenColor);
			}
		}
		this.mainGraphPanel.revalidate();
		this.mainGraphPanel.repaint();
	}

	public void setupOverviewPanel(JComponent container)
	{
		container.add(this.new OverviewInfoPanel("Name: ", container).setText("John Smith"));
		container.add(this.new OverviewInfoPanel("Age: ", container).setText("24"));
		container.add(this.new OverviewInfoPanel("Something Random: ", container).setText("Pizza"));
		container.add(this.new OverviewInfoPanel("More Random: ", container).setText("42"));
		container.add(this.new OverviewInfoPanel("Much Random: ", container).setText("Wolves"));
		container.add(this.new OverviewInfoPanel("So Data: ", container).setText("2748.9783"));
		container.add(this.new OverviewInfoPanel("Such Shleep: ", container).setText("Zzz"));

	}

	public class OverviewInfoPanel extends JPanel
	{
		private JLabel titleLabel;
		private JLabel textLabel;

		public OverviewInfoPanel(String title, JComponent container)
		{
			this.setBorder(new EmptyBorder(5, 5, 0, 0));
			this.setMaximumSize(new Dimension(1000, 22));
			this.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
			this.setOpaque(false);
			this.titleLabel = new JLabel(title);
			this.titleLabel.setFont(smallTextFontBold);
			this.textLabel = new JLabel();
			this.textLabel.setFont(smallTextFont);
			this.add(this.titleLabel);
			this.add(this.textLabel);
		}

		public OverviewInfoPanel setText(String text)
		{
			this.textLabel.setText(text);
			return this;
		}
	}
}
