package PhaseTwo;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.*;

public class GUI {

	private GUI gui;
	private JLabel museumLabel, tktSoldLabel, timeLabel, broadCastLabelEntrance, broadCastLabelExit, tktSysLabel;
	private JTextField mText, tktText, tktSoldText, timeText;
	private JTextArea broadCastTextEntrance, broadCastTextExit;
	private JButton StartButton, EndButton,TerminateButton;
	private Timer timer = new Timer(500, (ActionListener) new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			tickTock();
		}
	});
	private final int textFieldWidth = MasterControl.textFieldWidth, textFieldheight = MasterControl.textFieldheight,
			textLabelWidth = MasterControl.textLabelWidth;
	//
	objQueue<String> tq = new objQueue<String>();// "tq = ticket queue" : store the ticket generated, will pop when it
													// entered the museum.
	objQueue<String> mq = new objQueue<String>();// "mq = museum queue" : store the ticket pop by tq, Exit will run
													// based on this queue.
	Museum m;
	Ticket t;
	Buyer buyer;
	Action action;

	public void placeComponents(JPanel panel) {

		panel.setLayout(null);
		// Museum
		museumLabel = new JLabel("Museum(Full: " + MasterControl.maxVisitorInMuseum + "):");
		museumLabel.setBounds(10, 20, textLabelWidth, 25);
		panel.add(museumLabel);

		mText = new JTextField(20);
		mText.setEditable(false);
		mText.setBounds(museumLabel.getWidth() + 10, 20, textFieldWidth, textFieldheight);
		panel.add(mText);

		// Time
		timeLabel = new JLabel("Time:");
		timeLabel.setBounds(10, mText.getHeight() + textFieldheight, textLabelWidth, 25);
		panel.add(timeLabel);

		timeText = new JTextField(20);
		timeText.setEditable(false);
		timeText.setBounds(timeLabel.getWidth() + 10, mText.getHeight() + textFieldheight, textFieldWidth,
				textFieldheight);
		panel.add(timeText);

		// Ticket system
		tktSysLabel = new JLabel("Ticket System: ");
		tktSysLabel.setBounds(mText.getX() + mText.getWidth() + 30, 20, 110, 25);
		panel.add(tktSysLabel);

		tktText = new JTextField(20);
		tktText.setEditable(false);
		tktText.setBounds(tktSysLabel.getX() + tktSysLabel.getWidth() + 10, 20, textFieldWidth, textFieldheight);
		panel.add(tktText);

		// Ticket sold
		tktSoldLabel = new JLabel("Ticket Sold:");
		tktSoldLabel.setBounds(timeText.getX() + timeText.getWidth() + 30, tktText.getHeight() + textFieldheight, 110,
				25);
		panel.add(tktSoldLabel);

		tktSoldText = new JTextField(20);
		tktSoldText.setEditable(false);
		tktSoldText.setBounds(tktSoldLabel.getX() + tktSoldLabel.getWidth() + 10, tktText.getHeight() + textFieldheight,
				textFieldWidth, textFieldheight);
		panel.add(tktSoldText);

		// broadcasting board Entrance
		broadCastLabelEntrance = new JLabel("BroadCasting board (Entrance)");
		broadCastLabelEntrance.setBounds(10, tktSoldText.getY() + tktSoldText.getHeight() + 10, 200, 25);
		panel.add(broadCastLabelEntrance);

		broadCastTextEntrance = new JTextArea();
		broadCastTextEntrance.setEditable(false);
		JScrollPane scrollEntrance = new JScrollPane(broadCastTextEntrance, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollEntrance.setBounds(10, broadCastLabelEntrance.getY() + broadCastLabelEntrance.getHeight(), 600, 150);

		panel.add(scrollEntrance);
		panel.setVisible(true);

		// broadcasting board Exit
		broadCastLabelExit = new JLabel("BroadCasting board (Exit)");
		broadCastLabelExit.setBounds(10, scrollEntrance.getY() + scrollEntrance.getHeight() + 10, 200, 25);
		panel.add(broadCastLabelExit);

		broadCastTextExit = new JTextArea();
		broadCastTextExit.setEditable(false);
		JScrollPane scrollExit = new JScrollPane(broadCastTextExit, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollExit.setBounds(10, broadCastLabelExit.getY() + broadCastLabelExit.getHeight(), 600, 150);

		panel.add(scrollExit);
		panel.setVisible(true);

		// start
		StartButton = new JButton("Start");
		StartButton.setBounds(10, scrollExit.getY() + scrollExit.getHeight() + 30, 80, 30);
		StartButton.setEnabled(true);
		StartButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StartButtonActionPerformed(e);
			}
		});
		panel.add(StartButton);

		// end
		EndButton = new JButton("End");
		EndButton.setBounds(150, scrollExit.getY() + scrollExit.getHeight() + 30, 80, 30);
		EndButton.setEnabled(false);
		EndButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EndButtonActionPerformed(e);
			}
		});
		panel.add(EndButton);

		//Terminate
		TerminateButton = new JButton("Terminate");
		TerminateButton.setBounds(300, scrollExit.getY() + scrollExit.getHeight() + 30, 120, 30);
		TerminateButton.setEnabled(false);
		TerminateButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TerminateButtonActionPerformed(e);
			}
		});
		panel.add(TerminateButton);
	}

	// click start event
	private void StartButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_StartButtonActionPerformed
		m = new Museum(gui);
		t = new Ticket(tq, gui);
		buyer = new Buyer(m, tq, t, gui); // Keep buying ticket until max
		action = new Action(m, tq, mq, gui); // key enter and exit until no ticket is left

		settktSoldText(0);
		setmText(0);
		settktText("");
		setbroadcastEntrance("");
		setbroadcastExit("");

		buyer.start(); // start buyer thread that will buy the ticket
		action.start(); // action that will enter/exit museum
		
		StartTime(timer); // start the time
		StartButton.setEnabled(false);
		EndButton.setEnabled(true);
                TerminateButton.setEnabled(true);
	}

	// click end event
	private void EndButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_EndButtonActionPerformed
		buyer.closeThis(); // end the thread
		action.closeThis(); // end the thread
		StopTime(timer); // stop the timer as well
		StartButton.setEnabled(true);
		EndButton.setEnabled(false);
	}

	// click terminate event
	private void TerminateButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_EndButtonActionPerformed
		buyer.closeThis(); // end the thread
		action.closeThis(); // end the thread
		StopTime(timer); // stop the timer as well
		System.exit(0);
	}

	// Set the class to yourself, so the other class can use
	public void setClass(GUI gui) {
		this.gui = gui;
	}

	// change the ticket sold text
	public void settktSoldText(Integer number) {
		tktSoldText.setText(Integer.toString(number));
	}

	// change museum text
	public void setmText(Integer number) {
		mText.setText(Integer.toString(number));
	}

	// ticket system status
	public void settktText(String text) {
		tktText.setText(text);
	}

	// Broadcast entrance
	public void setbroadcastEntrance(String text) {
		broadCastTextEntrance.setText(broadCastTextEntrance.getText() + "\n" + text);
	}

	// Broadcast exit
	public void setbroadcastExit(String text) {
		broadCastTextExit.setText(broadCastTextExit.getText() + "\n" + text);
	}

	// time
	public void StartTime(Timer timer) {
		timer.setRepeats(true);
		timer.setCoalesce(true);
		timer.setInitialDelay(0);
		timer.start();
	}

	public void tickTock() {
		timeText.setText(DateFormat.getDateTimeInstance().format(new Date()));
	}

	public void StopTime(Timer timer) {
		timer.stop();
	}

}
