import jade.core.AID;
import jade.core.ServiceException;
import jade.core.behaviours.OneShotBehaviour;

import jade.core.messaging.TopicManagementHelper;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;


public class TOPIC extends OneShotBehaviour {

    //public ArrayList<String> getFoundAgents;
    public int amount_of_sellers;
    private String tp;
    //public ArrayList<String> foundAgents = new ArrayList<>();
    boolean stop=false;
    @Override
    public void action() {
        tp = "Test" + System.currentTimeMillis();
        AID top = createTopic(tp);    //доступ к агенту AID-указатель на агента

        AID test = subscribeTopic(tp);
        DFAgentDescription dfd = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setType("Auction");
        dfd.addServices(sd);

        DFAgentDescription[] foundAgents = new DFAgentDescription[0];
        try {
            foundAgents = DFService.search(getAgent(), dfd);
            for (int i = 0; i < foundAgents.length; ++i) {
                if (!foundAgents[i].getName().getLocalName().equals(getAgent().getLocalName())) {
                    System.out.println("Отправил приглашение -> " + foundAgents[i].getName().getLocalName());
                    //getFoundAgents.addElement(foundAgents[i].getName());
                    ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
                    msg.setContent(tp);
                    msg.setProtocol("SUB");
                    msg.addReceiver(foundAgents[i].getName());
                    myAgent.send(msg);
                }
            }
        } catch (FIPAException e) {
            e.printStackTrace();
        }

        amount_of_sellers = foundAgents.length - 1;
        myAgent.addBehaviour(new Auction(new TORG(test, amount_of_sellers), new Waker(getAgent(), test, 5000)));
    }

    private AID subscribeTopic(String topicName) {
        TopicManagementHelper topicHelper;
        AID jadeTopic = null;
        try {
            topicHelper = (TopicManagementHelper)
                    myAgent.getHelper(TopicManagementHelper.SERVICE_NAME);
            jadeTopic = topicHelper.createTopic(topicName);
            topicHelper.register(jadeTopic);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return jadeTopic;
    }


    private AID createTopic(String topicName) {
        TopicManagementHelper topicHelper;
        AID jadeTopic = null;
        try {
            topicHelper = (TopicManagementHelper) getAgent().getHelper(TopicManagementHelper.SERVICE_NAME);
            jadeTopic = topicHelper.createTopic(topicName);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return jadeTopic;
    }
}





