package sma;

import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.gui.GuiEvent;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
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
import sma.agents.VendeurAgent;

public class VendeurContainer extends Application{
	private VendeurAgent vendeurAgent;
	private ObservableList<String> observableList;
	private AgentContainer ac;
	private VendeurContainer vendeurContainer;
	public static void main(String[] args) {
		launch(VendeurContainer.class);
	}
	
	public void startContainer() {
		try {
			Runtime rt=Runtime.instance();
			ProfileImpl profile=new ProfileImpl(false); // c'est pas un main container
			profile.setParameter(ProfileImpl.MAIN_HOST, "localhost"); //@ de main container
			ac=rt.createAgentContainer(profile);
			/*deployer un agent*/
			/*AgentController agentController=ac.createNewAgent
					("vendeur1", "sma.agents.VendeurAgent", new Object[]{this});
			agentController.start();*/
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		startContainer();
		
		vendeurContainer=this;
		primaryStage.setTitle("Vendeur");
		 BorderPane borderPane=new BorderPane();
		 
		 HBox hBox=new HBox();
		 hBox.setPadding(new Insets(10));
		 hBox.setSpacing(10);
		 Label labelVendeur=new Label("Nom du vendeur: ");
		 TextField textFeildVendeur=new TextField();
		 Button buttonVendeur=new Button("Déployer l'agent");
		 hBox.getChildren().add(labelVendeur);
		 hBox.getChildren().add(textFeildVendeur);
		 hBox.getChildren().add(buttonVendeur);
		 borderPane.setTop(hBox);
		 
		 
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
		 
		 
		 buttonVendeur.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				try {
					String nomVendeur=textFeildVendeur.getText();
				AgentController agentController = ac.createNewAgent
							(nomVendeur, "sma.agents.VendeurAgent", new Object[]{vendeurContainer});
				agentController.start();
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
	}

	
	public VendeurAgent getVendeurAgent() {
		return vendeurAgent;
	}

	public void setVendeurAgent(VendeurAgent vendeurAgent) {
		this.vendeurAgent = vendeurAgent;
	}

	public void viewMessage(GuiEvent guiEvent) {
		String message=guiEvent.getParameter(0).toString();
		observableList.add(message);
	}
	
	
}