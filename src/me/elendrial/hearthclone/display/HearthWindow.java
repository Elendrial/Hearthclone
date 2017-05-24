package me.elendrial.hearthclone.display;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;

import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import me.elendrial.cardGameBase.display.Window;

public class HearthWindow extends Window {

	// GUI elements
	public JMenuBar menuBar;
	public JMenu game, cards;
	public JMenuItem host, connect, chat, exit;
	public JMenuItem deckManage, cardManage;
	
	public JLayeredPane layeredPane;
	public JPanel chatPanel;
	public JTextArea chatBox;
	public JTextField chatInput;
	public Font smallFont;
	
	public HearthWindow(String TITLE, int WIDTH, int HEIGHT) {
		super(TITLE, WIDTH, HEIGHT);
	}

	@Override
	public void createGUI() {
		
		// Have to have this here, as this class isn't properly loaded until _after_ the super constructor is completely finished.
		game = new JMenu("Game"); cards = new JMenu("Cards");
		host = new JMenuItem("Host", KeyEvent.VK_H); connect = new JMenuItem("Connect", KeyEvent.VK_C); chat = new JMenuItem("Chat", KeyEvent.VK_A); exit = new JMenuItem("Exit", KeyEvent.VK_E);
		deckManage = new JMenuItem("Manage Decks", KeyEvent.VK_D); cardManage = new JMenuItem("Manage Cards", KeyEvent.VK_C);
		
		
		menuBar = new JMenuBar();

		menuBar.add(game);
		menuBar.add(cards);
		
		host.addActionListener(MenuListener.self);
		game.add(host);
		
		connect.addActionListener(MenuListener.self);
		game.add(connect);
		
		chat.addActionListener(MenuListener.self);
		game.add(chat);
		
		exit.addActionListener(MenuListener.self);
		game.add(exit);
		
		deckManage.addActionListener(MenuListener.self);
		cards.add(deckManage);
		
		cardManage.addActionListener(MenuListener.self);
		cards.add(cardManage);
		
		this.frame.setJMenuBar(menuBar);
		
		smallFont = new Font(Font.MONOSPACED, Font.PLAIN, 10); 
		chatPanel = new JPanel(new BorderLayout()); chatPanel.setBorder(new EmptyBorder(2,3,3,2)); chatPanel.setSize(250, 300); chatPanel.setBounds(width - 2 - chatPanel.getSize().width, height - 2 - chatPanel.getSize().height, chatPanel.getSize().width, chatPanel.getSize().height);
		chatBox = new JTextArea(5, 5); chatBox.setFont(smallFont); chatBox.setEditable(false); chatBox.setLineWrap(true);
		
		layeredPane = new JLayeredPane(); layeredPane.setPreferredSize(new Dimension(width-4, height-10));
		
		chatPanel.add(new JScrollPane(chatBox,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
		
		this.display = new HearthDisplay(this);
		layeredPane.add(this.display, new Integer(0));
		
		layeredPane.add(chatPanel, new Integer(1));
		
		frame.add(layeredPane);
	}

}
