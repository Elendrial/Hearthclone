package me.elendrial.hearthclone.display;

import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import me.elendrial.cardGameBase.display.Window;

public class HearthWindow extends Window {

	// GUI elements
	public JMenuBar menuBar;
	public JMenu game, cards;
	public JMenuItem host, connect, chat, exit;
	public JMenuItem deckManage, cardManage;
	
	public HearthWindow(String TITLE, int WIDTH, int HEIGHT) {
		super(TITLE, WIDTH, HEIGHT);
	}

	@Override
	public void createGUI() {
		super.createGUI();
		
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
	}

}
