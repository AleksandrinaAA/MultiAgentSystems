import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class Receiver1 extends Agent {
    @Override
    protected void setup() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        addBehaviour(new Behaviour() {
            ACLMessage receivedMsg = null;
            @Override
            public void action() {

                MessageTemplate mt= MessageTemplate.MatchProtocol("forReceiver1");
                receivedMsg=receive(mt);
                if (receivedMsg != null) {
                    System.out.println(receivedMsg.getSender().getLocalName() + " : " + receivedMsg.getContent());
                    ACLMessage reply = receivedMsg.createReply();
                    reply.setPerformative(ACLMessage.INFORM);
                    reply.setContent("I_got_your_message_for_Receiver1");
                    getAgent().send(reply);
                } else {
                    block();
                }
            }

            @Override
            public boolean done() { return receivedMsg!=null;
}
});
        }
        }