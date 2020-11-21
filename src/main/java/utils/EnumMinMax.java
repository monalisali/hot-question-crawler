package utils;

public enum EnumMinMax {
    Min(1),Max(2);
    private int value;
    private EnumMinMax(int value){
        this.value = value;
    }

    public int getValue(){
        return this.value;
    }
}
