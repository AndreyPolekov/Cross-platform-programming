package logic;

public class Battle {
    public enum GameMoge {
        PvP, PvE
    }
    private GameMoge mode;

    private static Battle battle;

    private Field[] fields = new Field[2];

    private Battle() {
        for (int i = 0; i < 2; i++) {
            fields[i] = new Field();
        }
    }
    public static Battle getBattle() {
        if(battle == null){
            battle = new Battle();
        }
        return battle;
    }
    public Field getField(int i) {
        return fields[i];
    }
    public GameMoge getMode() {
        return mode;
    }
    public void setMode(GameMoge mode) {
        this.mode = mode;
    }
}
