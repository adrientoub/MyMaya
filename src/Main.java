import javafx.application.Application;
import javafx.stage.Stage;
import view.WindowView;

/**
 * Created by Adrien on 20/10/2016.
 */
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        WindowView wv = new WindowView(primaryStage);
        wv.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
