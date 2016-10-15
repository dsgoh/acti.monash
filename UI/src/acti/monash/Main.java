package acti.monash;

import acti.monash.gui.GuiFrame;
import acti.monash.gui.GuiImport;

public class Main
{
	public static void main(String[] args)
	{
		GuiFrame.loadImages();
		// GraphImages.load();
		GuiFrame.frame = new GuiFrame();
		GuiImport.launch();
	}
}
