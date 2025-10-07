package pt.iscte.poo.sokobanstarter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import pt.iscte.poo.utils.Point2D;

public class FileManager {
	
	private static final String LEVELS_PATH = "levels/";
    private static final String RESULTS_PATH = "results/";
    
    private FileManager() {
        // A classe não deve ser instanciada
    }

	// Lê o ficheiro do nível a jogar para que seja criado o cenário de jogo
	// correspondente com os dados desse nível
    public static void createWarehouseFromFile(String fileName) {	    	
		try {
			File file = new File(LEVELS_PATH + fileName);
			Scanner fileScanner = new Scanner(file);

			// Cria o armazém de acordo com o ficheiro, se este for válido
			if (isFileValid(fileScanner)) {
				fileScanner = new Scanner(file);
				// Para cada caractere do ficheiro é adicionado ao jogo o elemento correspondente
				for (int line = 0; line < 10 && fileScanner.hasNextLine(); line++) {
					String fileLine = fileScanner.nextLine();
					for (int column = 0; column < 10; column++) {
						processCharacter(fileLine.charAt(column), column, line); // adiciona o elemento ao jogo
					}
				}
			} else {
				fileScanner.close(); // fecha o scanner
				return;
			}

			fileScanner.close(); // fecha o scanner
		} catch (FileNotFoundException e) {
			System.err.println("Erro na abertura do ficheiro");
		}
    }
    
    public static void recreateWarehouse(int level){	
 		GameEngine.getInstance().clearWarehouse();         // limpa a grelha de jogo
 		createWarehouseFromFile("level" + level + ".txt"); // cria o cenário de acordo com o nível a jogar
 	}
    
    private static boolean isFileValid(Scanner fileScanner) {
        int lineCount = 0;

        while (fileScanner.hasNextLine()) {
            String fileLine = fileScanner.nextLine();
            lineCount++;

            // Verifica se a linha tem exatamente 10 caracteres
            if (fileLine.length() != 10) {
                System.err.print("Cada linha do ficheiro de configuração do nível deve ter exatamente 10 caracteres.");
                return false;
            }
            
            // Verifica se todos os caracteres na linha são válidos
            for (char ch : fileLine.toCharArray()) {
            	if (!ValidCharacters.isValidCharacter(ch)) {
                    System.err.println("Caractere inválido no ficheiro de configuração do nível: " + ch);
                    return false;
                }
            }
        }

        // Verifica se o ficheiro tem 10 linhas
        if (lineCount != 10) {
            System.err.println("O ficheiro do nível deve ter exatamente 10 linhas.");
            return false;
        }

        return true;
    }   

	// Método para processar um caractere e adicionar os elementos necessários 
	// à lista de elementos do jogo e as suas imagem à GUI nos respetivos pontos
	private static void processCharacter(char character, int column, int line) {
		GameEngine gameEngine = GameEngine.getInstance();
		switch (character) {
		case 'E':
			gameEngine.addElement(new Chao(new Point2D(column, line)));
			gameEngine.setEmpilhadora(new Empilhadora(new Point2D(column, line)));
			gameEngine.addElement(gameEngine.getEmpilhadora());
			break;
		case '#':
			gameEngine.addElement(new Chao(new Point2D(column, line)));
			gameEngine.addElement(new Parede(new Point2D(column, line)));
			break;
		case ' ':
			gameEngine.addElement(new Chao(new Point2D(column, line)));
			break;
		case '=':
			gameEngine.addElement(new Vazio(new Point2D(column, line)));
			break;
		case 'C':
			gameEngine.addElement(new Chao(new Point2D(column, line)));
			gameEngine.addElement(new Caixote(new Point2D(column, line)));
			break;
		case 'X':
			gameEngine.addElement(new Chao(new Point2D(column, line)));
			gameEngine.addElement(new Alvo(new Point2D(column, line)));
			break;
		case 'B':
			gameEngine.addElement(new Chao(new Point2D(column, line)));
			gameEngine.addElement(new Bateria(new Point2D(column, line)));
			break;
		case 'O':
			gameEngine.addElement(new Buraco(new Point2D(column, line)));
			break;
		case 'P':
			gameEngine.addElement(new Chao(new Point2D(column, line)));
			gameEngine.addElement(new Palete(new Point2D(column, line)));
			break;
		case 'M':
			gameEngine.addElement(new Chao(new Point2D(column, line)));
			gameEngine.addElement(new Martelo(new Point2D(column, line)));
			break;
		case '%':
			gameEngine.addElement(new Chao(new Point2D(column, line)));
			gameEngine.addElement(new ParedeRachada(new Point2D(column, line)));
			break;
		case 'T':
			gameEngine.addElement(new Chao(new Point2D(column, line)));
			gameEngine.addElement(new Teleporte(new Point2D(column, line)));
			break;
		}
	}
	
