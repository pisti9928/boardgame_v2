package boardgame;

import boardgame.jdbi.LeaderboardController;
import boardgame.jdbi.PlayerSet;
import boardgame.model.BoardGameModel;
import boardgame.model.GameStateType;
import boardgame.model.PawnDirection;
import boardgame.model.Position;
import boardgame.player.Player;
import boardgame.player.PlayerState;
import boardgame.scene.PlayerNameController;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.tinylog.Logger;

import java.util.ArrayList;
import java.util.List;

public class BoardGameController {

    private enum SelectionPhase {
        SELECT_FROM,
        SELECT_TO;

        public SelectionPhase alter() {
            return switch (this) {
                case SELECT_FROM -> SELECT_TO;
                case SELECT_TO -> SELECT_FROM;
            };
        }
    }

    private SelectionPhase selectionPhase = SelectionPhase.SELECT_FROM;

    private List<Position> selectablePositions = new ArrayList<>();

    private Position selected;

    private BoardGameModel model = new BoardGameModel();

    @FXML
    private GridPane board;

    @FXML
    private Label firstPlayerName;

    @FXML
    private Label secondPlayerName;

    @FXML
    private void initialize() {

        firstPlayerName.setText(PlayerState.getRedPlayerName());
        secondPlayerName.setText(PlayerState.getBluePlayerName());
        PlayerState.setStartingPlayer();
        createBoard();
        createPieces();
        setSelectablePositions();
        showSelectablePositions(PlayerState.getNextPlayer());
    }

    private void createBoard() {
        for (int i = 0; i < board.getRowCount(); i++) {
            for (int j = 0; j < board.getColumnCount(); j++) {
                var square = createSquare();
                board.add(square, j, i);
            }
        }
    }

    private Circle createPiece(Color color) {
        var piece = new Circle(50);
        piece.setFill(color);
        return piece;
    }

    private void createPieces() {
        for (int i = 0; i < model.getPieceCount(); i++) {
            model.positionProperty(i).addListener(this::piecePositionChange);
            var piece = createPiece(Color.valueOf(model.getPieceType(i).name()));
            getSquare(model.getPiecePosition(i)).getChildren().add(piece);
        }
    }

    private StackPane createSquare() {
        var square = new StackPane();
        square.getStyleClass().add("square");
        square.setOnMouseClicked(this::handleMouseClick);
        return square;
    }

    @FXML
    private void handleMouseClick(MouseEvent event) {
        var square = (StackPane) event.getSource();
        var row = GridPane.getRowIndex(square);
        var col = GridPane.getColumnIndex(square);
        var position = new Position(row, col);
        if (selectablePositions.contains(position)){
            var pieceNumber = model.getPieceNumber(position).getAsInt();
            model.remove(pieceNumber);
            var circle = (Circle) square.getChildren().get(0);
            circle.setFill(Color.TRANSPARENT);
            Logger.debug("Click on square {}", position);
        }

        handleClickOnSquare(position);

    }

    private void handleClickOnSquare(Position position) {
        if (selectablePositions.contains(position)) {
            selected = position;

            alterSelectionPhase();

            //Logger.debug("Remove piece {}", pieceNumber);
        }
    }


    private StackPane getSquare(Position position) {
        for (var child : board.getChildren()) {
            if (GridPane.getRowIndex(child) == position.row() && GridPane.getColumnIndex(child) == position.col()) {
                return (StackPane) child;
            }
        }
        throw new AssertionError();
    }


    private void alterSelectionPhase() {
        selectionPhase = selectionPhase.alter();
        hideSelectablePositions();
        setSelectablePositions();
        PlayerState.setNextPlayer();
        showSelectablePositions(PlayerState.getNextPlayer());
    }




    private void setSelectablePositions() {
        selectablePositions.clear();
        if (model.elsoLepes) {
            selectablePositions.addAll(model.getPiecePositions());
        }
        else {
            model.setstate();
            if(model.state.equals(GameStateType.PLAYING)){
                selectablePositions.addAll(model.getPiecePositions());
            }
            else if (model.state.equals(GameStateType.PLAYERBLUEWIN)){
                Logger.debug("BlueWin");

            }
            else if (model.state.equals(GameStateType.PLAYERREDWIN)){
                Logger.debug("RedWin");
            }
            else {
                Logger.debug("Draw");
            }


        }

        /*
        switch (selectionPhase) {
            case SELECT_FROM -> selectablePositions.addAll(model.getPiecePositions());
            case SELECT_TO -> {
                var pieceNumber = model.getPieceNumber(selected).getAsInt();
                for (var direction : model.getValidMoves(pieceNumber)) {
                    selectablePositions.add(selected.moveTo(direction));
                }
            }
        }*/
    }


    private void showSelectablePositions(Player nextPlayer) {

        for (var selectablePosition : selectablePositions) {
            var square = getSquare(selectablePosition);
            if (nextPlayer.equals(Player.PLAYERBLUE)){
                square.getStyleClass().add("selectable1");
            }
            else{
                square.getStyleClass().add("selectable2");
            }

        }
    }



    private void hideSelectablePositions() {
        for (var selectablePosition : selectablePositions) {
            var square = getSquare(selectablePosition);
            square.getStyleClass().remove("selectable1");
            square.getStyleClass().remove("selectable2");
        }
    }

    private void piecePositionChange(ObservableValue<? extends Position> observable, Position oldPosition, Position newPosition) {
        Logger.debug("Move: {} -> {}", oldPosition, newPosition);
        StackPane oldSquare = getSquare(oldPosition);
        StackPane newSquare = getSquare(newPosition);
        newSquare.getChildren().addAll(oldSquare.getChildren());
        oldSquare.getChildren().clear();
    }
}
