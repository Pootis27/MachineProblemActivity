package machineproblem.whowantstobeamillionaire;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MainMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Who Wants to Be a Millionaire");
        stage.setScene(scene);
        stage.setResizable(false);
        scene.getStylesheets().add(getClass().getResource("/machineproblem/whowantstobeamillionaire/styles.css").toExternalForm());
        stage.show();
    }

    //TODO: ideally separate scene for Main Menu, Main game, and end screen. So we gonna make another fxml for those and actually start with Main Menu
    //TODO: Main menu contains leaderboard (we'll just store it in a database. not that hard) and also asks us for name when starting the game
    //TODO: End screen shows us our final score. Whoever is in charge for this one, feel free to add more
    // Add music maybe idk

    // BTW when starting game, just load the MainGame,fxml. that controller's initialize() will automatically start the game
}
