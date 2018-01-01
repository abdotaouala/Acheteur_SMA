package sma.agents;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.wrapper.ControllerException;
import sma.ConsomateurContainer;

public class ConsomateurAgent extends GuiAgent{
	private ConsomateurContainer gui;
	@Override
	protected void setup() {
		gui=(ConsomateurContainer) getArguments()[0];
		gui.setConsomateurAgent(this);
		System.out.println("Initialisation de l'agent "+this.getAID().getName());
		addBehaviour(new CyclicBehaviour() {
			
			@Override
			public void action() {
				MessageTemplate messageTemplate=MessageTemplate.or(
						MessageTemplate.MatchPerformative(ACLMessage.REQUEST)
						, MessageTemplate.MatchPerformative(ACLMessage.REFUSE));
				ACLMessage message=receive(messageTemplate);
				if(message!=null) {
					System.out.println("Reception du message : "+message.getContent());
					GuiEvent guiEvent=new GuiEvent(this, 1);
					guiEvent.addParameter(message.getContent());
					gui.viewMessage(guiEvent);
					}
			}
		});
	}
	
	
	
	@Override
	protected void takeDown() {
		System.out.println("Destruction de l'agent");
	
	}
	
	@Override
	protected void beforeMove() {
		try {
			System.out.println("Avant migration"+this.getContainerController().getContainerName());
		} catch (ControllerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	protected void afterMove() {
		try {
			System.out.println("Après migration"+this.getContainerController().getContainerName());
		} catch (ControllerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onGuiEvent(GuiEvent guiEvent) {
		if(guiEvent.getType()==1) {
			ACLMessage aclMessage=new ACLMessage(ACLMessage.REQUEST);
			String livre=guiEvent.getParameter(0).toString();
			aclMessage.setContent(livre);
			aclMessage.addReceiver(new AID("acheteur",AID.ISLOCALNAME));
			send(aclMessage);
		}
		
	}

}
