package pt.iscte.poo.sokobanstarter;

import java.awt.event.KeyEvent;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.observer.Observed;
import pt.iscte.poo.observer.Observer;
import pt.iscte.poo.utils.Point2D;

public class GameEngine implements Observer {

	// Dimensoes da grelha de jogo
	public static final int GRID_HEIGHT = 10;
	public static final int GRID_WIDTH = 10;
	public static final int MAX_LEVEL = 6;

	private static GameEngine INSTANCE;   		 // Referencia para o unico objeto GameEngine (singleton)
	private ImageMatrixGUI gui;  		  	     // Referencia para ImageMatrixGUI (janela de interface com o utilizador) 
	private List<GameElement> GameElementsList;  // Lista de elementos do jogo
	private Empilhadora bobcat;	          		 // Referencia para a empilhadora
	private int level;                           // Nível em que se encontra o jogador
	private String playerName;					 // Nome do jogador

	// Construtor
	private GameEngine() {
		GameElementsList = new ArrayList<>();
	}

	// Implementacao do singleton para o GameEngine
	public static GameEngine getInstance() {
		if(INSTANCE == null)
			return INSTANCE = new GameEngine();
		return INSTANCE;
	}

	public ImageMatrixGUI getGui() {
		return gui;
	}
	
	public List<GameElement> getGameElementsList() {
		return new ArrayList<>(GameElementsList);
	}
	
	public Empilhadora getEmpilhadora() {
		return bobcat;
	}
	
	public List<GameElement> getElementsAtPosition(Point2D position) {
	    List<GameElement> elementsAtPosition = new ArrayList<>();

	    for (GameElement element : GameElementsList) {
	        if (element.getPosition().equals(position)) {
	            elementsAtPosition.add(element);
	        }
	    }

	    return elementsAtPosition;
	}
	
	public int getLevel() {
		return level;
	}
	
	public String getPlayer() {
		return playerName;
	}
	
	public int getMaxLevel() {
		return MAX_LEVEL;
	}
	
	public void setEmpilhadora(Empilhadora empilhadora) {
		bobcat = empilhadora;
	}
	
	public void clearWarehouse() {
		GameElementsList.clear(); // limpa a lista de elementos
		gui.clearImages();		  // limpa as imagens da grelha de jogo
	}
	
	public void removeElement(GameElement element){
		GameElementsList.remove(element); // remove o elemento da lista de elementos
		gui.removeImage(element);		  // remove a imagem do elemento da GUI
	}
	
	public void removeElementFromList(GameElement element){
		GameElementsList.remove(element); // remove o elemento da lista de elementos
	}
	
	public void addElement(GameElement element){
		GameElementsList.add(element); // adiciona o elemento da lista de elementos
		gui.addImage(element);         // adiciona a imagem do elemento da GUI
	}
	
	public void start() {	
		gameInitializer();		   // inicia o jogo
		restartLevelOnBackspace(); // reinicia o nível ao premir a tecla BackSpace		
		handleGameLoss();		   // reinicia o nível em caso de derrota			
		handleGameWin();		   // passa ao próximo nível, se houver, em caso de vitória
		gui.update();			   // redesenha a lista de ImageTiles na GUI				
		setStatusMessage();	       // escreve as informações de jogo na StatusBar	
	}
	
	// O metodo update() é invocado automaticamente sempre que o utilizador carrega numa tecla
	// No argumento do metodo é passada uma referencia para o objeto observado (neste caso a GUI)
	@Override
	public void update(Observed source) {
		int key = gui.keyPressed(); // obtem o codigo da tecla pressionada

		// Se for premida a tecla Back Space, reinicia o nível
		if (key == KeyEvent.VK_BACK_SPACE)
			start();

		// Se for premida uma tecla de uma seta de direção, a empilhadora move-se nessa direção
		if (key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN || key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT)
			bobcat.move(); // move a empilhadora
	}
	
	private void gameInitializer() {
		// Se a GUI ainda não foi inicializada, estamos no começo do jogo
		// Por isso, é inicializada a GUI, o cenário de abertura e pede-se o nome ao jogador
		if (gui == null) {
			launchGUIAndOpening(); 										   // lança a abertura e cria a gui
			level = 0; 			   										   // nível inicial do jogo
			FileManager.createWarehouseFromFile("level" + level + ".txt"); // cria o cenário para o primeiro nível
		}
	}
	
	private void restartLevelOnBackspace() {
		// Se for premida a tecla Back Space, reinicia o nível
		if (gui.keyPressed() == KeyEvent.VK_BACK_SPACE)
			FileManager.recreateWarehouse(level); // cria o cenário para o mesmo nível
	}

