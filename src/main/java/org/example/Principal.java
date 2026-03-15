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
    private Label lblComp;

    private static final String ESTILO_NORMAL = "-fx-font-family: Consolas; -fx-font-size: 13px;";
    private static final String ESTILO_ATUAL = "-fx-font-family: Consolas; -fx-font-size: 13px; -fx-background-color: #ffe082; -fx-font-weight: bold;";
    private static final String BTN_NORMAL = "-fx-font-size: 14px;";
    private static final String BTN_PAI = "-fx-font-size: 14px; -fx-background-color: #0A1626; -fx-text-fill: white;";
    private static final String BTN_FILHO = "-fx-font-size: 14px; -fx-background-color: #A64208;";
    private static final String BTN_MAIOR = "-fx-font-size: 14px; -fx-background-color: #0E5673;";
    private static final String BTN_ORDENADO = "-fx-font-size: 14px; -fx-background-color: #66bb6a; -fx-text-fill: white;";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Pesquisa e Ordenacao");
        pane = new AnchorPane();

        // botoes de comparacao, so visual
        btnCompA = new Button("-");
        btnCompA.setLayoutX(300);
        btnCompA.setLayoutY(350);
        btnCompA.setMinSize(50, 35);
        btnCompA.setStyle("-fx-background-color: #0E5673; -fx-text-fill: black; -fx-font-size: 16px; -fx-font-weight: bold;");
        pane.getChildren().add(btnCompA);

        btnCompB = new Button("-");
        btnCompB.setLayoutX(370);
        btnCompB.setLayoutY(350);
        btnCompB.setMinSize(50, 35);
        btnCompB.setStyle("-fx-background-color: #0E5673; -fx-text-fill: black; -fx-font-size: 16px; -fx-font-weight: bold;");
        pane.getChildren().add(btnCompB);

        lblComp = new Label("Comparacao: -");
        lblComp.setLayoutX(430);
        lblComp.setLayoutY(355);
        lblComp.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        pane.getChildren().add(lblComp);

        botao_inicio = new Button();
        botao_inicio.setLayoutX(10);
        botao_inicio.setLayoutY(100);
        botao_inicio.setText("Inicia...");
        botao_inicio.setOnAction(e -> {
            Task<Void> t = new Task<>() {
                @Override
                protected Void call() throws Exception {
                    heap_sort();
                    return null;
                }
            };
            new Thread(t).start();
        });
        pane.getChildren().add(botao_inicio);

        Random random = new Random();
        vet = new Button[8];
        for (int i = 0; i < vet.length; i++) {
            int numero = random.nextInt(100);
            vet[i] = new Button(String.valueOf(numero));
            vet[i].setLayoutX(100 + (i * 80));
            vet[i].setLayoutY(200);
            vet[i].setMinHeight(40);
            vet[i].setMinWidth(40);
            vet[i].setFont(new Font(14));
            pane.getChildren().add(vet[i]);
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
                "            if(vet[pai] < vet[Fmaior]){",
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
        painelCodigo.setLayoutX(820);
        painelCodigo.setLayoutY(40);
        painelCodigo.setStyle("-fx-background-color:#f4f4f4; -fx-padding:10; -fx-border-color:#ccc;");

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
        VBox legenda = new VBox(6);
        legenda.setLayoutX(1071);
        legenda.setLayoutY(477);
        legenda.setStyle("-fx-background-color:#f4f4f4; -fx-padding:10; -fx-border-color:#ccc;");

        Label titulo = new Label("Legenda de cores");
        titulo.setStyle("-fx-font-weight: bold; -fx-font-size: 13px;");

        legenda.getChildren().addAll(
                titulo,
                itemLegenda("#0A1626", "Pai"),
                itemLegenda("#A64208", "Filho (f1/f2)"),
                itemLegenda("#0E5673", "Maior filho"),
                itemLegenda("#66bb6a", "Ordenado")
        );

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
        Thread.sleep(260);
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
            if (resultado) {
                lblComp.setText(vet[a].getText() + " " + op + " " + vet[b].getText() + " ? SIM");
            } else {
                lblComp.setText(vet[a].getText() + " " + op + " " + vet[b].getText() + " ? NAO");
            }
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
                    Thread.sleep(300);
                }

                //aqui volta o heap normal
                if (f2 < tl && Integer.parseInt(vet[f2].getText()) > Integer.parseInt(vet[f1].getText())) {
                    destacarLinhaComPausa(9);
                    Fmaior = f2;
                }

                destacarBotao(Fmaior, BTN_MAIOR);
                destacarLinhaComPausa(11);

                // essa parte e so de comparacao e visual, nao tem nada com o codigo.
                boolean r2 = Integer.parseInt(vet[pai].getText()) < Integer.parseInt(vet[Fmaior].getText());
                mostrarComparacao(pai, Fmaior, "<", r2);
                Thread.sleep(300);

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
        Platform.runLater(() -> lblComp.setText("Comparacao: fim"));
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
