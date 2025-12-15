package machineproblem.whowantstobeamillionaire;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        stage.initStyle(StageStyle.UTILITY);
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MainGame.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    //TODO: ideally separate scene for Main Menu, Main game, and end screen. So we gonna make another fxml for those and actually start with Main Menu
    //TODO: Main menu contains leaderboard (we'll just store it in a database. not that hard) and also asks us for name when starting the game
    //TODO: End screen shows us our final score. Whoever is in charge for this one, feel free to add more
    // Add music maybe idk

    // BTW when starting game, just load the MainGame,fxml. that controller's initialize() will automatically start the game
}
