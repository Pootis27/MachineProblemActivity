module machineproblem.whowantstobeamillionaire {
    requires javafx.controls;
    requires javafx.fxml;


    opens machineproblem.whowantstobeamillionaire to javafx.fxml;
    exports machineproblem.whowantstobeamillionaire;
}