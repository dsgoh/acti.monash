package acti.monash.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class GuiImport extends JPanel
{
	private JPanel panel_1;
	public JFileChooser dataChooser;
	public PythonRunner pythonRunner;
	public static GuiImport instance;

	public static void launch()
	{
		EventQueue.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					GuiFrame.frame.setSize(new Dimension(750, 500));
					GuiFrame.frame.add(new GuiImport());
					GuiFrame.frame.setVisible(true);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	public GuiImport()
	{
		super();
		instance = this;
		this.setOpaque(false);
		this.setLayout(new BorderLayout(0, 0));
		this.panel_1 = new JPanel();
		this.panel_1.setPreferredSize(new Dimension(256, 420));
		this.panel_1.setMinimumSize(new Dimension(256, 420));
		this.panel_1.setMaximumSize(new Dimension(256, 420));
		this.panel_1.setBackground(new Color(255, 255, 255, 0));
		Box box = new Box(BoxLayout.Y_AXIS);
		box.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		box.add(Box.createVerticalGlue());
		box.add(this.panel_1);
		box.add(Box.createVerticalGlue());
		this.add(box);
		this.panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JPanel panelIcon = new JPanel()
		{
			@Override
			protected void paintComponent(Graphics g)
			{
				g.drawImage(GuiFrame.sheepIcon, 0, 0, this);
			}
		};
		panelIcon.setBackground(new Color(0, 0, 0, 0));
		panelIcon.setPreferredSize(new Dimension(256, 300));
		panelIcon.setMinimumSize(new Dimension(256, 300));
		panelIcon.setMaximumSize(new Dimension(256, 300));
		this.panel_1.add(panelIcon);

		this.dataChooser = new JFileChooser();
		this.pythonRunner = new PythonRunner();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("*.csv, *.txt", "csv", "txt");
		this.dataChooser.setFileFilter(filter);
		this.dataChooser.setDialogTitle("Import Data");
		this.dataChooser.setAcceptAllFileFilterUsed(false);
		GuiButton importButton = new GuiButton()
		{
			@Override
			public void mousePressed(MouseEvent e)
			{
				super.mousePressed(e);
				int returnVal = GuiImport.this.dataChooser.showOpenDialog(GuiImport.this);
				if (returnVal == JFileChooser.APPROVE_OPTION)
				{
					File file = GuiImport.this.dataChooser.getSelectedFile();
					GuiImport.copyFile(file);
					GuiImport.this.pythonRunner.execute();
					if (GuiImport.this.pythonRunner.isSuccessful)
					{
						GuiFrame.frame.clear();
						GuiFrame.frame.add(new GuiMain());
						GuiFrame.frame.revalidate();
						GuiFrame.frame.repaint();
					}
				}
			}
		};
		importButton.setDimension(new Dimension(this.panel_1.getPreferredSize().width, (int) (this.panel_1.getPreferredSize().width * ((float) (GuiFrame.importIcon.getHeight()) / GuiFrame.importIcon.getWidth()))));
		importButton.setImage(GuiFrame.importIcon);
		this.panel_1.add(importButton);
	}

	public static void copyFile(File file)
	{
		InputStream input = null;
		OutputStream output = null;

		try
		{

			input = new FileInputStream(file);
			// output = new FileOutputStream(new File("./data" +
			// file.getName().substring(file.getName().lastIndexOf("."),
			// file.getName().length())));
			output = new FileOutputStream(new File("./data.csv"));
			byte[] buf = new byte[1000000];

			int bytesRead;
			while ((bytesRead = input.read(buf)) > 0)
			{
				output.write(buf, 0, bytesRead);
			}
			input.close();
			output.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}
}
