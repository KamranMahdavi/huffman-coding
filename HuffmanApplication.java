import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.geometry.HPos;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import java.io.File;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import javafx.scene.control.Alert;
import java.lang.ProcessBuilder;

public class HuffmanApplication extends Application{

    private Stage primaryStage;

    private GridPane mainPane;

    private GridPane switchPane;
    private RadioButton encodeSwitch;
    private RadioButton decodeSwitch;
    private ToggleGroup group;

    private GridPane encodePane;
    private Label encodeInputLabel;
    private TextField encodeInputField;
    private Label encodeOutputLabel;
    private TextField encodeOutputField;
    private GridPane buttonPane;
    private Button downloadFile;
    private Button encodeClearFields;
    private Button encode;
    private GridPane mainEncodePane;
    private String downloadString;

    private GridPane decodePane;
    private Label decodeInputLabel;
    private TextField decodeInputField;
    private Label decodeOutputLabel;
    private TextField decodeOutputField;
    private GridPane decodeButtonPane;
    private Label pathName;
    private Button pathChooser;
    private Button decodeClearFields;
    private Button decode;
    private GridPane mainDecodePane;
    private String chosenPath;
    
    public void start(Stage primaryStage){

        this.primaryStage = primaryStage;
        
        primaryStage.setTitle("Huffman Coder Application");

        downloadString = "";
        chosenPath = "";

        group = new ToggleGroup();
        encodeSwitch = new RadioButton("Encode Mode");
        decodeSwitch = new RadioButton("Decode Mode");
        encodeSwitch.setToggleGroup(group);
        decodeSwitch.setToggleGroup(group);
        encodeSwitch.setOnAction(this::swtichToEncode);
        decodeSwitch.setOnAction(this::swtichToDecode);
        switchPane = new GridPane();
        switchPane.add(encodeSwitch, 0, 0);
        switchPane.add(decodeSwitch, 0, 1);
        switchPane.setAlignment(Pos.TOP_CENTER);
        switchPane.setVgap(10);


        
        encodeInputLabel = new Label("Text to be Encoded: ");
        encodeInputField = new TextField();
        encodeInputField.setPrefWidth(150);
        encodeInputField.setMaxWidth(150);
        encodeOutputLabel = new Label("Encoded Text: ");
        encodeOutputField = new TextField();
        encodeOutputField.setPrefWidth(200);
        encodeOutputField.setMaxWidth(200);
        downloadFile = new Button("Download Key File");
        encodeClearFields = new Button("Reset");
        encode = new Button("Encode");
        downloadFile.setOnAction(this::download);
        encodeClearFields.setOnAction(this::encodeClear);
        encode.setOnAction(this::encodeFunc);

        encodePane = new GridPane();
        encodePane.add(encodeInputLabel, 0, 0);
        encodePane.add(encodeInputField, 1, 0);
        encodePane.add(encodeOutputLabel, 0, 1);
        encodePane.add(encodeOutputField, 1, 1);
        encodePane.setAlignment(Pos.CENTER);
        encodePane.setHgap(20);
        encodePane.setVgap(20);

        buttonPane = new GridPane();
        buttonPane.add(downloadFile, 0, 0);
        buttonPane.add(encodeClearFields, 1, 0);
        buttonPane.add(encode, 2, 0);
        buttonPane.setAlignment(Pos.CENTER);
        buttonPane.setHgap(20);
        buttonPane.setVgap(20);

        mainEncodePane = new GridPane();
        mainEncodePane.add(encodePane, 0, 0);
        mainEncodePane.add(buttonPane, 0, 1);
        mainEncodePane.setAlignment(Pos.CENTER);
        mainEncodePane.setVgap(30);



        decodeInputLabel = new Label("Text to be Decoded: ");
        decodeInputField = new TextField();
        decodeInputField.setPrefWidth(200);
        decodeInputField.setMaxWidth(200);
        decodeOutputLabel = new Label("Decoded Text: ");
        decodeOutputField = new TextField();
        decodeOutputField.setPrefWidth(150);
        decodeOutputField.setMaxWidth(150);
        pathName = new Label("No File Selected.");
        pathChooser = new Button("Load Key File");
        decodeClearFields = new Button("Reset");
        decode = new Button("Decode");
        pathChooser.setOnAction(this::chooseFile);
        decodeClearFields.setOnAction(this::decodeClear);
        decode.setOnAction(this::decodeFunc);

        decodePane = new GridPane();
        decodePane.add(decodeInputLabel, 0, 0);
        decodePane.add(decodeInputField, 1, 0);
        decodePane.add(decodeOutputLabel, 0, 1);
        decodePane.add(decodeOutputField, 1, 1);
        decodePane.setAlignment(Pos.CENTER);
        decodePane.setHgap(20);
        decodePane.setVgap(20);

        decodeButtonPane = new GridPane();
        decodeButtonPane.add(pathChooser, 0, 1);
        decodeButtonPane.add(decodeClearFields, 1, 1);
        decodeButtonPane.add(decode, 2, 1);
        decodeButtonPane.setAlignment(Pos.CENTER);
        decodeButtonPane.setHgap(20);

        mainDecodePane = new GridPane();
        mainDecodePane.add(decodePane, 0, 0);
        mainDecodePane.add(pathName, 0, 1);
        mainDecodePane.add(decodeButtonPane, 0, 2);
        mainDecodePane.setAlignment(Pos.CENTER);
        mainDecodePane.setHalignment(pathName, HPos.CENTER);
        mainDecodePane.setVgap(20);



        mainPane = new GridPane();
        mainPane.add(switchPane, 0, 0);
        mainPane.add(mainEncodePane, 0, 1);
        mainPane.add(mainDecodePane, 0, 1);
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setVgap(30);
        setDefault(true);

        Scene myScene = new Scene(mainPane, 380, 320);
        primaryStage.setScene(myScene);
        primaryStage.show();
    }

