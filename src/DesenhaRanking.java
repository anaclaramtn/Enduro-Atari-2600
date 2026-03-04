import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class DesenhaRanking extends JFrame {
    private JPanel tela;
    private Cenario cenario = new Cenario(640, 480);

    DesenhaRanking(ArrayList<Jogador> ldj) {
        tela  =  new JPanel () {
            @Override
            public void paintComponent(Graphics g) {
                int largura = getWidth();
                int altura = getHeight();

                // Configs
                int margem = 40;
                int borda = 8;
                int tamanhoQuadro = 24;
                int linhasFaixa = 2;

                g.setColor(Color.BLACK);
                g.fillRect(0, 0, largura, altura);

                int x = margem;
                int y = margem;
                int w = largura - margem * 2;
                int h = altura - margem * 2;

                g.setColor(Color.WHITE);
                g.fillRect(x, y, w, borda);                // topo
                g.fillRect(x, y + borda, borda, h - borda * 2); // esquerda
                g.fillRect(x + w - borda, y + borda, borda, h - borda * 2); // direita
                g.fillRect(x, y + h - borda, w, borda);    // base

                int innerX = x + borda;
                int innerY = y + borda;
                int innerW = w - borda * 2;

                int qtdH = innerW / tamanhoQuadro;

                for (int linha = 0; linha < linhasFaixa; linha++) {
                    for (int col = 0; col < qtdH; col++) {

                        boolean branco = ((linha + col) % 2 == 0);
                        g.setColor(branco ? Color.WHITE : Color.BLACK);

                        int qx = innerX + col * tamanhoQuadro;
                        int qy = innerY + linha * tamanhoQuadro;

                        g.fillRect(qx, qy, tamanhoQuadro, tamanhoQuadro);

                        try {
                            InputStream is = getClass().getResourceAsStream("./Resources/Racing_1.otf");
                            Font fonteRacing = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(30f);

                            g.setFont(fonteRacing);

                        } catch (IOException | FontFormatException e) {
                            throw new RuntimeException(e);
                        }

                        g.setColor(Color.WHITE);
                        g.drawString("RANKING", getWidth()/2 - 90, getHeight() - 320);


                        try {
                            InputStream is = getClass().getResourceAsStream("./Resources/Racing_1.otf");
                            Font fonteRacing = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(15f);

                            g.setFont(fonteRacing);

                        } catch (IOException | FontFormatException e) {
                            throw new RuntimeException(e);
                        }

                        int yBase = getHeight() + 50;

                        if (ldj.size() <= 5) {
                            for (int i = 0; i < ldj.size(); i++) {
                                String linhaJogador = (i + 1) + "º  " + ldj.get(i).getNome() + " - " + ldj.get(i).getNumero();

                                g.drawString(linhaJogador, getWidth()/2 - 200, getHeight() - 290 + (i * 30));
                            }
                        } else {
                            for (int i = 0; i < 5; i++) {
                                String linhaJogador = (i + 1) + "º  " + ldj.get(i).getNome() + " - " + ldj.get(i).getNumero();

                                g.drawString(linhaJogador, getWidth()/2 - 200, getHeight() - 290 + (i * 30));
                            }
                            for (int i = 5; i < ldj.size(); i++) {
                                String linhaJogador = (i + 1) + "º  " + ldj.get(i).getNome() + " - " + ldj.get(i).getNumero();

                                g.drawString(linhaJogador, getWidth()/2 + 40, getHeight() - 440 + (i * 30));
                            }
                        }

                    }
                }

            }
        };
        getContentPane().add(tela);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(640, 480);
        setVisible(true);
        setResizable(false);

        cenario.carregar();
        tela.repaint();
    }
}
