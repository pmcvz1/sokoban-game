package pt.iscte.poo.sokobanstarter;

import java.awt.event.KeyEvent;
import java.util.List;

import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Empilhadora extends GameElement implements MovableElement{
	
	private int moves;
	private int energy;
	private boolean breakPower;
	
	public Empilhadora(Point2D initialPosition){
		super(initialPosition, "Empilhadora_U", 3);
		energy = 100;
		moves = 0;
	    breakPower = false;		
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
	
	public int getMoves() {
		return moves;
	}
	
	public int getEnergy() {
		return energy;
	}
	
	public boolean getBreakPower() {
		return breakPower;
	}
	
	@Override
	public void setName(String name) {
		super.setName(name);
	}
	
	@Override
	public void setPosition(Point2D position) {
		super.setPosition(position);
	}
	
	public void setEnergy(int energy){
		this.energy = energy;
		
		if(this.energy > 100) this.energy = 100; // energia máxima = 100
		if(this.energy < 0) this.energy = 0;	 // energia mínima = 0
	}
	
	public void setMoves(int moves){
		this.moves = moves;
	}
	
	public void setBreakPower(boolean b) {
		breakPower = b;
	}
	
	public String isCanBreakElementsActivated() {
		if(breakPower)
			return "Ativada";
		else
			return "Desativada";
	}
	
    public boolean areAllTargetElementsComplete(List<GameElement> gameElementsList) {     
        // Verifica todos os elementos do jogo
        for(GameElement element : gameElementsList){
        	// Se forem alvos, verifica se têm um WinningElement em cima
        	if(element instanceof TargetElement){
        		// Se pelo menos um alvo não estiver completo, devolve falso
        		if(!((TargetElement)element).isComplete()){
        			return false;
        		}
        	}
        }
        
        return true; // caso contrário, os alvos estão completos
	}
    
    public boolean isNumberOfWinningElementsValid(List<GameElement> gameElementsList) {   	
    	int numberOfTargetElements = 0;
		int numberOfWinningElements = 0;
		
		// Verifica todos os elementos do jogo
		for(GameElement element : gameElementsList) {
			// Se for um TargetElement, adiciona 1 à contagem de TargetElements
			if(element instanceof TargetElement)
				numberOfTargetElements++;
			// Se for um WinningElement, adiciona 1 à contagem de WinningElements
			if(element instanceof WinningElement)
				numberOfWinningElements++;
		}
		
		// Se existirem mais TargetElements do que WinningElements é impossível passar o nível
		if(numberOfTargetElements > numberOfWinningElements) {
			return false; // por isso, devolve falso
		}
		return true; // caso contrário, o número de caixotes é válido
    }
    
    public boolean existsEmpilhadora(List<GameElement> gameElementsList) {
    	// Verifica todos os elementos do jogo
    	for(GameElement element : gameElementsList) {
    		// Se for uma Empilhadora, retorna verdadeiro
    		if(element instanceof Empilhadora)
    			return true; // existe empilhadora
    	}
    	return false; // não existe a empilhadora
    }
	
	@Override
    public void move() {
        GameEngine gameEngine = GameEngine.getInstance();
        ImageMatrixGUI gui = gameEngine.getGui();
        int key = gui.keyPressed();
        Direction direction = Direction.directionFor(key);
        Point2D newPosition = getPosition().plus(direction.asVector());
        List<GameElement> elementsAtNewPosition = gameEngine.getElementsAtPosition(newPosition);
        
		if (isMoveValid(newPosition, direction)) {
			energy--; // Reduz a energia da empilhadora a cada movimento
			moves++;  // Aumenta o número de movimentos feitos
			
			// Verifica os elementos no caminho da empilhadora 
			for (GameElement element : elementsAtNewPosition) {
				moveOtherElements(element); 	  // Move outros elementos na nova posição
				consumeElements(element); 		  // Consume elementos consumíveis
				removeElements(element); 		  // Remove elementos removíveis se tiver o martelo
				updateImage(key); 				  // Atualiza a imagem da empilhadora
				setPosition(newPosition); 		  // Atualiza a posição da empilhadora
				enterElement(element, direction); // Entra no elemento de transporte
				gui.update(); 					  // Redesenha a lista de ImageTiles na GUI
				gameEngine.start(); 			  // Recomeça o nível se necessário
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
        
        // Verifica se existe algum elemento na nova posição que impeça o movimento da Empilhadora         
        List<GameElement> elementsAtNewPosition = GameEngine.getInstance().getElementsAtPosition(newPosition);
        for (GameElement element : elementsAtNewPosition) {
        	//Se for um elemento não móvel, a empilhadora não se pode mover
        	if (element instanceof StaticElement) {
        		return false;	// nesse caso, retorna falso
        	}      	
        	// Se for um elemento móvel, verifica se esse elemento se pode mover
        	if (element instanceof PushableElement) {
        		// Posição para onde o elemento móvel vai depois de ser empurrado pela empilhadora
        		Point2D newPositionME = newPosition.plus(direction.asVector());
        		// Verifica se essa posição é válida
        		if (!((PushableElement)element).isMoveValid(newPositionME, direction) || energy == 1) {
        			return false;	// caso essa posição seja inválida, retorna falso
        		}
        	}
        	// Se for um elemento removível, verifica se a empilhadora pode removê-lo
        	if (element instanceof RemovableElement) {
        		if (breakPower == false) {
        			return false;	// se a empilhadora não tiver o poder ativado , retorna falso
        		}
        	}
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
        
        return true;	// em qualquer outro caso, a empilhadora pode-se mover
	}
	
	@Override
	public void enterElement(GameElement element, Direction direction) {
		// Se for um elemento de transporte, a empilhadora entra nesse elemento
		if (element instanceof TransportElement) {
			// Posição para onde a empilhadora é transportada
			Point2D position = ((TransportElement) element).getPositionAfterTransport(direction);
			// Lista de elementos nessa posição
			List<GameElement> elementsAtPosition = GameEngine.getInstance().getElementsAtPosition(position);
			for (GameElement element2 : elementsAtPosition) {
				// Move os elementos móveis nessa posição (empurra-os), caso existam
				moveOtherElements(element2);
			}
			// Transporta a empilhadora
			((TransportElement) element).transport(this);

		}
	}  

	public void moveOtherElements(GameElement element) {
		// Se for um elemento móvel, move-o, se possível
		if (element instanceof MovableElement) {
			((MovableElement) element).move(); // move o elemento
		}
	}
    
	private void consumeElements(GameElement element) {
		// Se for um elemento consumível, consome-o
		if (element instanceof ConsumableElement && energy > 1) {
			((ConsumableElement) element).consume(); // consome o elemento
		}
	}
    
	private void removeElements(GameElement element) {
		// Se um deles for um elemento removível, verifica se a empilhadora pode removê-lo
		if (element instanceof RemovableElement) {
			// Se o martelo tiver sido coletado pela empilhadora, a parede rachada é removida
			if (breakPower == true) {
				((RemovableElement) element).remove(); // remove o elemento
			}
		}
	}
	
	private void updateImage(int key){
		// Atualiza a imagem da empilhadora de acordo com o movimento efetuado
		if(key == KeyEvent.VK_UP){
			setName("Empilhadora_U");
		}
		if(key == KeyEvent.VK_DOWN){
			setName("Empilhadora_D");
		}
		if(key == KeyEvent.VK_LEFT){
			setName("Empilhadora_L");
		}
		if(key == KeyEvent.VK_RIGHT){
			setName("Empilhadora_R");
		}
	}	

}