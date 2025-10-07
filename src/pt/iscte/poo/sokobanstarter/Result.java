package pt.iscte.poo.sokobanstarter;

public class Result implements Comparable<Result>{
    
	private String playerName;
    private int level;
    private int moves;

    public Result(String playerName, int level, int moves) {
    	this.playerName = playerName;
    	this.level = level;
    	this.moves = moves;
    }
    
    public String getPlayerName() {
    	return playerName;
    }
    
    public int getLevel() {
    	return level;
    }
    
    public int getMoves() {
    	return moves;
    }
    
    public String toString() {
    	return "Nível: " + level 
				+ " - Jogador: " + playerName 
				+ " - Movimentos: " + moves;
    }

	@Override
	public int compareTo(Result r1) {
		return this.moves - r1.moves; // ordena por ordem crescente de resultados por moves
	}
	
}