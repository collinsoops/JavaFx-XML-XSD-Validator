package sample;

import javafx.application.HostServices;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.swing.text.AbstractDocument;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static javax.swing.text.AbstractDocument.*;

public class Controller {
public  String XmlfilePath;
public  String XsdfilePath;
public  Stage stage;
public Scene scene;
public Parent root;

    @FXML
    private AnchorPane XsdXmlpane;
    @FXML
    private Label Xsdtext;
    @FXML
    private Label Xmltext;
    @FXML
    private Button validateXml;
    @FXML
    private Label Resultlabel;
    @FXML
    private TextArea Xsdtextarea;
    @FXML
    private HTMLEditor editor;
    @FXML
    private TextArea Xmltextarea;
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
            Stage stage= (Stage) XsdXmlpane.getScene().getWindow();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XSD files (*.xsd)", "*.xsd");
            fileChooser.getExtensionFilters().add(extFilter);

            File Xsdfile = fileChooser.showOpenDialog(stage);

           /*
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File("e:\\"));

            fileChooser.setTitle("Choose File");
            Stage stage= (Stage) XsdXmlpane.getScene().getWindow();
            File file = fileChooser.showOpenDialog(stage);*/
            XsdfilePath=Xsdfile.getAbsolutePath();

            String line = "";
            String tline="";

            try( FileReader fileStream = new FileReader( XsdfilePath );
                 BufferedReader bufferedReader = new BufferedReader(fileStream) ) {
                while((line = bufferedReader.readLine()) != null ) {
                    System.out.println("the line is"+line);
                    line=line+"\n";
                    tline += line;
                }
                Xsdtextarea.setText(tline);
                System.out.println("this is"+ tline);

            } catch ( FileNotFoundException ex ) {
                //exception Handling
            } catch ( IOException ex ) {
                //exception Handling
            }

            if (Xsdfile != null) {
                Xsdlabel.setText(Xsdfile.getAbsolutePath()
                        + " selected");
                System.out.println("The path is"+Xsdfile.getAbsolutePath());
                System.out.println("the tline is"+tline);

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
            Stage stage= (Stage) XsdXmlpane.getScene().getWindow();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
            fileChooser.getExtensionFilters().add(extFilter);
            File Xmlfile = fileChooser.showOpenDialog(stage);

         /*
          FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
            fileChooser.getExtensionFilters().add(extFilter);
            fileChooser.setInitialDirectory(new File("e:\\"));

            fileChooser.setTitle("Choose File");
            Stage stage= (Stage) XsdXmlpane.getScene().getWindow();
            File Xmlfile = fileChooser.showOpenDialog(stage);
         */

            XmlfilePath=Xmlfile.getAbsolutePath();
            String Xmlline = "";
            String Xmltext="";

            try(FileReader fileStream = new FileReader(XmlfilePath );
                 BufferedReader bufferedReader = new BufferedReader(fileStream) ) {
                while((Xmlline = bufferedReader.readLine()) != null ) {
                    System.out.println("the Xmline is"+Xmlline);
                    Xmlline=Xmlline+"\n";
                    Xmltext += Xmlline;
                }
                Xmltextarea.setText(Xmltext);
                System.out.println("this is"+ Xmltext);

            } catch ( FileNotFoundException ex ) {
                //exception Handling
            } catch ( IOException ex ) {
                //exception Handling
            }

            if (Xmlfile != null) {
                Xmllabel.setText(Xmlfile.getAbsolutePath()
                        + " selected");
                System.out.println("The path is"+Xmlfile.getAbsolutePath());
            } else {
                System.out.println("No file selected");
            }
            Xmlbutton.setOnAction((EventHandler<ActionEvent>) e);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    public void validateXmlwithXsd(ActionEvent event) throws IOException, SAXException {
        System.out.println("The reult is true using path"+validateXMLSchema(XsdfilePath, XmlfilePath));

        //converting to input stream
        InputStream Xsdstream = new ByteArrayInputStream(Xsdtextarea.getText().getBytes(StandardCharsets.UTF_8));
        InputStream Xmlstream = new ByteArrayInputStream(Xmltextarea.getText().getBytes(StandardCharsets.UTF_8));

        System.out.println("The text area values are"+Xsdtextarea.getText());
        Xsdtextarea.getText();
        Boolean isValidXml = validate(Xmltextarea.getText(),Xsdtextarea.getText());
                //validateAgainstXSDUsingInputstream(Xsdstream,Xmlstream);
        System.out.println("The is Xml is "+ isValidXml);

        if(isValidXml == true ){
            Resultlabel.setText("SUCCESS! CORRECT XML!");

        } else {
            Resultlabel.setText("WARNING! WRONG XML!");

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



    public boolean validate(String inputXml, String inputXsd)
            throws SAXException, IOException {
        // build the schema
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        //File schemaFile = new File(schemaLocation);
        Source Xsdsource = new StreamSource(new StringReader(inputXsd));
        Schema schema=factory.newSchema(Xsdsource);
        Validator validator = schema.newValidator();

        // create a source from a string
        Source source = new StreamSource(new StringReader(inputXml));

        // check input
        boolean isValid = true;
        try  {

            validator.validate(source);
        }
        catch (SAXException e) {

            System.err.println("Not valid");
            isValid = false;
        }
        return isValid;
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