    public void swtichToEncode(ActionEvent event){
        setDefault(true);
        encodeClear(event);
    }

    public void swtichToDecode(ActionEvent event){
        setDefault(false);
        decodeClear(event);
    }

    public void encodeFunc(ActionEvent event){
        String input = encodeInputField.getText().trim();

        if(input.length() == 0){
            showError("Input is empty.");
            return;
        }

        ProcessBuilder pb = new ProcessBuilder("python", "encode_backend.py", input);

        try{
            Process process = pb.start();
            InputStreamReader isr = new InputStreamReader(process.getInputStream());
            BufferedReader br = new BufferedReader(isr);

            String encodedText = br.readLine().trim();
            String encodedKey = br.readLine();

            String error = new String (process.getErrorStream().readAllBytes());

            int successCode = process.waitFor();

            if(successCode == 0){
                encodeOutputField.setText(encodedText);
                downloadString = encodedKey;
            }
            else{
                showError(error);
                encodeInputField.clear();
            }

        }
        catch (IOException ioe){
            showError("Problem with Input.");
        }
        catch(InterruptedException ie){
            showError("Interrupted Process Flow.");
        }
        


    }

    public void decodeFunc(ActionEvent event){

        String input = decodeInputField.getText().trim();

        if(input.length() == 0){
            showError("Input is empty.");
            return;
        }

        if(chosenPath.length() == 0){
            showError("No key file selected.");
            return;
        }

        ProcessBuilder pb = new ProcessBuilder("python", "decode_backend.py",
                                                input, chosenPath);
        try{
            Process process = pb.start();
            String decodedText = new String (process.getInputStream().readAllBytes());
            String error = new String (process.getErrorStream().readAllBytes());

            int successCode = process.waitFor();

            if(successCode == 0){
                decodeOutputField.setText(decodedText);
            }
            else{
                showError(error);
                decodeInputField.clear();
            }
        }
        catch(IOException ioe){
            showError("Problem with Input.");
        }
        catch(InterruptedException ie){
            showError("Interrupted Process Flow.");
        }

    }

    public void download(ActionEvent event){

        if(downloadString.length() <= 0){
            showError("No key found.");
            return;
        }

        FileChooser chooser = new FileChooser();
        chooser.setTitle("Download Key File");
        ExtensionFilter ef = new ExtensionFilter("JSON File", ".json");
        chooser.getExtensionFilters().add(ef);
        File file = chooser.showSaveDialog(primaryStage);
        if(file == null){
            return;
        }
        else{
            PrintWriter pw = null;

            try{
                pw = new PrintWriter(file);
                pw.print(downloadString);
            }
            catch(Exception e){
                showError(e.getMessage());
            }
            finally{
                if(pw != null){
                    pw.close();
                }
            }
        }
    }

    public void encodeClear(ActionEvent event){
        encodeInputField.clear();
        encodeOutputField.clear();
        downloadString = "";
    }

    public void decodeClear(ActionEvent event){
        decodeInputField.clear();
        decodeOutputField.clear();
        pathName.setText("No File Selected.");
        chosenPath = "";
    }

    public void chooseFile(ActionEvent event){
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Load The Appropriate Key File");
        File file = chooser.showOpenDialog(primaryStage);
        if(file == null){
            return;
        }
        else{
            chosenPath = file.getAbsolutePath();
            pathName.setText(chosenPath);

        }
    }

    private void setDefault(boolean val){
        encodeSwitch.setSelected(val);
        decodeSwitch.setSelected(!val);
        mainEncodePane.setVisible(val);
        mainDecodePane.setVisible(!val);
        mainEncodePane.setManaged(val);
        mainDecodePane.setManaged(!val);
    }

    private void showError(String message){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Error");
        alert.setHeaderText("Invalid Input");
        alert.setContentText(message);
        alert.showAndWait();
    }

}