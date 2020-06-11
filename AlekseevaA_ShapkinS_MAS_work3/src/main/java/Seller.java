import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.FSMBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;


public class Seller extends Agent {

    @Override
    protected void setup() {


        registerAgent();
        //Создаём топик
        addBehaviour(new SUBSC());
    }





    private void registerAgent() {
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("Auction");
        sd.setName("Auction"+getLocalName());
        dfd.addServices(sd);


        try {
            DFService.register(this,dfd);
        } catch (FIPAException e) {
            e.printStackTrace();
        }


    }
}