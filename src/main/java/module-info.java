module machineproblem.whowantstobeamillionaire {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.desktop;


    opens machineproblem.whowantstobeamillionaire to javafx.fxml;
    exports machineproblem.whowantstobeamillionaire;
}