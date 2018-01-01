package sma;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.util.ExtendedProperties;
import jade.util.leap.Properties;
import jade.wrapper.AgentContainer;
import jade.wrapper.ControllerException;

public class Main_Container {

	public static void main(String[] args) {
		try{
		Runtime runtime=Runtime.instance();
		Properties properties=new ExtendedProperties();
		properties.setProperty(Profile.GUI, "true");
		ProfileImpl profileImpl=new ProfileImpl(properties);
		AgentContainer mainContainer=runtime.createMainContainer(profileImpl);
		mainContainer.start();
		} catch (ControllerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}