package acti.monash.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class GuiFrame extends JFrame
{
	public static GuiFrame frame;

	private JPanel contentPane;
	private JPanel mainPanel;

	private static BufferedImage sheepBackground;
	private Image sheepBackgroundScaled;
	public static BufferedImage sheepIcon;
	public static BufferedImage importIcon;
	public static BufferedImage importButton;
	public static BufferedImage exportButton;

	public static void loadImages()
	{
		try
		{
			sheepBackground = ImageIO.read(new File("resources/background.png"));
			sheepIcon = ImageIO.read(new File("resources/title_icon.png"));
			importIcon = ImageIO.read(new File("resources/import_icon.png"));
			importButton = ImageIO.read(new File("resources/import_button.png"));
			exportButton = ImageIO.read(new File("resources/export_button.png"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public GuiFrame()
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		this.setTitle("Project Shleep");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(100, 100, 450, 300);
		this.contentPane = new JPanel();
		this.contentPane.setBorder(null);
		this.contentPane.setLayout(new BorderLayout(0, 0));
		this.setContentPane(this.contentPane);

		this.sheepBackgroundScaled = sheepBackground.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH);
		this.addComponentListener(new ComponentAdapter()
		{
			@Override
			public void componentResized(ComponentEvent e)
			{
				if (GuiFrame.this.getWidth() * ((float) (sheepBackground.getHeight()) / sheepBackground.getWidth()) < GuiFrame.this.getHeight())
				{
					GuiFrame.this.sheepBackgroundScaled = sheepBackground.getScaledInstance((int) (GuiFrame.this.getHeight() * ((float) (sheepBackground.getWidth()) / sheepBackground.getHeight())), GuiFrame.this.getHeight(), Image.SCALE_SMOOTH);
				}
				else GuiFrame.this.sheepBackgroundScaled = sheepBackground.getScaledInstance(GuiFrame.this.getWidth(), (int) (GuiFrame.this.getWidth() * ((float) (sheepBackground.getHeight()) / sheepBackground.getWidth())), Image.SCALE_SMOOTH);
			}
		});

		this.mainPanel = new JPanel()
		{
			@Override
			protected void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				g.drawImage(GuiFrame.this.sheepBackgroundScaled, 0, 0, this);
			}
		};
		this.mainPanel.setBackground(Color.BLACK);

		this.contentPane.add(this.mainPanel, BorderLayout.CENTER);
		this.mainPanel.setLayout(new BorderLayout(0, 0));
	}

	@Override
	public Component add(Component component)
	{
		return this.mainPanel.add(component);
	}

	public void clear()
	{
		this.mainPanel.removeAll();
		this.mainPanel.setLayout(new BorderLayout(0, 0));
	}
}
