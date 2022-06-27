package com.freesoft.triota;

/**
 * Created by Pinelopi on 31/7/2018.
 */

public class Model {

    private Player[][] checkers;
    private Player currentPlayer;
    private Presenter presenter;
    private Boolean switchState;


    public Model(Presenter presenter) {

        checkers = new Player[3][3];
        for (int i = 0; i < checkers.length; i++) {
            for (int j = 0; j < checkers.length; j++) {
                checkers[0][j] = Player.GREEN;
                checkers[1][j] = Player.NONE;
                checkers[2][j] = Player.RED;
            }
        }
        currentPlayer = Player.GREEN;
        this.presenter = presenter;
        switchState = false;
    }

    public Player[][] getCheckers() {
        return checkers;
    }

    public void setCheckers(Player[][] checkers) {
        this.checkers = checkers;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Player getChecker(int i, int j) {
        if (i >= 0 && i < 3 && j >= 0 && j < 3) {
            return checkers[i][j];
        } else {
            return null;
        }
    }

    public void updateModel(int fromI, int fromJ, int toI, int toJ) {

        checkers[fromI][fromJ] = Player.NONE;
        checkers[toI][toJ] = currentPlayer;
        if (!presenter.checkForWin()) {
            if (currentPlayer == Player.GREEN) {
                currentPlayer = Player.RED;
            } else {
                currentPlayer = Player.GREEN;
            }
        }

    }


    public void reinitilize() {

        switchState = false;
        if (currentPlayer == Player.GREEN) {
            currentPlayer = Player.RED;
        } else if (currentPlayer == Player.RED) {
            currentPlayer = Player.GREEN;
        }
        for (int i = 0; i < checkers.length; i++) {
            for (int j = 0; j < checkers.length; j++) {
                checkers[0][j] = Player.GREEN;
                checkers[1][j] = Player.NONE;
                checkers[2][j] = Player.RED;
            }
        }
    }

    public Boolean getSwitchState() {
        return switchState;
    }

    public void setSwitchState(Boolean switchState) {
        this.switchState = switchState;
    }
}
