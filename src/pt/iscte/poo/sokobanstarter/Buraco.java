package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Buraco extends GameElement implements TransportElement{
	
	public Buraco(Point2D Point2D){
		super(Point2D, "Buraco", 1);		
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
	public void transport(MovableElement element){
		GameEngine gameEngine = GameEngine.getInstance();
	   
        // Se o elemento for um CoverableElement, este cobre o buraco, ficando um chão no seu lugar
        if(element instanceof CoverableElement){
        	gameEngine.removeElementFromList((GameElement)element); // remove o elemento do jogo
        	((CoverableElement) element).cover(this); 			    // cobre o buraco
        } else {
        	gameEngine.removeElement((GameElement)element); // remove o elemento do jogo
        }
	}
	
	@Override
	public Point2D getPositionAfterTransport(Direction direction){
		return null;
	}

}