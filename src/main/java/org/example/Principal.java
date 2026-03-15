package org.example;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Random;

public class Principal extends Application {
    AnchorPane pane;
    Button botao_inicio;
    private Button[] vet;
    private Label[] linhasCodigo;
    private Button btnCompA, btnCompB;
    private Label lblCompSinal;
    private Label lblComp;

    private static final String ESTILO_NORMAL = "-fx-font-family: Consolas; -fx-font-size: 13px;";
    private static final String ESTILO_ATUAL = "-fx-font-family: Consolas; -fx-font-size: 13px; -fx-background-color: #ffe082; -fx-font-weight: bold;";
    private static final String BTN_NORMAL = "-fx-font-size: 14px;";
    private static final String BTN_PAI = "-fx-font-size: 14px; -fx-background-color: #0A1626; -fx-text-fill: white;";
    private static final String BTN_FILHO = "-fx-font-size: 14px; -fx-background-color: #A64208;";
    private static final String BTN_MAIOR = "-fx-font-size: 14px; -fx-background-color: #0E5673;";
    private static final String BTN_ORDENADO = "-fx-font-size: 14px; -fx-background-color: #66bb6a; -fx-text-fill: white;";
    private static final String COMP_BTN_BASE = "-fx-background-color: #0E5673; -fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;";
    private static final String COMP_BTN_TRUE = "-fx-background-color: #16a34a; -fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;";
    private static final String COMP_BTN_FALSE = "-fx-background-color: #dc2626; -fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;";
    private static final String COMP_LABEL_BASE = "-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #0f172a;";
    private static final String COMP_LABEL_TRUE = "-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #14532d; -fx-background-color: #dcfce7; -fx-padding: 4 8 4 8; -fx-background-radius: 8;";
    private static final String COMP_LABEL_FALSE = "-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #7f1d1d; -fx-background-color: #fee2e2; -fx-padding: 4 8 4 8; -fx-background-radius: 8;";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Pesquisa e Ordenacao");
        pane = new AnchorPane();
        pane.setStyle("-fx-background-color: #e9eef5;");

        // essa parte mexe na divisao visual da tela (esquerda execucao, direita codigo)
        AnchorPane areaExecucao = new AnchorPane();
        areaExecucao.setLayoutX(20);
        areaExecucao.setLayoutY(20);
        areaExecucao.setPrefSize(770, 660);
        areaExecucao.setStyle("-fx-background-color: #f8fafc; -fx-border-color: #cbd5e1; -fx-border-radius: 12; -fx-background-radius: 12;");
        pane.getChildren().add(areaExecucao);

        AnchorPane areaCodigo = new AnchorPane();
        areaCodigo.setLayoutX(810);
        areaCodigo.setLayoutY(20);
        areaCodigo.setPrefSize(470, 660);
        areaCodigo.setStyle("-fx-background-color: #f8fafc; -fx-border-color: #cbd5e1; -fx-border-radius: 12; -fx-background-radius: 12;");
        pane.getChildren().add(areaCodigo);

        // essa parte mexe no titulo da area de execucao
        Label titulo = new Label("Heap Sort - Visualizacao");
        titulo.setLayoutX(20);
        titulo.setLayoutY(18);
        titulo.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: #0f172a;");
        areaExecucao.getChildren().add(titulo);

        // botoes de comparacao, so visual
        btnCompA = new Button("-");
        btnCompA.setLayoutX(260);
        btnCompA.setLayoutY(420);
        btnCompA.setMinSize(50, 35);
        btnCompA.setStyle(COMP_BTN_BASE);
        areaExecucao.getChildren().add(btnCompA);

        btnCompB = new Button("-");
        btnCompB.setLayoutX(350);
        btnCompB.setLayoutY(420);
        btnCompB.setMinSize(50, 35);
        btnCompB.setStyle(COMP_BTN_BASE);
        areaExecucao.getChildren().add(btnCompB);

        lblCompSinal = new Label("?");
        lblCompSinal.setLayoutX(325);
        lblCompSinal.setLayoutY(424);
        lblCompSinal.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #0f172a;");
        areaExecucao.getChildren().add(lblCompSinal);

        lblComp = new Label("Comparacao: -");
        lblComp.setLayoutX(430);
        lblComp.setLayoutY(428);
        lblComp.setStyle(COMP_LABEL_BASE);
        areaExecucao.getChildren().add(lblComp);

        botao_inicio = new Button();
        botao_inicio.setLayoutX(20);
        botao_inicio.setLayoutY(90);
        botao_inicio.setText("Inicia...");
        //tirar o contorno do click no botão
        botao_inicio.setFocusTraversable(false);
        botao_inicio.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-background-color: #0f172a; -fx-text-fill: white;");
        botao_inicio.setOnAction(e -> {
            botao_inicio.setVisible(false);
            Task<Void> t = new Task<>() {
                @Override
                protected Void call() throws Exception {
                    bucket_sort();
                    return null;
                }
            };
            new Thread(t).start();
        });
        areaExecucao.getChildren().add(botao_inicio);

