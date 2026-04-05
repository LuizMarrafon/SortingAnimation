package org.example;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Principal extends Application
{
    AnchorPane pane;
    AnchorPane areaExecucao;
    Button botao_inicio;
    private Button[] vet;
    private Label[] indicesVet;
    private Label[] linhasCodigo;
    private Button btnCompA, btnCompB;
    private Label lblCompSinal;
    private Label lblComp;
    private Label lblTituloAlgoritmo;
    private Button btnStatus1;
    private Button btnStatus2;
    private Button btnStatus3;
    private Button btnStatus4;
    private Button btnStatusTL;
    private Button btnStatusMenor;
    private Button btnStatusMaior;
    private Button btnStatusP;
    private Button btnStatusPos;
    private int ultimoMenorBucket = -1;
    private int ultimoMaiorBucket = -1;
    private final List<ImageView> bucketImagens = new ArrayList<>();
    private Image imagemBalde;
    private int quantidadeBucketsAtual = 1;
    private String algoritmoSelecionado = "HEAP";
    private VBox painelCodigo;
    private ScrollPane scrollPainelCodigo;
    private VBox legendaCores;
    private Button btnMenuHeap;
    private Button btnMenuBucket;
    private Button botao_reset;
    private Button botao_parar;
    private Thread threadExecucaoAtual;
    private volatile boolean execucaoCancelada = false;

    private static final String ESTILO_NORMAL = "-fx-font-family: Consolas; -fx-font-size: 13px;";
    private static final String ESTILO_ATUAL = "-fx-font-family: Consolas; -fx-font-size: 13px; -fx-background-color: #ffe082; -fx-font-weight: bold;";
    private static final String BTN_NUM_BASE = "-fx-font-family: 'Segoe UI'; -fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: white; -fx-background-insets: 0; -fx-background-radius: 6; -fx-border-radius: 6; -fx-border-color: transparent; -fx-padding: 0;";
    private static final String BTN_NORMAL = BTN_NUM_BASE + "-fx-background-color: #475569;";
    private static final String BTN_PAI = BTN_NUM_BASE + "-fx-background-color: #0A1626;";
    private static final String BTN_FILHO = BTN_NUM_BASE + "-fx-background-color: #A64208;";
    private static final String BTN_MAIOR = BTN_NUM_BASE + "-fx-background-color: #0E5673;";
    private static final String BTN_INSERCAO = BTN_NUM_BASE + "-fx-background-color: #dc2626;";
    private static final String BTN_ORDENADO = BTN_NUM_BASE + "-fx-background-color: #66bb6a;";
    private static final String COMP_BTN_BASE = "-fx-background-color: #0E5673; -fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;";
    private static final String COMP_LABEL_BASE = "-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #0f172a;";
    private static final String COMP_LABEL_TRUE = "-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #14532d; -fx-background-color: #dcfce7; -fx-padding: 4 8 4 8; -fx-background-radius: 8;";
    private static final String COMP_LABEL_FALSE = "-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #7f1d1d; -fx-background-color: #fee2e2; -fx-padding: 4 8 4 8; -fx-background-radius: 8;";
    private static final String STATUS_BTN_STYLE = "-fx-font-size: 14px; -fx-font-weight: bold; -fx-background-color: #0f766e; -fx-text-fill: white;";

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage stage)
    {
        stage.setTitle("Pesquisa e Ordenacao");
        stage.setMaximized(true);
        pane = new AnchorPane();
        pane.setStyle("-fx-background-color: #e9eef5;");

        // divide a interface em duas metades

        // esquerda para ver a execucao
        areaExecucao = new AnchorPane();
        areaExecucao.setLayoutX(20);
        areaExecucao.setLayoutY(20);
        areaExecucao.setPrefSize(900, 700);
        areaExecucao.setStyle("-fx-background-color: #f8fafc; -fx-border-color: #cbd5e1; -fx-border-radius: 12; -fx-background-radius: 12;");
        pane.getChildren().add(areaExecucao);

        // carrega a imagemzinha do balde
        File arquivoBalde = new File("balde/balde.png");
        if (arquivoBalde.exists())
            imagemBalde = new Image(arquivoBalde.toURI().toString());

        // direita para acompanhar o codigo
        AnchorPane areaCodigo = new AnchorPane();
        areaCodigo.setLayoutX(940);
        areaCodigo.setLayoutY(20);
        areaCodigo.setPrefSize(490, 700);
        areaCodigo.setStyle("-fx-background-color: #f8fafc; -fx-border-color: #cbd5e1; -fx-border-radius: 12; -fx-background-radius: 12;");
        pane.getChildren().add(areaCodigo);

        // titulo da visualizacao atual
        lblTituloAlgoritmo = new Label("Heap Sort - Visualizacao");
        lblTituloAlgoritmo.setLayoutX(20);
        lblTituloAlgoritmo.setLayoutY(10);
        lblTituloAlgoritmo.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #0f172a;");
        areaExecucao.getChildren().add(lblTituloAlgoritmo);

        // menuzinho para heap e bucket
        btnMenuHeap = new Button("Heap Sort");
        btnMenuHeap.setLayoutX(20);
        btnMenuHeap.setLayoutY(50);
        btnMenuHeap.setFocusTraversable(false);
        btnMenuHeap.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-background-color: #0f172a; -fx-text-fill: white;");
        areaExecucao.getChildren().add(btnMenuHeap);

        btnMenuBucket = new Button("Bucket Sort");
        btnMenuBucket.setLayoutX(120);
        btnMenuBucket.setLayoutY(50);
        btnMenuBucket.setFocusTraversable(false);
        btnMenuBucket.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-background-color: #e2e8f0; -fx-text-fill: #0f172a;");
        areaExecucao.getChildren().add(btnMenuBucket);

        // caso o botao do heap seja apertado
        btnMenuHeap.setOnAction(e ->
        {
            algoritmoSelecionado = "HEAP";
            lblTituloAlgoritmo.setText("Heap Sort - Visualizacao");
            atualizarPainelCodigo("HEAP");
            atualizarVisibilidadeMetodo();
            btnMenuHeap.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-background-color: #0f172a; -fx-text-fill: white;");
            btnMenuBucket.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-background-color: #e2e8f0; -fx-text-fill: #0f172a;");
        });

        // caso o botao do bucket seja apertado
        btnMenuBucket.setOnAction(e ->
        {
            algoritmoSelecionado = "BUCKET";
            lblTituloAlgoritmo.setText("Bucket Sort - Visualizacao");
            atualizarPainelCodigo("BUCKET");
            atualizarVisibilidadeMetodo();
            btnMenuBucket.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-background-color: #0f172a; -fx-text-fill: white;");
            btnMenuHeap.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-background-color: #e2e8f0; -fx-text-fill: #0f172a;");
        });

        // area para simular as comparacoes
        btnCompA = new Button("-");
        btnCompA.setLayoutX(245);
        btnCompA.setLayoutY(480);
        btnCompA.setMinSize(50, 35);
        btnCompA.setPrefSize(50, 35);
        btnCompA.setMaxSize(50, 35);
        btnCompA.setMouseTransparent(true);
        btnCompA.setFocusTraversable(false);
        btnCompA.setStyle(COMP_BTN_BASE);
        areaExecucao.getChildren().add(btnCompA);

        btnCompB = new Button("-");
        btnCompB.setLayoutX(335);
        btnCompB.setLayoutY(480);
        btnCompB.setMinSize(50, 35);
        btnCompB.setPrefSize(50, 35);
        btnCompB.setMaxSize(50, 35);
        btnCompB.setMouseTransparent(true);
        btnCompB.setFocusTraversable(false);
        btnCompB.setStyle(COMP_BTN_BASE);
        areaExecucao.getChildren().add(btnCompB);

        lblCompSinal = new Label(">");
        lblCompSinal.setLayoutX(305);
        lblCompSinal.setLayoutY(478);
        lblCompSinal.setMinSize(20, 40);
        lblCompSinal.setPrefSize(20, 40);
        lblCompSinal.setMaxSize(20, 40);
        lblCompSinal.setAlignment(Pos.CENTER);
        lblCompSinal.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #0f172a;");
        areaExecucao.getChildren().add(lblCompSinal);

        lblComp = new Label("");
        lblComp.setLayoutX(395);
        lblComp.setLayoutY(485);
        lblComp.setStyle(COMP_LABEL_BASE);
        areaExecucao.getChildren().add(lblComp);

        btnStatus1 = new Button("-");
        btnStatus1.setLayoutX(260);
        btnStatus1.setLayoutY(90);
        btnStatus1.setMinWidth(95);
        btnStatus1.setFocusTraversable(false);
        btnStatus1.setStyle(STATUS_BTN_STYLE);
        areaExecucao.getChildren().add(btnStatus1);

        btnStatus2 = new Button("-");
        btnStatus2.setLayoutX(365);
        btnStatus2.setLayoutY(90);
        btnStatus2.setMinWidth(95);
        btnStatus2.setFocusTraversable(false);
        btnStatus2.setStyle(STATUS_BTN_STYLE);
        areaExecucao.getChildren().add(btnStatus2);

        btnStatus3 = new Button("-");
        btnStatus3.setLayoutX(470);
        btnStatus3.setLayoutY(90);
        btnStatus3.setMinWidth(125);
        btnStatus3.setFocusTraversable(false);
        btnStatus3.setStyle(STATUS_BTN_STYLE);
        areaExecucao.getChildren().add(btnStatus3);

        btnStatus4 = new Button("-");
        btnStatus4.setLayoutX(605);
        btnStatus4.setLayoutY(90);
        btnStatus4.setMinWidth(125);
        btnStatus4.setFocusTraversable(false);
        btnStatus4.setStyle(STATUS_BTN_STYLE);
        areaExecucao.getChildren().add(btnStatus4);

        // area para adiciionar as variaveis apra teste de mesa
        btnStatusTL = new Button("TL: -");
        btnStatusTL.setLayoutX(740);
        btnStatusTL.setLayoutY(90);
        btnStatusTL.setMinWidth(80);
        btnStatusTL.setFocusTraversable(false);
        btnStatusTL.setStyle(STATUS_BTN_STYLE);
        areaExecucao.getChildren().add(btnStatusTL);
        
        btnStatusMenor = new Button("menor: -");
        btnStatusMenor.setLayoutX(260);
        btnStatusMenor.setLayoutY(130);
        btnStatusMenor.setMinWidth(95);
        btnStatusMenor.setFocusTraversable(false);
        btnStatusMenor.setStyle(STATUS_BTN_STYLE);
        btnStatusMenor.setVisible(false);
        areaExecucao.getChildren().add(btnStatusMenor);
        
        btnStatusMaior = new Button("maior: -");
        btnStatusMaior.setLayoutX(365);
        btnStatusMaior.setLayoutY(130);
        btnStatusMaior.setMinWidth(95);
        btnStatusMaior.setFocusTraversable(false);
        btnStatusMaior.setStyle(STATUS_BTN_STYLE);
        btnStatusMaior.setVisible(false);
        areaExecucao.getChildren().add(btnStatusMaior);

        btnStatusP = new Button("p: -");
        btnStatusP.setLayoutX(470);
        btnStatusP.setLayoutY(130);
        btnStatusP.setMinWidth(95);
        btnStatusP.setFocusTraversable(false);
        btnStatusP.setStyle(STATUS_BTN_STYLE);
        btnStatusP.setVisible(false);
        areaExecucao.getChildren().add(btnStatusP);

        btnStatusPos = new Button("pos: -");
        btnStatusPos.setLayoutX(575);
        btnStatusPos.setLayoutY(130);
        btnStatusPos.setMinWidth(95);
        btnStatusPos.setFocusTraversable(false);
        btnStatusPos.setStyle(STATUS_BTN_STYLE);
        btnStatusPos.setVisible(false);
        areaExecucao.getChildren().add(btnStatusPos);

        // iniciar a animacao selecionada
        botao_inicio = new Button();
        botao_inicio.setLayoutX(20);
        botao_inicio.setLayoutY(90);
        botao_inicio.setText("Iniciar");
        botao_inicio.setFocusTraversable(false);
        botao_inicio.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-background-color: #0f172a; -fx-text-fill: white;");
        botao_inicio.setOnAction(e ->
        {
            execucaoCancelada = false;
            botao_inicio.setVisible(false);
            btnMenuHeap.setVisible(false);
            btnMenuBucket.setVisible(false);
            botao_reset.setVisible(false);
            botao_parar.setVisible(true);
            Task<Void> t = new Task<>()
            {
                @Override
                protected Void call() throws Exception
                {
                    if (algoritmoSelecionado.equals("HEAP"))
                        heap_sort();
                    else
                        bucket_sort();
                    return null;
                }
            };
            t.setOnSucceeded(ev ->
            {
                botao_parar.setVisible(false);
                if (!execucaoCancelada)
                    botao_reset.setVisible(true);
                threadExecucaoAtual = null;
            });
            t.setOnFailed(ev ->
            {
                botao_parar.setVisible(false);
                if (!execucaoCancelada)
                    botao_reset.setVisible(true);
                threadExecucaoAtual = null;
            });
            threadExecucaoAtual = new Thread(t);
            threadExecucaoAtual.start();
        });
        areaExecucao.getChildren().add(botao_inicio);

        // para resetar dps q termina a animacao
        botao_reset = new Button("Resetar");
        botao_reset.setLayoutX(100);
        botao_reset.setLayoutY(90);
        botao_reset.setFocusTraversable(false);
        botao_reset.setVisible(false);
        botao_reset.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-background-color: #475569; -fx-text-fill: white;");
        botao_reset.setOnAction(e -> resetarTela());
        areaExecucao.getChildren().add(botao_reset);

        // para parar enquanto esta acontecendo alguma animacao
        botao_parar = new Button("Parar");
        botao_parar.setLayoutX(190);
        botao_parar.setLayoutY(90);
        botao_parar.setFocusTraversable(false);
        botao_parar.setVisible(false);
        botao_parar.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-background-color: #b91c1c; -fx-text-fill: white;");
        botao_parar.setOnAction(e ->
        {
            execucaoCancelada = true;
            if (threadExecucaoAtual != null)
                threadExecucaoAtual.interrupt();
            resetarTela();
        });
        areaExecucao.getChildren().add(botao_parar);

        // 9 valores aleatorios pro vetor
        Random random = new Random();
        vet = new Button[9];
        indicesVet = new Label[9];
        for (int i = 0; i < vet.length; i++)
        {
            int numero = random.nextInt(100);
            vet[i] = new Button(String.valueOf(numero));
            vet[i].setLayoutX(110 + (i * 80));
            vet[i].setLayoutY(200);
            vet[i].setMinHeight(40);
            vet[i].setMinWidth(40);
            vet[i].setPrefHeight(40);
            vet[i].setPrefWidth(40);
            vet[i].setMaxHeight(40);
            vet[i].setMaxWidth(40);
            vet[i].setFocusTraversable(false);
            vet[i].setMouseTransparent(true);
            vet[i].setStyle(BTN_NORMAL);
            areaExecucao.getChildren().add(vet[i]);

            indicesVet[i] = new Label(String.valueOf(i));
            indicesVet[i].setLayoutX(110 + (i * 80) + 15);
            indicesVet[i].setLayoutY(245);
            indicesVet[i].setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #475569;");
            areaExecucao.getChildren().add(indicesVet[i]);
        }

        criarPainelCodigo();
        atualizarVisibilidadeMetodo();

        Scene scene = new Scene(pane, 1470, 850);
        stage.setScene(scene);
        stage.show();
    }

    private void resetarTela()
    {
        ultimoMenorBucket = -1;
        ultimoMaiorBucket = -1;
        threadExecucaoAtual = null;
        Random random = new Random();
        for (int i = 0; i < vet.length; i++)
        {
            int numero = random.nextInt(100);
            vet[i].setText(String.valueOf(numero));
            vet[i].setLayoutX(110 + (i * 80));
            vet[i].setLayoutY(200);
            vet[i].setStyle(BTN_NORMAL);
        }
        btnCompA.setText("-");
        btnCompB.setText("-");
        lblCompSinal.setText(">");
        lblComp.setText("");
        lblComp.setStyle(COMP_LABEL_BASE);
        configurarIndicadoresMetodo();
        destacarLinha(-1);
        atualizarVisibilidadeMetodo();

        botao_inicio.setVisible(true);
        btnMenuHeap.setVisible(true);
        btnMenuBucket.setVisible(true);
        botao_reset.setVisible(false);
        botao_parar.setVisible(false);
    }

    private void atualizarVisibilidadeMetodo()
    {
        boolean ehHeap = "HEAP".equals(algoritmoSelecionado);
        posicionarPainelComparacao(ehHeap);

        btnCompA.setVisible(ehHeap);
        btnCompB.setVisible(ehHeap);
        lblCompSinal.setVisible(ehHeap);
        lblComp.setVisible(ehHeap);
        legendaCores.setVisible(ehHeap);
        btnStatusTL.setVisible(ehHeap);
        btnStatusMenor.setVisible(!ehHeap);
        btnStatusMaior.setVisible(!ehHeap);
        btnStatusP.setVisible(!ehHeap);
        btnStatusPos.setVisible(!ehHeap);

        btnStatus4.setVisible(true);
        setBucketImagensVisiveis(!ehHeap);
        configurarIndicadoresMetodo();
    }

    // preciso passar por parametro pq os 2 metodos colocam essa area de comparacao em lugares diferntes
    private void posicionarPainelComparacao(boolean ehHeap)
    {
        if (ehHeap)
        {
            btnCompA.setLayoutX(245);
            btnCompA.setLayoutY(480);
            btnCompB.setLayoutX(335);
            btnCompB.setLayoutY(480);
            lblCompSinal.setLayoutX(305);
            lblCompSinal.setLayoutY(478);
            lblComp.setLayoutX(395);
            lblComp.setLayoutY(485);
        }
        else
        {
            btnCompA.setLayoutX(720);
            btnCompA.setLayoutY(140);
            btnCompB.setLayoutX(810);
            btnCompB.setLayoutY(140);
            lblCompSinal.setLayoutX(780);
            lblCompSinal.setLayoutY(138);
            lblComp.setLayoutX(720);
            lblComp.setLayoutY(180);
        }
    }

    private void criarPainelCodigo()
    {
        painelCodigo = new VBox(2);
        painelCodigo.setStyle("-fx-background-color:#ffffff; -fx-padding:12;");

        scrollPainelCodigo = new ScrollPane(painelCodigo);
        scrollPainelCodigo.setLayoutX(960);
        scrollPainelCodigo.setLayoutY(35);
        scrollPainelCodigo.setPrefSize(470, 650);
        scrollPainelCodigo.setFitToWidth(true);
        scrollPainelCodigo.setStyle("-fx-background-color:#ffffff; -fx-border-color:#cbd5e1; -fx-background-radius: 10; -fx-border-radius: 10;");
        pane.getChildren().add(scrollPainelCodigo);
        atualizarPainelCodigo("HEAP");
        criarLegendaCores();
    }

    private void atualizarPainelCodigo(String algoritmo)
    {
        String[] codigo;
        if ("BUCKET".equals(algoritmo))
        {
            codigo = new String[]
                    {
                    "public void bucketSort(){",
                    "    int qntdBuckets = (int)Math.sqrt(filesize());",
                    "    if(qntdBuckets == 0)",
                    "        qntdBuckets = 1;",
                    "    int menor, maior, intervalo, pos, k;",
                    "    int[][] baldes = new int[qntdBuckets][TL];",
                    "    int[] TLbaldes = new int[qntdBuckets];",
                    "    menor = maior = vetor[0];",
                    "    for(int i = 0; i < TL; i++){",
                    "        if(vetor[i] < menor)",
                    "            menor = vetor[i];",
                    "        if(vetor[i] > maior)",
                    "            maior = vetor[i];",
                    "    }",
                    "    intervalo = (maior - menor + 1) / qntdBuckets;",
                    "    for(int i = 0; i < qntdBuckets; i++)",
                    "        TLbaldes[i] = 0;",
                    "    for(int i = 0; i < TL; i++){",
                    "        if(intervalo == 0){",
                    "            baldes[0][TLbaldes[0]++] = vetor[i];",
                    "        }else{",
                    "            pos = (vetor[i] - menor) / intervalo;",
                    "            if(pos >= qntdBuckets)",
                    "                pos = qntdBuckets - 1;",
                    "            baldes[pos][TLbaldes[pos]++] = vetor[i];",
                    "        }",
                    "    }",
                    "    for(int i = 0; i < qntdBuckets; i++){",
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
                    "    for(int i = 0; i < qntdBuckets; i++){",
                    "        for(int j = 0; j < TLbaldes[i]; j++)",
                    "            vetor[k++] = baldes[i][j];",
                    "    }",
                    "}"
            };
        }
        else
        {
            codigo = new String[]
                    {
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
        for (int i = 0; i < codigo.length; i++)
        {
            Label linha = new Label(String.format("%2d  %s", i + 1, codigo[i]));
            linha.setStyle("-fx-font-family: Consolas; -fx-font-size: 13px;");
            linhasCodigo[i] = linha;
            painelCodigo.getChildren().add(linha);
        }
    }

    private void criarLegendaCores()
    {
        legendaCores = new VBox(8);
        legendaCores.setLayoutX(200);
        legendaCores.setLayoutY(540);
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

        areaExecucao.getChildren().add(legendaCores);
    }

    private HBox itemLegenda(String corHex, String texto)
    {
        Label cor = new Label("   ");
        cor.setMinSize(18, 18);
        cor.setStyle("-fx-background-color: " + corHex + "; -fx-border-color: #666;");

        Label desc = new Label(texto);
        desc.setStyle("-fx-font-size: 12px;");

        HBox linha = new HBox(8, cor, desc);
        linha.setAlignment(Pos.CENTER_LEFT);
        return linha;
    }

    private void destacarLinhaComPausa(int numLinha) throws InterruptedException
    {
        if (execucaoCancelada || Thread.currentThread().isInterrupted())
            throw new InterruptedException("Execucao cancelada");

        destacarLinha(numLinha);
        Thread.sleep(700);
    }

    private void destacarLinha(int numLinha)
    {
        Platform.runLater(() ->
        {
            for (Label l : linhasCodigo)
            {
                l.setStyle(ESTILO_NORMAL);
            }
            int idx = numLinha - 1;
            if (idx >= 0 && idx < linhasCodigo.length)
                linhasCodigo[idx].setStyle(ESTILO_ATUAL);
        });
    }

    private void pintarBase(int tl)
    {
        for (int i = 0; i < vet.length; i++)
        {
            if (i >= tl)
                vet[i].setStyle(BTN_ORDENADO);
            else
                vet[i].setStyle(BTN_NORMAL);
        }
    }

    public void destacarBotao(int botao, String estilo)
    {
        Platform.runLater(() -> vet[botao].setStyle(estilo));
    }

    public void mostrarComparacao(int a, int b, String op, boolean resultado)
    {
        Platform.runLater(() ->
        {
            btnCompA.setText(vet[a].getText());
            btnCompB.setText(vet[b].getText());
            lblCompSinal.setText(op);
            btnCompA.setStyle(vet[a].getStyle());
            btnCompB.setStyle(vet[b].getStyle());
            if (resultado)
            {
                lblComp.setText("Resultado: SIM");
                lblComp.setStyle(COMP_LABEL_TRUE);
            } else
            {
                lblComp.setText("Resultado: NAO");
                lblComp.setStyle(COMP_LABEL_FALSE);
            }
        });
    }

    private void mostrarComparacaoBotoes(Button a, Button b, String op, boolean resultado)
    {
        Platform.runLater(() ->
        {
            btnCompA.setText(a.getText());
            btnCompB.setText(b.getText());
            lblCompSinal.setText(op);
            btnCompA.setStyle(a.getStyle());
            btnCompB.setStyle(b.getStyle());
            if (resultado)
            {
                lblComp.setText("Resultado: SIM");
                lblComp.setStyle(COMP_LABEL_TRUE);
            } else
            {
                lblComp.setText("Resultado: NAO");
                lblComp.setStyle(COMP_LABEL_FALSE);
            }
        });
    }

    private void limparComparacaoVisual()
    {
        Platform.runLater(() ->
        {
            btnCompA.setText("-");
            btnCompB.setText("-");
            btnCompA.setStyle(COMP_BTN_BASE);
            btnCompB.setStyle(COMP_BTN_BASE);
            lblCompSinal.setText(">");
            lblComp.setText("");
            lblComp.setStyle(COMP_LABEL_BASE);
        });
    }

    private void desenharBucketsVisuais(int qntdBuckets)
    {
        quantidadeBucketsAtual = Math.max(1, qntdBuckets);
        Platform.runLater(() ->
        {
            for (ImageView view : bucketImagens)
                areaExecucao.getChildren().remove(view);
            bucketImagens.clear();

            for (int b = 0; b < qntdBuckets; b++)
            {
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

    private double calcularPosicaoXBucket(int indiceBalde)
    {
        double largura = areaExecucao.getPrefWidth();
        double margemEsquerda = 110;
        double margemDireita = largura - 140;
        if (quantidadeBucketsAtual <= 1)
            return (margemEsquerda + margemDireita) / 2.0;
        double passo = (margemDireita - margemEsquerda) / (quantidadeBucketsAtual - 1.0);
        return margemEsquerda + (indiceBalde * passo);
    }

    private void setBucketImagensVisiveis(boolean visivel)
    {
        Platform.runLater(() ->
        {
            for (ImageView view : bucketImagens)
            {
                view.setVisible(visivel);
            }
        });
    }

    private void configurarIndicadoresMetodo()
    {
        if ("HEAP".equals(algoritmoSelecionado))
            atualizarIndicadoresHeap(-1, -1, -1, "-");
        else
            atualizarIndicadoresBucket(-1, -1, calcularQuantidadeBuckets(), "-");
    }

    private void atualizarIndicadoresHeap(int pai, int filho1, int filho2, String auxValor)
    {
        Platform.runLater(() ->
        {
            btnStatus1.setText("pai: " + (pai >= 0 ? pai : "-"));
            btnStatus2.setText("filho1: " + (filho1 >= 0 ? filho1 : "-"));
            btnStatus3.setText("filho2: " + (filho2 >= 0 ? filho2 : "-"));
            btnStatus4.setText("aux: " + auxValor);
        });
    }
    
    private void atualizarIndicadoresHeap(int pai, int filho1, int filho2, String auxValor, int tl)
    {
        Platform.runLater(() ->
        {
            btnStatus1.setText("pai: " + (pai >= 0 ? pai : "-"));
            btnStatus2.setText("filho1: " + (filho1 >= 0 ? filho1 : "-"));
            btnStatus3.setText("filho2: " + (filho2 >= 0 ? filho2 : "-"));
            btnStatus4.setText("aux: " + auxValor);
            btnStatusTL.setText("TL: " + tl);
        });
    }

    private void atualizarIndicadoresBucket(int i, int j, int qtdBaldes)
    {
        atualizarIndicadoresBucket(i, j, qtdBaldes, "-", -1, -1, -1, -1);
    }

    private void atualizarIndicadoresBucket(int i, int j, int qtdBaldes, String auxValor)
    {
        atualizarIndicadoresBucket(i, j, qtdBaldes, auxValor, -1, -1, -1, -1);
    }
    
    private void atualizarIndicadoresBucket(int i, int j, int qtdBaldes, String auxValor, int menor, int maior)
    {
        atualizarIndicadoresBucket(i, j, qtdBaldes, auxValor, menor, maior, -1, -1);
    }

    private void atualizarIndicadoresBucket(int i, int j, int qtdBaldes, String auxValor, int menor, int maior, int p) {
        atualizarIndicadoresBucket(i, j, qtdBaldes, auxValor, menor, maior, p, -1);
    }

    private void atualizarIndicadoresBucket(int i, int j, int qtdBaldes, String auxValor, int menor, int maior, int p, int pos)
    {
        Platform.runLater(() ->
        {
            if (menor >= 0)
                ultimoMenorBucket = menor;


            if (maior >= 0)
                ultimoMaiorBucket = maior;

            btnStatus1.setText("i: " + (i >= 0 ? i : "-"));
            btnStatus2.setText("j: " + (j >= 0 ? j : "-"));
            btnStatus3.setText("qntdBuckets: " + qtdBaldes);
            btnStatus4.setText("aux: " + auxValor);
            btnStatusMenor.setText("menor: " + (ultimoMenorBucket >= 0 ? ultimoMenorBucket : "-"));
            btnStatusMaior.setText("maior: " + (ultimoMaiorBucket >= 0 ? ultimoMaiorBucket : "-"));
            btnStatusP.setText("p: " + (p >= 0 ? p : "-"));
            btnStatusPos.setText("pos: " + (pos >= 0 ? pos : "-"));
        });
    }

    private int filesize()
    {
        return vet.length;
    }

    private int calcularQuantidadeBuckets()
    {
        int qntdBuckets = (int) Math.sqrt(filesize());
        if (qntdBuckets == 0)
            qntdBuckets = 1;

        return qntdBuckets;
    }

    public void heap_sort() throws InterruptedException
    {
        destacarLinhaComPausa(1);
        int pai, f1, f2, Fmaior, tl = vet.length;
        destacarLinhaComPausa(2);
        atualizarIndicadoresHeap(-1, -1, -1, "-", tl);
        destacarLinhaComPausa(3);
        while (tl > 1)
        {
            destacarLinhaComPausa(4);

            for (pai = tl / 2 - 1; pai >= 0; pai--)
            {
                destacarBotao(pai, BTN_PAI);
                destacarLinhaComPausa(5);
                f1 = 2 * pai + 1;
                atualizarIndicadoresHeap(pai, f1, -1, "-", tl);
                if (f1 < tl)
                    destacarBotao(f1, BTN_FILHO);


                destacarLinhaComPausa(6);
                f2 = f1 + 1;
                atualizarIndicadoresHeap(pai, f1, f2 < tl ? f2 : -1, "-", tl);
                if (f2 < tl)
                    destacarBotao(f2, BTN_FILHO);


                destacarLinhaComPausa(7);
                Fmaior = f1;
                destacarLinhaComPausa(8);

                if (f2 < tl)
                {
                    boolean r1 = Integer.parseInt(vet[f2].getText()) > Integer.parseInt(vet[f1].getText());
                    mostrarComparacao(f2, f1, ">", r1);
                    Thread.sleep(1700);
                    limparComparacaoVisual();
                    if(f2 < tl && Integer.parseInt(vet[f2].getText()) > Integer.parseInt(vet[f1].getText()))
                    {
                        destacarLinhaComPausa(9);
                        Fmaior = f2;
                    }
                }
                destacarBotao(Fmaior, BTN_MAIOR);
                destacarLinhaComPausa(11);

                boolean r2 = Integer.parseInt(vet[pai].getText()) < Integer.parseInt(vet[Fmaior].getText());
                mostrarComparacao(Fmaior,pai, ">", r2);
                Thread.sleep(1700);
                limparComparacaoVisual();

                if (Integer.parseInt(vet[pai].getText()) < Integer.parseInt(vet[Fmaior].getText()))
                {
                    destacarLinhaComPausa(12);
                    atualizarIndicadoresHeap(pai, f1, f2 < tl ? f2 : -1, vet[pai].getText(), tl);

                    Thread t = move_botoes(pai, Fmaior);

                    boolean mostrou13 = false;
                    boolean mostrou14 = false;
                    boolean mostrou15 = false;

                    while (t.isAlive())
                    {
                        if (execucaoCancelada || Thread.currentThread().isInterrupted())
                        {
                            t.interrupt();
                            throw new InterruptedException("Execucao cancelada");
                        }

                        if (!mostrou13)
                        {
                            destacarLinha(13); // int aux = vet[pai];
                            mostrou13 = true;
                            Thread.sleep(120);
                        }
                        else if (!mostrou14)
                        {
                            destacarLinha(14); // vet[pai] = vet[Fmaior];
                            mostrou14 = true;
                            Thread.sleep(120);
                        }
                        else if (!mostrou15)
                        {
                            destacarLinha(15); // vet[Fmaior] = aux;
                            mostrou15 = true;
                            Thread.sleep(120);
                        }
                        else
                        {
                            Thread.sleep(40);
                        }
                    }

                    if (!mostrou15)
                        destacarLinha(15);

                    t.join();
                    atualizarIndicadoresHeap(pai, f1, f2 < tl ? f2 : -1, "-", tl);
                }
                pintarBase(tl);
            }

            destacarLinhaComPausa(17);
            atualizarIndicadoresHeap(0, -1, -1, vet[0].getText(), tl);
            Thread t = move_botoes(0, tl - 1);
            t.join();
            atualizarIndicadoresHeap(0, -1, -1, "-", tl);
            int pos = tl - 1;
            Platform.runLater(() -> vet[pos].setStyle(BTN_ORDENADO));
            Thread.sleep(80);
            destacarLinhaComPausa(20);
            tl--;
            final int tlAtual = tl;
            Platform.runLater(() -> btnStatusTL.setText("TL: " + tlAtual));
        }
        Platform.runLater(() -> vet[0].setStyle(BTN_ORDENADO));
        Thread.sleep(80);
        atualizarIndicadoresHeap(-1, -1, -1, "-", tl);
        destacarLinha(-1);
    }

    private int valorBotao(Button b)
    {
        return Integer.parseInt(b.getText());
    }

    private Thread moverBotaoPara(Button botao, double alvoX, double alvoY)
    {
        Task<Void> task = new Task<>()
        {
            @Override
            protected Void call()
            {
                boolean cancelar = execucaoCancelada || Thread.currentThread().isInterrupted();

                while ((Math.abs(alvoX - botao.getLayoutX()) > 1 || Math.abs(alvoY - botao.getLayoutY()) > 1) && !cancelar)
                {
                    double atualX = botao.getLayoutX();
                    double atualY = botao.getLayoutY();

                    double dx = alvoX - atualX;
                    double dy = alvoY - atualY;

                    double passoX = Math.signum(dx) * Math.min(5, Math.abs(dx));
                    double passoY = Math.signum(dy) * Math.min(5, Math.abs(dy));

                    double novoX = atualX + passoX;
                    double novoY = atualY + passoY;

                    Platform.runLater(() ->
                    {
                        botao.setLayoutX(novoX);
                        botao.setLayoutY(novoY);
                    });

                    try
                    {
                        Thread.sleep(30);
                    }
                    catch (InterruptedException e)
                    {
                        Thread.currentThread().interrupt();
                        cancelar = true;
                    }

                    cancelar = cancelar || execucaoCancelada || Thread.currentThread().isInterrupted();
                }

                if (!cancelar)
                {
                    Platform.runLater(() ->
                    {
                        botao.setLayoutX(alvoX);
                        botao.setLayoutY(alvoY);
                    });
                }

                return null;
            }
        };

        Thread thread = new Thread(task);
        thread.start();
        return thread;
    }

    private Thread animarParaBalde(Button botao, int balde, int pos)
    {
        double x = calcularPosicaoXBucket(balde) - 20;
        double y = 320 + pos * 35;
        return moverBotaoPara(botao, x, y);
    }

    private Thread animarDoBaldeParaLinha(Button botao, int indiceFinal)
    {
        double x = 110 + indiceFinal * 80; // volta pra linha principal
        double y = 200;
        return moverBotaoPara(botao, x, y);
    }

    private Thread animarAuxiliarBalde(Button botao, int balde, int pos)
    {
        double x = calcularPosicaoXBucket(balde) - 70;
        double y = 320 + pos * 35;
        return moverBotaoPara(botao, x, y);
    }

    private void pintarComparacaoBucket(Button a, Button b)
    {
        Platform.runLater(() -> {
            a.setStyle(BTN_INSERCAO);
            b.setStyle(BTN_INSERCAO);
        });
    }

    private void limparComparacaoBucket(Button a, Button b)
    {
        Platform.runLater(() ->
        {
            a.setStyle(BTN_NORMAL);
            b.setStyle(BTN_NORMAL);
        });
    }

    public void bucket_sort() throws InterruptedException
    {
        destacarLinhaComPausa(1);
        destacarLinhaComPausa(2);
        int qntdBuckets = (int) Math.sqrt(filesize());
        destacarLinhaComPausa(3);
        if (qntdBuckets == 0)
        {
            destacarLinhaComPausa(4);
            qntdBuckets = 1;
        }
        desenharBucketsVisuais(qntdBuckets);
        atualizarIndicadoresBucket(-1, -1, qntdBuckets);
        int n = vet.length;
        destacarLinhaComPausa(5);
        int menor, maior, intervalo, pos, k;

        destacarLinhaComPausa(6);
        Button[][] baldes = new Button[qntdBuckets][n];
        destacarLinhaComPausa(7);
        int[] tlBaldes = new int[qntdBuckets];

        destacarLinhaComPausa(8);
        menor = maior = valorBotao(vet[0]);
        atualizarIndicadoresBucket(-1, -1, qntdBuckets, "-", menor, maior);
        Thread.sleep(700);
        destacarLinhaComPausa(9);
        for (int i = 0; i < n; i++)
        {
            atualizarIndicadoresBucket(i, -1, qntdBuckets, "-", menor, maior);
            int valor = valorBotao(vet[i]);
            vet[i].setStyle(BTN_PAI);
            destacarLinhaComPausa(10);
            if (valor < menor)
            {
                destacarLinhaComPausa(11);
                menor = valor;
            }
            destacarLinhaComPausa(12);
            if (valor > maior)
            {
                destacarLinhaComPausa(13);
                maior = valor;
            }
            atualizarIndicadoresBucket(i, -1, qntdBuckets, "-", menor, maior);
            Thread.sleep(450);
            vet[i].setStyle(BTN_NORMAL);
        }

        destacarLinhaComPausa(15);
        intervalo = (maior - menor + 1) / qntdBuckets;
        if (intervalo == 0)
            intervalo = 1;

        destacarLinhaComPausa(18);
        for (int i = 0; i < n; i++)
        {
            atualizarIndicadoresBucket(i, -1, qntdBuckets);
            int valor = valorBotao(vet[i]);
            destacarLinhaComPausa(22);
            pos = (valor - menor) / intervalo;
            atualizarIndicadoresBucket(i, -1, qntdBuckets, "-", menor, maior, -1, pos);
            destacarLinhaComPausa(23);
            if (pos >= qntdBuckets)
            {
                destacarLinhaComPausa(24);
                pos = qntdBuckets - 1;
                atualizarIndicadoresBucket(i, -1, qntdBuckets, "-", menor, maior, -1, pos);
            }

            Thread t = animarParaBalde(vet[i], pos, tlBaldes[pos]);
            t.join();

            destacarLinhaComPausa(25);
            atualizarIndicadoresBucket(i, -1, qntdBuckets, "-", menor, maior, -1, pos);
            baldes[pos][tlBaldes[pos]] = vet[i];
            tlBaldes[pos]++;
        }

        destacarLinhaComPausa(28);
        Platform.runLater(() ->
        {
            btnCompA.setVisible(true);
            btnCompB.setVisible(true);
            lblCompSinal.setVisible(true);
            lblComp.setVisible(true);
        });
        for (int b = 0; b < qntdBuckets; b++)
        {
            destacarLinhaComPausa(28);
            for (int j = 1; j < tlBaldes[b]; j++)
            {
                destacarLinhaComPausa(29);
                Button aux = baldes[b][j];
                destacarLinhaComPausa(30);
                atualizarIndicadoresBucket(b, j, qntdBuckets, aux.getText(), -1, -1, j);
                int posAuxVisual = j;
                Thread tAuxLado = animarAuxiliarBalde(aux, b, posAuxVisual);
                tAuxLado.join();
                destacarLinhaComPausa(31);
                int p = j;
                atualizarIndicadoresBucket(b, j, qntdBuckets, aux.getText(), -1, -1, p);
                destacarLinhaComPausa(32);
                boolean continuaComparando = true;
                while (p > 0 && continuaComparando)
                {
                    atualizarIndicadoresBucket(b, j, qntdBuckets, aux.getText(), -1, -1, p);
                    Button comparado = baldes[b][p - 1];
                    pintarComparacaoBucket(comparado, aux);
                    Thread.sleep(700);

                    boolean precisaTrocar = valorBotao(comparado) > valorBotao(aux);
                    mostrarComparacaoBotoes(comparado, aux, ">", precisaTrocar);
                    Thread.sleep(750);
                    destacarLinhaComPausa(33);
                    if (precisaTrocar)
                    {
                        baldes[b][p] = comparado;
                        destacarLinhaComPausa(34);
                        Thread tShift = animarParaBalde(comparado, b, p);
                        tShift.join();
                        p--;
                        atualizarIndicadoresBucket(b, j, qntdBuckets, aux.getText(), -1, -1, p);
                        posAuxVisual--;
                        Thread tAuxSobe = animarAuxiliarBalde(aux, b, posAuxVisual);
                        tAuxSobe.join();
                        Thread.sleep(600);
                    }
                    else
                    {
                        continuaComparando = false;
                    }
                    limparComparacaoBucket(comparado, aux);
                    limparComparacaoVisual();
                }
                destacarLinhaComPausa(36);
                baldes[b][p] = aux;
                atualizarIndicadoresBucket(b, j, qntdBuckets, aux.getText(), -1, -1, p);
                Thread tAux = animarParaBalde(aux, b, p);
                tAux.join();
                Thread.sleep(800);
            }
        }
        atualizarVisibilidadeMetodo();

        Button[] novoVet = new Button[n];
        destacarLinhaComPausa(39);
        k = 0;
        destacarLinhaComPausa(40);
        for (int b = 0; b < qntdBuckets; b++)
        {
            destacarLinhaComPausa(40);
            for (int j = 0; j < tlBaldes[b]; j++)
            {
                destacarLinhaComPausa(41);
                atualizarIndicadoresBucket(b, j, qntdBuckets);
                Button atual = baldes[b][j];
                Thread t = animarDoBaldeParaLinha(atual, k);
                t.join();
                Platform.runLater(() -> atual.setStyle(BTN_ORDENADO));
                Thread.sleep(320);
                destacarLinhaComPausa(42);
                novoVet[k] = atual;
                k++;
            }
        }
        vet = novoVet;
        atualizarIndicadoresBucket(-1, -1, qntdBuckets, "-", menor, maior, -1);
        destacarLinha(-1);
    }

    public Thread move_botoes(int botao0, int botao1)
    {
        Task<Void> task = new Task<>()
        {
            @Override
            protected Void call()
            {
                boolean cancelar = execucaoCancelada || Thread.currentThread().isInterrupted();

                for (int i = 0; i < 10 && !cancelar; i++)
                {
                    Platform.runLater(() -> vet[botao0].setLayoutY(vet[botao0].getLayoutY() + 5));
                    Platform.runLater(() -> vet[botao1].setLayoutY(vet[botao1].getLayoutY() - 5));

                    try
                    {
                        Thread.sleep(50);
                    }
                    catch (InterruptedException e)
                    {
                        Thread.currentThread().interrupt();
                        cancelar = true;
                    }

                    cancelar = cancelar || execucaoCancelada || Thread.currentThread().isInterrupted();
                }

                double distancia = Math.abs(vet[botao1].getLayoutX() - vet[botao0].getLayoutX());
                int passos = (int) (distancia / 5);

                for (int i = 0; i < passos && !cancelar; i++)
                {
                    Platform.runLater(() -> vet[botao0].setLayoutX(vet[botao0].getLayoutX() + 5));
                    Platform.runLater(() -> vet[botao1].setLayoutX(vet[botao1].getLayoutX() - 5));

                    try
                    {
                        Thread.sleep(25);
                    }
                    catch (InterruptedException e)
                    {
                        Thread.currentThread().interrupt();
                        cancelar = true;
                    }

                    cancelar = cancelar || execucaoCancelada || Thread.currentThread().isInterrupted();
                }

                for (int i = 0; i < 10 && !cancelar; i++)
                {
                    Platform.runLater(() -> vet[botao0].setLayoutY(vet[botao0].getLayoutY() - 5));
                    Platform.runLater(() -> vet[botao1].setLayoutY(vet[botao1].getLayoutY() + 5));

                    try
                    {
                        Thread.sleep(50);
                    }
                    catch (InterruptedException e)
                    {
                        Thread.currentThread().interrupt();
                        cancelar = true;
                    }

                    cancelar = cancelar || execucaoCancelada || Thread.currentThread().isInterrupted();
                }

                if (!cancelar)
                {
                    Button aux = vet[botao0];
                    vet[botao0] = vet[botao1];
                    vet[botao1] = aux;
                }

                return null;
            }
        };

        Thread thread = new Thread(task);
        thread.start();
        return thread;
    }
}
