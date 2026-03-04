import base.CenarioPadrao;
import java.awt.*;

public class Cenario extends CenarioPadrao {

    private double pontoFugaX;

    private double scroll = 0.0;

    private int larguraTela;
    private int alturaTela;

    private long ultimoTempoMs = -1;

    private double velocidadeScrollLinhasPorSegundo = 24.0;

    public Cenario(int largura, int altura) {
        super(largura, altura);
        this.larguraTela = largura;
        this.alturaTela = altura;
    }

    @Override
    public void carregar() {
        ultimoTempoMs = System.currentTimeMillis();
    }

    @Override
    public void descarregar() {
    }

    @Override
    public void atualizar() {
        // Serve somente para atualizar o scroll
        long agora = System.currentTimeMillis();
        if (ultimoTempoMs != -1) {
            double dt = (agora - ultimoTempoMs) / 1000.0;
            scroll += velocidadeScrollLinhasPorSegundo * dt;
        }
        ultimoTempoMs = agora;
    }

    @Override
    public void desenhar(Graphics2D g) {

        int horizonteY = alturaTela / 3 - 10;

        int centroTela = larguraTela / 2;

        int linhas = 28;
        int alturaLinha = 7;

        int larguraTopo = 30;
        int larguraBase = 400;

        for (int i = 0; i < linhas; i++) {

            double pos = (i + scroll) / (double) linhas;
            double profundidade = pos - Math.floor(pos);

            int larguraLinha = (int) (larguraTopo + profundidade * (larguraBase - larguraTopo));

            int y = (int) (horizonteY + profundidade * (linhas * alturaLinha));
            if (y >= alturaTela - alturaLinha) y = alturaTela - alturaLinha;

            pontoFugaX = centroTela;

            int centroLinha = (int) (pontoFugaX);

            int xEsq = centroLinha - larguraLinha / 2;
            int xDir = centroLinha + larguraLinha / 2;

            int corBorda = clamp((int)(40 + profundidade * 160), 0, 255);
            g.setColor(new Color(corBorda, corBorda, corBorda));

            int larguraBorda = 6;
            g.fillRect(xEsq, y, larguraBorda, alturaLinha);
            g.fillRect(xDir - larguraBorda, y, larguraBorda, alturaLinha);
        }
    }

    private int clamp(int v, int a, int b) {
        return Math.max(a, Math.min(b, v));
    }

    public double getPontoFugaX() {
        return pontoFugaX;
    }

    public void setVelocidadeScroll(double v) {
        this.velocidadeScrollLinhasPorSegundo = v;
    }

    public double getVelocidadeScroll() {
        return velocidadeScrollLinhasPorSegundo;
    }

}
