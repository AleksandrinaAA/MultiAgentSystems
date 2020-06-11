import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.*;

public class TORG extends Behaviour {

    AID test;
    public boolean OVER;

    public int amount_of_sellers;
    public double best_price=10000000.0;
    public String best_Seller;
    public String best_info;
    ArrayList <String> Data = new ArrayList<>();
    public TORG(AID test, int amount_of_sellers) {
        this. amount_of_sellers= amount_of_sellers;
        this.test=test;

    }
    public int SIZE;


    public ArrayList<String> Sellers = new ArrayList(0);
    public ArrayList<Double> PRICE = new ArrayList(0);

    @Override
    public void action() {

            MessageTemplate mt = MessageTemplate.and(
                    MessageTemplate.MatchTopic(test), MessageTemplate.or(MessageTemplate.MatchProtocol("leave"),
                            MessageTemplate.MatchProtocol("price")));

            ACLMessage msg = getAgent().receive(mt);

            if (msg != null) {
                    System.out.println("Письмо пришло от:" + msg.getSender().getLocalName() + " " + msg.getContent());

                    if (!msg.getProtocol().equals("leave")) {

//                        try {
//                            Thread.sleep(500);
//
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }

                        Sellers.add(msg.getSender().getLocalName());
                        //System.out.println(Sellers);
                        PRICE.add(Double.valueOf(msg.getContent()));
                        if (Sellers.size() == amount_of_sellers) {
                            System.out.println('\n');
                            best_price = Collections.min(PRICE);

                            best_Seller = Sellers.get(PRICE.indexOf(best_price));
                            System.out.println("Предварительный победитель " + best_Seller + " предложенная им цена " + best_price);
                            ACLMessage msg1 = new ACLMessage(ACLMessage.INFORM);

                            best_info = best_price + "," + best_Seller+ ",";
                            //System.out.println(best_info);
                            msg1.setContent(best_info);
                            msg1.setProtocol("best_info");
                            msg1.addReceiver(test);
                            myAgent.send(msg1);
                            //Data.add(best_info);
                            ACLMessage msg2 = new ACLMessage(ACLMessage.AGREE);
                            AID aid = new AID("Buyers_wife", false);
                            msg2.setContent(String.valueOf(best_info));
                            msg2.setProtocol("Data");
                            msg2.addReceiver(aid);
                            //msg2.addReceiver(test);
                            myAgent.send(msg2);
                            Sellers.clear();
                            PRICE.clear();
                            best_info="";


                        }

                    } else {
                        System.out.println("Продавец:" + msg.getSender().getLocalName() + " покинул нас  ");
                        amount_of_sellers = amount_of_sellers - 1;
                        if (amount_of_sellers == 1) {
                            System.out.println("Победитель " + best_Seller + " предложенная им цена " + best_price);

                            //System.out.println("Аукцион закончин");
                            OVER = true;
                            //OVER = true;
                        }
                    }
            } else block();

        }
    @Override
    public boolean done() {
        return OVER;
    }
}
