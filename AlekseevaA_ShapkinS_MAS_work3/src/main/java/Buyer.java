import jade.core.Agent;
import jade.core.behaviours.WakerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;



public class Buyer extends Agent {

    @Override
    protected void setup() {
        //Создаём сервис
        registerAgent();
        //Создаём топик
        addBehaviour(new WakerBehaviour(this,15000) {
            @Override
            protected void onWake() {
                getAgent().addBehaviour(new TOPIC());
            }
        });
    }

        private void registerAgent () {
            DFAgentDescription dfd = new DFAgentDescription();
            dfd.setName(getAID());
            ServiceDescription sd = new ServiceDescription();
            sd.setType("Auction");
            sd.setName("Auction" + getLocalName());
            dfd.addServices(sd);


            try {
                DFService.register(this, dfd);
            } catch (FIPAException e) {
                e.printStackTrace();
            }

        }
    }