	public static void updateResultsFiles(String playerName, int level, Empilhadora bobcat){		
		addResultToFile(new Result(playerName, level, bobcat.getMoves())); // adiciona o reultado ao ficheiro de resultados
    	writeTop3FilesByLevel();										   // escreve os ficheiros de top 3 resultados por nível
	}

	// Adiciona um resultado ao ficheiro de resultados
	private static void addResultToFile(Result result) {
		try {
			PrintWriter writer = new PrintWriter(new FileWriter(RESULTS_PATH + "results.txt", true));
			writer.write(result.toString() + "\n"); // escreve o resultado no ficheiro de resultados
			writer.close();					        // fecha o PrintWriter
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Escreve os 3 melhores resultados de cada nível num ficheiro por nível
	private static void writeTop3FilesByLevel() {
		// Escreve os ficheiros de top 3 para cada nível até ao nível máximo do jogo
		for (int level = 0; level <= GameEngine.getInstance().getMaxLevel(); level++) {
			List<Result> levelResults = listTop3ForLevel(level); // lista de todos os resultados do nível 'level'

			// Escreve os top 3 resultados no ficheiro correspondente ao nível 'level'
			try {
				PrintWriter writer = new PrintWriter(new FileWriter(RESULTS_PATH + "level" + level + "_top3.txt"));
				writer.write("TOP 3 - NÍVEL " + level + "\n\n"); // escreve o título do ficheiro de top 3 para cada nível

				int position = 1; // posição em que ficou cada jogador
				
				// Para cada nível, é escrito o top 3 de prestações em cada nível
				for (Result result : levelResults) {
					writer.write(position + "º Lugar: " 
							+ result.getPlayerName() 
							+ " (" + result.getMoves() 
							+ " movimementos)\n");
					position++;
				}

				writer.close(); // fecha o PrintWriter
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	// Faz a lista do top 3 resultados para o nível dado
	private static List<Result> listTop3ForLevel(int level) {
		List<Result> results = readResultsFromFile();  // lista de todos os resultados
		List<Result> levelResults = new ArrayList<>(); // lista para colocar todos os resultados do nível 'level' e filtrar pelo top 3
		
		// Filtra os resultados para o nível 'level'
		for (Result result : results) {
			if (result.getLevel() == level) {
				levelResults.add(result);
			}
		}

		// Ordena os resultados por ordem crescente de número de movimentos
		Collections.sort(levelResults);

		// Limita a lista aos top 3 resultados
		if (levelResults.size() > 3) {
			levelResults = levelResults.subList(0, 3);
		}
		
		return levelResults; // devolve a lista do top 3 melhores resultados para um nível
	}
	
	// Lê os resultados do ficheiro e coloca-os numa lista
	private static List<Result> readResultsFromFile() {
		List<Result> results = new ArrayList<>(); // lista para colocar todos os resultados
		try {
			File file = new File(RESULTS_PATH + "results.txt");
			Scanner fileScanner = new Scanner(file);
			
			// Adiciona à lista o resultado presente em cada linha do ficheiro
			while (fileScanner.hasNextLine()) {
				String line = fileScanner.nextLine(); // linha do ficheiro
				String[] parts = line.split(" ");     // divide a linha por espaços
				if (parts.length == 8) {
					String playerName = parts[4];           		   // recolhe o nome do jogador escrito na linha
					int level = Integer.parseInt(parts[1]);            // recolhe o nível escrito na linha
					int moves = Integer.parseInt(parts[7]);			   // recolhe o número de movimentos escreito na linha
					results.add(new Result(playerName, level, moves)); // adiciona o resultado à lista
				}
			}

			fileScanner.close(); // fecha o scanner
		} catch (FileNotFoundException e) {
			System.err.println("Erro na leitura do ficheiro results.txt");
		}

		return results; // devolve a lista com todos os resultados
	}
}
