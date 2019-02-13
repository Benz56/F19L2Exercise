/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package f19l2exercise;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.stream.Stream;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

/**
 *
 * https://e-learn.sdu.dk/bbcswebdav/pid-5473544-dt-content-rid-9385468_2/courses/TEK-SI2-VOP-U1-1-F19/Search%20and%20Replace.pdf
 * 
 * @author Benjamin Staugaard | Benz56
 */
public class FXMLDocumentController implements Initializable {

    private FileChooser fileChooser;

    @FXML
    private TextField searchField;

    @FXML
    private TextField replaceField;

    @FXML
    private TextArea textarea;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("."));
    }

    @FXML
    private void onReplaceAll() {
        textarea.setText(textarea.getText().replaceAll(searchField.getText(), replaceField.getText()));
    }

    @FXML
    private void onOpen() {
        File selectedFile = fileChooser.showOpenDialog(searchField.getScene().getWindow());
        if (selectedFile != null) {
            textarea.setText("");
            try (Scanner scanner = new Scanner(selectedFile)) {
                while (scanner.hasNextLine()) {
                    textarea.appendText(scanner.nextLine() + (scanner.hasNextLine() ? "\n" : ""));
                }
            } catch (FileNotFoundException ex) {
                textarea.setText("Can't open file!");
            }
        }
    }

    @FXML
    private void onSaveAs() {
        File selectedFile = fileChooser.showSaveDialog(searchField.getScene().getWindow());
        if (selectedFile != null) {
            try {
                if (!selectedFile.exists()) {
                    selectedFile.createNewFile();
                }
            } catch (IOException ex) {
                System.out.println("Couldn't create file!");
                return;
            }
            try (PrintWriter printWriter = new PrintWriter(selectedFile)) {
                Stream.of(textarea.getText().split("\n")).forEach(line -> printWriter.println(line));
            } catch (FileNotFoundException ex) {
                textarea.setText("File not found!");
            }
        }
    }
}
