package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public interface TransportElement {

	void transport(MovableElement element);	
	
	Point2D getPositionAfterTransport(Direction direction);
	
}
