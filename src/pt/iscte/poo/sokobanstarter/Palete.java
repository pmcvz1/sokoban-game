package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Point2D;

public class Palete extends PushableElement implements CoverableElement{
	
	public Palete(Point2D Point2D){
		super(Point2D, "Palete", 2);
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
	public void cover(GameElement element) {
		GameEngine.getInstance().removeElementFromList(element);	  // remove o elemento coberto do jogo
    	GameEngine.getInstance().addElement(new Chao(getPosition())); // adiciona um chão no lugar desse elemento e da palete	       	
	}

	@Override
	protected void updateImage(GameElement element) {
		// A imagem do caixote nunca é alterada
	}

    
	

	
	
	
	
	
	
	
	
	
	
	
	
	

}