package pt.iscte.poo.sokobanstarter;

public enum ValidCharacters {

	EMPILHADORA('E'), 
	CAIXOTE('C'), 
	PALETE('P'), 
	PAREDE('#'), 
	CHAO(' '),
	VAZIO('='),
	ALVO('X'),
	BATERIA('B'),
	BURACO('O'),
	MARTELO('M'),
	PAREDE_RACHADA('%'),
	TELEPORTE('T');
	

	private final char symbol;

	ValidCharacters(char symbol) {
		this.symbol = symbol;
	}

	public char getSymbol() {
		return symbol;
	}

	public static boolean isValidCharacter(char ch) {
		for (ValidCharacters element : values()) {
			if (element.symbol == ch) {
				return true; // retorna verdadeiro se o caractere for válido
			}
		}
		return false; // retorna falso caso contrário
	}
}
