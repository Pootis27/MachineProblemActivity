module machineproblem.whowantstobeamillionaire {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens machineproblem.whowantstobeamillionaire to javafx.fxml;
    exports machineproblem.whowantstobeamillionaire;
}