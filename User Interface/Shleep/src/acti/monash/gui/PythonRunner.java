package acti.monash.gui;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

public class PythonRunner
{
	public static String pythonLocation = "python/";
	public boolean isSuccessful = true;
	private static boolean donePythonLoading = false;

	public static JDialog progressDialog;

	public PythonRunner()
	{
		JLabel msgLabel;
		JProgressBar progressBar;
		JPanel panel;

		progressBar = new JProgressBar(0, 100);
		progressBar.setIndeterminate(true);
		msgLabel = new JLabel("Processing Data...");
		msgLabel.setOpaque(false);

		panel = new JPanel(new BorderLayout(5, 5));
		panel.add(msgLabel, BorderLayout.PAGE_START);
		panel.add(progressBar, BorderLayout.CENTER);
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		msgLabel.setBackground(panel.getBackground());

		progressDialog = new JDialog(GuiFrame.frame, "Acti | Monash", true);
		progressDialog.getContentPane().add(panel);
		progressDialog.setResizable(false);
		progressDialog.setSize(200, 90);
		progressDialog.setLocationRelativeTo(null);
		progressDialog.setAlwaysOnTop(false);
	}

	public void execute()
	{

		String errorMessage = "";
		try
		{
			File crashFile = new File(pythonLocation + "crashes.txt");
			File errorLog = new File(pythonLocation + "Error_Log.txt");
			File fileWorked = new File("worked.txt");
			if (crashFile.exists()) crashFile.delete();
			if (errorLog.exists()) errorLog.delete();
			if (fileWorked.exists()) fileWorked.delete();

			ProcessBuilder pb = new ProcessBuilder("python", "main.py");
			pb.directory(new File(pythonLocation));
			pb.redirectError();

			SwingWorker worker = new SwingWorker()
			{

				@Override
				protected void done()
				{
					progressDialog.dispose();
				}

				@Override
				protected void process(List chunks)
				{

				}

				@Override
				protected Object doInBackground() throws Exception
				{
					Process p = pb.start();
					while (p.isAlive())
						Thread.sleep(10);
					this.publish();
					this.done();
					return null;
				}
			};
			worker.execute();
			pb.redirectError(errorLog);
			progressDialog.setVisible(true);

			if (crashFile.exists())
			{
				BufferedReader br = new BufferedReader(new FileReader(crashFile));
				String line;
				while ((line = br.readLine()) != null)
					errorMessage += (line + "\n");
				br.close();
			}
			if (errorMessage.trim().equals("None")) errorMessage = "";
			if (crashFile.exists() && errorMessage.equals(""))
			{
				BufferedReader br = new BufferedReader(new FileReader(errorLog));
				String line;
				while ((line = br.readLine()) != null)
					errorMessage += (line + "\n");
				br.close();
			}
			if (errorMessage.trim().equals("None")) errorMessage = "";

			fileWorked = new File("worked.txt");
			if (!fileWorked.exists() && errorMessage.equals("")) errorMessage = "Data processing was unsuccessful";
			fileWorked.delete();
		}
		catch (Exception e)
		{
			errorMessage = "Unexpected Error\n\n" + e.getMessage();
			e.printStackTrace();
		}
		errorMessage = errorMessage.trim();

		if (!errorMessage.equals(""))
		{
			JOptionPane.showMessageDialog(GuiFrame.frame, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
			this.isSuccessful = false;
		}
		else this.isSuccessful = true;

		this.isSuccessful = true;

		GraphImages.load();
	}
}
