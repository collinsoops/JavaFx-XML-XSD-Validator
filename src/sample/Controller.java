package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

public class Controller {
public  String XmlfilePath;
public  String XsdfilePath;
public  Stage stage;
public Scene scene;
public Parent root;
    @FXML
    private AnchorPane XsdXmlpane;
    @FXML
    private Button validateXml;
    @FXML
    private Label Xsdlabel;
    @FXML
    private Label Xmllabel;
    @FXML
    private Button Xsdbutton;
    @FXML
    private Button Xmlbutton;
    @FXML
    public void handleXsdFile(ActionEvent e) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File("e:\\"));

            fileChooser.setTitle("Choose File");
            Stage stage= (Stage) XsdXmlpane.getScene().getWindow();
            File file = fileChooser.showOpenDialog(stage);
            XsdfilePath=file.getAbsolutePath();
            if (file != null) {
                Xsdlabel.setText(file.getAbsolutePath()
                        + " selected");
                System.out.println("The path is"+file.getAbsolutePath());
            } else {
                System.out.println("No file selected");
            }
            Xsdbutton.setOnAction((EventHandler<ActionEvent>) e);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void handleXmlFile(ActionEvent e) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File("e:\\"));

            fileChooser.setTitle("Choose File");
            Stage stage= (Stage) XsdXmlpane.getScene().getWindow();
            File Xsdfile = fileChooser.showOpenDialog(stage);
              XmlfilePath=Xsdfile.getAbsolutePath();
            if (Xsdfile != null) {
                Xmllabel.setText(Xsdfile.getAbsolutePath()
                        + " selected");
                System.out.println("The path is"+Xsdfile.getAbsolutePath());
            } else {
                System.out.println("No file selected");
            }
            Xmlbutton.setOnAction((EventHandler<ActionEvent>) e);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }
    public void validateXmlwithXsd(ActionEvent event) throws IOException {
        System.out.println("The value is"+validateXMLSchema(XsdfilePath, XmlfilePath));
        Boolean isValidXml= validateXMLSchema(XsdfilePath, XmlfilePath);
        if(isValidXml==true){
             root = FXMLLoader.load(getClass().getResource("success.fxml"));
             stage=(Stage) ((Node)event.getSource()).getScene().getWindow();
             stage.setTitle("Success Valid XML and XSD");
            stage.setScene(new Scene(root));
            stage.show();
        } else {
            root = FXMLLoader.load(getClass().getResource("invalid.fxml"));
            stage=(Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setTitle("Invalid XML or XSD");
            stage.setScene(new Scene(root));
            stage.show();
        }

    }

    public static boolean validateXMLSchema(String xsdPath, String xmlPath){
        try {
            SchemaFactory factory =
                    SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(xsdPath));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new File(xmlPath)));
        } catch (IOException | SAXException e) {
            System.out.println("Exception: "+e.getMessage());
            return false;
        }
        return true;
    }
@FXML
    public void goBackHome(ActionEvent event) throws IOException {
            root = FXMLLoader.load(getClass().getResource("home.fxml"));
            stage=(Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setTitle("Home");
            stage.setScene(new Scene(root));
            stage.show();
        }

    }





