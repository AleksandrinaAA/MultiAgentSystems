import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class SellerProcess extends Behaviour {


    AID test;
    double price;
    double double_price;
    boolean over=false;
    double best_price;
    String best_Seller;
    public double past_price;


    public SellerProcess(AID test, double price, double double_price, double past_price) {
        this.test=test;
        this.price=price;
        this.double_price=double_price;
        this.past_price=past_price;
    }


    /*
    public double S1=100;
    public double S1=100;
    public double S1=100;
     */

    public double delta() {
        double delta;
        switch (this.getAgent().getLocalName()) {
            case ("Seller_1"):
                delta = 100;
                break;
            case ("Seller_2"):
                delta = 200;
                break;
            case ("Seller_3"):
                delta = 300;
                break;
            default:
                delta=1000;
        }
        return delta;
    }




    @Override
    public void action() {

       /* if ("Seller_1".equals(this.getAgent().getLocalName())) {

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
*/
        MessageTemplate mt = MessageTemplate.and(
                MessageTemplate.MatchTopic(test),MessageTemplate.or(MessageTemplate.MatchProtocol("best_info"),MessageTemplate.MatchProtocol("VICTORY")));

        ACLMessage msg = getAgent().receive(mt);
        if (msg != null) {

            String CONTENT[] = msg.getContent().split(",");
            best_price = Double.valueOf(CONTENT[0]);
            best_Seller = (CONTENT[1]);
            if (best_price > price && best_price != past_price) {

                try {
                    Thread.sleep(1000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                best_price -= delta();
                //double_price*=0.7;
                System.out.println(getAgent().getLocalName() + " отправляет цену " + best_price);
                ACLMessage msg1 = new ACLMessage(ACLMessage.INFORM);
                //best_info = best_price + "," + best_Seller;
                msg1.setContent(Double.toString(best_price));
                past_price = best_price;
                msg1.setProtocol("price");
                msg1.addReceiver(test);
                myAgent.send(msg1);


            } else if (best_price == past_price) {
                //System.out.println(getAgent().getLocalName() + " при своей цене " + double_price);
                ACLMessage msg1 = new ACLMessage(ACLMessage.INFORM);
                //best_info = best_price + "," + best_Seller;
                msg1.setContent(Double.toString(best_price));
                past_price = best_price;
                msg1.setProtocol("price");
                msg1.addReceiver(test);
                myAgent.send(msg1);

                    try {
                        Thread.sleep(1000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

            } else if (best_price < price) {
                System.out.println("Не участвует "  + myAgent.getLocalName());
                ACLMessage msg1 = new ACLMessage(ACLMessage.INFORM);
                //best_info = best_price + "," + best_Seller;
                //msg1.setContent(Double.toString(double_price));
                msg1.setProtocol("leave");
                msg1.addReceiver(test);
                myAgent.send(msg1);
                over = true;
            }
        }

        else block();
    }


    @Override
    public boolean done() {
        return over;
    }
}
