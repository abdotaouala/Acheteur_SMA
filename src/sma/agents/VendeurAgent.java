package sma.agents;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.wrapper.ControllerException;
import sma.VendeurContainer;

public class VendeurAgent extends GuiAgent{
	private VendeurContainer gui;
	@Override
	protected void setup() {
		gui=(VendeurContainer) getArguments()[0];
		gui.setVendeurAgent(this);
		System.out.println("Initialisation de l'agent "+this.getAID().getName());
		ParallelBehaviour parallelBehaviour=new ParallelBehaviour();
		addBehaviour(parallelBehaviour);
		parallelBehaviour.addSubBehaviour(new OneShotBehaviour() {
			
			@Override
			public void action() {
				/*
				 * creation d'un service
				 */
				DFAgentDescription dfa=new DFAgentDescription();
				dfa.setName(getAID());
				ServiceDescription sd=new ServiceDescription();
				sd.setType("Vente");
				sd.setName("Vente-livres");
				dfa.addServices(sd);
				try {
					DFService.register(myAgent, dfa);//dans un comportement on ne peut pas utiliser this et à la place de this on utilise myAgent
				} catch (FIPAException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		
		parallelBehaviour.addSubBehaviour(new CyclicBehaviour() {
			
			@Override
			public void action() {
				ACLMessage message=receive();
				if(message!=null) {
					switch (message.getPerformative()) {
					case ACLMessage.CFP:
						GuiEvent guiEvent=new GuiEvent(this,1);
						guiEvent.addParameter(message.getContent());
						gui.viewMessage(guiEvent);
						
						break;
						
					case ACLMessage.ACCEPT_PROPOSAL:
						
						break;

					default:
						break;
					}
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
