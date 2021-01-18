package Game;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Engine {
    public Player getPlayerOne() {
        return playerOne;
    }

    public Player getPlayerTwo() {
        return playerTwo;
    }

    private final Player playerOne;
    private final Player playerTwo;
    private final Random random;

    private List<Creature> battlefield1 = new LinkedList();
    private List<Creature> battlefield2 = new LinkedList();

    private Boolean activePlayerOne; //True == playerOne; False == playerTwo

    public Engine() {
        this.playerOne = new Player(30);
        this.playerTwo = new Player(30);
        random = new Random();
        activePlayerOne = Boolean.TRUE;
    }

    public void init() {
        playerOne.generateRandomDeck(Boolean.TRUE);
        playerTwo.generateRandomDeck(Boolean.FALSE);

        playerOne.drawMultipleCards(4);
        playerTwo.drawMultipleCards(4);
    }

    public Boolean getActivePlayerOne() {
        return activePlayerOne;
    }

    public void nextTurn() {
        activePlayerOne = !activePlayerOne;

        if (activePlayerOne) {
            if (playerOne.getHand().size() < 10 && playerOne.getDeck().size() > 0) {
                playerOne.drawCard();
            }
            if (playerOne.getManaPool() < 10) {
                playerOne.setManaPool(playerOne.getManaPool() + 1);
            }
            playerOne.setMana(playerOne.getManaPool());

        } else {
            if (playerTwo.getHand().size() < 10 && playerTwo.getDeck().size() > 0) {
                playerTwo.drawCard();
            }
            if (playerTwo.getManaPool() < 10) {
                playerTwo.setManaPool(playerTwo.getManaPool() + 1);
            }
            playerTwo.setMana(playerTwo.getManaPool());
        }

        for (Creature creature : battlefield1) {
            creature.setExhausted(Boolean.FALSE);
        }
        for (Creature creature : battlefield2) {
            creature.setExhausted(Boolean.FALSE);
        }
    }

    public List<Creature> getBattlefield1() {
        return battlefield1;
    }

    public void setBattlefield1(List<Creature> battlefield1) {
        this.battlefield1 = battlefield1;
    }

    public List<Creature> getBattlefield2() {
        return battlefield2;
    }

    public void setBattlefield2(List<Creature> battlefield2) {
        this.battlefield2 = battlefield2;
    }

    public void castCreature(Creature creature) {
        if (activePlayerOne) {
            if (battlefield1.size() < 7 && playerOne.getMana() >= creature.getCost()) {
                List newHand = playerOne.getHand();
                newHand.remove(creature);
                playerOne.setHand(newHand);
                playerOne.setMana(playerOne.getMana() - creature.getCost());
                battlefield1.add(creature);
            }
        } else {
            if (battlefield2.size() < 7 && playerTwo.getMana() >= creature.getCost()) {
                List newHand = playerTwo.getHand();
                newHand.remove(creature);
                playerTwo.setHand(newHand);
                playerTwo.setMana(playerTwo.getMana() - creature.getCost());
                battlefield2.add(creature);
            }
        }
    }

    //creature1 must be from battlefield1, creature2 similarly
    public void attack(Creature creature1, Creature creature2) {
        creature1.setCurrentHealth(creature1.getCurrentHealth() - creature2.getAttack());
        creature2.setCurrentHealth(creature2.getCurrentHealth() - creature1.getAttack());
        if (creature1.getCurrentHealth() <= 0) {
            System.out.println("smierc");
            battlefield1.remove(creature1);
        }
        if (creature2.getCurrentHealth() <= 0) {
            System.out.println("smierc");
            battlefield1.remove(creature2);
        }
    }
}
