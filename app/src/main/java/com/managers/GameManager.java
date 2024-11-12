package com.managers;

import com.models.Player;
import com.game.GameState;
import com.utils.CollisionDetector;
import com.managers.CharacterManager;
public class GameManager {
    private static GameManager instance;
    private GameState gameState;
    private Player player1;
    private Player player2;

    private GameManager() {
        gameState = GameState.MENU;
    }

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    public void startGame(int player1CharacterId, int player2CharacterId) {
        CharacterManager characterManager = CharacterManager.getInstance();

        player1 = new Player(1, characterManager.getCharacter(player1CharacterId), 200, 500);
        player2 = new Player(2, characterManager.getCharacter(player2CharacterId), 800, 500);

        gameState = GameState.PLAYING;
    }

    public void update(double deltaTime) {
        if (gameState == GameState.PLAYING) {
            player1.update(deltaTime);
            player2.update(deltaTime);

            checkCollisions();
            checkGameOver();
        }
    }

    private void checkCollisions() {
        CollisionDetector.checkAttackCollisions(player1, player2);
    }

    private void checkGameOver() {
        if (!player1.getCharacter().isAlive() || !player2.getCharacter().isAlive()) {
            gameState = GameState.GAME_OVER;
        }
    }

    // Getters
    public GameState getGameState() { return gameState; }
    public Player getPlayer1() { return player1; }
    public Player getPlayer2() { return player2; }
}
