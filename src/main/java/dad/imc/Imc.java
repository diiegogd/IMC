package dad.imc;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Imc extends Application {
    private TextField pesoTextField, alturaTextField;
    private Label imcLabel, clasificacionLabel;
    private DoubleProperty imc;

    @Override
    public void start(Stage primaryStage) {
        pesoTextField = new TextField();
        pesoTextField.setPrefWidth(80);

        alturaTextField = new TextField();
        alturaTextField.setPrefWidth(80);

        imcLabel = new Label();
        imcLabel.setAlignment(Pos.CENTER);

        clasificacionLabel = new Label();
        clasificacionLabel.setAlignment(Pos.CENTER);
        clasificacionLabel.setText("Bajo peso | Normal | Sobrepeso | Obeso");

        imc = new SimpleDoubleProperty();

        // HBox para el campo Peso
        HBox pesoHBox = new HBox(new Label("Peso:"), pesoTextField, new Label("kg"));
        pesoHBox.setSpacing(5);
        pesoHBox.setAlignment(Pos.CENTER);

        // HBox para el campo Altura
        HBox alturaHBox = new HBox(new Label("Altura:"), alturaTextField, new Label("cm"));
        alturaHBox.setSpacing(5);
        alturaHBox.setAlignment(Pos.CENTER);

        // HBox para mostrar el IMC
        HBox imcHBox = new HBox(new Label("IMC:"), imcLabel);
        imcHBox.setSpacing(5);
        imcHBox.setAlignment(Pos.CENTER);

        // HBox para mostrar la clasificación según el IMC
        HBox clasificacionHBox = new HBox(clasificacionLabel);
        clasificacionHBox.setSpacing(5);
        clasificacionHBox.setAlignment(Pos.CENTER);

        // Listeners
        pesoTextField.textProperty().addListener((o, ov, nv) -> { // Si el campo de texto nv o el de altura esta vacío
            if (nv.isEmpty() || alturaTextField.getText().isEmpty()) { // Establece el texto por defecto
                clasificacionLabel.setText("Bajo peso | Normal | Sobrepeso | Obeso");
            } else { // Si no llama al método calcularIMC para calcular el IMC y actualizar la clasificacion
                calcularIMC();
            }
        });

        alturaTextField.textProperty().addListener((o, ov, nv) -> {
            if (nv.isEmpty() || pesoTextField.getText().isEmpty()) {
                clasificacionLabel.setText("Bajo peso | Normal | Sobrepeso | Obeso");
            } else {
                calcularIMC();
            }
        });

        // Binding con formato de 2 decimales
        imcLabel.textProperty().bind(imc.asString("%.2f"));

        // Contenedor principal
        VBox root = new VBox(pesoHBox, alturaHBox, imcHBox, clasificacionHBox);
        root.setStyle("-fx-padding: 50px");
        root.setSpacing(10);

        // Configuración de la escena
        Scene scene = new Scene(root, 350, 220);
        primaryStage.setScene(scene);
        primaryStage.setTitle("IMC");
        primaryStage.show();
    }

    // Método para calcular el IMC y mostrar su clasficación
    private void calcularIMC() {
        try {
            if (pesoTextField.getText().isEmpty() || alturaTextField.getText().isEmpty()) {
                imc.set(0.0);
            } else {
                double peso = Double.parseDouble(pesoTextField.getText());
                double altura = Double.parseDouble(alturaTextField.getText()) / 100;
                if (altura != 0) {
                    imc.set(peso / (altura * altura));
                    double imcValor = imc.get();
                    if (imcValor < 18.5) {
                        clasificacionLabel.setText("Bajo peso");
                    } else if (imcValor < 25) {
                        clasificacionLabel.setText("Normal");
                    } else if (imcValor < 30) {
                        clasificacionLabel.setText("Sobrepeso");
                    } else {
                        clasificacionLabel.setText("Obeso");
                    }
                }
            }
        } catch (NumberFormatException | ArithmeticException e) {
            imc.set(0.0);
        }
    }
}