import base.Elemento;

import java.awt.*;

public class CarroJogador extends Elemento {

    public float x;
    public float velX = 10f;
    public float accel = 4.0f;
    public float fric  = 0.8f;

    CarroJogador(int x, int y, int width, int height, int speed){
        this.x = x;
        this.setPy(y);
        this.setLargura(width);
        this.setAltura(height);
        this.setVel(speed);
    }

    public int getX() {
        return (int)x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void desenhar(Graphics2D g) {

        int x = getX();
        int y = getPy();
        int w = getLargura();
        int h = getAltura();

        // criamos um "carro menor" dentro da hitbox sem mover a hitbox
        int cw = (int)(w * 0.75);   // largura real do carro desenhado
        int ch = (int)(h * 0.60);   // altura real desenhada

        int cx = x + (w - cw) / 2;  // centraliza dentro da hitbox
        int cy = y + (h - ch);      // encosta embaixo sem estourar hitbox

        // corpo
        g.setColor(new Color(200, 40, 40));
        Color vidro = new Color(40, 40, 70);

        int frente = (int)(cw * 0.45);
        int traseira = (int)(cw * 0.95);

        int xf = cx + (cw - frente) / 2;
        int xt = cx + (cw - traseira) / 2;

        Polygon corpo = new Polygon();
        // Tudo isso desenha o carro, os adversarios sao desenhados da mesma maneira, mas em outra classe e com outras cores
        corpo.addPoint(xf, cy);
        corpo.addPoint(xf + frente, cy);
        corpo.addPoint(xt + traseira, cy + ch);
        corpo.addPoint(xt, cy + ch);
        g.fillPolygon(corpo);

        g.setColor(vidro);
        int vw = (int)(frente * 0.55);
        int vh = (int)(ch * 0.22);
        int vx = cx + (cw - vw) / 2;
        int vy = cy + (int)(ch * 0.08);
        g.fillRect(vx, vy, vw, vh);

        g.setColor(Color.BLACK);
        int rw = 8;
        int rh = 10;

        g.fillRect(xt - 6, cy + ch - rh, rw + 2, rh);
        g.fillRect(xt + traseira - (rw + 2) + 6, cy + ch - rh, rw + 2, rh);

        g.fillRect(xf - 3, cy + (int)(ch * 0.10), rw, rh - 2);
        g.fillRect(xf + frente - rw + 3, cy + (int)(ch * 0.10), rw, rh - 2);
    }


}
