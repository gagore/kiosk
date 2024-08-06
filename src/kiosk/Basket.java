package kiosk;
public class Basket {

    public Integer mount;
    public Food food;
    public String nots;

    public static Basket[] basketList;

    public Basket(Food food, Integer mount) {
        this.food = food;
        this.mount = mount;
        this.nots = null;
        appendToBasket(this);
        MainGUI.gui.basketReload();
    }

    // public Basket(Food food, Integer mount, String[] nots) {

    public Basket(Food food, Integer mount, String nots) {
        this.food = food;
        this.mount = mount;
        if (nots == "") {
            this.nots = null;
        } else {
            this.nots = nots;
        } 
        appendToBasket(this);
        MainGUI.gui.basketReload();
    }

    public static void main(String[] args) {
        resetBasket();
    }
    public static void resetBasket() {
        basketList = new Basket[0];

    }

    public static void appendToBasket(Basket value) {
        if (basketList == null) {
            basketList = new Basket[0];
        };
        Basket[] existingArray = basketList;
        Basket[] newArray = new Basket[existingArray.length + 1];
        System.arraycopy(existingArray, 0, newArray, 0, existingArray.length);
        newArray[newArray.length - 1] = value;
        basketList = newArray;
    }
    public static Basket[] removeVariableFromList(Basket[] array, Basket variableToRemove) {

        int indexToRemove = -1;
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(variableToRemove)) {
                indexToRemove = i;
                break;
            }
        }

        if (indexToRemove != -1) {
            Basket[] newArray = new Basket[array.length - 1];
            int newArrayIndex = 0;
            for (int i = 0; i < array.length; i++) {
                if (i != indexToRemove) {
                    newArray[newArrayIndex] = array[i];
                    newArrayIndex++;
                }
            }
            return newArray;
        }

        return array; // 요소가 없거나 제거할 요소를 찾지 못한 경우 원래 배열을 그대로 반환
    }
}

