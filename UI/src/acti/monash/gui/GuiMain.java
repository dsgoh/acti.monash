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
	public static Color panelColor = new Color(255, 255, 255, 210);
	public static Color panelColorGrey = new Color(230, 230, 230, 210);
	public static Color panelColorLightGrey = new Color(240, 240, 240, 230);
	public static Color greenColor = new Color(50, 150, 0, 255);
	public static Color darkGreenColor = new Color(10, 120, 0, 255);
	public static Font smallTextFont = new Font("Segoe UI", Font.PLAIN, 13);
	public static Font smallTextFontBold = new Font("Segoe UI", Font.BOLD, 13);
	public static Font largeTitleFont = new Font("Segoe UI", Font.PLAIN, 25);

	public GuiButton[] tabButtons = new GuiButton[3];
	public JPanel[] tabPanels = new JPanel[3];

	public JPanel mainGraphPanel;
	public JPanel graphContainerGeneral;
	public JPanel graphContainerMisc;
	public JPanel[] scrollPaneViews;
	public JLabel summaryLabel;
	public JLabel detailsLabel;
	public JPanel sidePanel;
	public JScrollPane overviewInfoScroll;

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

		this.sidePanel = new JPanel();
		this.add(this.sidePanel, BorderLayout.WEST);
		this.sidePanel.setPreferredSize(new Dimension(250, 0));
		this.sidePanel.setMaximumSize(new Dimension(270, 0));
		this.sidePanel.setMinimumSize(new Dimension(270, 0));
		this.sidePanel.setOpaque(false);
		this.sidePanel.setLayout(new BorderLayout(0, 0));
		this.sidePanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		JPanel sidePanelInner = new JPanel();
		sidePanelInner.setOpaque(false);
		sidePanelInner.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		sidePanelInner.setBackground(panelColor);
		sidePanelInner.setLayout(new BorderLayout(0, 0));
		sidePanelInner.setBorder(BorderFactory.createMatteBorder(0, 0, 5, 0, darkGreenColor));
		this.sidePanel.add(sidePanelInner, BorderLayout.CENTER);

		JPanel overviewTitleHolder = new JPanel();
		overviewTitleHolder.setPreferredSize(new Dimension(this.sidePanel.getPreferredSize().width - 20, 50));
		overviewTitleHolder.setBackground(greenColor);
		overviewTitleHolder.setBorder(BorderFactory.createMatteBorder(0, 0, 5, 0, darkGreenColor));
		sidePanelInner.add(overviewTitleHolder, BorderLayout.NORTH);
		overviewTitleHolder.setLayout(new BorderLayout(0, 0));

		JLabel lblOverview = new JLabel("Summary");
		lblOverview.setHorizontalAlignment(SwingConstants.LEFT);
		lblOverview.setForeground(Color.WHITE);
		lblOverview.setFont(largeTitleFont);
		lblOverview.setPreferredSize(new Dimension(100, 40));
		lblOverview.setBorder(new EmptyBorder(0, 10, 0, 0));
		overviewTitleHolder.add(lblOverview, BorderLayout.CENTER);

		this.overviewInfoScroll = new JScrollPane();
		this.overviewInfoScroll.setOpaque(false);
		this.overviewInfoScroll.setBorder(null);
		this.overviewInfoScroll.getViewport().setOpaque(false);
		this.overviewInfoScroll.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
		this.overviewInfoScroll.getHorizontalScrollBar().setVisibleAmount(500);
		sidePanelInner.add(this.overviewInfoScroll, BorderLayout.CENTER);

		JPanel overviewInfoPanel = new JPanel();
		overviewInfoPanel.setBackground(panelColor);
		overviewInfoPanel.setBorder(new EmptyBorder(10, 10, 5, 10));
		BoxLayout layout = new BoxLayout(overviewInfoPanel, BoxLayout.Y_AXIS);
		overviewInfoPanel.setLayout(new BorderLayout(0, 0));
		this.overviewInfoScroll.setViewportView(overviewInfoPanel);
		// this.setupOverviewPanel(overviewInfoPanel);

		JPanel labelPanel = new JPanel();
		labelPanel.setOpaque(false);
		labelPanel.setLayout(new BorderLayout(0, 0));
		labelPanel.setBackground(Color.red);
		overviewInfoPanel.add(labelPanel, BorderLayout.WEST);

		this.summaryLabel = new JLabel(GraphImages.summaryGeneral);
		this.summaryLabel.setOpaque(false);
		this.summaryLabel.setAlignmentY(Component.TOP_ALIGNMENT);
		this.summaryLabel.setVerticalAlignment(SwingConstants.TOP);

		labelPanel.add(this.summaryLabel, BorderLayout.WEST);

		JPanel overviewBottom = new JPanel();
		overviewBottom.setPreferredSize(new Dimension(0, 65));
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

					if (GuiImport.instance.pythonRunner.isSuccessful)
					{
						GuiFrame.frame.clear();
						GuiFrame.frame.addToFrame(new GuiMain());
						GuiFrame.frame.revalidate();
						GuiFrame.frame.paintAll(GuiFrame.frame.getGraphics());
					}
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
		// overviewBottom.add(exportButton);

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
		this.tabButtons[0].setText("Data by Day", smallTextFont, Color.WHITE);
		this.tabButtons[1].setText("Light Data", smallTextFont, Color.WHITE);
		this.tabButtons[2].setText("Details", smallTextFont, Color.WHITE);
		tabs.add(this.tabButtons[0]);
		tabs.add(this.tabButtons[1]);
		tabs.add(this.tabButtons[2]);

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
		this.mainGraphPanel.setOpaque(false);
		mainGraphPanelContainer.add(this.mainGraphPanel, BorderLayout.CENTER);
		// this.mainGraphPanel.setOpaque(false);
		this.mainGraphPanel.setLayout(new BorderLayout(0, 0));

		this.scrollPaneViews = new JPanel[3];
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
			scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			scrollPane.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
			scrollPane.getVerticalScrollBar().setUnitIncrement(10);

			JScrollPane scrollPane2 = new JScrollPane();
			scrollPane2.setOpaque(false);
			scrollPane2.setBorder(null);
			scrollPane2.getViewport().setOpaque(false);
			scrollPane2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
			scrollPane2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
			scrollPane2.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
			scrollPane2.getVerticalScrollBar().setUnitIncrement(10);
			scrollPane.add(scrollPane2);
			this.tabPanels[i].add(scrollPane, BorderLayout.CENTER);

			this.scrollPaneViews[i] = new JPanel();
			this.scrollPaneViews[i].setBackground(panelColor);
			BoxLayout boxLayout = new BoxLayout(this.scrollPaneViews[i], BoxLayout.Y_AXIS);
			this.scrollPaneViews[i].setLayout(new BorderLayout(0, 0));
			scrollPane.setViewportView(this.scrollPaneViews[i]);
		}

		JPanel graphPanelBottom = new JPanel();
		graphPanelBottom.setLayout(new BorderLayout(0, 0));
		graphPanelBottom.setBackground(panelColorGrey);
		graphPanelBottom.setPreferredSize(new Dimension(0, 5));
		graphPanelBottom.setBorder(BorderFactory.createMatteBorder(0, 0, 5, 0, darkGreenColor));
		mainGraphPanelContainer.add(graphPanelBottom, BorderLayout.SOUTH);

		this.graphContainerMisc = new JPanel();
		this.graphContainerMisc.setOpaque(false);
		this.graphContainerMisc.setLayout(new ModifiedFlowLayout(FlowLayout.LEFT, 0, 0));
		this.scrollPaneViews[1].add(this.graphContainerMisc);

		this.graphContainerGeneral = new JPanel();
		this.graphContainerGeneral.setOpaque(false);
		this.graphContainerGeneral.setLayout(new BoxLayout(this.graphContainerGeneral, BoxLayout.Y_AXIS));
		this.scrollPaneViews[0].add(this.graphContainerGeneral);

		this.graphBoxes = new ArrayList<GuiGraphBox>();
		// this.addGraphBoxes(this.scrollPaneViews[0], 10);
		GuiGraphBox test = new GuiGraphBox("White Count Asleep", GraphImages.whiteCountAsleep);
		test.setPreferredSize(new Dimension(100, 100));
		test.setMinimumSize(new Dimension(100, 100));
		test.setMaximumSize(new Dimension(100, 100));
		test.setBackground(Color.BLUE);
		this.graphContainerMisc.add(new GuiGraphBox("White Count Asleep", GraphImages.whiteCountAsleep));
		this.graphContainerMisc.add(new GuiGraphBox("White Probability", GraphImages.whiteProbability));
		this.graphContainerMisc.add(new GuiGraphBox("Red Count Asleep", GraphImages.redCountAsleep));
		this.graphContainerMisc.add(new GuiGraphBox("Red Probability", GraphImages.redProbability));
		this.graphContainerMisc.add(new GuiGraphBox("Green Count Asleep", GraphImages.greenCountAsleep));
		this.graphContainerMisc.add(new GuiGraphBox("Green Probability", GraphImages.greenProbability));
		this.graphContainerMisc.add(new GuiGraphBox("Blue Count Asleep", GraphImages.blueCountAsleep));
		this.graphContainerMisc.add(new GuiGraphBox("Blue Probability", GraphImages.blueProbability));

		for (int i = 0; i < GraphImages.dailyData.size(); i++)
		{
			this.graphContainerGeneral.add(new GuiGraphBox("Day " + (i + 1), GraphImages.dailyData.get(i), true, true));
		}

		this.detailsLabel = new JLabel(GraphImages.details);
		this.detailsLabel.setBorder(new EmptyBorder(10, 10, 10, 10));

		this.detailsLabel.setOpaque(false);
		this.detailsLabel.setAlignmentY(Component.TOP_ALIGNMENT);
		this.detailsLabel.setVerticalAlignment(SwingConstants.TOP);
		this.scrollPaneViews[2].add(this.detailsLabel, BorderLayout.WEST);

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
				GuiFrame.frame.revalidate();
				GuiFrame.frame.repaint();
			}
			else
			{
				this.tabButtons[i].setBackground(darkGreenColor);
			}
		}
		this.mainGraphPanel.revalidate();
		this.mainGraphPanel.repaint();

		if (tab == 0)
		{
			this.summaryLabel.setText(GraphImages.summaryGeneral);
			this.summaryLabel.setMinimumSize(new Dimension(190, 0));
			this.summaryLabel.setPreferredSize(new Dimension(190, 20));
			this.summaryLabel.setMaximumSize(new Dimension(190, 20));
		}
		if (tab == 1)
		{
			this.summaryLabel.setText(GraphImages.summaryMisc);
			this.summaryLabel.setMinimumSize(new Dimension(190, 0));
			this.summaryLabel.setPreferredSize(new Dimension(190, 1500));
			this.summaryLabel.setMaximumSize(new Dimension(190, 1500));
		}
		if (tab == 2)
		{
			this.summaryLabel.setText("<html>Details regarding the participant and collection of the data.</html>");
			this.summaryLabel.setMinimumSize(new Dimension(190, 0));
			this.summaryLabel.setPreferredSize(new Dimension(190, 0));
			this.summaryLabel.setMaximumSize(new Dimension(190, 0));
		}
		this.overviewInfoScroll.getVerticalScrollBar().setValue(0);
		this.overviewInfoScroll.revalidate();
		this.overviewInfoScroll.repaint();
		this.sidePanel.revalidate();
		this.summaryLabel.revalidate();
		this.summaryLabel.paintAll(this.summaryLabel.getGraphics());
		this.detailsLabel.paintAll(this.detailsLabel.getGraphics());
		// this.sidePanel.revalidate();
		// this.sidePanel.paintComponents(this.sidePanel.getGraphics());
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
