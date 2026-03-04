public class Jogador implements Comparable<Jogador>{
    private String nome;
    private int numero;

    Jogador(String nome, int numero) {
        this.nome = nome;
        this.numero = numero;
    }

    @Override
    public int compareTo(Jogador o) {
        if (o.numero - this.numero == 0) {
            return this.nome.compareTo(o.nome);
        }
        return o.numero - this.numero;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    @Override
    public String toString() {
        return nome + ";" + numero;
    }
}
