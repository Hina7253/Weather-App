module org.example.weatherapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;


    opens org.example.weatherapp to javafx.fxml;
    exports org.example.weatherapp;
}