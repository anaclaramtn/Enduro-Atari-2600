import base.Elemento;

import java.awt.*;

public class Adversario extends Elemento {
    private int y;
    private int numeroCorAleatorio;
    private int numeroDaDirecao;

    Adversario(int x, int y, int width, int height, int speed, int numeroCorAleatorio, int numeroDaDirecao) {
        this.setPx(x);
        this.y = y;
        this.setLargura(width);
        this.setAltura(height);
        this.setVel(speed);
        this.numeroCorAleatorio = numeroCorAleatorio;
        this.numeroDaDirecao = numeroDaDirecao;
    }
    public void desenharCarrosDia(Graphics2D g, Color c) {

        int x = getPx();
        int y = this.y;

        // deixa o carro menor sem mexer na classe inteira
        int w = (int)(getLargura() * 0.75);
        int h = (int)(getAltura() * 0.60);

        // reposiciona para continuar centralizado
        x += (getLargura() - w) / 2;
        y += (getAltura() - h);

        // cores
        g.setColor(c);
        Color vidro = new Color(40, 40, 70);

        int frente = (int)(w * 0.45);   // frente mais estreita
        int traseira = (int)(w * 0.95); // traseira mais larga (camera baixa)

        int xf = x + (w - frente) / 2;
        int xt = x + (w - traseira) / 2;

        // Corpo mais baixinho
        Polygon corpo = new Polygon();
        corpo.addPoint(xf, y);                       // frente esquerda
        corpo.addPoint(xf + frente, y);              // frente direita
        corpo.addPoint(xt + traseira, y + h);        // traseira direita (bem mais larga)
        corpo.addPoint(xt, y + h);                   // traseira esquerda
        g.fillPolygon(corpo);

        // Vidro: menor, mais recuado e mais "inclinado"
        g.setColor(vidro);
        int vw = (int)(frente * 0.55);
        int vh = (int)(h * 0.22);
        int vx = x + (w - vw) / 2;
        int vy = y + (int)(h * 0.08);                // mais perto do topo => efeito de câmera baixa
        g.fillRect(vx, vy, vw, vh);

        // Rodas: mais baixas e menores para reforçar perspectiva
        g.setColor(Color.BLACK);

        // tamanho das rodas
        int rw = 8;
        int rh = 10;

        // rodas traseiras maiores
        g.fillRect(xt - 6, y + h - rh, rw + 2, rh);
        g.fillRect(xt + traseira - (rw + 2) + 6, y + h - rh, rw + 2, rh);

        // rodas dianteiras bem pequenas (câmera bem baixa)
        g.fillRect(xf - 3, y + (int)(h * 0.10), rw, rh - 2);
        g.fillRect(xf + frente - rw + 3, y + (int)(h * 0.10), rw, rh - 2);
    }

    public void desenharCarrosNoite(Graphics2D g, Color c) {

        int x = getPx();
        int y = this.y;

// escala interna (igual ao seu código)
        int w = (int)(getLargura() * 0.75);
        int h = (int)(getAltura() * 0.60);

// centraliza dentro da hitbox
        x += (getLargura() - w) / 2;
        y += (getAltura() - h);

// cores
        Color corCarro = c;                 // a cor que você passar
        Color corFarol = new Color(255, 50, 50);   // vermelho forte

// perspectiva lateral
        int frente = (int)(w * 0.45);
        int traseira = (int)(w * 0.95);

        int xf = x + (w - frente) / 2;       // frente (não vamos usar)
        int xt = x + (w - traseira) / 2;     // base da traseira

//--------------------------------------------
// 1) DESENHA SOMENTE A TRASEIRA DO CARRO
//--------------------------------------------

        g.setColor(corCarro);
        Polygon traseiraCarro = new Polygon();

// só a parte de baixo do carro (traseira)
        traseiraCarro.addPoint(xt, y + h);                 // canto inferior esquerdo
        traseiraCarro.addPoint(xt + traseira, y + h);      // canto inferior direito
        traseiraCarro.addPoint(xt + (int)(traseira * 0.9), y + (int)(h * 0.75));
        traseiraCarro.addPoint(xt + (int)(traseira * 0.1), y + (int)(h * 0.75));

        g.fillPolygon(traseiraCarro);

//--------------------------------------------
// 2) DESENHA OS FARÓIS TRASEIROS
//--------------------------------------------

// posição dos faróis baseada no formato trapezoidal
        int fh = (int)(h * 0.12);   // altura dos faróis
        int fw = (int)(traseira * 0.20); // largura dos faróis

// y dos faróis (um pouco acima da base)
        int fy = y + h - fh - 2;

// farol esquerdo
        int fxEsq = xt + (int)(traseira * 0.10);

// farol direito
        int fxDir = xt + (int)(traseira * 0.70);

        g.setColor(corFarol);
        g.fillRoundRect(fxEsq, fy, fw, fh, 4, 4);
        g.fillRoundRect(fxDir, fy, fw, fh, 4, 4);
    }


    public int getY (){
        return (int) y;
    }

    public void setY (int y){
        this.y = y;
    }

    public int getNumeroCorAleatorio() {
        return numeroCorAleatorio;
    }

    public void setNumeroCorAleatorio(int numeroCorAleatorio) {
        this.numeroCorAleatorio = numeroCorAleatorio;
    }

    public int getNumeroDaDirecao() {
        return numeroDaDirecao;
    }

    public void setNumeroDaDirecao(int numeroDaDirecao) {
        this.numeroDaDirecao = numeroDaDirecao;
    }
}
