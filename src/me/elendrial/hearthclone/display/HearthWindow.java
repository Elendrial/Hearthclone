package me.elendrial.hearthclone.display;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import me.elendrial.cardGameBase.display.Display;
import me.elendrial.cardGameBase.display.Window;
import me.elendrial.cardGameBase.server.GameClient;
import me.elendrial.hearthclone.HearthController;
import me.elendrial.hearthclone.server.ClientProtocol;

public class HearthWindow extends Window {

	public HearthOverlay hOverlay = new HearthOverlay();
	
	// GUI elements
	public JMenuBar menuBar;
	public JMenu game, cards;
	public JMenuItem host, connect, chat, temp, exit;
	public JMenuItem deckManage, cardManage;
	
	public JLayeredPane layeredPane;
	public JPanel chatBoxPanel, chatFieldPanel;
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
		
		temp = new JMenuItem("Temp");
		
		menuBar = new JMenuBar();

		menuBar.add(game);
		menuBar.add(cards);
		
		host.addActionListener(MenuListener.self);
		game.add(host);
		
		connect.addActionListener(MenuListener.self);
		game.add(connect);
		
		chat.addActionListener(MenuListener.self);
		game.add(chat);
		
		temp.addActionListener(MenuListener.self);
		game.add(temp);
		
		exit.addActionListener(MenuListener.self);
		game.add(exit);
		
		deckManage.addActionListener(MenuListener.self);
		cards.add(deckManage);
		
		cardManage.addActionListener(MenuListener.self);
		cards.add(cardManage);
		
		this.frame.setJMenuBar(menuBar);
		
		smallFont = new Font(Font.MONOSPACED, Font.PLAIN, 10); 
		chatBoxPanel = new JPanel(new BorderLayout()); chatBoxPanel.setBorder(new EmptyBorder(2,3,3,2)); chatBoxPanel.setSize(250, 270); chatBoxPanel.setBounds(width - 2 - chatBoxPanel.getSize().width, height - 28 - chatBoxPanel.getSize().height, chatBoxPanel.getSize().width, chatBoxPanel.getSize().height);
		chatBox = new JTextArea(5, 5); chatBox.setFont(smallFont); chatBox.setEditable(false); chatBox.setLineWrap(true); chatBox.setAutoscrolls(true);
		chatFieldPanel = new JPanel(new BorderLayout()); chatFieldPanel.setSize(250, 20); chatFieldPanel.setBounds(width - 2 - chatFieldPanel.getSize().width, height - 2 - chatFieldPanel.getSize().height, chatFieldPanel.getSize().width, chatFieldPanel.getSize().height);
		chatInput = new JTextField(10); chatInput.setFont(smallFont); 
		
		chatInput.addKeyListener(new KeyListener(){
			@Override public void keyPressed(KeyEvent arg0) {if(arg0.getKeyCode() == KeyEvent.VK_ENTER){ ((ClientProtocol) GameClient.getProtocol()).sendChat(chatInput.getText());chatInput.setText("");}}
			@Override public void keyReleased(KeyEvent arg0) {}@Override public void keyTyped(KeyEvent arg0) {}
		});
		
		layeredPane = new JLayeredPane(); layeredPane.setPreferredSize(new Dimension(width-4, height-10));
		
		chatBoxPanel.add(new JScrollPane(chatBox,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
		
		chatFieldPanel.add(chatInput);
		
		this.display = new Display(this);
		this.display.overlays.add(hOverlay);
		
		layeredPane.add(this.display, new Integer(0));
		
		layeredPane.add(chatBoxPanel, new Integer(1));
		layeredPane.add(chatFieldPanel, new Integer(1));
		
		frame.add(layeredPane);
	}
	
	public void updateChat(){
		String text = "";
		
		for(String s : ClientProtocol.getChatLogs()){
			text += "\n" + s;
		}
		
		((HearthWindow)HearthController.mainWindow).chatBox.setText(text);
	}
	
}
