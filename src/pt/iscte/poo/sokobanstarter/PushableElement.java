package pt.iscte.poo.sokobanstarter;

import java.util.List;

import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public abstract class PushableElement extends GameElement implements MovableElement {

	public PushableElement(Point2D Point2D, String name, int layer) {
		super(Point2D, name, layer);
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
	
	protected abstract void updateImage(GameElement element);
	
	@Override
    public void move() {
		GameEngine gameEngine = GameEngine.getInstance();
		ImageMatrixGUI gui = gameEngine.getGui();
        int key = gui.keyPressed();
        Direction direction = Direction.directionFor(key);
        Point2D newPosition = getPosition().plus(direction.asVector());
        Empilhadora bobcat = gameEngine.getEmpilhadora();
        List<GameElement> elementsAtNewPosition = gameEngine.getElementsAtPosition(newPosition);
        
        // Se o movimento for válido
        if (isMoveValid(newPosition, direction)) {
        	bobcat.setEnergy(bobcat.getEnergy() - 1); // Reduz a energia da empilhadora a cada empurrão
        	
        	for(GameElement element : elementsAtNewPosition){
        		updateImage(element);             // Atualiza a imagem do caixote
        		super.setPosition(newPosition);	  // Atualiza a posição do PushableElement
        		enterElement(element, direction); // Entra no elemento de transporte
        	}
        	
        	
        }	
    }

	@Override
	public boolean isMoveValid(Point2D newPosition, Direction direction) {	
		// Verifica se a nova posição está dentro dos limites da grelha de jogo
        if (newPosition.getX() < 0 || newPosition.getX() >= GameEngine.GRID_WIDTH ||
            newPosition.getY() < 0 || newPosition.getY() >= GameEngine.GRID_HEIGHT) {
            return false;
        }
        
        // Verifica se existe algum elemento na nova posição que impeça o movimento do PushableElement
        List<GameElement> elementsAtNewPosition = GameEngine.getInstance().getElementsAtPosition(newPosition);
        for (GameElement element : elementsAtNewPosition) {
        	// Se exisir no seu caminho um elemento móvel, não móvel ou consumível, o PushableElement não se move
        	if (element instanceof StaticElement 
        			|| element instanceof ConsumableElement 
        			|| element instanceof MovableElement
        			|| element instanceof RemovableElement) 
        		return false;	// nesse caso, retorna falso
        	
        	// Se for um elemento de transporte, verifica a posição para onde será transportado
        	if (element instanceof TransportElement) {
        		// Posição depois do transporte
        		Point2D positionAT = ((TransportElement)element).getPositionAfterTransport(direction);
        		// Se a posição não seja nula, verifica-se essa posição
        		if (positionAT != null){
        			// Verifica se essa posição é válida
        			if (!isMoveValid(positionAT, direction)){
        				return false;	// caso essa posição seja inválida, retorna falso
        			}
        		}
        	}
        }
      
        return true;	// em qualquer outro caso, o PushableElement pode-se mover
	}
        
	@Override
	public void enterElement(GameElement element, Direction direction) {
		// Se for um elemento de transporte, então o PushableElement entra nesse elemento
		if (element instanceof TransportElement) {
			((TransportElement) element).transport(this); // transporta o PushableElement
		}
	}
	
	
	

}
