package org.example;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.text.Font;

import java.util.Random;


public class Principal extends Application {
    AnchorPane pane;
    Button botao_inicio;
    private Button vet[];
    private Label[] linhasCodigo;
    private static final String ESTILO_NORMAL = "-fx-font-family: Consolas; -fx-font-size: 13px;";
    private static final String ESTILO_ATUAL  = "-fx-font-family: Consolas; -fx-font-size: 13px; -fx-background-color: #ffe082; -fx-font-weight: bold;";


    public static void main(String[] args)
    {
        launch(args);
    }


    @Override
    public void start(Stage stage) throws Exception
    {
        stage.setTitle("Pesquisa e Ordenacao");
        pane = new AnchorPane();
        botao_inicio = new Button();
        botao_inicio.setLayoutX(10); botao_inicio.setLayoutY(100);
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
            int numero = random.nextInt(100); // 0 a 99
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

    private void criarPainelCodigo()
    {
        String[] codigo = {
                "public void heap_sort(){",
                "    int pai, f1, f2, Fmaior, tl;",
                "    while(tl > 1){",
                "        for(pai = tl2/2-1; pai >= 0; pai--){",
                "            f1 = 2*pai+1;",
                "            f2 = f1+1;",
                "            Fmaior = f1;",
                "            if(f2 < tl2 && vet[f2] > vet[f1]){",
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
    }

    private void destacarLinhaComPausa(int linha1Based) throws InterruptedException {
        destacarLinha(linha1Based);
        Thread.sleep(200); // pausa para visualização
    }

    private void destacarLinha(int linha1Based) {
        Platform.runLater(() -> {
            for (Label l : linhasCodigo) l.setStyle(ESTILO_NORMAL);
            int idx = linha1Based - 1;
            if (idx >= 0 && idx < linhasCodigo.length) {
                linhasCodigo[idx].setStyle(ESTILO_ATUAL);
            }
        });
    }


    public void heap_sort() throws InterruptedException {
        int pai, f1, f2, Fmaior, tl = vet.length;
        destacarLinhaComPausa(3); // while(tl > 1)
        while (tl > 1) {
            destacarLinhaComPausa(4); // for(pai = ...)
            for (pai = tl / 2 - 1; pai >= 0; pai--) {
                destacarLinhaComPausa(5);
                f1 = 2 * pai + 1;
                destacarLinhaComPausa(6);
                f2 = f1 + 1;
                destacarLinhaComPausa(7);
                Fmaior = f1;
                destacarLinhaComPausa(8);
                if (f2 < tl && Integer.parseInt(vet[f2].getText()) > Integer.parseInt(vet[f1].getText())) {
                    destacarLinhaComPausa(9);
                    Fmaior = f2;
                }
                destacarLinhaComPausa(11);
                if (Integer.parseInt(vet[pai].getText()) < Integer.parseInt(vet[Fmaior].getText())) {
                    destacarLinhaComPausa(12);
                    Thread t = move_botoes(pai, Fmaior);
                    t.join();
                }
            }
            destacarLinhaComPausa(17);
            Thread t = move_botoes(0, tl - 1);
            t.join();
            destacarLinhaComPausa(20);
            tl--;
        }
        destacarLinhaComPausa(-1); // limpa destaque no final
    }

    public Thread move_botoes(int botao0, int botao1)
    {
        Task<Void> task = new Task<Void>(){
            @Override
            protected Void call() {
                //permutação na tela
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
                        Thread.sleep(50);
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
                //permutação na memória
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
