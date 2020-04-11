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
    public static Battle get() {
        if(battle == null){
            battle = new Battle();
        }
        return battle;
    }
    public Field getField(int i) {
        return fields[i];
    }
    public void openFireOnField(int fieldIndex, int x, int y) {
        fields[fieldIndex].openFireOnCell(x, y);
        //System.out.println("выстрел по полю " + fieldIndex + " состоялся");///////////////////////////////////////
        if (fields[fieldIndex].isAllShipsDestriyed()) {
            if (fieldIndex == 1) {
                battleResult(1);
            } else {
                battleResult(2);
            }
        }
    }
    private void battleResult(int player) {
        System.out.println("Игрок " + player + " победил!");
    }
    public GameMoge getMode() {
        return mode;
    }
    public void setMode(GameMoge mode) {
        this.mode = mode;
    }
    public static void clear() {
        battle = null;
    }
}
