package Game;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Player {
    private final int MAX_HEALTH = 30;
    private int health;
    private List<Creature> deck = new LinkedList();
    private List<Creature> hand = new LinkedList();
    private final Random random;
    private final int DECK_SIZE = 30;
    private int mana = 1;
    private int manaPool = 1;

    public int getMAX_HEALTH() {
        return MAX_HEALTH;
    }

    public int getManaPool() {
        return manaPool;
    }

    public void setManaPool(int manaPool) {
        this.manaPool = manaPool;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setDeck(List<Creature> deck) {
        this.deck = deck;
    }

    public void setHand(List<Creature> hand) {
        this.hand = hand;
    }

    public int getHealth() {
        return health;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public List<Creature> getDeck() {
        return deck;
    }

    public List<Creature> getHand() {
        return hand;
    }

    public Player(int health, List<Creature> deck, List<Creature> hand) {
        this.health = health;
        this.deck = deck;
        this.hand = hand;
        this.random = new Random();
    }

    public Player(int health) {
        this.health = health;
        this.random = new Random();
    }

    public void drawCard() {
        int randomIndex = random.nextInt(deck.size());
        hand.add(deck.get(randomIndex));
        deck.remove(randomIndex);
    }

    public void drawMultipleCards(int numberOfCards) {
        for (int i = 0; i < numberOfCards; i++) {
            drawCard();
        }
    }

    public void generateRandomDeck(boolean playerOne) {
        for (int i = 0; i < DECK_SIZE; i++) {
            deck.add(new Creature(random.nextInt(8), random.nextInt(7) + 1, random.nextInt(8), playerOne));
        }
    }
}
