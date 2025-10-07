package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Point2D;

public class Martelo extends GameElement implements ConsumableElement, PowerElement {
	
	public Martelo(Point2D Point2D){
		super(Point2D, "Martelo", 1);		
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
		GameEngine.getInstance().removeElement(this); // remove o martelo do jogo
		setPower();									  // ativa o poder
	}

	@Override
	public void setPower() {
		GameEngine.getInstance().getEmpilhadora().setBreakPower(true); // ativa a habilidade da empilhadora partir elementos removíveis
	}

}