        Random random = new Random();
        vet = new Button[8];
        for (int i = 0; i < vet.length; i++) {
            int numero = random.nextInt(100);
            vet[i] = new Button(String.valueOf(numero));
            vet[i].setLayoutX(80 + (i * 80));
            vet[i].setLayoutY(220);
            vet[i].setMinHeight(40);
            vet[i].setMinWidth(40);
            //tirar o contorno do click no botão
            vet[i].setFocusTraversable(false);
            vet[i].setFont(new Font(14));
            areaExecucao.getChildren().add(vet[i]);
        }

        criarPainelCodigo();

        Scene scene = new Scene(pane, 1300, 700);
        stage.setScene(scene);
        stage.show();
    }

    private void criarPainelCodigo() {
        String[] codigo = {
                "public void heap_sort(){",
                "    int pai, f1, f2, Fmaior, tl;",
                "    while(tl > 1){",
                "        for(pai = tl/2-1; pai >= 0; pai--){",
                "            f1 = 2*pai+1;",
                "            f2 = f1+1;",
                "            Fmaior = f1;",
                "            if(f2 < tl && vet[f2] > vet[f1]){",
                "                Fmaior = f2;",
                "            }",
                "            if(vet[Fmaior] > vet[pai]){",
                "                int aux = vet[pai];",
                "                vet[pai] = vet[Fmaior];",
                "                vet[Fmaior] = aux;",
                "            }",
                "        }",
                "        int aux = vet[0];",
                "        vet[0] = vet[tl-1];",
                "        vet[tl-1] = aux;",
                "        tl--;",
                "    }",
                "}"
        };

        VBox painelCodigo = new VBox(2);
        // essa parte mexe no card de codigo da area da direita
        painelCodigo.setLayoutX(835);
        painelCodigo.setLayoutY(40);
        painelCodigo.setStyle("-fx-background-color:#ffffff; -fx-padding:12; -fx-border-color:#cbd5e1; -fx-background-radius: 10; -fx-border-radius: 10;");

        linhasCodigo = new Label[codigo.length];
        for (int i = 0; i < codigo.length; i++) {
            Label linha = new Label(String.format("%2d  %s", i + 1, codigo[i]));
            linha.setStyle("-fx-font-family: Consolas; -fx-font-size: 13px;");
            linhasCodigo[i] = linha;
            painelCodigo.getChildren().add(linha);
        }
        pane.getChildren().add(painelCodigo);
        criarLegendaCores();
    }

    private void criarLegendaCores() {
        // essa parte mexe na legenda: agora em linha horizontal
        VBox legenda = new VBox(8);
        legenda.setLayoutX(835);
        legenda.setLayoutY(560);
        legenda.setStyle("-fx-background-color:#ffffff; -fx-padding:10; -fx-border-color:#cbd5e1; -fx-background-radius: 10; -fx-border-radius: 10;");

        Label titulo = new Label("Legenda de cores");
        titulo.setStyle("-fx-font-weight: bold; -fx-font-size: 13px;");

        HBox linhaLegenda = new HBox(18,
                itemLegenda("#0A1626", "Pai"),
                itemLegenda("#A64208", "Filho (f1/f2)"),
                itemLegenda("#0E5673", "Maior filho"),
                itemLegenda("#66bb6a", "Ordenado")
        );
        linhaLegenda.setAlignment(Pos.CENTER_LEFT);
        legenda.getChildren().addAll(titulo, linhaLegenda);

        pane.getChildren().add(legenda);
    }

    private HBox itemLegenda(String corHex, String texto) {
        Label cor = new Label("   ");
        cor.setMinSize(18, 18);
        cor.setStyle("-fx-background-color: " + corHex + "; -fx-border-color: #666;");

        Label desc = new Label(texto);
        desc.setStyle("-fx-font-size: 12px;");

        HBox linha = new HBox(8, cor, desc);
        linha.setAlignment(Pos.CENTER_LEFT);
        return linha;
    }

    private void destacarLinhaComPausa(int linha1Based) throws InterruptedException {
        destacarLinha(linha1Based);
        Thread.sleep(700);
    }

    private void destacarLinha(int linha1Based) {
        Platform.runLater(() -> {
            for (Label l : linhasCodigo) {
                l.setStyle(ESTILO_NORMAL);
            }
            int idx = linha1Based - 1;
            if (idx >= 0 && idx < linhasCodigo.length) {
                linhasCodigo[idx].setStyle(ESTILO_ATUAL);
            }
        });
    }

    private void pintarBase(int tl) {
        for (int i = 0; i < vet.length; i++) {
            if (i >= tl) {
                vet[i].setStyle(BTN_ORDENADO);
            } else {
                vet[i].setStyle(BTN_NORMAL);
            }
        }
    }

    public void destacarBotao(int botao, String estilo) {
        Platform.runLater(() -> vet[botao].setStyle(estilo));
    }

    public void mostrarComparacao(int a, int b, String op, boolean resultado) {
        Platform.runLater(() -> {
            btnCompA.setText(vet[a].getText());
            btnCompB.setText(vet[b].getText());
            lblCompSinal.setText(op);
            // essa parte deixa os botoes da comparacao com a mesma cor dos botoes do vetor.
            btnCompA.setStyle(vet[a].getStyle());
            btnCompB.setStyle(vet[b].getStyle());
            if (resultado) {
                lblComp.setText("Resultado: SIM");
                lblComp.setStyle(COMP_LABEL_TRUE);
            } else {
                lblComp.setText("Resultado: NAO");
                lblComp.setStyle(COMP_LABEL_FALSE);
            }
        });
    }

    private void limparComparacaoVisual() {
        Platform.runLater(() -> {
            btnCompA.setStyle(COMP_BTN_BASE);
            btnCompB.setStyle(COMP_BTN_BASE);
            lblCompSinal.setText("?");
            lblComp.setStyle(COMP_LABEL_BASE);
        });
    }

    public void heap_sort() throws InterruptedException {
        int pai, f1, f2, Fmaior, tl = vet.length;
        destacarLinhaComPausa(3);
        while (tl > 1) {
            destacarLinhaComPausa(4);

            for (pai = tl / 2 - 1; pai >= 0; pai--) {
                destacarBotao(pai, BTN_PAI);
                destacarLinhaComPausa(5);
                f1 = 2 * pai + 1;
                if (f1 < tl) {
                    destacarBotao(f1, BTN_FILHO);
                }

                destacarLinhaComPausa(6);
                f2 = f1 + 1;
                if (f2 < tl) {
                    destacarBotao(f2, BTN_FILHO);
                }

                destacarLinhaComPausa(7);
                Fmaior = f1;

                destacarLinhaComPausa(8);
                // essa parte e so de comparacao e visual, nao tem nada com o codigo.
                if (f2 < tl) {
                    boolean r1 = Integer.parseInt(vet[f2].getText()) > Integer.parseInt(vet[f1].getText());
                    mostrarComparacao(f2, f1, ">", r1);
                    Thread.sleep(1700);
                    limparComparacaoVisual();if (f2 < tl && Integer.parseInt(vet[f2].getText()) > Integer.parseInt(vet[f1].getText())) {
                        destacarLinhaComPausa(9);
                        Fmaior = f2;
                    }
                }

                //aqui volta o heap normal


                destacarBotao(Fmaior, BTN_MAIOR);
                destacarLinhaComPausa(11);

                // essa parte e so de comparacao e visual, nao tem nada com o codigo.
                boolean r2 = Integer.parseInt(vet[pai].getText()) < Integer.parseInt(vet[Fmaior].getText());
                mostrarComparacao(Fmaior,pai, ">", r2);
                Thread.sleep(1700);
                limparComparacaoVisual();

                // aqui volta o heap normal
                if (Integer.parseInt(vet[pai].getText()) < Integer.parseInt(vet[Fmaior].getText())) {
                    destacarLinhaComPausa(12);
                    Thread t = move_botoes(pai, Fmaior);
                    t.join();
                }
                pintarBase(tl);
            }

            destacarLinhaComPausa(17);
            Thread t = move_botoes(0, tl - 1);
            t.join();
            int pos = tl - 1;
            Platform.runLater(() -> vet[pos].setStyle(BTN_ORDENADO));
            Thread.sleep(80);
            destacarLinhaComPausa(20);
            tl--;
        }
        Platform.runLater(() -> vet[0].setStyle(BTN_ORDENADO));
        Thread.sleep(80);
        Platform.runLater(() -> lblComp.setText("Comparacao: fim"));
        destacarLinha(-1);
    }

    private int valorBotao(Button b) {
        return Integer.parseInt(b.getText());
    }

    // essa parte anima o botao indo para uma posicao de balde.
    private Thread moverBotaoPara(Button botao, double alvoX, double alvoY) {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                while (true) {
                    final double[] dx = {alvoX - botao.getLayoutX()};
                    final double[] dy = {alvoY - botao.getLayoutY()};
                    if (Math.abs(dx[0]) <= 1 && Math.abs(dy[0]) <= 1) {
                        Platform.runLater(() -> {
                            botao.setLayoutX(alvoX);
                            botao.setLayoutY(alvoY);
                        });
                        break;
                    }
                    double passoX = 0;
                    double passoY = 0;
                    if (Math.abs(dx[0]) > 0) passoX = Math.signum(dx[0]) * Math.min(5, Math.abs(dx[0]));
                    if (Math.abs(dy[0]) > 0) passoY = Math.signum(dy[0]) * Math.min(5, Math.abs(dy[0]));
                    final double fx = passoX;
                    final double fy = passoY;
                    Platform.runLater(() -> {
                        botao.setLayoutX(botao.getLayoutX() + fx);
                        botao.setLayoutY(botao.getLayoutY() + fy);
                    });
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }
        };
        Thread thread = new Thread(task);
        thread.start();
        return thread;
    }

    private Thread animarParaBalde(Button botao, int indiceBalde, int posNoBalde) {
        double baseX = 90 + (indiceBalde * 130);
        double baseY = 320;
        double alvoX = baseX;
        double alvoY = baseY + (posNoBalde * 32);
        return moverBotaoPara(botao, alvoX, alvoY);
    }

    private Thread animarDoBaldeParaLinha(Button botao, int indiceFinal) {
        double alvoX = 80 + (indiceFinal * 80);
        double alvoY = 220;
        return moverBotaoPara(botao, alvoX, alvoY);
    }

    public void bucket_sort() throws InterruptedException {
        int qtdbaldes = 5;
        int n = vet.length;
        int menor, maior, intervalo, pos, k;

        Button[][] baldes = new Button[qtdbaldes][n];
        int[] tlBaldes = new int[qtdbaldes];

        // acha o maior e o menor elemento do vetor
        menor = maior = valorBotao(vet[0]);
        for (int i = 0; i < n; i++) {
            int valor = valorBotao(vet[i]);
            if (valor < menor)
                menor = valor;
            if (valor > maior)
                maior = valor;
        }

        // com o maior e o menor numero, da para fazer o calculo do range
        intervalo = (maior - menor + 1) / qtdbaldes;
        if (intervalo == 0)
            intervalo = 1;

        // coloca os elementos do vetor nos respectivos baldes (com animacao)
        for (int i = 0; i < n; i++) {
            int valor = valorBotao(vet[i]);
            pos = (valor - menor) / intervalo;
            if (pos >= qtdbaldes)
                pos = qtdbaldes - 1;

            Thread t = animarParaBalde(vet[i], pos, tlBaldes[pos]);
            t.join();

            baldes[pos][tlBaldes[pos]] = vet[i];
            tlBaldes[pos]++;
        }

        // ordena cada balde (insercao direta)
        for (int b = 0; b < qtdbaldes; b++) {
            for (int i = 1; i < tlBaldes[b]; i++) {
                Button aux = baldes[b][i];
                int j = i - 1;
                while (j >= 0 && valorBotao(baldes[b][j]) > valorBotao(aux)) {
                    baldes[b][j + 1] = baldes[b][j];
                    j--;
                }
                baldes[b][j + 1] = aux;
            }
        }

        // junta tudo de volta no vetor original (com animacao)
        Button[] novoVet = new Button[n];
        k = 0;
        for (int b = 0; b < qtdbaldes; b++) {
            for (int j = 0; j < tlBaldes[b]; j++) {
                Button atual = baldes[b][j];
                Thread t = animarDoBaldeParaLinha(atual, k);
                t.join();
                novoVet[k] = atual;
                k++;
            }
        }
        vet = novoVet;
    }

    public Thread move_botoes(int botao0, int botao1) {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                for (int i = 0; i < 10; i++) {
                    Platform.runLater(() -> vet[botao0].setLayoutY(vet[botao0].getLayoutY() + 5));
                    Platform.runLater(() -> vet[botao1].setLayoutY(vet[botao1].getLayoutY() - 5));
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                double distancia = Math.abs(vet[botao1].getLayoutX() - vet[botao0].getLayoutX());
                int passos = (int) (distancia / 5);

                for (int i = 0; i < passos; i++) {
                    Platform.runLater(() -> vet[botao0].setLayoutX(vet[botao0].getLayoutX() + 5));
                    Platform.runLater(() -> vet[botao1].setLayoutX(vet[botao1].getLayoutX() - 5));
                    try {
                        Thread.sleep(25);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                for (int i = 0; i < 10; i++) {
                    Platform.runLater(() -> vet[botao0].setLayoutY(vet[botao0].getLayoutY() - 5));
                    Platform.runLater(() -> vet[botao1].setLayoutY(vet[botao1].getLayoutY() + 5));
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Button aux = vet[botao0];
                vet[botao0] = vet[botao1];
                vet[botao1] = aux;
                return null;
            }
        };
        Thread thread = new Thread(task);
        thread.start();
        return thread;
    }
}
