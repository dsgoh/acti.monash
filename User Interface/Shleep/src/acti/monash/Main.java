package acti.monash;

import acti.monash.gui.GraphImages;
import acti.monash.gui.GuiFrame;
import acti.monash.gui.GuiImport;

public class Main
{
	public static void main(String[] args)
	{
		GuiFrame.loadImages();
		GraphImages.load();
		GuiFrame.frame = new GuiFrame();

		// GuiFrame frame = new GuiFrame();
		// frame.setSize(new Dimension(750, 500));
		// frame.add(new GuiGraphGroup("View All | Day 1", null));
		// frame.setVisible(true);
		GuiImport.launch();
	}
}
