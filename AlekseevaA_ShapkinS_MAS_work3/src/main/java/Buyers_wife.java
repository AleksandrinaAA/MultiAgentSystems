import jade.core.Agent;
public class Buyers_wife extends Agent {
    @Override
    protected void setup() {
        addBehaviour(new Winner());
    }
    }



