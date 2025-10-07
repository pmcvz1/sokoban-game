package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Point2D;

public class ParedeRachada extends GameElement implements RemovableElement {
	
	public ParedeRachada(Point2D Point2D){
		super(Point2D, "ParedeRachada", 2);		
	}
	
	@Override
	public String getName() {
		return super.getName();
	}

	@Override
	public Point2D getPosition() {
		return super.getPosition();
	}

	@Override
	public int getLayer() {
		return super.getLayer();
	}
	
	@Override
	public void remove() {
		GameEngine gameEngine = GameEngine.getInstance();
		gameEngine.removeElement(this);	// remove a parede rachada do jogo
	}

}