package me.elendrial.hearthclone.containers;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

import me.elendrial.cardGameBase.containers.BaseContainer;
import me.elendrial.cardGameBase.helpers.TextureHelper;
import me.elendrial.cardGameBase.server.GameClient;
import me.elendrial.hearthclone.action.Action;
import me.elendrial.hearthclone.decks.HearthstoneDeck;
import me.elendrial.hearthclone.ruleSets.RuleSet;
import me.elendrial.hearthclone.server.ClientProtocol;

public class ClientMatchContainer extends BaseContainer{
	
	public boolean first;
	public RuleSet ruleSet;
	
	public ClientMatchContainer(){
		this.players = 2;
		this.identifier = "match";
		this.background = TextureHelper.loadTexture("textures/background/", "Match.png", this);
		this.turn = 0;
	}
	
	public void setupMatch(boolean first, HearthstoneDeck deck, int oppDeckSize){
		this.first = first;
		
		this.decks = new HearthstoneDeck[]{deck, HearthstoneDeck.getUnknownDeck(oppDeckSize, ruleSet.maxTotalDeckSize)};
		decks[0].setPosition(50, 50); // TODO: Make this all relative so the decks go in the right place (Priority: Medium)
		decks[0].setDimensions(50, 80);
		
		decks[1].setPosition(50, 150);
		decks[1].setDimensions(50, 80);
		
	}
	
	public void tryAction(Action a){
		GameClient.getProtocol().sendData("match:-action " + a.getStringRepresentation());
	}
	
	public void confirmAction(Action a){
		
	}
	
	public void endTurn(){
		turn++;
		GameClient.getProtocol().sendData("match-endTurn");
	}
	
	public void endGame(){
		((ClientProtocol)GameClient.getProtocol()).endGame();
	}
	

	
	@Override
	public void mousePressed(MouseEvent arg){
		
	}
	
	@Override
	public void mouseDragged(MouseEvent arg){
		
	}
	
	@Override
	public void mouseReleased(MouseEvent arg){
		
	}
	
	@Override
	public void keyPressed(KeyEvent arg){
		if(arg.getKeyCode() == KeyEvent.VK_ESCAPE){
			int quit = JOptionPane.showOptionDialog(null, "Would you like to quit the match?", "Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[]{"Yes", "No"}, "No");

		}
	}
	
}
