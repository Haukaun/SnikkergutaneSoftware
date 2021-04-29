package com.snikkergutane;

import com.snikkergutane.project.Comment;
import com.snikkergutane.project.Project;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.ImageInput;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.time.LocalDate;

public class CommentDialog extends Dialog<Comment>{


    private final CommentDialog.Mode mode;

    /**
     * Creates an instance of the CommentDialog
     */
    public CommentDialog() {
        super();
        this.mode = CommentDialog.Mode.NEW;
        showContent();
    }

    /**
     * The mode of the Dialog. If the dialog is open to edit on existing
     * comment, the made is set to <code>Mode.EDIT</code>. If the dialog is
     * opened to create a new comment, the <code>Mode.NEW</code> is used
     */
    public enum Mode{
        NEW, EDIT, INFO
    }


    private Comment existingComment = null;

    /**
     * Creates an instance of the CommentDialog
     * @param comment the comment instance to edit
     * @param editable if set to <code>true</code>, the dialog will enable
     *                 editing of the fields in the dialog. if <code>false</code>
     *                 the information will be displayed in non-editable fields.
     */
    public CommentDialog (Comment comment, boolean editable){
        super();

        if (editable){
            this.mode = CommentDialog.Mode.EDIT;
        } else {
            this.mode = CommentDialog.Mode.INFO;
        }

        this.existingComment = comment;
        showContent();
    }

    /**
     * Creates the content of the dialog
     */
    private void showContent(){

        Stage stage = (Stage) getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(getClass().getResource("/com/snikkergutane/images/logoSG .png").toExternalForm()));
        stage.setTitle("Kommentar");

        ButtonType okButtonType = new ButtonType("Lagre", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        getDialogPane().setGraphic(new ImageView(getClass().getResource("/com/snikkergutane/images/SnikkergutaneLogo.png").toExternalForm()));
        getDialogPane().setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));

        //Adds the grid pane to the UI
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 50, 10, 10));

        //Creates text fields for the dialog
        TextField user = new TextField();
        user.setBorder(new Border(new BorderStroke(Color.LIGHTBLUE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        user.setPromptText("Bruker");

        TextArea commentText = new TextArea();
        commentText.setBorder(new Border(new BorderStroke(Color.LIGHTBLUE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        commentText.setPromptText("Kommentar felt");

        TextField imageURL = new TextField();
        imageURL.setBorder(new Border(new BorderStroke(Color.LIGHTBLUE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        imageURL.setPromptText("Bilde URL");

        //Creates the Image button for the dialog
        HBox imageBox = new HBox();
        Button selectPictureButton = new Button("Rediger valgt kommentar");
        selectPictureButton.setText("Velg Bilde");
        selectPictureButton.setOnAction(e -> imageURL.setText(choosePicture()));
        imageBox.getChildren().addAll(imageURL,selectPictureButton);




        if((mode == CommentDialog.Mode.EDIT) || (mode == CommentDialog.Mode.INFO)){

            user.setText(existingComment.getUser());
            commentText.setText(existingComment.getCommentText());
            imageURL.setText(existingComment.getImage().getUrl());
            user.setEditable(false);
            imageURL.setEditable(false);

            if(mode == CommentDialog.Mode.INFO){
                user.setEditable(false);
                commentText.setEditable(false);
                imageURL.setEditable(false);
            }

        }

        // Adds the text fields and image button
        grid.add(new Label("Bruker:"), 1, 0);
        grid.add(user, 2, 0 );

        grid.add(new Label("Kommentar felt:"), 1, 1);
        grid.add(commentText, 2, 1);

        grid.add(new Label("Bilde URL:"), 1, 2);

        grid.add(imageBox, 2,2) ;


        Node okButton = getDialogPane().lookupButton(okButtonType);
        okButton.setDisable(false);

        getDialogPane().setContent(grid);


        //Sets the user input
        setResultConverter(
                (ButtonType button) -> {
                    Comment result = null;
                    if (button == okButtonType) {
                        if (mode == CommentDialog.Mode.NEW) {
                            if(null != imageURL.getText() && !imageURL.getText().equals("") ) {
                                result = new Comment(LocalDate.now(), user.getText(), commentText.getText(), imageURL.getText());
                            }
                            else{
                                result = new Comment(LocalDate.now(), user.getText(), commentText.getText());
                            }
                        } else if (mode == CommentDialog.Mode.EDIT) {
                            existingComment.setCommentText(commentText.getText());
                            result = existingComment;
                        }
                    }
                    return result;
                }
        );

    }

    /**
     * This button opens a file chooser in order to choose image.
     * This will open file explorer and let the user choose a picture to add to the comment
     * @return returns the imageURL as a String to the text field.
     */
    private String choosePicture(){
        String result = null;
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter(
                "Bildefiler (*.png;*.jpg;*.jpeg)", "*.png;*.jpg;*.jpeg");
        fileChooser.getExtensionFilters().add(extensionFilter);

        fileChooser.setTitle("Åpne ressursfil");
        File file = fileChooser.showOpenDialog(this.getDialogPane().getScene().getWindow());
        if(file != null){
            result = file.toURI().toString();
        }
        return result;

    }



}
