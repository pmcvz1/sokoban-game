package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Point2D;

public class Caixote extends PushableElement implements WinningElement {

	public Caixote(Point2D Point2D) {
		super(Point2D, "Caixote", 3);
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
	public boolean isStored(GameElement element) {
		// Se for um TargetElement, então o caixote está arrumado
		if (element instanceof TargetElement)
			return true; // nesse caso, devolve verdadeiro

		return false; // caso contrário, devolve falso
	}

	@Override
	protected void updateImage(GameElement element) {
		// Atualiza a imagem do caixote de acordo com o estado do caixote
		if (isStored(element)) {
			super.setName("CaixoteNoAlvo"); // se o caixote estiver arrumado
		} else {
			super.setName("Caixote"); // se o caixote não estiver arrumado
		}
	}
	
}