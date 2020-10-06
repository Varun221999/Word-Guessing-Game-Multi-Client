
import java.util.Arrays;
import java.util.HashMap;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.swing.*;

public class WordGuessClient extends Application{


	TextField s1,s2,s3,s4, c1,ip,portnumber;
	Button serverChoice,clientChoice,b1,b2,b3,sports,PlayAgain,Quit;
	HashMap<String, Scene> sceneMap;
	GridPane grid;
	HBox buttonBox;
	VBox clientBox;
	Scene startScene;
	BorderPane startPane;
	Client clientConnection;

	ListView<String> listItems, listItems2;


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("Client");

		this.clientChoice = new Button("Client");
		this.clientChoice.setStyle("-fx-pref-width: 100px");
		this.clientChoice.setStyle("-fx-pref-height: 50px");
		ip =  new TextField("Enter Ip Address");

		portnumber = new TextField("Enter Port Number");
		String message = "Wrong portNumber please enter valid portNumber";
		String message1 = "Wrong Ipaddress";


		this.clientChoice.setOnAction(e->
		{
		if(!ip.getText().equals("127.0.0.1") || !portnumber.getText().equals( "5555"))
			{
				if(!portnumber.getText().equals( "5555"))
				{
					JOptionPane.showMessageDialog(new JFrame(), message1, "Error",
							JOptionPane.ERROR_MESSAGE);

				}
				else {
					JOptionPane.showMessageDialog(new JFrame(), message, "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}


			else {
			primaryStage.setScene(sceneMap.get("client"));
			primaryStage.setTitle("This is a client");
			clientConnection = new Client(data -> {
				Platform.runLater(() -> {

					listItems2.getItems().add(data.toString());

					if (clientConnection.info1.guesses_over) {
						//okay we need one more condition here that checks for rounds (max limit 3)
						if(!clientConnection.info1.won ) {
							b2.setDisable(false);
						}

						if(!clientConnection.info1.won2) {
							b3.setDisable(false);
						}

						if(!clientConnection.info1.won1){
							sports.setDisable(false);
						}
						clientConnection.info.guesses = 6;
						listItems2.getItems().add("You are out of guesses. You are out of 1 attempt in this category. ");
						if(clientConnection.info1.attempts == true){ // todo : check any of the three attempts in server.
							listItems2.getItems().add("Game Over! You Have Lost! ");
							b2.setDisable(true);
							b3.setDisable(true);
							sports.setDisable(true);
							PlayAgain.setDisable(false);
							Quit.setDisable(false);
						}
					}


					//when player won animal category clientConnection.info1.won
					if (clientConnection.info1.won && clientConnection.info1.won2==false && clientConnection.info1.won1 == false) {
						b3.setDisable(false); ////if user won from Country category then disable that button
						b2.setDisable(true);
						sports.setDisable(false);
					}
//when player won country category only
					if (clientConnection.info1.won==false && clientConnection.info1.won2 && clientConnection.info1.won1 == false) {
						b3.setDisable(true); ////if user won from Country category then disable that button
						b2.setDisable(false);
						sports.setDisable(false);
					}


					//when player won sports category only
					if (clientConnection.info1.won==false && clientConnection.info1.won2==false && clientConnection.info1.won1 == true) {
						b3.setDisable(false); ////if user won from Country category then disable that button
						b2.setDisable(false);
						sports.setDisable(true);
					}



					//when player won both country and animal categories
					if (clientConnection.info1.won2 && clientConnection.info1.won && clientConnection.info1.won1 == false) {
						b3.setDisable(true); ////if user won from Country category then disable that button
						b2.setDisable(true);
						sports.setDisable(false);

					}

					//when player won both sports and animal categories
					if (clientConnection.info1.won1 && clientConnection.info1.won && clientConnection.info1.won2 == false) {
						b3.setDisable(false); ////if user won from Country category then disable that button
						b2.setDisable(true);
						sports.setDisable(true);

					}

					//when player won both country and sports categories
					if (clientConnection.info1.won1 && clientConnection.info1.won2 && clientConnection.info1.won == false) {
						b3.setDisable(true); ////if user won from Country category then disable that button
						b2.setDisable(false);
						sports.setDisable(true);

					}

					//when player won all categories
					if (clientConnection.info1.won1 && clientConnection.info1.won2 && clientConnection.info1.won ) {
						b3.setDisable(true); ////if user won from Country category then disable that button
						b2.setDisable(true);
						sports.setDisable(true);
						clientConnection.info.guesses = 6;

						// todo : added
						//dialog box for show
//							JOptionPane.showMessageDialog(new JFrame(), "You have won the Game!!!", "Congrats",
//									JOptionPane.ERROR_MESSAGE);

						listItems2.getItems().add("You Won The Game! You can Play Again or Quit...");

						PlayAgain.setDisable(false);
						Quit.setDisable(false);

					}




					if (clientConnection.info1.attempts == true) {
						b2.setDisable(true);
						b3.setDisable(true);
						sports.setDisable(true);
						PlayAgain.setDisable(false);
						Quit.setDisable(false);
					}


				});
			});

			clientConnection.start(); //null
		}
		});

		this.buttonBox = new HBox(400, clientChoice);
		startPane = new BorderPane();
		VBox vb = new VBox();
		vb.getChildren().addAll(ip,portnumber);
		startPane.setPadding(new Insets(70));
		startPane.setCenter(buttonBox);
		startPane.setTop(vb);
		BackgroundImage myBI= new BackgroundImage(new Image("http://www.billfrymire.com/gallery/weblarge/North-America-globe-connect.jpg",300,300,false,true),
				BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
				BackgroundSize.DEFAULT);
		startPane.setBackground(new Background(myBI));
		startScene = new Scene(startPane, 300,300);

		listItems2 = new ListView<String>();

		c1 = new TextField();
		b1 = new Button("Send");
		b2 = new Button ("Animal");
		b3 = new Button ("Country");
		sports = new Button("Sports");
		PlayAgain = new Button("PlayAgain");
		Quit = new Button ("Quit");

		PlayAgain.setDisable(true);
		Quit.setDisable(true);


	//send button
		b1.setOnAction(e->
		{
			clientConnection.send(c1.getText()); c1.clear();
		});


//animal button pressed
		b2.setOnAction(e->
		{
            clientConnection.send1(true);
            b2.setDisable(true);
            b3.setDisable(true);
            sports.setDisable(true);

        });

		//country button
        b3.setOnAction(e->
        {
        	b2.setDisable(true);
			b3.setDisable(true);
			sports.setDisable(true);
        	clientConnection.send2(true);
        });
//sports button
		sports.setOnAction(e->
		{
			b2.setDisable(true);
			b3.setDisable(true);
			sports.setDisable(true);
			clientConnection.send3(true);

		});

		Quit.setOnAction(e->{
			Platform.exit();
		});

		PlayAgain.setOnAction(e->{

			listItems2.getItems().clear();
			b2.setDisable(false);
			b3.setDisable(false);
			sports.setDisable(false);
			clientConnection.info.pressed_playagain=true;
			clientConnection.send_playagain();
			PlayAgain.setDisable(true);

		});
		sceneMap = new HashMap<String, Scene>();

		sceneMap.put("client",  createClientGui());

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


	public Scene createClientGuiforanimal() {

		clientBox = new VBox(10, c1,b1,listItems2);
		clientBox.setStyle("-fx-background-color: blue");
		return new Scene(clientBox, 300, 300);

	}


	public Scene createClientGui() {

		HBox last_buttons = new HBox();
		last_buttons.getChildren().addAll(PlayAgain,Quit);
		clientBox = new VBox(10, c1,b2,b3,sports,b1,last_buttons,listItems2);
		BackgroundImage myBI= new BackgroundImage(new Image("https://store-images.s-microsoft.com/image/apps.23216.9007199266246289.99eca207-26e7-4c4a-b02f-3ea96d21b37b.f89edb6e-7a3c-44a8-9e93-aac220a9051f?mode=scale&q=90&h=600&w=1200&background=%23288C67",500,400,false,true),
				BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
				BackgroundSize.DEFAULT);
		clientBox.setBackground(new Background(myBI));

		//Editing GUI
		for(Button b : Arrays.asList(b2,b3,sports,b1)){
			b.setMinWidth(250);
			b.setPadding(new Insets(5,5,5,5));
			b.setStyle("-fx-font-family: 'Arial Black';" + "-fx-text-fill: #6666A9;" + "-fx-font-size: 12px");
		}
		PlayAgain.setStyle("-fx-font-family: 'Arial Black';" + "-fx-text-fill: #6666A9;" + "-fx-font-size: 15px");
		Quit.setStyle("-fx-font-family: 'Arial Black';" + "-fx-text-fill: #6666A9;" + "-fx-font-size: 15px");
		PlayAgain.setPrefWidth(150);
		Quit.setPrefWidth(100);


		return new Scene(clientBox, 500, 400);

	}

}
