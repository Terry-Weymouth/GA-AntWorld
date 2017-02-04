package org.weymouth.example;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

//based in part on ProgressMonitorDemo...
/* From http://java.sun.com/docs/books/tutorial/index.html */
/*
* Copyright (c) 2006 Sun Microsystems, Inc. All Rights Reserved.
*
* Redistribution and use in source and binary forms, with or without
* modification, are permitted provided that the following conditions are met:
*
* -Redistribution of source code must retain the above copyright notice, this
*  list of conditions and the following disclaimer.
*
* -Redistribution in binary form must reproduce the above copyright notice,
*  this list of conditions and the following disclaimer in the documentation
*  and/or other materials provided with the distribution.
*
* Neither the name of Sun Microsystems, Inc. or the names of contributors may
* be used to endorse or promote products derived from this software without
* specific prior written permission.
*
* This software is provided "AS IS," without a warranty of any kind. ALL
* EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING
* ANY IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE
* OR NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN MIDROSYSTEMS, INC. ("SUN")
* AND ITS LICENSORS SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE
* AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
* DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS BE LIABLE FOR ANY LOST
* REVENUE, PROFIT OR DATA, OR FOR DIRECT, INDIRECT, SPECIAL, CONSEQUENTIAL,
* INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER CAUSED AND REGARDLESS OF THE THEORY
* OF LIABILITY, ARISING OUT OF THE USE OF OR INABILITY TO USE THIS SOFTWARE,
* EVEN IF SUN HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
*
* You acknowledge that this software is not designed, licensed or intended
* for use in the design, construction, operation or maintenance of any
* nuclear facility.
*/

/*
 * From ProgressMonitorDemo.java is a 1.4 application that requires these files:
 *   LongTask.java
 *   SwingWorker.java
 */
public class ProcessingTestController extends JPanel implements ActionListener {
	private static final long serialVersionUID = 6312553427468914037L;

	public final static int ONE_SECOND = 1000;

	private ProgressMonitor progressMonitor;
	private Timer timer;
	private JButton startButton;
	private LongTask task;
	private JTextArea taskOutput;
	private String newline = "\n";

	public ProcessingTestController() {
		super(new BorderLayout());
		task = new LongTask();

		// Create the demo's UI.
		startButton = new JButton("Start");
		startButton.setActionCommand("start");
		startButton.addActionListener(this);

		taskOutput = new JTextArea(5, 20);
		taskOutput.setMargin(new Insets(5, 5, 5, 5));
		taskOutput.setEditable(false);

		add(startButton, BorderLayout.PAGE_START);
		add(new JScrollPane(taskOutput), BorderLayout.CENTER);
		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		// Create a timer.
		timer = new Timer(ONE_SECOND, new TimerListener());
	}

	/**
	 * The actionPerformed method in this class is called each time the Timer
	 * "goes off".
	 */
	class TimerListener implements ActionListener {
		public void actionPerformed(ActionEvent evt) {
			progressMonitor.setProgress(task.getCurrent());
			String s = task.getMessage();
			if (s != null) {
				progressMonitor.setNote(s);
				taskOutput.append(s + newline);
				taskOutput.setCaretPosition(taskOutput.getDocument().getLength());
			}
			if (progressMonitor.isCanceled() || task.isDone()) {
				progressMonitor.close();
				task.stop();
				Toolkit.getDefaultToolkit().beep();
				timer.stop();
				if (task.isDone()) {
					taskOutput.append("Task completed." + newline);
				} else {
					taskOutput.append("Task canceled." + newline);

				}
				startButton.setEnabled(true);
			}
		}
	}

	/**
	 * Called when the user presses the start button.
	 */
	public void actionPerformed(ActionEvent evt) {
		progressMonitor = new ProgressMonitor(ProcessingTestController.this, "Running a Long Task", "", 0,
				task.getLengthOfTask());
		progressMonitor.setProgress(0);
		progressMonitor.setMillisToDecideToPopup(2 * ONE_SECOND);

		startButton.setEnabled(false);
		task.go();
		timer.start();
	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event-dispatching thread.
	 */
	private static void createAndShowGUI() {
		// Make sure we have nice window decorations.
		JFrame.setDefaultLookAndFeelDecorated(true);

		// Create and set up the window.
		JFrame frame = new JFrame("ProgressMonitorDemo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create and set up the content pane.
		JComponent newContentPane = new ProgressMonitorDemo();
		newContentPane.setOpaque(true); // content panes must be opaque
		frame.setContentPane(newContentPane);

		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
}