	private void handleGameLoss() {
		// Se a empilhadora ficar sem energia e os caixotes ainda não estiverem arrumados, o nível é reiniciado por derrota
		if (bobcat.getEnergy() <= 0 && !bobcat.areAllTargetElementsComplete(GameElementsList)) {
			// Escrever as informações de jogo na StatusBar e a mensagem de derrota
			setStatusMessage(); 
			gui.setMessage("Perdeste...\n" + "A Empilhadora ficou sem energia.\n" + "Clica em 'OK' para recomeçar o nível:");
			FileManager.recreateWarehouse(level); // cria o cenário de jogo do nível atual
		}

		// Se não existirem caixotes suficientes para passar de nível, o nível é reiniciado por derrota
		if (!(bobcat.isNumberOfWinningElementsValid(GameElementsList))) {
			// Escreve a mensagem de derrota
			gui.setMessage("Perdeste...\n" + "Já não tens caixotes suficientes para passar de nível.\n" + "Clica em 'OK' para recomeçar o nível:");
			FileManager.recreateWarehouse(level); // cria o cenário de jogo do nível atual
		}

		// Se o jogador ficar sem empilhadora, o nível é reiniciado por derrota
		if (!(bobcat.existsEmpilhadora(GameElementsList))) {
			// Escreve a mensagem de derrota
			gui.setMessage("Perdeste...\n" + "A Empilhadora caiu no buraco.\n" + "Clica em 'OK' para recomeçar o nível:");
			FileManager.recreateWarehouse(level); // cria o cenário de jogo do nível atual
		}
	}
	
	private void handleGameWin() {
		// Se os alvos estiverem completos, passa de nível
		if (bobcat.areAllTargetElementsComplete(GameElementsList)) {
			setStatusMessage();   // escreve as informações de jogo na StatusBar
			FileManager.updateResultsFiles(playerName, level, bobcat); // cria os ficheiros de resultados
			
			// Se tiver passado o último nível, completa o jogo Sokoban
			if (level == MAX_LEVEL) {
				gui.setMessage("Concluiste o nível!\n" + "Movimentos: " + bobcat.getMoves() + "\n" + "Clica em 'OK' para continuar:");
				gui.setMessage("PARABÉNS! Venceste o jogo!\n" + "Clica em 'OK' sair");
				gui.setMessage("Jogo terminado.");
				gui.dispose();
			// Se não for o último nível, passa para o próximo nível
			} else {
				// Escreve a mensagem de vitória de nível
				gui.setMessage("Passaste ao próximo nível!\n" + "Movimentos: " + bobcat.getMoves() + "\n" + "Clica em 'OK' para continuar:");
				level++; // próximo nível
				FileManager.recreateWarehouse(level); // cria o cenário do próximo nível
			}			
		}
	}
	
	private void launchGUIAndOpening() {
		// Setup inicial da janela que faz a interface com o utilizador
		gui = ImageMatrixGUI.getInstance();    		// obtem instancia ativa de ImageMatrixGUI	
		gui.setSize(GRID_HEIGHT, GRID_WIDTH);  		// configura as dimensoes 
		gui.registerObserver(this);            		// regista o objeto ativo GameEngine como observador da GUI
		
		JFrame openingFrame = GameOpening.launchOpeningImage();	// cria a imagem de abertura do jogo	
		
		while (true) {
		    // Pergunta o nome ao jogador
		    playerName = gui.askUser("Digite o seu nome de jogador:");

		    // Se o utilizador premiu "Cancelar", o jogo termina
		    if (playerName == null) {
		        gui.setMessage("Jogo terminado."); // mensagem de fim de jogo  
		        openingFrame.dispose();			   // fecha o JFrame
		        return;							   // encerra o método
		    }

		    // Remove espaços em branco no início e no final do nome
		    playerName = playerName.trim();
		    

		    // Verifica se o nome está vazio
		    if (!playerName.isEmpty() && !playerName.contains(" ")) {
		        break; // nome válido, quebra o ciclo while e começa o jogo
		    }

		    // Se o nome estiver vazio, exibe uma mensagem de aviso
		    gui.setMessage("O nome não pode ser vazio, nem pode conter espaços.");
		}
		
		gui.setMessage("Objetivo: Arrumar todos os Caixotes nos Alvos do jogo.\n" + "Para reiniciar o nível, pressiona Backspace.");
	    openingFrame.dispose();	// fecha o JFrame
		gui.go();               // lanca a GUI
	}
	
	private void setStatusMessage(){
		gui.setStatusMessage("Nível: " + level 
				+ " - Jogador: " + playerName 
				+ " - Movimentos: " + bobcat.getMoves()
				+ " - Energia: " + bobcat.getEnergy()
				+ " - Habilidade Especial: " + bobcat.isCanBreakElementsActivated());
	}
	
}	