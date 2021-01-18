package Visualiser;

import Game.Creature;
import Game.Engine;
import com.sun.org.apache.xalan.internal.xsltc.compiler.FlowList;

import javax.sound.sampled.Line;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GuiContainer {
    private static final int APP_HEIGHT = 1000;
    private static final int APP_WIDTH = 1200;
    private final Engine engine;

    private JFrame frame;

    private JPanel hand1;
    private JPanel hand2;
    private JPanel battlefield1;
    private JPanel battlefield2;
    private JPanel playerInfo1;
    private JPanel playerInfo2;

    private JLabel info1;
    private JLabel info2;

    private JButton endTurnBtn1;
    private JButton endTurnBtn2;

    private JButton face1;
    private JButton face2;

    private Creature selectedAttacker;
    private boolean isSelected = Boolean.FALSE;

    public GuiContainer(Engine engine) {
        this.engine = engine;
        initLayout();
    }

    private void initLayout() {
        engine.init();

        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("TotallyNotHearthstone");
        frame.pack();
        frame.setVisible(true);
        frame.setSize(APP_WIDTH, APP_HEIGHT);
        frame.setLayout(new GridLayout(6, 1));

        hand1 = new JPanel();
        //hand1.setLayout(new BoxLayout(hand1, BoxLayout.X_AXIS));
        hand1.setLayout(new FlowLayout());
//        hand1.add(createCard(new Creature(2, 2137)));
        frame.add(hand1);

        playerInfo1 = new JPanel();
        endTurnBtn1 = new JButton();
        endTurnBtn1.setText("End Turn");
        endTurnBtn1.setBackground(Color.RED);
        playerInfo1.add(endTurnBtn1);

        playerInfo1.setBackground(Color.GRAY);
        playerInfo1.setLayout(new GridLayout(3, 1));
        info1 = new JLabel("Player 1    |   MANA: " + engine.getPlayerOne().getMana() + "/" + engine.getPlayerOne().getManaPool(), SwingConstants.CENTER);
        playerInfo1.add(info1);
        face1 = new JButton();
        face1.setText("HEALTH:" + engine.getPlayerOne().getHealth() + "/" + engine.getPlayerOne().getMAX_HEALTH());
        playerInfo1.add(face1);
        frame.add(playerInfo1);


        battlefield1 = new JPanel();
        battlefield1.setBackground(Color.lightGray);
        battlefield1.setLayout(new FlowLayout());
        frame.add(battlefield1);

        battlefield2 = new JPanel();
        battlefield1.setLayout(new FlowLayout());
        frame.add(battlefield2);
        battlefield2.setBackground(Color.lightGray);

        playerInfo2 = new JPanel();
        playerInfo2.setBackground(Color.GRAY);
        playerInfo2.setLayout(new GridLayout(3, 1));
        info2 = new JLabel("Player 2    |    MANA: " + engine.getPlayerTwo().getMana() + "/" + engine.getPlayerTwo().getManaPool(), SwingConstants.CENTER);
        face2 = new JButton();
        face2.setText("HEALTH:" + engine.getPlayerTwo().getHealth() + "/" + engine.getPlayerTwo().getMAX_HEALTH());
        playerInfo2.add(face2);
        playerInfo2.add(info2);
        frame.add(playerInfo2);

        endTurnBtn2 = new JButton();
        endTurnBtn2.setText("End Turn");
        endTurnBtn2.setBackground(Color.RED);
        playerInfo2.add(endTurnBtn2);

        hand2 = new JPanel();
        hand2.setLayout(new FlowLayout());
        frame.add(hand2);


        face1.addActionListener(e -> {
            if (isSelected && !selectedAttacker.isPlayerOneControlled() && selectedAttacker.isPlayerOneControlled() == engine.getActivePlayerOne() && !selectedAttacker.isExhausted()) {
                engine.getPlayerOne().setHealth(engine.getPlayerOne().getHealth() - selectedAttacker.getAttack());
                selectedAttacker.setExhausted(Boolean.TRUE);
                face1.setText("HEALTH:" + engine.getPlayerOne().getHealth() + "/" + engine.getPlayerOne().getMAX_HEALTH());
                updateBattlefields();
                checkWinCondition(Boolean.TRUE);
            }
            isSelected = Boolean.FALSE;
        });

        face2.addActionListener(e -> {
            if (isSelected && selectedAttacker.isPlayerOneControlled() && selectedAttacker.isPlayerOneControlled() == engine.getActivePlayerOne() && !selectedAttacker.isExhausted()) {
                engine.getPlayerTwo().setHealth(engine.getPlayerTwo().getHealth() - selectedAttacker.getAttack());
                selectedAttacker.setExhausted(Boolean.TRUE);
                face2.setText("HEALTH:" + engine.getPlayerTwo().getHealth() + "/" + engine.getPlayerTwo().getMAX_HEALTH());
                updateBattlefields();
                checkWinCondition(Boolean.FALSE);
            }
            isSelected = Boolean.FALSE;
        });

        endTurnBtn1.addActionListener(e -> {
            if (engine.getActivePlayerOne()) {
                engine.nextTurn();
                updateBattlefields();
                updateHands();
                updateLabels();
                isSelected = Boolean.FALSE;
            }
        });

        endTurnBtn2.addActionListener(e -> {
            if (!engine.getActivePlayerOne()) {
                engine.nextTurn();
                updateBattlefields();
                updateHands();
                updateLabels();
                isSelected = Boolean.FALSE;
            }
        });

        updateLabels();
        updateHands();
    }

    private JButton createCard(Creature creature, Boolean inHand, Boolean fromHandOne) {
        JButton cardBtn = new JButton();
        cardBtn.setBorder(BorderFactory.createLineBorder(Color.black));
        cardBtn.setPreferredSize(new Dimension(100, 150));
        cardBtn.setBorder(new LineBorder(Color.BLACK));
        cardBtn.setText(creature.getAttack() + "A  |  " + creature.getCurrentHealth() + "HP  |  " + creature.getCost() + "$");

        if (inHand) {
            cardBtn.setText(creature.getAttack() + "A  |  " + creature.getCurrentHealth() + "HP  |  " + creature.getCost() + "$");
            if (engine.getActivePlayerOne() && creature.getCost() <= engine.getPlayerOne().getMana()) {
                cardBtn.setBorder(new LineBorder(Color.GREEN));
            }
            if (!engine.getActivePlayerOne() && creature.getCost() <= engine.getPlayerTwo().getMana()) {
                cardBtn.setBorder(new LineBorder(Color.GREEN));
            }
            cardBtn.addActionListener(e -> {
                if (fromHandOne == engine.getActivePlayerOne()) {
                    engine.castCreature(creature);
                    updateHands();
                    updateBattlefields();
                    updateLabels();
                }
            });
        } else {
            cardBtn.setText(creature.getAttack() + "A  |  " + creature.getCurrentHealth() + "/" + creature.getMaxHealth() + "HP");
            if (creature.isExhausted()) {
                cardBtn.setBorder(new LineBorder(Color.gray));
            }
            cardBtn.addActionListener(e -> {

                if (!isSelected) {
                    selectedAttacker = creature;
                    isSelected = Boolean.TRUE;
                    if (selectedAttacker.isPlayerOneControlled() == engine.getActivePlayerOne() && !selectedAttacker.isExhausted()) {
                        cardBtn.setBorder(new LineBorder(Color.RED));
                    } else {
                        isSelected = Boolean.FALSE;
                    }
                } else {
                    if (selectedAttacker.isPlayerOneControlled() != creature.isPlayerOneControlled() && selectedAttacker.isPlayerOneControlled() == engine.getActivePlayerOne() && !selectedAttacker.isExhausted()) {
                        System.out.println("walka");
                        if (selectedAttacker.isPlayerOneControlled()) {
                            engine.attack(selectedAttacker, creature);
                        } else {
                            engine.attack(creature, selectedAttacker);
                        }
                        isSelected = Boolean.FALSE;
                        selectedAttacker.setExhausted(Boolean.TRUE);
                    } else {
                        isSelected = Boolean.FALSE;
                    }
                    updateBattlefields();
                    updateLabels();
                }

            });
        }

        return cardBtn;
    }

    private void updateHands() {
        hand1.removeAll();
        hand2.removeAll();
        for (Creature creature : engine.getPlayerOne().getHand()) {
            hand1.add(createCard(creature, Boolean.TRUE, Boolean.TRUE));
        }
        for (Creature creature : engine.getPlayerTwo().getHand()) {
            hand2.add(createCard(creature, Boolean.TRUE, Boolean.FALSE));
        }
        hand1.revalidate();
        hand1.repaint();
        hand2.revalidate();
        hand2.repaint();
    }

    private void updateBattlefields() {
        battlefield1.removeAll();
        battlefield2.removeAll();
        for (Creature creature : engine.getBattlefield1()) {
            if (creature.getCurrentHealth() <= 0) {
                continue;
            }
            battlefield1.add(createCard(creature, Boolean.FALSE, Boolean.FALSE));
        }
        for (Creature creature : engine.getBattlefield2()) {
            if (creature.getCurrentHealth() <= 0) {
                continue;
            }
            battlefield2.add(createCard(creature, Boolean.FALSE, Boolean.FALSE));
        }
        battlefield1.revalidate();
        battlefield1.repaint();
        battlefield2.revalidate();
        battlefield2.repaint();
    }

    private void updateLabels() {
        if (engine.getActivePlayerOne()) {
            endTurnBtn1.setBackground(Color.RED);
            endTurnBtn2.setBackground(Color.gray);
        } else {
            endTurnBtn1.setBackground(Color.gray);
            endTurnBtn2.setBackground(Color.RED);

        }
        info1.setText("Player 1    |   MANA: " + engine.getPlayerOne().getMana() + "/" + engine.getPlayerOne().getManaPool());
        info2.setText("Player 2    |   MANA: " + engine.getPlayerTwo().getMana() + "/" + engine.getPlayerTwo().getManaPool());

        info1.setBorder(new LineBorder(Color.BLACK));
        playerInfo1.setBackground(Color.GRAY);
    }

    private void checkWinCondition(Boolean playerOneAttacked) {
        if (playerOneAttacked) {
            if (engine.getPlayerOne().getHealth() <= 0) {
                JOptionPane.showMessageDialog(frame, "Player 2 wins!");
            }
        } else {
            if (engine.getPlayerTwo().getHealth() <= 0) {
                JOptionPane.showMessageDialog(frame, "Player 1 wins!");
            }
        }
    }
}
