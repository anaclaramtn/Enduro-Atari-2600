import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Ranking {
    private ArrayList<Jogador> listaTop10 =  new ArrayList<>();

    Ranking(String nomeJogador, int pontosJogador, ArrayList<Jogador> listaDePessoas) throws IOException {
        listaDePessoas.add(new Jogador(nomeJogador, pontosJogador));
        Collections.sort(listaDePessoas);

        if (listaDePessoas.size()>=10){
            for (int i = 0; i < 10; i++) {
                listaTop10.add(listaDePessoas.get(i));
            }
        } else {
            for (Jogador j : listaDePessoas) {
                listaTop10.add(j);
            }
        }

        FileWriter fw = new FileWriter(".\\src\\Resources\\Ranking.TXT", false);

        for (Jogador jogador : listaTop10) {
            fw.write(jogador.getNome() + ";" + jogador.getNumero() + "\n");
        }
        fw.close();
    }

    @Override
    public String toString() {
        String resultado = "";
        for(Jogador j : listaTop10) {
            resultado += j.getNome() + ";" + j.getNumero() + "\n";
        }
        return resultado;
    }

    public ArrayList<Jogador> getListaTop10() {
        return listaTop10;
    }

    public void setListaTop10(ArrayList<Jogador> listaTop10) {
        this.listaTop10 = listaTop10;
    }
}
