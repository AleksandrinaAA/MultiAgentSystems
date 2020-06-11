
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.lang.acl.ACLMessage;
public class Auction extends ParallelBehaviour {
    //public String foundAgents;
    //ArrayList <String> Data = new ArrayList<>();
    private Behaviour a;
    private Behaviour b;
    AID test;

    public Auction(Behaviour a, Behaviour b) {
        super(WHEN_ANY);
        this.a = a;
        this.b = b;
    }


    @Override
    public void onStart() {
        addSubBehaviour(a);
        addSubBehaviour(b);
    }

    @Override
    public int onEnd() {
        if (done()) {
            ACLMessage msg2 = new ACLMessage(ACLMessage.AGREE);
            AID aid = new AID("Buyers_wife", false);
            msg2.setProtocol("stop");
            msg2.addReceiver(aid);
            //msg2.addReceiver(test);
            myAgent.send(msg2);
        }
        return 1;
    }


}