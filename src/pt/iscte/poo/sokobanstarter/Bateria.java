package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Point2D;

public class Bateria extends GameElement implements ConsumableElement, EnergyElement {
	
	public Bateria(Point2D Point2D){
		super(Point2D, "Bateria", 1);		
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
	public void consume() {
		GameEngine gameEngine = GameEngine.getInstance();	
		gameEngine.removeElement(this); // remove a bateria do jogo
		setEnergy();					// dá energia à empilhadora
	}

	@Override
	public void setEnergy() {
		Empilhadora bobcat = GameEngine.getInstance().getEmpilhadora();
		bobcat.setEnergy(bobcat.getEnergy() + 50); // adiciona 50 de energia à empilhadora
	}
	
	
	
	

}