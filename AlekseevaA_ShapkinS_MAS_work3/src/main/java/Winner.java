import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

import java.util.ArrayList;

public class Winner extends Behaviour {

    ArrayList<String>Data=new ArrayList<>();
    String str;
    @Override
    public void action() {


        ACLMessage msg = getAgent().receive();
        if (msg != null) {
            if (msg.getProtocol().equals("stop")) {
                String str []= Data.get(Data.size() - 1).split(",");
                System.out.println("ПОБЕДИТЕЛЬ!!!  " + str[1]+"с предложением  " +str[0]);
                System.out.println("Аукцион закончен");
            }
            if (msg.getProtocol().equals("Data")) {
                Data.add(msg.getContent());
            }
        } else {
            block();
        }
    }


    @Override
    public boolean done() {
        return false;
    }
}
