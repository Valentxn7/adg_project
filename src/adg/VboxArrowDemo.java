package adg;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class VboxArrowDemo extends Application {

    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();

        // Création de deux VBox représentant des classes
        Classe classe1 = new Classe("Classe1");

        Classe classe2 = new Classe("Classe2");
        VBox c1= new VueClasse(classe1);
        VBox c2= new VueClasse(classe2);
        c1.setLayoutX(100);
        c1.setLayoutY(100);
        c2.setLayoutX(300);
        c2.setLayoutY(100);
        // Création d'une flèche
        Text a = new Text("Attribut");
        Fleche arrow = new FlecheAttri(a);
        arrow.setPos(c2, c1);
        root.getChildren().add(arrow);
        root.getChildren().addAll(c1, c2, a);
        Polygon arrowHead = arrow.getTete();
        root.getChildren().add(arrowHead);
        c1.setOnDragDetected(e -> {
            makeDraggable(c1, arrow, c2);
        });
        // Création de la scène
        Scene scene = new Scene(root, 600, 400);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        primaryStage.setTitle("Flèches entre VBox");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Méthode pour créer une VBox représentant une classe
    private VBox createClassBox(String className, double x, double y) {
        VBox vbox = new VBox();
        vbox.setLayoutX(x);
        vbox.setLayoutY(y);
        vbox.setStyle("-fx-border-color: black; -fx-background-color: lightgray;");
        vbox.setSpacing(10);
        vbox.getChildren().add(new Text(className));
        vbox.setPrefSize(100, 60);
        return vbox;
    }

    // Méthode pour rendre une VBox déplaçable
    private void makeDraggable(VBox vbox, Fleche arrow, VBox otherBox) {
        final double[] mouseOffset = new double[2];

        vbox.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            mouseOffset[0] = event.getSceneX() - vbox.getLayoutX();
            mouseOffset[1] = event.getSceneY() - vbox.getLayoutY();
        });

        vbox.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> {
            vbox.setLayoutX(event.getSceneX() - mouseOffset[0]);
            vbox.setLayoutY(event.getSceneY() - mouseOffset[1]);
            arrow.setPos(vbox, otherBox);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}

