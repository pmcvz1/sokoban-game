package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Teleporte extends GameElement implements TransportElement {
	
	public Teleporte(Point2D Point2D){
		super(Point2D, "Teleporte", 1);		
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
	
	public Teleporte getOtherTeleport(){			
		for(GameElement gameElement : GameEngine.getInstance().getGameElementsList()) {
			if(gameElement instanceof Teleporte && gameElement != this) {
				return (Teleporte)gameElement; // devolve o segundo teleporte
			}
		}
		
		return null;
	}
	
	@Override
	public Point2D getPositionAfterTransport(Direction direction){
		// Devolve a posição para onde um elemento vai após passar o teleporte
		return getOtherTeleport().getPosition().plus(direction.asVector());
	}

	@Override
	public void transport(MovableElement element) {	
		int key = GameEngine.getInstance().getGui().keyPressed();
		Direction direction = Direction.directionFor(key);
		Point2D newPositionT = getPositionAfterTransport(direction);	
		
		((GameElement)element).setPosition(newPositionT); // transporta o elemento
	}
	
	

}