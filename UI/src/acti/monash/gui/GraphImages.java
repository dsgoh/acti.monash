package acti.monash.gui;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class GraphImages
{
	public static ArrayList<BufferedImage> dailyData1 = new ArrayList<BufferedImage>();

	public static Image whiteCountAsleep;
	public static Image whiteProbability;
	public static Image redCountAsleep;
	public static Image redProbability;
	public static Image greenCountAsleep;
	public static Image greenProbability;
	public static Image blueCountAsleep;
	public static Image blueProbability;

	public static String summaryGeneral = "";
	public static String summaryMisc = "";
	public static String details = "";

	public static void load()
	{
		try
		{
			int miscGraphWidth = (int) (800 * 0.6);
			int miscGraphHeight = (int) (600 * 0.6);
			whiteCountAsleep = ImageIO.read(new File(PythonRunner.pythonLocation + "White Count_Asleep.png")).getScaledInstance(miscGraphWidth, miscGraphHeight, Image.SCALE_SMOOTH);
			whiteProbability = ImageIO.read(new File(PythonRunner.pythonLocation + "White Probability.png")).getScaledInstance(miscGraphWidth, miscGraphHeight, Image.SCALE_SMOOTH);
			redCountAsleep = ImageIO.read(new File(PythonRunner.pythonLocation + "Red Count_Asleep.png")).getScaledInstance(miscGraphWidth, miscGraphHeight, Image.SCALE_SMOOTH);
			redProbability = ImageIO.read(new File(PythonRunner.pythonLocation + "Red Probability.png")).getScaledInstance(miscGraphWidth, miscGraphHeight, Image.SCALE_SMOOTH);
			greenCountAsleep = ImageIO.read(new File(PythonRunner.pythonLocation + "Green Count_Asleep.png")).getScaledInstance(miscGraphWidth, miscGraphHeight, Image.SCALE_SMOOTH);
			greenProbability = ImageIO.read(new File(PythonRunner.pythonLocation + "Green Probability.png")).getScaledInstance(miscGraphWidth, miscGraphHeight, Image.SCALE_SMOOTH);
			blueCountAsleep = ImageIO.read(new File(PythonRunner.pythonLocation + "Blue Count_Asleep.png")).getScaledInstance(miscGraphWidth, miscGraphHeight, Image.SCALE_SMOOTH);
			blueProbability = ImageIO.read(new File(PythonRunner.pythonLocation + "Blue Probability.png")).getScaledInstance(miscGraphWidth, miscGraphHeight, Image.SCALE_SMOOTH);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		try
		{
			BufferedReader br = new BufferedReader(new FileReader(new File("resources/" + "summaryMisc.txt")));
			String line;
			while ((line = br.readLine()) != null)
				summaryMisc += line;
			summaryMisc = "<html>" + summaryMisc + "</html>";
			br.close();

			br = new BufferedReader(new FileReader(new File("resources/" + "summaryGeneral.txt")));
			while ((line = br.readLine()) != null)
				summaryGeneral += line;
			summaryGeneral = "<html>" + summaryGeneral + "</html>";
			br.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		try
		{
			BufferedReader br = new BufferedReader(new FileReader(new File(PythonRunner.pythonLocation + "detail.csv")));
			String line;
			while ((line = br.readLine()) != null)
			{
				String title = "<b>" + line.split(":")[0] + ":" + "</b>";
				String info = line.substring(line.indexOf(":") + 1, line.length()) + "<br>";
				details += (title + info);
			}
			details = "<html>" + details + "</html>";
			br.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
