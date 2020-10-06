
import java.util.HashMap;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.swing.*;

public class WordGuessServer extends Application{


	TextField s1,s2,s3,s4, c1,portnumber;
	Button serverChoice,clientChoice,b1,sports;
	HashMap<String, Scene> sceneMap;
	GridPane grid;
	HBox buttonBox;
	VBox clientBox;
	Scene startScene;
	BorderPane startPane;
	Server serverConnection;
	private MediaPlayer mp;
	private MediaView mediaView;
	ListView<String> listItems, listItems2;


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("Server") ;

		this.serverChoice = new Button("Server");
		this.serverChoice.setStyle("-fx-pref-width: 100px");
		this.serverChoice.setStyle("-fx-pref-height: 50px");
        portnumber = new TextField("Enter Port Number");
        Label error = new Label();
        String message = "Wrong portNumber please enter valid portNumber";
		this.serverChoice.setOnAction(e->{
		    if(!portnumber.getText().equals( "5555"))
            {
                JOptionPane.showMessageDialog(new JFrame(), message, "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
		    else {
                primaryStage.setScene(sceneMap.get("server"));
                primaryStage.setTitle("This is the Server");
                serverConnection = new Server(data -> {
                    Platform.runLater(() -> {
                        listItems.getItems().add(data.toString());
                    });

                });
            }
		});




		this.buttonBox = new HBox(20, serverChoice);
		startPane = new BorderPane();
		startPane.setPadding(new Insets(70));
		startPane.setTop(portnumber);
		startPane.setCenter(buttonBox);

        BackgroundImage myBI= new BackgroundImage(new Image("https://thumbs.dreamstime.com/b/d-people-connect-server-color-background-43782245.jpg",300,300,false,true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
            startPane.setBackground(new Background(myBI));
		startScene = new Scene(startPane, 300,300);

		listItems = new ListView<String>(); //listitems for the server

		c1 = new TextField();
		b1 = new Button("Send");


		sceneMap = new HashMap<String, Scene>();

		sceneMap.put("server",  createServerGui());

		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent t) {
				Platform.exit();
				System.exit(0);
			}
		});



		primaryStage.setScene(startScene);
		primaryStage.show();

	}

	public Scene createServerGui() {

		BorderPane pane = new BorderPane();
		pane.setPadding(new Insets(70));
        BackgroundImage myBI= new BackgroundImage(new Image("https://us.123rf.com/450wm/olegdudko/olegdudko1809/olegdudko180902309/108396929-texture-mother-plate-on-server-background.jpg?ver=6",500,400,false,true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
pane.setBackground(new Background(myBI));

		pane.setCenter(listItems);

		return new Scene(pane, 500, 400);


	}

}
