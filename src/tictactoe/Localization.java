package tictactoe;

public enum Localization {
    TIE("Game has ended. It's a tie!"),
    NEXT_TURN("It\'s %s\'s turn! Please indicate your next move (e.g.: A1):"),
	INVALID_INPUT("Invalid input! Please indicate your next move (e.g.: A1):"),
	SPOT_OCCUPIED("That spot is occuppied. Please pick another spot."),
	GAME_ENDED("Game has ended! Winner: %s"),
	PLAY_AGAIN("Do you want to play again? (y/n)");

    private String line;

    Localization(String string) {
        this.line = string;
    }

    public String getLine(Object... args) {
        return String.format(line, args);
    }
}
