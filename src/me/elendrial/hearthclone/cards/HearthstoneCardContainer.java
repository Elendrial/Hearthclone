package me.elendrial.hearthclone.cards;

import java.awt.Point;

import me.elendrial.cardGameBase.containers.BaseCardContainer;

public class HearthstoneCardContainer extends BaseCardContainer {

	public HearthstoneCardContainer(){}
	
	public HearthstoneCardContainer(HearthstoneCard card) {
		super(card);
		this.faceUp = true;
	}

	public HearthstoneCardContainer(HearthstoneCard card, Point position, Point size) {
		super(card, position, size, true);
	}
	

}
