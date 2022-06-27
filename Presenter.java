package com.freesoft.triota;

/**
 * Created by Pinelopi on 31/7/2018.
 */

public class Presenter {

    private MainActivity view;
    private Model model;

    public Presenter(MainActivity view) {

        this.view = view;
        this.model = new Model(this);
    }

    public boolean checkForWin() {

        Player[][] checkers = model.getCheckers();
        if (checkers[0][0] == Player.GREEN && checkers[0][1] == Player.GREEN && checkers[0][2] == Player.GREEN) {
            return false;
        } else if (checkers[2][0] == Player.RED && checkers[2][1] == Player.RED && checkers[2][2] == Player.RED) {
            return false;
        }

        //----ROW----

        for (int j = 0; j < 3; j++) {
            if (checkers[0][j] == model.getCurrentPlayer() && checkers[1][j] == model.getCurrentPlayer() && checkers[2][j] == model.getCurrentPlayer()) {
                model.setCheckers(checkers);
                return true;
            }
        }       //----COLUMN-----------

        for (int i = 0; i < 3; i++) {
            if (checkers[i][0] == model.getCurrentPlayer() && checkers[i][1] == model.getCurrentPlayer() && checkers[i][2] == model.getCurrentPlayer()) {
                model.setCheckers(checkers);
                return true;
            }
        }//-----DIAG--------
        if (checkers[0][0] == model.getCurrentPlayer() && checkers[1][1] == model.getCurrentPlayer() && checkers[2][2] == model.getCurrentPlayer()) {
            model.setCheckers(checkers);
            return true;
        } else if (checkers[0][2] == model.getCurrentPlayer() && checkers[1][1] == model.getCurrentPlayer() && checkers[2][0] == model.getCurrentPlayer()) {
            model.setCheckers(checkers);
            return true;
        }
        return false;
    }

    public void checkMovement(int fromI, int fromJ, int toI, int toJ) {

        Player currentP = model.getCurrentPlayer();
        Player fromP = model.getChecker(fromI, fromJ);
        Player toP = model.getChecker(toI, toJ);

        if (currentP != fromP) {
            return;
        } else if (toP != Player.NONE) {
            return;
        } else if (model.getSwitchState() == false && (Math.abs(toI - fromI) > 1 || Math.abs(toJ - fromJ) > 1)) {
            return;
        }
        view.updateUI(currentP, fromI, fromJ, toI, toJ);
        model.updateModel(fromI, fromJ, toI, toJ);
        if (checkForWin()) {
            gameIsOver(currentP);
        }
    }

    public void gameIsOver(Player currentPlayer) {
        view.openDialog(currentPlayer);

    }

    public void switchChanged() {
        model.setSwitchState(!model.getSwitchState());
    }

    public void afterDialogClosed() {
        model.reinitilize();
        view.reinitialize(model.getCurrentPlayer());
    }

    public boolean getSwitchStatus() {
        return model.getSwitchState();
    }

    public void FabPressed() {
        model.reinitilize();
        view.reinitialize(model.getCurrentPlayer());
    }
}
