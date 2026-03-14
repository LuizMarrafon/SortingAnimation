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
        botao_inicio.setOnAction(e->{ move_botoes();});
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
                "    int pai, f1, f2, Fmaior, tl2 = tl;",
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


    public void heap_sort(){

    }
    public void move_botoes()
    {
        Task<Void> task = new Task<Void>(){
            @Override
            protected Void call() {
                //permutação na tela
                for (int i = 0; i < 10; i++) {
                    Platform.runLater(() -> vet[0].setLayoutY(vet[0].getLayoutY() + 5));
                    Platform.runLater(() -> vet[1].setLayoutY(vet[1].getLayoutY() - 5));
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                for (int i = 0; i < 16; i++) {
                    Platform.runLater(() -> vet[0].setLayoutX(vet[0].getLayoutX() + 5));
                    Platform.runLater(() -> vet[1].setLayoutX(vet[1].getLayoutX() - 5));
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                for (int i = 0; i < 10; i++) {
                    Platform.runLater(() -> vet[0].setLayoutY(vet[0].getLayoutY() - 5));
                    Platform.runLater(() -> vet[1].setLayoutY(vet[1].getLayoutY() + 5));
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //permutação na memória
                Button aux = vet[0];
                vet[0] = vet[1];
                vet[1] = aux;
                return null;
            }
        };
        Thread thread = new Thread(task);
        thread.start();
    }
}
