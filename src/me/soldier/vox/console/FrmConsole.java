package me.soldier.vox.console;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

import me.soldier.vox.core.*;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by Thomas on 24 janv. 2016
 */
public class FrmConsole extends JFrame
{
	private static final long serialVersionUID = -623155454415628173L;
	private JPanel contentPane;
	private JTextField textField;
	private CommandHandler commandHandler;

	/**
	 * Create the frame.
	 */
	public FrmConsole(Engine e)
	{
		commandHandler = new CommandHandler(e);
		setTitle("Voxel Engine Console");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 488, 320);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setForeground(Color.WHITE);
		textField.setBackground(Color.BLACK);
		textField.setBounds(10, 250, 452, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		final JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setForeground(Color.WHITE);
		textArea.setBackground(Color.BLACK);
		textArea.setBounds(10, 11, 452, 228);
		contentPane.add(textArea);
	
		//Define events
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if(arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					textArea.setText(textArea.getText()+"\n"+textField.getText()+"\n"+commandHandler.OnCommandIssued(textField.getText()));
					textField.setText("");
				}
			}
		});
		this.setVisible(true);
	}
}
