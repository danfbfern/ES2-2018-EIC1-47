package testFrame;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
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
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.DefaultCaret;

import mailPackage.FetchingMail;


public class frame extends JFrame {

	private JPanel contentPane;
	private DefaultListModel<String> model;
	private JList<String> mailList;
	private JScrollPane mailListScroll;
	private JButton refreshB;

	private ArrayList<Message> msgList = new ArrayList<>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame frame = new frame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public frame() {
		refreshB = new JButton("");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 887, 612);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JButton btnNew = new JButton("Create");
		btnNew.setForeground(Color.BLACK);
		btnNew.setBackground(Color.WHITE);
		btnNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout c = (CardLayout) (contentPane.getLayout());
				c.show(contentPane, "t1");
			}
		});
		menuBar.add(btnNew);

		JButton btnTest = new JButton("Email");
		btnTest.setBackground(Color.WHITE);
		btnTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout c = (CardLayout) (contentPane.getLayout());
				c.show(contentPane, "t2");
			}
		});
		menuBar.add(btnTest);

		JButton btnTest_1 = new JButton("Help");
		btnTest_1.setBackground(Color.WHITE);
		btnTest_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CardLayout c = (CardLayout) (contentPane.getLayout());
				c.show(contentPane, "t3");
			}
		});
		menuBar.add(btnTest_1);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new CardLayout(0, 0));

		JPanel panel1 = new JPanel();
		panel1.setBackground(SystemColor.menu);
		contentPane.add(panel1, "t1");
		panel1.setLayout(null);

		JMenuBar menuBar_1 = new JMenuBar();
		menuBar_1.setBounds(0, 0, 859, 26);
		panel1.add(menuBar_1);

		JPanel contentPane2 = new JPanel();
		JComboBox comboBox = new JComboBox();
		comboBox.setMaximumSize(new Dimension(100, 32767));
		comboBox.addItem("none");
		comboBox.addItem("Variables");
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CardLayout cl = (CardLayout) (contentPane2.getLayout());
				if (comboBox.getSelectedIndex() == 0) {
					cl.show(contentPane2, "c1");
				}
				if (comboBox.getSelectedIndex() == 1) {
					cl.show(contentPane2, "c2");
				}
			}
		});
		menuBar_1.add(comboBox);

		contentPane2.setBounds(0, 29, 859, 499);
		panel1.add(contentPane2);
		contentPane2.setLayout(new CardLayout(0, 0));

		JPanel paneltest1 = new JPanel();
		contentPane2.add(paneltest1, "c1");

		JPanel variable = new JPanel();
		contentPane2.add(variable, "c2");
		variable.setLayout(null);

		JLabel lblVariables = new JLabel("Variables:");
		lblVariables.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblVariables.setBounds(12, 13, 71, 16);
		variable.add(lblVariables);

		JSpinner spinner = new JSpinner();
		spinner.setBounds(84, 11, 39, 22);
		variable.add(spinner);

		JPanel varPane = new JPanel();
		varPane.setBackground(SystemColor.control);
		varPane.setBounds(32, 46, 356, 131);
		variable.add(varPane);

		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int value = (int) spinner.getValue();
				varPane.setLayout(new GridLayout(value, 2, 1, 1));
				for (int i = 0; i != value; i++) {
					varPane.add(new JLabel("V" + (i + 1)));
					varPane.add(new JTextField());
					varPane.setVisible(true);
					varPane.repaint();
					varPane.revalidate();
				}
			}
		});
		btnOk.setBounds(126, 10, 61, 25);
		variable.add(btnOk);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		JPanel panel2 = new JPanel();
		panel2.setBackground(SystemColor.menu);
		contentPane.add(panel2, "t2");

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
		refreshB.setIcon(new ImageIcon(frame.class.getResource("/com/sun/javafx/scene/web/skin/Redo_16x16_JFX.png")));
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

		JPanel panel3 = new JPanel();
		contentPane.add(panel3, "t3");
		panel3.setLayout(new GridLayout(1, 0, 0, 0));

		JTextArea textArea = new JTextArea();
		panel3.add(textArea);

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