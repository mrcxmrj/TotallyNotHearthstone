package Game;

public class Creature {
    private final int maxHealth;
    private final int attack;
    private final int cost;
    private int currentHealth;
    private boolean isExhausted = Boolean.TRUE;

    public boolean isExhausted() {
        return isExhausted;
    }

    public void setExhausted(boolean exhausted) {
        isExhausted = exhausted;
    }

    public boolean isPlayerOneControlled() {
        return isPlayerOneControlled;
    }

    public void setPlayerOneControlled(boolean playerOneControlled) {
        isPlayerOneControlled = playerOneControlled;
    }

    private boolean isPlayerOneControlled;


    public Creature(int attack, int max_health, int cost, boolean isPlayerOneControlled) {
        this.maxHealth = max_health;
        this.attack = attack;
        this.cost = cost;
        currentHealth = max_health;
        this.isPlayerOneControlled = isPlayerOneControlled;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getAttack() {
        return attack;
    }

    public int getCost() {
        return cost;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }
}
