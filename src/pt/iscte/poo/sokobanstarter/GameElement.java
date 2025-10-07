package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public abstract class GameElement implements ImageTile{
	
	private Point2D Point2D;
	private String name;
	private int layer;
	
	public GameElement(Point2D Point2D, String name, int layer){
		this.Point2D = Point2D;	
		this.name = name;
		this.layer = layer;
	}

	@Override
	public Point2D getPosition() {
		return Point2D;
	}
	
	@Override
	public String getName(){
		return name;
	} 
	
	@Override
	public int getLayer(){
		return layer;
	}
	
	public void setPosition(Point2D newPosition){
		Point2D = newPosition;
	}
	
	public void setName(String newName){
		name = newName;
	}
	
}
