package Game;

import Visualiser.GuiContainer;

public class Main {
    public static void main(String[] args) {
        Engine engine = new Engine();

        GuiContainer window = new GuiContainer(engine);
    }
}
