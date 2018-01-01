package sma;

import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.gui.GuiEvent;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sma.agents.ConsomateurAgent;

public class ConsomateurContainer extends Application{
	private ConsomateurAgent consomateurAgent;
	private ObservableList<String> observableList;
	public static void main(String[] args) {
		launch(ConsomateurContainer.class);
	}
	
	public void startContainer() {
		try {
			Runtime rt=Runtime.instance();
			ProfileImpl profile=new ProfileImpl(false); // c'est pas un main container
			profile.setParameter(ProfileImpl.MAIN_HOST, "localhost"); //@ de main container
			AgentContainer ac=rt.createAgentContainer(profile);
			/*deployer un agent*/
			AgentController agentController=ac.createNewAgent
					("consomateur", "sma.agents.ConsomateurAgent", new Object[]{this});
			agentController.start();
			
			} catch (ControllerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		startContainer();
		primaryStage.setTitle("Consomateur");
		 BorderPane borderPane=new BorderPane();
		 //__
		 HBox hBox=new HBox();
		 hBox.setPadding(new Insets(10));
		 hBox.setSpacing(10);
		 Label labelLivre=new Label("Livre: ");
		 TextField textFeildLivre=new TextField();
		 Button buttonAcheter=new Button("Acheter");
		 hBox.getChildren().add(labelLivre);
		 hBox.getChildren().add(textFeildLivre);
		 hBox.getChildren().add(buttonAcheter);
		 borderPane.setTop(hBox);
		 
		 //||
		 VBox vBox=new VBox();
		 GridPane gridPane=new GridPane();
		 observableList=FXCollections
				 .observableArrayList();
		 ListView<String> listViewMessages=new ListView<String>(observableList);
		 gridPane.add(listViewMessages, 0, 0);
		 vBox.setPadding(new Insets(10));
		 vBox.setSpacing(10);
		 vBox.getChildren().add(gridPane);
		 borderPane.setCenter(vBox);
		 
		 
		 Scene scene=new Scene(borderPane, 400, 500);
		 primaryStage.setScene(scene);
		 primaryStage.show();
		 
		 
		 buttonAcheter.setOnAction
		 (new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				String livre=
						textFeildLivre.getText();
				GuiEvent guiEvent=
						new GuiEvent(this, 1);
				guiEvent.addParameter(livre);
				consomateurAgent.onGuiEvent(guiEvent);
				
			}
		});
	}

	public ConsomateurAgent getConsomateurAgent() {
		return consomateurAgent;
	}

	public void setConsomateurAgent(ConsomateurAgent consomateurAgent) {
		this.consomateurAgent = consomateurAgent;
	}
	public void viewMessage(GuiEvent guiEvent) {
		String message=guiEvent.getParameter(0).toString();
		observableList.add(message);
	}
	
	
}