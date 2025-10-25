package org.example.weatherapp;



import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class WeatherAppGUI extends Application {

    private Label cityLabel = new Label("Enter City:");
    private TextField cityField = new TextField();
    private Button searchButton = new Button("Search");
    private Label weatherIcon = new Label();
    private Label tempLabel = new Label();
    private Label descLabel = new Label();
    private VBox weatherBox = new VBox(10);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Modern Weather App");

        // Layout
        HBox inputBox = new HBox(10, cityLabel, cityField, searchButton);
        inputBox.setAlignment(Pos.CENTER);
        inputBox.setPadding(new Insets(20));

        weatherBox.setAlignment(Pos.CENTER);
        weatherBox.getChildren().addAll(weatherIcon, tempLabel, descLabel);
        weatherBox.setPadding(new Insets(20));

        VBox root = new VBox(20, inputBox, weatherBox);
        root.setAlignment(Pos.TOP_CENTER);
        root.setBackground(new Background(new BackgroundFill(
                new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE,
                        new Stop(0, Color.web("#89f7fe")),
                        new Stop(1, Color.web("#66a6ff"))),
                CornerRadii.EMPTY, Insets.EMPTY)));

        // Fonts & Style
        weatherIcon.setFont(Font.font(80));
        tempLabel.setFont(Font.font(40));
        descLabel.setFont(Font.font(20));

        Scene scene = new Scene(root, 450, 400);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Button Action
        searchButton.setOnAction(e -> fetchWeather());
    }

    private void fetchWeather() {
        String city = cityField.getText().trim();
        if (city.isEmpty()) return;

        new Thread(() -> {
            Weather.WeatherInfo info = Weather.getWeather(city);
            Platform.runLater(() -> {
                if (info.error != null) {
                    weatherIcon.setText("❌");
                    tempLabel.setText("");
                    descLabel.setText(info.error);
                } else {
                    weatherIcon.setText(getIcon(info.condition));
                    tempLabel.setText(info.temp + "°C");
                    descLabel.setText(info.description);
                }
            });
        }).start();
    }

    private String getIcon(String condition) {
        switch (condition.toLowerCase()) {
            case "clear": return "🌞";
            case "clouds": return "☁️";
            case "rain": return "🌧️";
            case "drizzle": return "💧";
            case "thunderstorm": return "⛈️";
            case "snow": return "❄️";
            default: return "🌡️";
        }
    }
}

