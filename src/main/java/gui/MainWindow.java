package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import yapper.Yapper;


public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Yapper yapper;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/User.jpg"));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/Yapper.jpg"));

    @FXML
    /**
     * @brief   initializes the chatroom with welcome by the chatbot
     */
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());

        String opening = "Wagwan, its yapper. What can I do for ya?";
        dialogContainer.getChildren().add(
                DialogBox.getYapperDialog(opening, dukeImage));
    }

    /** Injects the Duke instance */
    public void setYapper(Yapper y) {
        yapper = y;
    }


    @FXML
    /**
     * @brief handles user input and generates chatbot replies, and terminates the session on "bye".
     */
    private void handleUserInput() {
        String input = userInput.getText();
        String response = yapper.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getYapperDialog(response, dukeImage)
        );
        userInput.clear();

        if ("bye".equalsIgnoreCase(input.trim())) {
            Stage stage = (Stage) sendButton.getScene().getWindow();
            stage.close();
        }
    }
}
