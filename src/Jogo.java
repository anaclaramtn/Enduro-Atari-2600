import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class Jogo extends JFrame {
    // Instanciacao de cenario e jogador, a tela aqui foi desclarada, mas sera instanciada abaixo
    private JPanel tela;
    private Cenario cenario = new Cenario(640, 480);
    private CarroJogador jogador = new CarroJogador(294, 290, 53, 53, 30);

    // Instanciando numeros aleatorios para controle
    private Adversario adversario;
    private Random numeroCorAleatorio = new Random();
    private Random numeroDaDirecao = new Random();
    // Instanciando uma lista para guardar os carros desenhados na pista
    private ArrayList<Adversario> listaCarrosAdversarios = new ArrayList<Adversario>();

    //Informações que são do Ranking.txt
    private String nomeJogador;
    private ArrayList<Jogador> listaJogadores = new  ArrayList<>(); // Precisamos essa lista para guardar
    //os jogadores lidos no arquivo

    // Contadores da HUD
    private int contadorDePontos;
    private int carrosQueFaltam = 50;
    private int contadorDeVoltas;

    private boolean jogando = true;
    private int fps = 1000/20; //  20 fps

    // Variáveis de controle de timer
    private double tempoTotal = 0.0;
    private long ultimoTempo = System.currentTimeMillis();

    // Instanciando a tela
    Jogo () {
        tela = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                // Aqui comecam os desenhos dos componentes do jogo utilizando o objeto g de Graphics

                g.setColor(Color.BLACK);
                g.fillRect(0, 0, getWidth(), getHeight());

                // Condicionais para definir o turno
                if (tempoTotal < 10.0) {
                    Color ceu = new Color(65,105,225);
                    g.setColor(ceu);
                    g.fillRect(40, 0, getWidth() - 80, getHeight() / 3);
                    Color chao = new Color(0, 80, 0);
                    g.setColor(chao);
                    g.fillRect(40, getHeight() / 3, getWidth() - 80, getHeight() - 235);
                } else if (tempoTotal > 10 && tempoTotal < 30){
                    Color midnight = new Color(5,0,40);
                    g.setColor(midnight);
                    g.fillRect(40, 0, getWidth() - 80, getHeight() / 3);

                    g.setColor(Color.black);
                    g.fillRect(40, getHeight() / 3, getWidth() - 80, getHeight() - 235);
                } else {
                    Color ceu = new Color(65,105,225);
                    g.setColor(ceu);
                    g.fillRect(40, 0, getWidth() - 80, getHeight() / 3);
                    Color chao = new Color(0, 80, 0);
                    g.setColor(chao);
                    g.fillRect(40, getHeight() / 3, getWidth() - 80, getHeight() - 235);
                }


                cenario.desenhar((Graphics2D) g);
                jogador.desenhar((Graphics2D) g);

                // Fonte que importamos para colocar na HUD
                try {
                    InputStream is = getClass().getResourceAsStream("./Resources/Racing_1.otf");
                    Font fonteRacing = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(15f);

                    g.setFont(fonteRacing);
                } catch (IOException | FontFormatException e) {
                    throw new RuntimeException(e);
                }

                // Desenho da HUD
                g.setColor(Color.RED);
                g.fillRect(50, 30, 120, 30);
                g.setColor(Color.WHITE);
                g.drawString("Score    " + contadorDePontos, 60, 50);

                g.setColor(Color.RED);
                g.fillRect(50, 80, 150, 30);
                g.setColor(Color.WHITE);
                g.drawString("Carros    " + carrosQueFaltam, 60, 100);

                g.setColor(Color.WHITE);
                g.drawString(String.format("%.2f", tempoTotal), tela.getWidth()/2 - 20, tela.getHeight() - 420);


                // Bloco responsavel por desenhar os carros da lista
                int i = 0;
                while (i < listaCarrosAdversarios.size()) {
                    Adversario a = listaCarrosAdversarios.get(i);

                    if (a.getNumeroCorAleatorio() == 0) {
                        a.desenharCarrosDia((Graphics2D) g, Color.MAGENTA);
                    } if (a.getNumeroCorAleatorio() == 1) {
                        a.desenharCarrosDia((Graphics2D) g, Color.BLUE);
                    } if (a.getNumeroCorAleatorio() == 2) {
                        a.desenharCarrosDia((Graphics2D) g, Color.GREEN);
                    } if (a.getNumeroCorAleatorio() == 3) {
                        a.desenharCarrosDia((Graphics2D) g, Color.YELLOW);
                    }

                    // vai chamar esse metodo e vai aplicar todas as colisões
                    if (verificarCondicoes(a) == false) {
                        i++;
                    }
                }

                if (carrosQueFaltam == 0) {
                    reiniciarFase();
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

        // Configuracao das teclas estilo apostila, utilizando um keylistener
        super.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyReleased(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                int tecla = e.getKeyCode();

                switch (tecla) {
                    case KeyEvent.VK_ESCAPE:
                        jogando = false;
                        dispose();
                        break;

                    case KeyEvent.VK_LEFT:
                        jogador.velX -= jogador.accel;
                        break;

                    case KeyEvent.VK_RIGHT:
                        jogador.velX += jogador.accel;
                        break;
                }
            }
        });
    }

    // Animação
    public void animando() {
        long prxAtualizado = 0; // Controla o frame
        int tempoDesenhoCarro = 0; // Desenha meu carro a cada x tempo
        long ultimoFrame = System.currentTimeMillis();

        while(jogando){
            long agora = System.currentTimeMillis();
            double dt = (agora - ultimoTempo) / 1000.0;
            ultimoTempo = agora;

            tempoTotal += dt;
            if (agora >= prxAtualizado) {

                // atauliza a posicao de jogador
                jogador.x += jogador.velX;// movimentacao do carro principal
                jogador.velX *= jogador.fric;   // resistencia pra nao correr pra longe -> "compensacao"

                limitarJogadorNaPista();


                // Atualiza a pista porque preciso mudar a sensacao de velocidade da pista
                cenario.setVelocidadeScroll(24.0 + tempoTotal * 2);
                cenario.atualizar();

                if (tempoDesenhoCarro % 60 == 0) {
                    // Vou criar 1 carro adversario e adicionar ele ao meu array
                    listaCarrosAdversarios.add
                            (this.adversario = new Adversario(
                                    315,150, 0,0,
                                    1 + (int) (tempoTotal * 0.3),
                                    numeroCorAleatorio.nextInt(4),
                                    numeroDaDirecao.nextInt(3)));
                }


                int i = 0;
                while (i < listaCarrosAdversarios.size()) {
                    Adversario adversario = listaCarrosAdversarios.get(i);
                    // Animacao de distancia do carro
                    if (adversario.getLargura() < 53 && adversario.getAltura() < 53) {
                        adversario.setLargura(adversario.getLargura() + 1);
                        adversario.setAltura(adversario.getAltura() + 1);
                    }if (adversario.getLargura() < 53 && adversario.getAltura() < 53) {
                        adversario.setLargura(adversario.getLargura() + 1);
                        adversario.setAltura(adversario.getAltura() + 1);
                    }

                    if (adversario.getNumeroDaDirecao() == 0) {
                        adversario.setPx(adversario.getPx() - 1);
                    }
                    if (adversario.getNumeroDaDirecao() == 1) {
                        adversario.setPx(adversario.getPx() + 1);
                    }
                    if (adversario.getNumeroDaDirecao() == 2) {
                        adversario.setPx(adversario.getPx());
                    }
                    adversario.setY(adversario.getY() + adversario.getVel());

                    if (!verificarCondicoes(adversario)) {
                        i++;
                    }
                }
                tela.repaint();
                // Incrementa meu controlador de tempo de desenho dos carros
                tempoDesenhoCarro++;
                // Calcula tempo pro próximo frame
                prxAtualizado = System.currentTimeMillis() + this.fps;
            }
        }
    }

    public boolean verificarCondicoes(Adversario adversario) {
        // Vai checar se colidiu com o jogador
        if (colisaoJogador(adversario)) {
            listaCarrosAdversarios.remove(adversario);
            carrosQueFaltam--;
            System.out.println("Colidiu com o jogador");
            System.out.println("Pontuação atual (se bater, nao aumenta, pelo contrario, é game over): " + contadorDePontos);
            System.out.println("Carros faltando para terminar o circuito: " + carrosQueFaltam);
            this.jogando = false;

            // Leitura do arquivo
            FileReader arquivo = null;
            try {
                arquivo = new FileReader(".\\src\\Resources\\Ranking.TXT");
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            Scanner ler = new Scanner(arquivo);
            while(ler.hasNextLine()) {
                String linha = ler.nextLine();
                String[] linhaSplit = linha.split(";");
                String nome = linhaSplit[0];
                int numero = Integer.parseInt(linhaSplit[1]);
                listaJogadores.add(new Jogador(nome,numero));
            }

            // Lendo o arquivo antes de montar o top 10 para verificar se nao existe algum nome igual ao que foi digitado previamente ou vazio
            while (true) {
                this.nomeJogador = JOptionPane.showInputDialog("Digite aqui o seu nome:");

                while(true) {
                    for (int i = 0; i < listaJogadores.size(); i++) {
                        Jogador e = listaJogadores.get(i);
                        if (e.getNome().equals(nomeJogador)) {
                            this.nomeJogador = JOptionPane.showInputDialog("Nome já inserido. Digite novamente: ");
                            i = 0;
                        }
                    }
                    break;
                }

                if (!this.nomeJogador.equals("")) {
                    break;
                }
            }
            dispose();
        }

        // Vai checar se colidiu com o fundo
        if (colisaoCenario(adversario)) {
            listaCarrosAdversarios.remove(adversario);
            contadorDePontos += 10;
            carrosQueFaltam--;
            System.out.println("Carro adversario olidiu com o fundo");
            System.out.println("Pontuação atual (aumenta 10 a cada carro ultrapassado): " + contadorDePontos);
            System.out.println("Carros faltando para terminar o circuito: " + carrosQueFaltam);

        } else {
            return false;
        } return true;
    }

    public boolean colisaoJogador(Adversario carro) {
        boolean colisaox = false;
        boolean colisaoy = false;
        //Colisao em x
        if (this.jogador.getX() <= carro.getPx() + carro.getLargura() && jogador.getX() + jogador.getLargura() >= carro.getPx()) {
            colisaox = true;
        }
        //Colisao em y
        if (jogador.getPy() <= carro.getY() + carro.getAltura() && jogador.getPy() + jogador.getAltura() >= carro.getY()) {
            colisaoy = true;
        }
        //Ambas tiverem certas
        if (colisaox == true && colisaoy == true) {
            return true;
        } return false;
    }

    public boolean colisaoCenario(Adversario carro) {
        if (carro.getY() + carro.getAltura() >= 353) {
            return true;
        } return false;
    }

    public void reiniciarFase() {
        //Aumenta a quantidade de carros
        carrosQueFaltam = 200 + (50 * (contadorDeVoltas - 1));
        //Aumenta o contador que exibi em qual volta ele está
        contadorDeVoltas ++;

        //Vai checar se o jogo acabou
        if (contadorDeVoltas == 100) {
            this.jogando = false;
        }
    }
    private void limitarJogadorNaPista() {
        int limiteEsquerdo = 140;
        int limiteDireito = getWidth() - 140;

        // Corrige para considerar a largura do carro
        int xMin = limiteEsquerdo;
        int xMax = limiteDireito - jogador.getLargura();

        // Impede que o jogador passe desses limites
        if (jogador.x < xMin) {
            jogador.x = xMin;
            jogador.velX = 0;
        }
        if (jogador.x > xMax) {
            jogador.x = xMax;
            jogador.velX = 0;
        }
    }

    public double getTempoTotal() {
        return tempoTotal;
    }

    public ArrayList<Jogador> getListaJogadores() {
        return listaJogadores;
    }

    public static void main(String[] args) throws IOException {
        Jogo j = new Jogo();
        j.animando();

        Ranking r = new Ranking(j.nomeJogador, j.contadorDePontos, j.listaJogadores);

        DesenhaRanking dr = new DesenhaRanking(r.getListaTop10());

        System.out.println(r.getListaTop10());
    }
}
