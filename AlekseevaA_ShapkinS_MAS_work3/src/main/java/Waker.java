
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.WakerBehaviour;
import jade.lang.acl.ACLMessage;

public class Waker extends WakerBehaviour {

    AID test;
    public Waker(Agent a, AID test, long timeout) {
        super(a, timeout);
        this.test=test;
    }

    @Override
    protected void onWake() {
        System.out.println("Аукцион закончился по времени");

    }

}