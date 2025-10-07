package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public interface MovableElement {
	
    void move();

    boolean isMoveValid(Point2D newPosition, Direction direction);
    
    void enterElement(GameElement element, Direction direction);

}
