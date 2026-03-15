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
    private Label lblBucketMinMax;
    private Label lblTituloAlgoritmo;
    private String algoritmoSelecionado = "HEAP";
    private VBox painelCodigo;
    private VBox legendaCores;
    private Button btnMenuHeap;
    private Button btnMenuBucket;
    private Button botao_reset;

    private static final String ESTILO_NORMAL = "-fx-font-family: Consolas; -fx-font-size: 13px;";
    private static final String ESTILO_ATUAL = "-fx-font-family: Consolas; -fx-font-size: 13px; -fx-background-color: #ffe082; -fx-font-weight: bold;";
    private static final String BTN_NORMAL = "-fx-font-size: 14px;";
    private static final String BTN_PAI = "-fx-font-size: 14px; -fx-background-color: #0A1626; -fx-text-fill: white;";
    private static final String BTN_FILHO = "-fx-font-size: 14px; -fx-background-color: #A64208;";
    private static final String BTN_MAIOR = "-fx-font-size: 14px; -fx-background-color: #0E5673;";
    private static final String BTN_INSERCAO = "-fx-font-size: 14px; -fx-background-color: #dc2626; -fx-text-fill: white;";
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
        areaExecucao.setPrefSize(900, 800);
        areaExecucao.setStyle("-fx-background-color: #f8fafc; -fx-border-color: #cbd5e1; -fx-border-radius: 12; -fx-background-radius: 12;");
        pane.getChildren().add(areaExecucao);

        AnchorPane areaCodigo = new AnchorPane();
        areaCodigo.setLayoutX(940);
        areaCodigo.setLayoutY(20);
        areaCodigo.setPrefSize(490, 800);
        areaCodigo.setStyle("-fx-background-color: #f8fafc; -fx-border-color: #cbd5e1; -fx-border-radius: 12; -fx-background-radius: 12;");
        pane.getChildren().add(areaCodigo);

        // essa parte mexe no titulo da area de execucao
        lblTituloAlgoritmo = new Label("Heap Sort - Visualizacao");
        lblTituloAlgoritmo.setLayoutX(20);
        lblTituloAlgoritmo.setLayoutY(18);
        lblTituloAlgoritmo.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: #0f172a;");
        areaExecucao.getChildren().add(lblTituloAlgoritmo);

        // menu de escolha do algoritmo
        btnMenuHeap = new Button("Heap Sort");
        btnMenuHeap.setLayoutX(20);
        btnMenuHeap.setLayoutY(70);
        btnMenuHeap.setFocusTraversable(false);
        btnMenuHeap.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-background-color: #0f172a; -fx-text-fill: white;");
        areaExecucao.getChildren().add(btnMenuHeap);

        btnMenuBucket = new Button("Bucket Sort");
        btnMenuBucket.setLayoutX(120);
        btnMenuBucket.setLayoutY(70);
        btnMenuBucket.setFocusTraversable(false);
        btnMenuBucket.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-background-color: #e2e8f0; -fx-text-fill: #0f172a;");
        areaExecucao.getChildren().add(btnMenuBucket);

        btnMenuHeap.setOnAction(e -> {
            algoritmoSelecionado = "HEAP";
            lblTituloAlgoritmo.setText("Heap Sort - Visualizacao");
            atualizarPainelCodigo("HEAP");
            atualizarVisibilidadeMetodo();
            btnMenuHeap.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-background-color: #0f172a; -fx-text-fill: white;");
            btnMenuBucket.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-background-color: #e2e8f0; -fx-text-fill: #0f172a;");
        });

        btnMenuBucket.setOnAction(e -> {
            algoritmoSelecionado = "BUCKET";
            lblTituloAlgoritmo.setText("Bucket Sort - Visualizacao");
            atualizarPainelCodigo("BUCKET");
            atualizarVisibilidadeMetodo();
            btnMenuBucket.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-background-color: #0f172a; -fx-text-fill: white;");
            btnMenuHeap.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-background-color: #e2e8f0; -fx-text-fill: #0f172a;");
        });

        // botoes de comparacao, so visual
        btnCompA = new Button("-");
        btnCompA.setLayoutX(260);
        btnCompA.setLayoutY(500);
        btnCompA.setMinSize(50, 35);
        btnCompA.setStyle(COMP_BTN_BASE);
        areaExecucao.getChildren().add(btnCompA);

        btnCompB = new Button("-");
        btnCompB.setLayoutX(350);
        btnCompB.setLayoutY(500);
        btnCompB.setMinSize(50, 35);
        btnCompB.setStyle(COMP_BTN_BASE);
        areaExecucao.getChildren().add(btnCompB);

        lblCompSinal = new Label("?");
        lblCompSinal.setLayoutX(325);
        lblCompSinal.setLayoutY(504);
        lblCompSinal.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #0f172a;");
        areaExecucao.getChildren().add(lblCompSinal);

        lblComp = new Label("Comparacao: -");
        lblComp.setLayoutX(430);
        lblComp.setLayoutY(508);
        lblComp.setStyle(COMP_LABEL_BASE);
        areaExecucao.getChildren().add(lblComp);

        // texto do bucket: mostra menor e maior durante o primeiro for
        lblBucketMinMax = new Label("Bucket -> Menor: - | Maior: -");
        lblBucketMinMax.setLayoutX(260);
        lblBucketMinMax.setLayoutY(555);
        lblBucketMinMax.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #1e293b;");
        areaExecucao.getChildren().add(lblBucketMinMax);

        botao_inicio = new Button();
        botao_inicio.setLayoutX(20);
        botao_inicio.setLayoutY(110);
        botao_inicio.setText("Inicia...");
        //tirar o contorno do click no botão
        botao_inicio.setFocusTraversable(false);
        botao_inicio.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-background-color: #0f172a; -fx-text-fill: white;");
        botao_inicio.setOnAction(e -> {
            botao_inicio.setVisible(false);
            btnMenuHeap.setVisible(false);
            btnMenuBucket.setVisible(false);
            Task<Void> t = new Task<>() {
                @Override
                protected Void call() throws Exception {
                    if (algoritmoSelecionado.equals("HEAP")) {
                        heap_sort();
                    } else {
                        bucket_sort();
                    }
                    return null;
                }
            };
            t.setOnSucceeded(ev -> botao_reset.setVisible(true));
            t.setOnFailed(ev -> botao_reset.setVisible(true));
            new Thread(t).start();
        });
        areaExecucao.getChildren().add(botao_inicio);

        // botao para voltar ao inicio e escolher outro metodo
        botao_reset = new Button("Resetar");
        botao_reset.setLayoutX(100);
        botao_reset.setLayoutY(110);
        botao_reset.setFocusTraversable(false);
        botao_reset.setVisible(false);
        botao_reset.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-background-color: #475569; -fx-text-fill: white;");
        botao_reset.setOnAction(e -> resetarTela());
        areaExecucao.getChildren().add(botao_reset);

        Random random = new Random();
        vet = new Button[9];
        for (int i = 0; i < vet.length; i++) {
            int numero = random.nextInt(100);
            vet[i] = new Button(String.valueOf(numero));
            vet[i].setLayoutX(110 + (i * 80));
            vet[i].setLayoutY(260);
            vet[i].setMinHeight(40);
            vet[i].setMinWidth(40);
            //tirar o contorno do click no botão
            vet[i].setFocusTraversable(false);
            vet[i].setFont(new Font(14));
            areaExecucao.getChildren().add(vet[i]);
        }

        criarPainelCodigo();
        atualizarVisibilidadeMetodo();

        Scene scene = new Scene(pane, 1470, 850);
        stage.setScene(scene);
        stage.show();
    }

    private void resetarTela() {
        Random random = new Random();
        for (int i = 0; i < vet.length; i++) {
            int numero = random.nextInt(100);
            vet[i].setText(String.valueOf(numero));
            vet[i].setLayoutX(110 + (i * 80));
            vet[i].setLayoutY(260);
            vet[i].setStyle(BTN_NORMAL);
        }
        btnCompA.setText("-");
        btnCompB.setText("-");
        lblCompSinal.setText("?");
        lblComp.setText("Comparacao: -");
        lblComp.setStyle(COMP_LABEL_BASE);
        lblBucketMinMax.setText("Bucket -> Menor: - | Maior: -");
        destacarLinha(-1);
        atualizarVisibilidadeMetodo();

        botao_inicio.setVisible(true);
        btnMenuHeap.setVisible(true);
        btnMenuBucket.setVisible(true);
        botao_reset.setVisible(false);
    }

    private void atualizarVisibilidadeMetodo() {
        boolean ehHeap = "HEAP".equals(algoritmoSelecionado);

        btnCompA.setVisible(ehHeap);
        btnCompB.setVisible(ehHeap);
        lblCompSinal.setVisible(ehHeap);
        lblComp.setVisible(ehHeap);
        legendaCores.setVisible(ehHeap);

        lblBucketMinMax.setVisible(!ehHeap);
    }

    private void criarPainelCodigo() {
        painelCodigo = new VBox(2);
        // essa parte mexe no card de codigo da area da direita
        painelCodigo.setLayoutX(960);
        painelCodigo.setLayoutY(35);
        painelCodigo.setStyle("-fx-background-color:#ffffff; -fx-padding:12; -fx-border-color:#cbd5e1; -fx-background-radius: 10; -fx-border-radius: 10;");
        pane.getChildren().add(painelCodigo);
        atualizarPainelCodigo("HEAP");
        criarLegendaCores();
    }

    // essa parte troca o codigo exibido no painel conforme o algoritmo escolhido.
    private void atualizarPainelCodigo(String algoritmo) {
        String[] codigo;
        if ("BUCKET".equals(algoritmo)) {
            codigo = new String[]{
                    "public void bucketSort(){",
                    "    int menor, maior, intervalo, pos, k;",
                    "    int qtdbaldes = 5;",
                    "    int[][] baldes = new int[qtdbaldes][TL];",
                    "    int[] TLbaldes = new int[qtdbaldes];",
                    "    menor = maior = vetor[0];",
                    "    for(int i = 0; i < TL; i++){",
                    "        if(vetor[i] < menor) menor = vetor[i];",
                    "        if(vetor[i] > maior) maior = vetor[i];",
                    "    }",
                    "    intervalo = (maior - menor + 1) / qtdbaldes;",
                    "    for(int i = 0; i < qtdbaldes; i++) TLbaldes[i] = 0;",
                    "    for(int i = 0; i < TL; i++){",
                    "        if(intervalo == 0){",
                    "            baldes[0][TLbaldes[0]++] = vetor[i];",
                    "        }else{",
                    "            pos = (vetor[i] - menor) / intervalo;",
                    "            if(pos >= qtdbaldes) pos = qtdbaldes - 1;",
                    "            baldes[pos][TLbaldes[pos]++] = vetor[i];",
                    "        }",
                    "    }",
                    "    for(int i = 0; i < qtdbaldes; i++){",
                    "        for(int j = 1; j < TLbaldes[i]; j++){",
                    "            int aux = baldes[i][j];",
                    "            int p = j;",
                    "            while(p > 0 && aux < baldes[i][p-1]){",
                    "                baldes[i][p] = baldes[i][p-1];",
                    "                p--;",
                    "            }",
                    "            baldes[i][p] = aux;",
                    "        }",
                    "    }",
                    "    k = 0;",
                    "    for(int i = 0; i < qtdbaldes; i++){",
                    "        for(int j = 0; j < TLbaldes[i]; j++)",
                    "            vetor[k++] = baldes[i][j];",
                    "    }",
                    "}"
            };
        } else {
            codigo = new String[]{
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
        }

        painelCodigo.getChildren().clear();
        linhasCodigo = new Label[codigo.length];
        for (int i = 0; i < codigo.length; i++) {
            Label linha = new Label(String.format("%2d  %s", i + 1, codigo[i]));
            linha.setStyle("-fx-font-family: Consolas; -fx-font-size: 13px;");
            linhasCodigo[i] = linha;
            painelCodigo.getChildren().add(linha);
        }
    }

    private void criarLegendaCores() {
        // essa parte mexe na legenda: agora em linha horizontal
        legendaCores = new VBox(8);
        legendaCores.setLayoutX(538);
        legendaCores.setLayoutY(725);
        legendaCores.setStyle("-fx-background-color:#ffffff; -fx-padding:10; -fx-border-color:#cbd5e1; -fx-background-radius: 10; -fx-border-radius: 10;");

        Label titulo = new Label("Legenda de cores");
        titulo.setStyle("-fx-font-weight: bold; -fx-font-size: 13px;");

        HBox linhaLegenda = new HBox(18,
                itemLegenda("#0A1626", "Pai"),
                itemLegenda("#A64208", "Filho (f1/f2)"),
                itemLegenda("#0E5673", "Maior filho"),
                itemLegenda("#66bb6a", "Ordenado")
        );
        linhaLegenda.setAlignment(Pos.CENTER_LEFT);
        legendaCores.getChildren().addAll(titulo, linhaLegenda);

        pane.getChildren().add(legendaCores);
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

    private void mostrarComparacaoBotoes(Button a, Button b, String op, boolean resultado) {
        Platform.runLater(() -> {
            btnCompA.setText(a.getText());
            btnCompB.setText(b.getText());
            lblCompSinal.setText(op);
            btnCompA.setStyle(a.getStyle());
            btnCompB.setStyle(b.getStyle());
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

    private void mostrarBucketMinMax(int menor, int maior) {
        Platform.runLater(() -> lblBucketMinMax.setText("Bucket -> Menor: " + menor + " | Maior: " + maior));
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
                while (Math.abs(alvoX - botao.getLayoutX()) > 1 || Math.abs(alvoY - botao.getLayoutY()) > 1) {
                    double atualX = botao.getLayoutX();
                    double atualY = botao.getLayoutY();

                    double dx = alvoX - atualX;
                    double dy = alvoY - atualY;

                    double passoX = Math.signum(dx) * Math.min(5, Math.abs(dx));
                    double passoY = Math.signum(dy) * Math.min(5, Math.abs(dy));

                    double novoX = atualX + passoX;
                    double novoY = atualY + passoY;

                    Platform.runLater(() -> {
                        botao.setLayoutX(novoX);
                        botao.setLayoutY(novoY);
                    });
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Platform.runLater(() -> {
                    botao.setLayoutX(alvoX);
                    botao.setLayoutY(alvoY);
                });
                return null;
            }
        };
        Thread thread = new Thread(task);
        thread.start();
        return thread;
    }

    private Thread animarParaBalde(Button botao, int balde, int pos) {
        double x = 40 + balde * 175; // mais espaco entre os baldes
        double y = 320 + pos * 35;    // itens do balde em coluna
        return moverBotaoPara(botao, x, y);
    }

    private Thread animarDoBaldeParaLinha(Button botao, int indiceFinal) {
        double x = 110 + indiceFinal * 80; // volta pra linha principal
        double y = 260;
        return moverBotaoPara(botao, x, y);
    }

    // anima o elemento auxiliar da insercao em uma trilha lateral do balde
    private Thread animarAuxiliarBalde(Button botao, int balde, int pos) {
        double x = 40 + balde * 175 - 55;
        double y = 320 + pos * 35;
        return moverBotaoPara(botao, x, y);
    }

    private void pintarComparacaoBucket(Button a, Button b) {
        Platform.runLater(() -> {
            a.setStyle(BTN_INSERCAO);
            b.setStyle(BTN_INSERCAO);
        });
    }

    private void limparComparacaoBucket(Button a, Button b) {
        Platform.runLater(() -> {
            a.setStyle(BTN_NORMAL);
            b.setStyle(BTN_NORMAL);
        });
    }

    public void bucket_sort() throws InterruptedException {
        destacarLinhaComPausa(1);
        int qtdbaldes = 5;
        int n = vet.length;
        int menor, maior, intervalo, pos, k;

        destacarLinhaComPausa(4);
        Button[][] baldes = new Button[qtdbaldes][n];
        destacarLinhaComPausa(5);
        int[] tlBaldes = new int[qtdbaldes];

        // acha o maior e o menor elemento do vetor
        destacarLinhaComPausa(6);
        menor = maior = valorBotao(vet[0]);
        mostrarBucketMinMax(menor, maior);
        Thread.sleep(700);
        destacarLinhaComPausa(7);
        for (int i = 0; i < n; i++) {
            int valor = valorBotao(vet[i]);
            vet[i].setStyle(BTN_PAI);
            destacarLinhaComPausa(8);
            if (valor < menor)
                menor = valor;
            destacarLinhaComPausa(9);
            if (valor > maior)
                maior = valor;
            mostrarBucketMinMax(menor, maior);
            Thread.sleep(450);
            vet[i].setStyle(BTN_NORMAL);
        }

        // com o maior e o menor numero, da para fazer o calculo do range
        destacarLinhaComPausa(11);
        intervalo = (maior - menor + 1) / qtdbaldes;
        if (intervalo == 0)
            intervalo = 1;

        // coloca os elementos do vetor nos respectivos baldes (com animacao)
        destacarLinhaComPausa(13);
        for (int i = 0; i < n; i++) {
            int valor = valorBotao(vet[i]);
            destacarLinhaComPausa(14);
            pos = (valor - menor) / intervalo;
            destacarLinhaComPausa(18);
            if (pos >= qtdbaldes)
                pos = qtdbaldes - 1;

            Thread t = animarParaBalde(vet[i], pos, tlBaldes[pos]);
            t.join();

            baldes[pos][tlBaldes[pos]] = vet[i];
            tlBaldes[pos]++;
        }

        // ordena cada balde (insercao direta)
        destacarLinhaComPausa(22);
        Platform.runLater(() -> {
            btnCompA.setVisible(true);
            btnCompB.setVisible(true);
            lblCompSinal.setVisible(true);
            lblComp.setVisible(true);
        });
        for (int b = 0; b < qtdbaldes; b++) {
            destacarLinhaComPausa(23);
            for (int i = 1; i < tlBaldes[b]; i++) {
                destacarLinhaComPausa(24);
                Button aux = baldes[b][i];
                int posAuxVisual = i;
                Thread tAuxLado = animarAuxiliarBalde(aux, b, posAuxVisual);
                tAuxLado.join();
                destacarLinhaComPausa(25);
                int j = i - 1;
                destacarLinhaComPausa(26);
                boolean continuaComparando = true;
                while (j >= 0 && continuaComparando) {
                    Button comparado = baldes[b][j];
                    pintarComparacaoBucket(comparado, aux);
                    Thread.sleep(700);

                    boolean precisaTrocar = valorBotao(comparado) > valorBotao(aux);
                    mostrarComparacaoBotoes(comparado, aux, ">", precisaTrocar);
                    Thread.sleep(750);
                    destacarLinhaComPausa(27);
                    if (precisaTrocar) {
                        baldes[b][j + 1] = comparado;
                        destacarLinhaComPausa(28);
                        Thread tShift = animarParaBalde(comparado, b, j + 1);
                        tShift.join();
                        j--;
                        posAuxVisual--;
                        Thread tAuxSobe = animarAuxiliarBalde(aux, b, posAuxVisual);
                        tAuxSobe.join();
                        Thread.sleep(600);
                    } else {
                        continuaComparando = false;
                    }
                    limparComparacaoBucket(comparado, aux);
                    limparComparacaoVisual();
                }
                destacarLinhaComPausa(30);
                baldes[b][j + 1] = aux;
                Thread tAux = animarParaBalde(aux, b, j + 1);
                tAux.join();
                Thread.sleep(800);
            }
        }
        atualizarVisibilidadeMetodo();

        // junta tudo de volta no vetor original (com animacao)
        destacarLinhaComPausa(33);
        Button[] novoVet = new Button[n];
        destacarLinhaComPausa(34);
        k = 0;
        destacarLinhaComPausa(35);
        for (int b = 0; b < qtdbaldes; b++) {
            destacarLinhaComPausa(36);
            for (int j = 0; j < tlBaldes[b]; j++) {
                Button atual = baldes[b][j];
                Thread t = animarDoBaldeParaLinha(atual, k);
                t.join();
                Platform.runLater(() -> atual.setStyle(BTN_ORDENADO));
                Thread.sleep(320);
                novoVet[k] = atual;
                k++;
            }
        }
        vet = novoVet;
        destacarLinha(-1);
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
