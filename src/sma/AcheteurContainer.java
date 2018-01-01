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

import javafx.geometry.Insets;
import javafx.scene.Scene;

import javafx.scene.control.ListView;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sma.agents.AcheteurAgent;


public class AcheteurContainer extends Application{
	private AcheteurAgent acheteurAgent;
	private ObservableList<String> observableList;
	public static void main(String[] args) {
		launch(AcheteurContainer.class);
	}
	
	public void startContainer() {
		try {
			Runtime rt=Runtime.instance();
			ProfileImpl profile=new ProfileImpl(false); // c'est pas un main container
			profile.setParameter(ProfileImpl.MAIN_HOST, "localhost"); //@ de main container
			AgentContainer ac=rt.createAgentContainer(profile);
			/*deployer un agent*/
			AgentController agentController=ac.createNewAgent
					("acheteur", "sma.agents.AcheteurAgent", new Object[]{this});
			agentController.start();
			
			} catch (ControllerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		startContainer();
		primaryStage.setTitle("Acheteur");
		 BorderPane borderPane=new BorderPane();
		 
		 VBox vBox=new VBox();
		 GridPane gridPane=new GridPane();
		 observableList=FXCollections.observableArrayList();
		 ListView<String> listViewMessages=new ListView<String>(observableList);
		 gridPane.add(listViewMessages, 0, 0);
		 vBox.setPadding(new Insets(10));
		 vBox.setSpacing(10);
		 vBox.getChildren().add(gridPane);
		 borderPane.setCenter(vBox);
		 
		 
		 Scene scene=new Scene(borderPane, 400, 500);
		 primaryStage.setScene(scene);
		 primaryStage.show();
		 
		 
	}

	public AcheteurAgent getAcheteurAgent() {
		return acheteurAgent;
	}

	public void setAcheteurAgent(AcheteurAgent acheteurAgent) {
		this.acheteurAgent = acheteurAgent;
	}

	public void viewMessage(GuiEvent guiEvent) {
		String message=guiEvent.getParameter(0).toString();
		observableList.add(message);
	}
	
	
}