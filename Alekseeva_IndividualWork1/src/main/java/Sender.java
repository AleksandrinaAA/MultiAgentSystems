import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class Sender extends Agent {
    private AID[] Receivers = {new AID("Smith-Rec2", false),
            new AID("Smith-Rec1", false)};
    protected void setup() {
        addBehaviour(new OneShotBehaviour() {
            public void action() {
                ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                ACLMessage msg2 = new ACLMessage(ACLMessage.INFORM);
                //AID aid =new AID ("Smith-Rec1", false);
                for (int i = 0; i < Receivers.length; ++i) {
                    msg.addReceiver(Receivers[i]);
                    msg2.addReceiver(Receivers[i]);
                }
                msg.setContent("HelloReceiver1");
                msg2.setContent("HelloReceiver2");
                msg2.setProtocol("forReceiver2");
                msg.setProtocol("forReceiver1");
                getAgent().send(msg);
                getAgent().send(msg2);
            }
        });
    }
}
