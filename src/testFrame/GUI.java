package testFrame;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.DefaultCaret;

import mailPackage.FetchingMail;

public class GUI {

	private JFrame frame;
	private JTabbedPane tabbedPaneProblem;
	private JTabbedPane tabbedPaneMain;
	private JTextField txProblem;
	private JTextField txMail;
	private JTextField txDescription;
	private JTextField txtVarGroup;
	private JComboBox<Integer> comboVarNr = new JComboBox<Integer>();
	private DefaultListModel<String> model;
	private JList<String> mailList;
	private JScrollPane mailListScroll;
	private JButton refreshB;
	private ArrayList<Message> msgList = new ArrayList<>();

	public static void main(String[] args) {
		new GUI();
	}
	
	public GUI() {
		addFrameContent();
		frame.setVisible(true);
	}

	private void addFrameContent() {
		frame = new JFrame();
		frame.setBounds(100, 100, 500, 500);
		frame.setSize(900,600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		frame.getContentPane().setLayout(gridBagLayout);
		
		
		tabbedPaneMain = new JTabbedPane(JTabbedPane.TOP);
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane.gridx = 0;
		gbc_tabbedPane.gridy = 0;
		gbc_tabbedPane.weightx = 1;
		gbc_tabbedPane.weighty = 1;
		frame.getContentPane().add(tabbedPaneMain, gbc_tabbedPane);
		
		
		tabbedPaneProblem = new JTabbedPane(JTabbedPane.TOP);
		tabbedPaneMain.add("NEW PROBLEM", tabbedPaneProblem);
		tabbedPaneProblem.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if (tabbedPaneProblem.getSelectedIndex() == 1) 
					editVariables((int) comboVarNr.getSelectedItem());
			}
		});
		
		tabbedPaneProblem.insertTab("SUMBIT PROBLEM", null, new JPanel(), null , 0);
		tabbedPaneProblem.insertTab("VARIABLE GROUP", null, new JPanel(), null , 1);
		tabbedPaneProblem.insertTab("CRITERIOS", null,  new JPanel(), null, 2);
		
		buildProblemTab();
		buildEmailTab();
		buildHelpTab();
		
	}

	private void buildProblemTab() {
		JPanel panel = new JPanel();
		tabbedPaneProblem.setComponentAt(0, panel);

		
		GridBagLayout gbl_panel = new GridBagLayout();
		panel.setLayout(gbl_panel);

		JLabel lbProblem = new JLabel("DESCRIPTION");
		GridBagConstraints gbc_lbProblem = new GridBagConstraints();
		gbc_lbProblem.insets = new Insets(5, 5, 5, 5);
		gbc_lbProblem.anchor = GridBagConstraints.NORTHEAST;
		gbc_lbProblem.gridx = 0;
		gbc_lbProblem.gridy = 0;
		panel.add(lbProblem, gbc_lbProblem);

		txProblem = new JTextField();
		lbProblem.setLabelFor(txProblem);
		GridBagConstraints gbc_txProblem = new GridBagConstraints();
		gbc_txProblem.gridwidth = 3;
		gbc_txProblem.insets = new Insets(5, 5, 5, 0);
		gbc_txProblem.fill = GridBagConstraints.HORIZONTAL;
		gbc_txProblem.gridx = 1;
		gbc_txProblem.gridy = 0;
		panel.add(txProblem, gbc_txProblem);
		txProblem.setColumns(10);

		JLabel lbMail = new JLabel("E-MAIL:");
		GridBagConstraints gbc_lbMail = new GridBagConstraints();
		gbc_lbMail.anchor = GridBagConstraints.EAST;
		gbc_lbMail.insets = new Insets(5, 5, 5, 5);
		gbc_lbMail.gridx = 0;
		gbc_lbMail.gridy = 1;
		panel.add(lbMail, gbc_lbMail);

		txMail = new JTextField();
		GridBagConstraints gbc_txMail = new GridBagConstraints();
		gbc_txMail.gridwidth = 3;
		gbc_txMail.insets = new Insets(5, 5, 5, 0);
		gbc_txMail.fill = GridBagConstraints.HORIZONTAL;
		gbc_txMail.gridx = 1;
		gbc_txMail.gridy = 1;
		panel.add(txMail, gbc_txMail);
		txMail.setColumns(10);

		JLabel lbDescription = new JLabel("DESCRIPTION:");
		GridBagConstraints gbc_lbDescription = new GridBagConstraints();
		gbc_lbDescription.anchor = GridBagConstraints.WEST;
		gbc_lbDescription.insets = new Insets(2, 5, 5, 5);
		gbc_lbDescription.gridx = 0;
		gbc_lbDescription.gridy = 3;
		panel.add(lbDescription, gbc_lbDescription);

		txDescription = new JTextField();
		GridBagConstraints gbc_txDescription = new GridBagConstraints();
		gbc_txDescription.fill = GridBagConstraints.BOTH;
		gbc_txDescription.weighty = 1.0;
		gbc_txDescription.weightx = 1.0;
		gbc_txDescription.gridwidth = 4;
		gbc_txDescription.insets = new Insets(5, 5, 5, 0);
		gbc_txDescription.gridx = 0;
		gbc_txDescription.gridy = 4;
		panel.add(txDescription, gbc_txDescription);
		txDescription.setColumns(10);
		txDescription.setSize(new Dimension(1000, 1000));

		Component glue = Box.createGlue();
		GridBagConstraints gbc_glue = new GridBagConstraints();
		gbc_glue.insets = new Insets(0, 0, 5, 0);
		gbc_glue.anchor = GridBagConstraints.WEST;
		gbc_glue.fill = GridBagConstraints.VERTICAL;
		gbc_glue.gridwidth = 4;
		gbc_glue.gridx = 0;
		gbc_glue.gridy = 2;
		panel.add(glue, gbc_glue);

		JLabel lbVarGroup = new JLabel("VAR GROUP NAME:");
		GridBagConstraints gbc_lbVarGroup = new GridBagConstraints();
		gbc_lbVarGroup.anchor = GridBagConstraints.EAST;
		gbc_lbVarGroup.insets = new Insets(0, 0, 5, 5);
		gbc_lbVarGroup.gridx = 0;
		gbc_lbVarGroup.gridy = 5;
		panel.add(lbVarGroup, gbc_lbVarGroup);

		txtVarGroup = new JTextField();
		GridBagConstraints gbc_txtVarGroup = new GridBagConstraints();
		gbc_txtVarGroup.insets = new Insets(0, 0, 5, 5);
		gbc_txtVarGroup.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtVarGroup.gridx = 1;
		gbc_txtVarGroup.gridy = 5;
		panel.add(txtVarGroup, gbc_txtVarGroup);
		txtVarGroup.setColumns(10);

		JLabel lbNrVar = new JLabel("NR OF VARIABLES:");
		lbNrVar.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lbNrVar = new GridBagConstraints();
		gbc_lbNrVar.insets = new Insets(0, 0, 5, 5);
		gbc_lbNrVar.anchor = GridBagConstraints.EAST;
		gbc_lbNrVar.gridx = 2;
		gbc_lbNrVar.gridy = 5;
		panel.add(lbNrVar, gbc_lbNrVar);

		comboVarNr = new JComboBox<Integer>();
		comboVarNr.setModel(new DefaultComboBoxModel<Integer>(new Integer[] {1, 2, 3, 4, 5, 6, 7, 8}));
		comboVarNr.setMaximumRowCount(5);
		GridBagConstraints gbc_comboVarNr = new GridBagConstraints();
		gbc_comboVarNr.anchor = GridBagConstraints.WEST;
		gbc_comboVarNr.insets = new Insets(0, 0, 5, 0);
		gbc_comboVarNr.gridx = 3;
		gbc_comboVarNr.gridy = 5;
		panel.add(comboVarNr, gbc_comboVarNr);

	}

	private void buildEmailTab() {
		
		refreshB = new JButton("");


		JPanel panel2 = new JPanel();
		panel2.setBackground(SystemColor.menu);


		model = new DefaultListModel<String>();

		mailList = new JList<String>(model);
		mailListScroll = new JScrollPane(mailList);
		mailListScroll.setBounds(0, 40, 192, 445);

		new MailLoader().start();

		JButton btnNewButton = new JButton("New");
		btnNewButton.setBounds(766, 490, 66, 25);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SendFrame();
			}
		});

		JEditorPane showMail = new JEditorPane();
		JScrollPane ShowMailScroll = new JScrollPane(showMail);
		ShowMailScroll.setBounds(289, 42, 558, 443);
		DefaultCaret caret = (DefaultCaret) showMail.getCaret();
		caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);

		refreshB.setBounds(12, 490, 49, 25);
		refreshB.setIcon(new ImageIcon(GUI.class.getResource("/com/sun/javafx/scene/web/skin/Redo_16x16_JFX.png")));
		refreshB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				refreshB.setEnabled(false);
				mailList.setSelectedIndex(-1);
				model.removeAllElements();
				new MailLoader().start();
			}
		});
		panel2.setLayout(null);

		JLabel lblCaixaDeEntrada = new JLabel("   Mailbox");
		lblCaixaDeEntrada.setForeground(Color.BLUE);
		lblCaixaDeEntrada.setBounds(51, 13, 79, 20);
		lblCaixaDeEntrada.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblCaixaDeEntrada.setBackground(Color.WHITE);
		panel2.add(lblCaixaDeEntrada);
		panel2.add(mailListScroll);
		panel2.add(refreshB);
		panel2.add(btnNewButton);
		panel2.add(ShowMailScroll);

		mailList.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (mailList.getSelectedIndex() != -1) {
					for (Message m : msgList) {
						try {
							System.out.println("antes");
							System.out.println(m.getSubject());
							if (mailList.getSelectedValue().equals(m.getSubject())) {
								if (m.isMimeType("multipart/*")) {
									Multipart mp = (Multipart) m.getContent();
									BodyPart bp = mp.getBodyPart(0);
									String content = bp.getContent().toString();
									showMail.setText(content);
									System.out.println(content);
								} else {
									showMail.setText((String) m.getContent());
									System.out.println(m.getContentType());
								}

							}

						} catch (MessagingException | IOException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		});
		

		tabbedPaneMain.add("E-MAIL", panel2);	
		}
	
	private void buildHelpTab() {
		JPanel panel = new JPanel();
		tabbedPaneMain.add("HELP", panel);	
	}
	
	private void editVariables(int nrVars) {
		
		JPanel varGroupPanel = new JPanel();
		tabbedPaneProblem.setComponentAt(1, varGroupPanel);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		varGroupPanel.setLayout(gbl_panel_1);
		
		JLabel lblNewLabel = new JLabel("VARIABLES");
		lblNewLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.fill = GridBagConstraints.VERTICAL;
		gbc_lblNewLabel.insets = new Insets(10, 0, 10, 0);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		varGroupPanel.add(lblNewLabel, gbc_lblNewLabel);

		for (int i = 0; i < nrVars; i++) {
			JPanel varPanel = buildVarPanel(i + 1);
			GridBagConstraints gbc_panel = new GridBagConstraints();
			gbc_panel.fill = GridBagConstraints.BOTH;
			gbc_panel.gridx = 0;
			gbc_panel.gridy = 1 + i;
			varGroupPanel.add(varPanel, gbc_panel);
		}
	

	}

	private JPanel buildVarPanel(int nr) {
		JPanel varPanel = new JPanel();

		GridBagLayout gbl_panel = new GridBagLayout();
		varPanel.setLayout(gbl_panel);

		JLabel label = new JLabel("VARIABLE " + nr + ":");
		label.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.anchor = GridBagConstraints.EAST;
		gbc_label.insets = new Insets(10, 5, 5, 5);
		gbc_label.fill = GridBagConstraints.VERTICAL;
		gbc_label.gridx = 0;
		gbc_label.gridy = 0;
		varPanel.add(label, gbc_label);

		JLabel lblName = new JLabel("Name:");
		lblName.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.insets = new Insets(0, 5, 5, 5);
		gbc_lblName.anchor = GridBagConstraints.EAST;
		gbc_lblName.gridx = 0;
		gbc_lblName.gridy = 1;
		varPanel.add(lblName, gbc_lblName);

		JTextField txtVarNa = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 1;
		varPanel.add(txtVarNa, gbc_textField);
		txtVarNa.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Variable type:");
		lblNewLabel_1.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1.insets = new Insets(0, 5, 5, 5);
		gbc_lblNewLabel_1.gridx = 2;
		gbc_lblNewLabel_1.gridy = 1;
		varPanel.add(lblNewLabel_1, gbc_lblNewLabel_1);

		JComboBox comboBox = new JComboBox();
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 3;
		gbc_comboBox.gridy = 1;
		varPanel.add(comboBox, gbc_comboBox);

		JLabel lblLimitarDe = new JLabel("Limitar de:");
		lblLimitarDe.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		GridBagConstraints gbc_lblLimitarDe = new GridBagConstraints();
		gbc_lblLimitarDe.insets = new Insets(0, 0, 0, 5);
		gbc_lblLimitarDe.anchor = GridBagConstraints.EAST;
		gbc_lblLimitarDe.gridx = 2;
		gbc_lblLimitarDe.gridy = 2;
		varPanel.add(lblLimitarDe, gbc_lblLimitarDe);

		JTextField txtxy = new JTextField();
		txtxy.setText("[x,y]");
		GridBagConstraints gbc_txtxy = new GridBagConstraints();
		gbc_txtxy.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtxy.gridx = 3;
		gbc_txtxy.gridy = 2;
		varPanel.add(txtxy, gbc_txtxy);
		txtxy.setColumns(10);

		return varPanel;
	}
	private class MailLoader extends Thread {
		public void run() {
			try {
				refreshB.setEnabled(false);
				FetchingMail.fetch("pop.gmail.com", "pop3", "testmail2018es@gmail.com", "testmail2018");
				msgList = FetchingMail.getMsg();

				System.out.println(msgList.size());

				for (Message m : msgList) {
					model.addElement(m.getSubject());

				}
				refreshB.setEnabled(true);

			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
