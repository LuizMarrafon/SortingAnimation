package org.example;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Principal extends Application {
    // Painel "raiz" da cena, como uma mesa onde todos os componentes sao apoiados.
    AnchorPane pane;
    // Lado esquerdo da tela: onde acontece a animacao dos algoritmos.
    AnchorPane areaExecucao;
    Button botao_inicio;
    private Button[] vet;
    private Label[] linhasCodigo;
    private Button btnCompA, btnCompB;
    private Label lblCompSinal;
    private Label lblComp;
    private Label lblBucketMinMax;
    private Label lblTituloAlgoritmo;
    private Button btnStatus1;
    private Button btnStatus2;
    private Button btnStatus3;
    private Button btnStatus4;
    private final List<ImageView> bucketImagens = new ArrayList<>();
    private Image imagemBalde;
    private int quantidadeBucketsAtual = 1;
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
    private static final String STATUS_BTN_STYLE = "-fx-font-size: 14px; -fx-font-weight: bold; -fx-background-color: #0f766e; -fx-text-fill: white;";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        // Configuracao inicial da janela do projeto.
        stage.setTitle("Pesquisa e Ordenacao");
        pane = new AnchorPane();
        pane.setStyle("-fx-background-color: #e9eef5;");

        // Divide a interface em duas metades:
        // esquerda para ver a execucao, direita para acompanhar o codigo.
        areaExecucao = new AnchorPane();
        areaExecucao.setLayoutX(20);
        areaExecucao.setLayoutY(20);
        areaExecucao.setPrefSize(900, 800);
        areaExecucao.setStyle("-fx-background-color: #f8fafc; -fx-border-color: #cbd5e1; -fx-border-radius: 12; -fx-background-radius: 12;");
        pane.getChildren().add(areaExecucao);

        // Carrega a imagem de balde (se existir) para usar no modo Bucket.
        File arquivoBalde = new File("balde/balde.png");
        if (arquivoBalde.exists()) {
            imagemBalde = new Image(arquivoBalde.toURI().toString());
        }

        AnchorPane areaCodigo = new AnchorPane();
        areaCodigo.setLayoutX(940);
        areaCodigo.setLayoutY(20);
        areaCodigo.setPrefSize(490, 800);
        areaCodigo.setStyle("-fx-background-color: #f8fafc; -fx-border-color: #cbd5e1; -fx-border-radius: 12; -fx-background-radius: 12;");
        pane.getChildren().add(areaCodigo);

        // Titulo da visualizacao atual.
        lblTituloAlgoritmo = new Label("Heap Sort - Visualizacao");
        lblTituloAlgoritmo.setLayoutX(20);
        lblTituloAlgoritmo.setLayoutY(18);
        lblTituloAlgoritmo.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: #0f172a;");
        areaExecucao.getChildren().add(lblTituloAlgoritmo);

        // Menu simples para alternar entre Heap e Bucket.
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

        // Bloco de comparacao visual (serve como "zoom" do passo atual).
        btnCompA = new Button("-");
        btnCompA.setLayoutX(260);
        btnCompA.setLayoutY(610);
        btnCompA.setMinSize(50, 35);
        btnCompA.setStyle(COMP_BTN_BASE);
        areaExecucao.getChildren().add(btnCompA);

        btnCompB = new Button("-");
        btnCompB.setLayoutX(350);
        btnCompB.setLayoutY(610);
        btnCompB.setMinSize(50, 35);
        btnCompB.setStyle(COMP_BTN_BASE);
        areaExecucao.getChildren().add(btnCompB);

        lblCompSinal = new Label("?");
        lblCompSinal.setLayoutX(325);
        lblCompSinal.setLayoutY(614);
        lblCompSinal.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #0f172a;");
        areaExecucao.getChildren().add(lblCompSinal);

        lblComp = new Label("Comparacao: -");
        lblComp.setLayoutX(430);
        lblComp.setLayoutY(618);
        lblComp.setStyle(COMP_LABEL_BASE);
        areaExecucao.getChildren().add(lblComp);

        // Placar do bucket durante a varredura inicial de menor/maior.
        lblBucketMinMax = new Label("Bucket -> Menor: - | Maior: -");
        lblBucketMinMax.setLayoutX(260);
        lblBucketMinMax.setLayoutY(670);
        lblBucketMinMax.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #1e293b;");
        areaExecucao.getChildren().add(lblBucketMinMax);

        btnStatus1 = new Button("-");
        btnStatus1.setLayoutX(260);
        btnStatus1.setLayoutY(120);
        btnStatus1.setMinWidth(95);
        btnStatus1.setFocusTraversable(false);
        btnStatus1.setStyle(STATUS_BTN_STYLE);
        areaExecucao.getChildren().add(btnStatus1);

        btnStatus2 = new Button("-");
        btnStatus2.setLayoutX(365);
        btnStatus2.setLayoutY(120);
        btnStatus2.setMinWidth(95);
        btnStatus2.setFocusTraversable(false);
        btnStatus2.setStyle(STATUS_BTN_STYLE);
        areaExecucao.getChildren().add(btnStatus2);

        btnStatus3 = new Button("-");
        btnStatus3.setLayoutX(470);
        btnStatus3.setLayoutY(120);
        btnStatus3.setMinWidth(125);
        btnStatus3.setFocusTraversable(false);
        btnStatus3.setStyle(STATUS_BTN_STYLE);
        areaExecucao.getChildren().add(btnStatus3);

        btnStatus4 = new Button("-");
        btnStatus4.setLayoutX(605);
        btnStatus4.setLayoutY(120);
        btnStatus4.setMinWidth(125);
        btnStatus4.setFocusTraversable(false);
        btnStatus4.setStyle(STATUS_BTN_STYLE);
        areaExecucao.getChildren().add(btnStatus4);

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

        // Reset geral: gera novos valores e libera novamente a escolha do metodo.
        botao_reset = new Button("Resetar");
        botao_reset.setLayoutX(100);
        botao_reset.setLayoutY(110);
        botao_reset.setFocusTraversable(false);
        botao_reset.setVisible(false);
        botao_reset.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-background-color: #475569; -fx-text-fill: white;");
        botao_reset.setOnAction(e -> resetarTela());
        areaExecucao.getChildren().add(botao_reset);

        // Gera o vetor inicial com 9 valores aleatorios.
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
        // Volta o app para um estado "limpo", como no comeco da aula/demo.
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
        configurarIndicadoresMetodo();
        destacarLinha(-1);
        atualizarVisibilidadeMetodo();

        botao_inicio.setVisible(true);
        btnMenuHeap.setVisible(true);
        btnMenuBucket.setVisible(true);
        botao_reset.setVisible(false);
    }

    private void atualizarVisibilidadeMetodo() {
        // Liga/desliga partes da tela conforme o algoritmo ativo.
        boolean ehHeap = "HEAP".equals(algoritmoSelecionado);

        btnCompA.setVisible(ehHeap);
        btnCompB.setVisible(ehHeap);
        lblCompSinal.setVisible(ehHeap);
        lblComp.setVisible(ehHeap);
        legendaCores.setVisible(ehHeap);

        lblBucketMinMax.setVisible(!ehHeap);
        btnStatus4.setVisible(true);
        setBucketImagensVisiveis(!ehHeap);
        configurarIndicadoresMetodo();
    }

    private void criarPainelCodigo() {
        // Painel da direita: funciona como "roteiro" do algoritmo em execucao.
        painelCodigo = new VBox(2);
        // essa parte mexe no card de codigo da area da direita
        painelCodigo.setLayoutX(960);
        painelCodigo.setLayoutY(35);
        painelCodigo.setStyle("-fx-background-color:#ffffff; -fx-padding:12; -fx-border-color:#cbd5e1; -fx-background-radius: 10; -fx-border-radius: 10;");
        pane.getChildren().add(painelCodigo);
        atualizarPainelCodigo("HEAP");
        criarLegendaCores();
    }

    // Troca o pseudocodigo da lateral para combinar com o algoritmo selecionado.
    private void atualizarPainelCodigo(String algoritmo) {
        String[] codigo;
        if ("BUCKET".equals(algoritmo)) {
            codigo = new String[]{
                    "public void bucketSort(){",
                    "    int quantidadeBuckets = (int)Math.sqrt(filesize());",
                    "    if(quantidadeBuckets == 0) quantidadeBuckets = 1;",
                    "    int menor, maior, intervalo, pos, k;",
                    "    int[][] baldes = new int[quantidadeBuckets][TL];",
                    "    int[] TLbaldes = new int[quantidadeBuckets];",
                    "    menor = maior = vetor[0];",
                    "    for(int i = 0; i < TL; i++){",
                    "        if(vetor[i] < menor) menor = vetor[i];",
                    "        if(vetor[i] > maior) maior = vetor[i];",
                    "    }",
                    "    intervalo = (maior - menor + 1) / quantidadeBuckets;",
                    "    for(int i = 0; i < quantidadeBuckets; i++) TLbaldes[i] = 0;",
                    "    for(int i = 0; i < TL; i++){",
                    "        if(intervalo == 0){",
                    "            baldes[0][TLbaldes[0]++] = vetor[i];",
                    "        }else{",
                    "            pos = (vetor[i] - menor) / intervalo;",
                    "            if(pos >= quantidadeBuckets) pos = quantidadeBuckets - 1;",
                    "            baldes[pos][TLbaldes[pos]++] = vetor[i];",
                    "        }",
                    "    }",
                    "    for(int i = 0; i < quantidadeBuckets; i++){",
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
                    "    for(int i = 0; i < quantidadeBuckets; i++){",
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
        // Legenda de apoio para o Heap: cada cor representa um papel no passo atual.
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
        // Monta uma linhazinha da legenda: quadrado + descricao textual.
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
        // Helper para nao repetir codigo: destaca e espera um pouco para leitura humana.
        destacarLinha(linha1Based);
        Thread.sleep(700);
    }

    private void destacarLinha(int linha1Based) {
        // Marca a linha "em execucao" para sincronizar explicacao e animacao.
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
        // No Heap: tudo que passa do TL atual ja esta ordenado e fica verde.
        for (int i = 0; i < vet.length; i++) {
            if (i >= tl) {
                vet[i].setStyle(BTN_ORDENADO);
            } else {
                vet[i].setStyle(BTN_NORMAL);
            }
        }
    }

    public void destacarBotao(int botao, String estilo) {
        // Atalho visual para pintar um elemento especifico do vetor.
        Platform.runLater(() -> vet[botao].setStyle(estilo));
    }

    public void mostrarComparacao(int a, int b, String op, boolean resultado) {
        // Mostra uma comparacao entre dois indices do vetor principal.
        Platform.runLater(() -> {
            btnCompA.setText(vet[a].getText());
            btnCompB.setText(vet[b].getText());
            lblCompSinal.setText(op);
            // Copia o estilo dos elementos reais para a comparacao ficar intuitiva.
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
        // Mesma ideia da comparacao acima, mas recebendo botoes ja resolvidos.
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
        // Limpa o painel de comparacao para preparar o proximo passo.
        Platform.runLater(() -> {
            btnCompA.setStyle(COMP_BTN_BASE);
            btnCompB.setStyle(COMP_BTN_BASE);
            lblCompSinal.setText("?");
            lblComp.setStyle(COMP_LABEL_BASE);
        });
    }

    private void mostrarBucketMinMax(int menor, int maior) {
        // Atualiza o placar de faixa do bucket (menor e maior vistos ate agora).
        Platform.runLater(() -> lblBucketMinMax.setText("Bucket -> Menor: " + menor + " | Maior: " + maior));
    }

    private void desenharBucketsVisuais(int quantidadeBuckets) {
        // Cria um "icone de balde" por coluna, como gavetas de classificacao.
        quantidadeBucketsAtual = Math.max(1, quantidadeBuckets);
        Platform.runLater(() -> {
            for (ImageView view : bucketImagens) {
                areaExecucao.getChildren().remove(view);
            }
            bucketImagens.clear();

            if (imagemBalde == null) {
                return;
            }

            for (int b = 0; b < quantidadeBuckets; b++) {
                ImageView view = new ImageView(imagemBalde);
                view.setFitWidth(52);
                view.setFitHeight(52);
                view.setPreserveRatio(true);
                view.setLayoutX(calcularPosicaoXBucket(b) - 26);
                view.setLayoutY(525);
                bucketImagens.add(view);
            }

            areaExecucao.getChildren().addAll(bucketImagens);
        });
    }

    private double calcularPosicaoXBucket(int indiceBalde) {
        // Distribui os baldes pela largura util para evitar espaco ocioso.
        double largura = areaExecucao.getPrefWidth();
        double margemEsquerda = 110;
        double margemDireita = largura - 140;
        if (quantidadeBucketsAtual <= 1) {
            return (margemEsquerda + margemDireita) / 2.0;
        }
        double passo = (margemDireita - margemEsquerda) / (quantidadeBucketsAtual - 1.0);
        return margemEsquerda + (indiceBalde * passo);
    }

    private void setBucketImagensVisiveis(boolean visivel) {
        // Baldes so aparecem quando o metodo atual e o Bucket.
        Platform.runLater(() -> {
            for (ImageView view : bucketImagens) {
                view.setVisible(visivel);
            }
        });
    }

    private void configurarIndicadoresMetodo() {
        // Ajusta os indicadores de topo para refletir o algoritmo atual.
        if ("HEAP".equals(algoritmoSelecionado)) {
            atualizarIndicadoresHeap(-1, -1, -1, "-");
        } else {
            atualizarIndicadoresBucket(-1, -1, calcularQuantidadeBuckets(), "-");
        }
    }

    private void atualizarIndicadoresHeap(int pai, int filho1, int filho2, String auxValor) {
        // Atualiza os "indicadores de depuracao" de cima no modo Heap.
        Platform.runLater(() -> {
            btnStatus1.setText("pai: " + (pai >= 0 ? pai : "-"));
            btnStatus2.setText("filho1: " + (filho1 >= 0 ? filho1 : "-"));
            btnStatus3.setText("filho2: " + (filho2 >= 0 ? filho2 : "-"));
            btnStatus4.setText("aux: " + auxValor);
        });
    }

    private void atualizarIndicadoresBucket(int i, int j, int qtdBaldes) {
        // Sobrecarga para chamadas sem valor de aux explicito.
        atualizarIndicadoresBucket(i, j, qtdBaldes, "-");
    }

    private void atualizarIndicadoresBucket(int i, int j, int qtdBaldes, String auxValor) {
        // Atualiza os indicadores de cima no modo Bucket.
        Platform.runLater(() -> {
            btnStatus1.setText("i: " + (i >= 0 ? i : "-"));
            btnStatus2.setText("j: " + (j >= 0 ? j : "-"));
            btnStatus3.setText("qtd: " + qtdBaldes);
            btnStatus4.setText("aux: " + auxValor);
        });
    }

    private int filesize() {
        // Nesta visualizacao, filesize equivale ao tamanho do vetor atual.
        return vet.length;
    }

    private int calcularQuantidadeBuckets() {
        // Regra do trabalho: usar raiz do tamanho para estimar qtd de baldes.
        int quantidadeBuckets = (int) Math.sqrt(filesize());
        if (quantidadeBuckets == 0) {
            quantidadeBuckets = 1;
        }
        return quantidadeBuckets;
    }

    public void heap_sort() throws InterruptedException {
        // Heap Sort aqui funciona em "ciclos":
        // 1) monta (ou remonta) um heap maximo na parte ainda nao ordenada
        // 2) coloca o maior elemento da vez no final do vetor
        // 3) diminui o TL e repete ate sobrar 1 elemento
        int pai, f1, f2, Fmaior, tl = vet.length;
        atualizarIndicadoresHeap(-1, -1, -1, "-");
        destacarLinhaComPausa(3);
        while (tl > 1) {
            // enquanto tiver pelo menos 2 elementos "ativos", ainda da para ordenar.
            destacarLinhaComPausa(4);

            for (pai = tl / 2 - 1; pai >= 0; pai--) {
                // percorre os pais de tras para frente para garantir propriedade de heap.
                destacarBotao(pai, BTN_PAI);
                destacarLinhaComPausa(5);
                f1 = 2 * pai + 1;
                atualizarIndicadoresHeap(pai, f1, -1, "-");
                if (f1 < tl) {
                    destacarBotao(f1, BTN_FILHO);
                }

                destacarLinhaComPausa(6);
                f2 = f1 + 1;
                atualizarIndicadoresHeap(pai, f1, f2 < tl ? f2 : -1, "-");
                if (f2 < tl) {
                    destacarBotao(f2, BTN_FILHO);
                }

                destacarLinhaComPausa(7);
                // por padrao assume o filho da esquerda como maior.
                Fmaior = f1;

                destacarLinhaComPausa(8);
                // Comparacao visual entre os filhos para decidir qual e o maior.
                if (f2 < tl) {
                    boolean r1 = Integer.parseInt(vet[f2].getText()) > Integer.parseInt(vet[f1].getText());
                    mostrarComparacao(f2, f1, ">", r1);
                    Thread.sleep(1700);
                    limparComparacaoVisual();if (f2 < tl && Integer.parseInt(vet[f2].getText()) > Integer.parseInt(vet[f1].getText())) {
                        destacarLinhaComPausa(9);
                        Fmaior = f2;
                    }
                }

                // Depois de decidir quem e o maior filho, pinta ele para ficar didatico.
                destacarBotao(Fmaior, BTN_MAIOR);
                destacarLinhaComPausa(11);

                // Compara pai vs maior filho para decidir se precisa trocar.
                boolean r2 = Integer.parseInt(vet[pai].getText()) < Integer.parseInt(vet[Fmaior].getText());
                mostrarComparacao(Fmaior,pai, ">", r2);
                Thread.sleep(1700);
                limparComparacaoVisual();

                // Se pai for menor, faz a troca para manter a regra do heap maximo.
                if (Integer.parseInt(vet[pai].getText()) < Integer.parseInt(vet[Fmaior].getText())) {
                    destacarLinhaComPausa(12);
                    atualizarIndicadoresHeap(pai, f1, f2 < tl ? f2 : -1, vet[pai].getText());
                    Thread t = move_botoes(pai, Fmaior);
                    t.join();
                    atualizarIndicadoresHeap(pai, f1, f2 < tl ? f2 : -1, "-");
                }
                pintarBase(tl);
            }

            // Com heap pronto, a raiz (indice 0) guarda o maior da parte ativa.
            // Entao troca raiz com ultima posicao valida (tl-1) e "congela" esse fim.
            destacarLinhaComPausa(17);
            atualizarIndicadoresHeap(0, -1, -1, vet[0].getText());
            Thread t = move_botoes(0, tl - 1);
            t.join();
            atualizarIndicadoresHeap(0, -1, -1, "-");
            int pos = tl - 1;
            Platform.runLater(() -> vet[pos].setStyle(BTN_ORDENADO));
            Thread.sleep(80);
            destacarLinhaComPausa(20);
            tl--;
        }
        Platform.runLater(() -> vet[0].setStyle(BTN_ORDENADO));
        Thread.sleep(80);
        Platform.runLater(() -> lblComp.setText("Comparacao: fim"));
        atualizarIndicadoresHeap(-1, -1, -1, "-");
        destacarLinha(-1);
    }

    private int valorBotao(Button b) {
        // Como o numero esta no texto do botao, converte para inteiro aqui.
        return Integer.parseInt(b.getText());
    }

    // Motor de animacao: move um botao em pequenos passos ate o alvo.
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
        // Leva o elemento para dentro do balde escolhido.
        double x = calcularPosicaoXBucket(balde) - 20;
        double y = 320 + pos * 35;
        return moverBotaoPara(botao, x, y);
    }

    private Thread animarDoBaldeParaLinha(Button botao, int indiceFinal) {
        // Traz de volta para o vetor principal, na ordem final.
        double x = 110 + indiceFinal * 80; // volta pra linha principal
        double y = 260;
        return moverBotaoPara(botao, x, y);
    }

    // Trilha lateral do auxiliar na insercao (facilita enxergar o "aux").
    private Thread animarAuxiliarBalde(Button botao, int balde, int pos) {
        double x = calcularPosicaoXBucket(balde) - 70;
        double y = 320 + pos * 35;
        return moverBotaoPara(botao, x, y);
    }

    private void pintarComparacaoBucket(Button a, Button b) {
        // Destaca em vermelho os dois que estao sendo comparados no balde.
        Platform.runLater(() -> {
            a.setStyle(BTN_INSERCAO);
            b.setStyle(BTN_INSERCAO);
        });
    }

    private void limparComparacaoBucket(Button a, Button b) {
        // Volta os botoes comparados para o estilo padrao.
        Platform.runLater(() -> {
            a.setStyle(BTN_NORMAL);
            b.setStyle(BTN_NORMAL);
        });
    }

    public void bucket_sort() throws InterruptedException {
        // Bucket em alto nivel:
        // 1) encontra menor/maior
        // 2) distribui por faixa
        // 3) ordena cada balde por insercao
        // 4) concatena tudo de volta.
        destacarLinhaComPausa(1);
        // Quantidade de baldes escolhida por raiz do tamanho.
        int quantidadeBuckets = (int) Math.sqrt(filesize());
        if (quantidadeBuckets == 0)
            quantidadeBuckets = 1;
        desenharBucketsVisuais(quantidadeBuckets);
        atualizarIndicadoresBucket(-1, -1, quantidadeBuckets);
        int n = vet.length;
        int menor, maior, intervalo, pos, k;

        destacarLinhaComPausa(4);
        Button[][] baldes = new Button[quantidadeBuckets][n];
        destacarLinhaComPausa(5);
        // tlBaldes guarda "quantos elementos ja entraram" em cada balde.
        int[] tlBaldes = new int[quantidadeBuckets];

        // Etapa de mapeamento da faixa de valores.
        destacarLinhaComPausa(6);
        menor = maior = valorBotao(vet[0]);
        mostrarBucketMinMax(menor, maior);
        Thread.sleep(700);
        destacarLinhaComPausa(7);
        for (int i = 0; i < n; i++) {
            atualizarIndicadoresBucket(i, -1, quantidadeBuckets);
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

        // Intervalo define o tamanho de cada faixa numerica.
        // Exemplo: se intervalo = 10, valores [0..9] caem num balde, [10..19] em outro...
        destacarLinhaComPausa(11);
        intervalo = (maior - menor + 1) / quantidadeBuckets;
        if (intervalo == 0)
            intervalo = 1;

        // Distribuicao: transforma o valor em indice de balde e anima a "queda".
        destacarLinhaComPausa(13);
        for (int i = 0; i < n; i++) {
            atualizarIndicadoresBucket(i, -1, quantidadeBuckets);
            int valor = valorBotao(vet[i]);
            destacarLinhaComPausa(14);
            pos = (valor - menor) / intervalo;
            destacarLinhaComPausa(18);
            // Protecao para nao estourar o ultimo balde por arredondamento.
            if (pos >= quantidadeBuckets)
                pos = quantidadeBuckets - 1;

            Thread t = animarParaBalde(vet[i], pos, tlBaldes[pos]);
            t.join();

            baldes[pos][tlBaldes[pos]] = vet[i];
            tlBaldes[pos]++;
        }

        // Cada balde e pequeno, entao insercao direta funciona muito bem aqui.
        // A ideia e "arrumar a gaveta por dentro" antes de juntar tudo.
        destacarLinhaComPausa(22);
        Platform.runLater(() -> {
            btnCompA.setVisible(true);
            btnCompB.setVisible(true);
            lblCompSinal.setVisible(true);
            lblComp.setVisible(true);
        });
        for (int b = 0; b < quantidadeBuckets; b++) {
            destacarLinhaComPausa(23);
            for (int i = 1; i < tlBaldes[b]; i++) {
                destacarLinhaComPausa(24);
                Button aux = baldes[b][i];
                atualizarIndicadoresBucket(i, -1, quantidadeBuckets, aux.getText());
                int posAuxVisual = i;
                Thread tAuxLado = animarAuxiliarBalde(aux, b, posAuxVisual);
                tAuxLado.join();
                destacarLinhaComPausa(25);
                int j = i - 1;
                atualizarIndicadoresBucket(i, j, quantidadeBuckets, aux.getText());
                destacarLinhaComPausa(26);
                boolean continuaComparando = true;
                while (j >= 0 && continuaComparando) {
                    // Enquanto aux for menor que o da esquerda, empurra o da esquerda para frente.
                    atualizarIndicadoresBucket(i, j, quantidadeBuckets, aux.getText());
                    Button comparado = baldes[b][j];
                    pintarComparacaoBucket(comparado, aux);
                    Thread.sleep(700);

                    boolean precisaTrocar = valorBotao(comparado) > valorBotao(aux);
                    mostrarComparacaoBotoes(comparado, aux, ">", precisaTrocar);
                    Thread.sleep(750);
                    destacarLinhaComPausa(27);
                    if (precisaTrocar) {
                        // "abre espaco": desloca comparado uma posicao a direita.
                        baldes[b][j + 1] = comparado;
                        destacarLinhaComPausa(28);
                        Thread tShift = animarParaBalde(comparado, b, j + 1);
                        tShift.join();
                        j--;
                        atualizarIndicadoresBucket(i, j, quantidadeBuckets, aux.getText());
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
                // Quando parar, j+1 e exatamente a casa correta do aux.
                baldes[b][j + 1] = aux;
                atualizarIndicadoresBucket(i, j + 1, quantidadeBuckets, aux.getText());
                Thread tAux = animarParaBalde(aux, b, j + 1);
                tAux.join();
                Thread.sleep(800);
            }
        }
        atualizarVisibilidadeMetodo();

        // Junta os baldes na sequencia para montar o vetor ordenado final.
        destacarLinhaComPausa(33);
        Button[] novoVet = new Button[n];
        destacarLinhaComPausa(34);
        k = 0;
        destacarLinhaComPausa(35);
        for (int b = 0; b < quantidadeBuckets; b++) {
            // Concatena na ordem dos baldes: do menor intervalo para o maior.
            destacarLinhaComPausa(36);
            for (int j = 0; j < tlBaldes[b]; j++) {
                atualizarIndicadoresBucket(-1, j, quantidadeBuckets);
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
        atualizarIndicadoresBucket(-1, -1, quantidadeBuckets);
        destacarLinha(-1);
    }

    public Thread move_botoes(int botao0, int botao1) {
        // Animacao de troca no Heap: separa, cruza e recoloca.
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                // 1) afasta verticalmente para visualizar que vai ter troca.
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

                // 2) atravessa na horizontal.
                for (int i = 0; i < passos; i++) {
                    Platform.runLater(() -> vet[botao0].setLayoutX(vet[botao0].getLayoutX() + 5));
                    Platform.runLater(() -> vet[botao1].setLayoutX(vet[botao1].getLayoutX() - 5));
                    try {
                        Thread.sleep(25);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                // 3) volta para a linha do vetor.
                for (int i = 0; i < 10; i++) {
                    Platform.runLater(() -> vet[botao0].setLayoutY(vet[botao0].getLayoutY() - 5));
                    Platform.runLater(() -> vet[botao1].setLayoutY(vet[botao1].getLayoutY() + 5));
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                // Troca de fato no array de botoes (estado logico).
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
