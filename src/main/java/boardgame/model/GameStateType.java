package boardgame.model;

/**
 * Enum osztaly amely 4 erteket vehet fel. Jatek allapotat hatarozza meg, hogy tart-e meg vagy folyamatban van a jatek.
 */
public enum GameStateType {
    PLAYERBLUEWIN,
    PLAYERREDWIN,
    DRAW,
    PLAYING
}
