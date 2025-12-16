module machineproblem.whowantstobeamillionaire {
    requires javafx.controls;
    requires javafx.fxml;
    requires freetts;


    opens machineproblem.whowantstobeamillionaire to javafx.fxml;
    exports machineproblem.whowantstobeamillionaire;
}