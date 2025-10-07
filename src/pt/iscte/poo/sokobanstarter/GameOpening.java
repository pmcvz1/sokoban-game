package pt.iscte.poo.sokobanstarter;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class GameOpening { 
	
	public static final int FRAME_WIDTH = 515;
	public static final int FRAME_HEIGHT = 555;
	
	private GameOpening() {
		// A classe não deve ser instanciada
    }
	
	public static JFrame launchOpeningImage() {
        // Cria uma instância de JFrame
        JFrame openingFrame = new JFrame("SokobanOpening");
        
        // Configura o tamanho
        openingFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);

        // Cria um JPanel que contém a imagem e a mensagem
        JPanel openingPanel = new JPanel(new BorderLayout());
        
        // Adiciona a imagem ao JPanel
        JLabel openingLabel = new JLabel(new ImageIcon("images/SokobanOpening.png"));
        openingPanel.add(openingLabel, BorderLayout.CENTER);
        
        // Adiciona a mensagem ao JPanel
        JLabel messageLabel = new JLabel("Bem-vindo ao Sokoban! Para começar a jogar, insira o seu nome", SwingConstants.CENTER);
        messageLabel.setForeground(Color.WHITE);  			// cor do texto
        openingPanel.add(messageLabel, BorderLayout.NORTH); // posição do texto

        // Adiciona o JPanel ao JFrame
        openingFrame.add(openingPanel);

        // Configuração da cor do JPanel
        openingPanel.setBackground(Color.BLACK);

        // Torna o JFrame visível
        openingFrame.setVisible(true);

        // A aplicação é encerrada quando a janela é fechada
        openingFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        return openingFrame;
    }
	
}
