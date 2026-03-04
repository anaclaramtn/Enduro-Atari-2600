Enduro ATARI 2000 - Avalicação Final da Disciplina T164-54 Programação Orientada a Objetos, orientada pelo professor Gilson Pereira.

O Enduro é um jogo onde o jogador deve correr incessantemente em uma pista, desviando e ultrapassando os inúmeros carros em seu caminho. Para passar de nível, o jogador deve ultrapassar certa quantidade de carros antes que o dia termine, enquanto o tempo passa o jogador deve enfrentar mudanças como neblina, neve, raios do sol e o próprio anoitecer.
Todavia, na nossa adaptação (minha, Ana Clara, e do Inácio Araripe @github), vamos colocar outro sistema de pontuação, onde o jogador perde quando bate em um carro, diminui de velocidade quando colide com as paredes da pista, desacelerando bastante. O sistema de pontuação no jogo é feito de forma continua, os pontos aumentam conforme tua velocidade aumenta e aumentam numa progressao menor conforme tua velocidade diminui. A ideia do jogo é ter a maior quantidade de pontos possíveis antes do anoitecer do dia da corrida.

Para esse projeto, temos que estudar uma apostila, onde temos documentações sobre como criar jogos simples utilizando bibliotecas do JDK, principalmente Swing e AWT (Abstract Window Toolkit), e os jogos que acreditamos que sirvam melhor como base para o nosso jogo são: Asteroids e Space Invaders, principalmente o primeiro.O Asteroids tem múltiplos objetos aparecendo de maneira bem suave na tela, movimentos contínuos, diferentes tamanhos de asteroids, e loops bem úteis para entender como funciona, o que nos deu uma base boa para entender as classes e os objetos que manipulamos.
Uma requisição extra dada pelo professor que ministra a cadeira (Gilson Pereira) foi o sistema de rank, que será implementado por meio de manipulação de arquivo em Java.
Funciona como um mini-formulário na própria janela Swing.

Para abrir o projeto, copiar todos os arquivos, criar um novo projeto no Intellij e colar na pasta src, criada assim que um novo projeto é criado.


Organização do projeto:
Lembrando, não vamos importar diretamente os arquivos disponibilizados no GitHub para o projeto, porque, para melhor organização, acabaríamos colocando dentro de uma pasta, enquanto os arquivos foram feitos para serem abertos como projetos na IDE, dessa maneira, acabam, caso criamos alguma pasta auxiliar (como, ArquivosJogos), surgindo varias colisoes, por causa das packages e imports. Dessa maneira, manteremos o projeto em um arquivo separado, e a base e os jogos dos livros em outras janelas da IDE, somente para copiar classes e trechos de código em geral.

ProjetoAV3 - Grupo3
├── ./src
│   ├── ./base
│   │   ├── CenarioPadrao.java
│   │   ├── Elemento.java
│   │   ├── MatUtil.java
│   │   ├── Menu.java
│   │   ├── Texto.java
│   │   └── Util.java
│   ├── ./Resources
│   │   ├── Ranking.TXT
│   │   ├── Racing_1.java
│   │   └── Util.java
│   ├── Jogo.java
│   ├── Cenario.java
│   ├── Adversario.java
│   ├── CarroJogador.java
│   ├── DesenhaRanking.java
│   ├── Jogador.java
│   ├── Ranking.java
│   └── README.txt
└── README.md

