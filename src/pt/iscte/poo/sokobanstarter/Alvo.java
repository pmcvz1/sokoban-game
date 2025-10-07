package pt.iscte.poo.sokobanstarter;

import java.util.List;

import pt.iscte.poo.utils.Point2D;

public class Alvo extends GameElement implements TargetElement {
	
	public Alvo(Point2D Point2D) {
		super(Point2D, "Alvo", 1);	
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
	public boolean isComplete() {
		GameEngine gameEngine = GameEngine.getInstance();
        List<GameElement> elementsAtPosition = gameEngine.getElementsAtPosition(getPosition());
        
        // Verifica se um TargetElement tem um WinningElement em cima
        for(GameElement element : elementsAtPosition){
        	// Se tiver, o TargetElement está completo
        	if(element instanceof WinningElement){
        		return true;	// nesse caso, devolve verdadeiro
        	}
        }
        
        return false;	// caso contrário, devolve falso
	}
}
