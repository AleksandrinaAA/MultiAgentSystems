import jade.core.AID;
import jade.core.ServiceException;
import jade.core.behaviours.Behaviour;
import jade.core.messaging.TopicManagementHelper;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class SUBSC extends Behaviour{
    AID test;
    //public boolean over=false;
    public double past_price;
    public double double_price;
    public double price;
    public double Price() {
        double price;
        switch (this.getAgent().getLocalName()) {
            case ("Seller_1"):
                price = 1000;
                break;
            case ("Seller_2"):
                price = 1250;
                break;
            case ("Seller_3"):
                price = 1630;
                break;
            default:
                price=200;
        }
        return price;
    }

            @Override
            public void action() {
                MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.REQUEST), MessageTemplate.MatchProtocol("SUB"));
                ACLMessage msg = getAgent().receive(mt);


                if (msg != null ) {
                    System.out.println("Принял сообщение" + myAgent.getLocalName());
                    test = subscribeTopic(msg.getContent());
                    //System.out.println(test.getLocalName());
                    price=Price();
                    double_price = Price()*2;
                    ACLMessage msg1 = new ACLMessage(ACLMessage.INFORM);
                    msg1.setContent(Double.toString(double_price));
                    //System.out.println( msg1.getContent());
                    msg1.setProtocol("price");
                    msg1.addReceiver(test);
                    past_price =  double_price;
                    //System.out.println(test);
                    if ("Seller_1".equals(this.getAgent().getLocalName())) {

                        try {
                            Thread.sleep(1000);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }


                    if ("Seller_2".equals(this.getAgent().getLocalName())) {

                        try {
                            Thread.sleep(1020);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    if ("Seller_3".equals(this.getAgent().getLocalName())) {
                        try {
                            Thread.sleep(1040);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    myAgent.send(msg1);
                    myAgent.addBehaviour(new SellerProcess(test,price,double_price,past_price));
                }
                else block();
            }

            @Override
            public boolean done() {
                //return over;
                return false;
            }



    private AID subscribeTopic(String  topicName){
        TopicManagementHelper topicHelper;
        AID jadeTopic = null;
        try {
            topicHelper=(TopicManagementHelper)getAgent().getHelper(TopicManagementHelper.SERVICE_NAME);
            jadeTopic=topicHelper.createTopic( topicName);
            topicHelper.register(jadeTopic);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return jadeTopic;
    }



}
