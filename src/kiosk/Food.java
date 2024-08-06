package kiosk;

public class Food {
    public static Food[] foods; // public으로 변경

    String category;
    String name;
    String root;
    Integer price;   
    String[] nots;

    public Food(String category, String name, Integer price, String root) {
        this.category = category;
        this.name = name;
        this.root = root;
        // this.nots = new String[0];
        this.nots = null;
        this.price = price;
    }

    public Food(String category, String name, Integer price, String root, String[] nots) {
        this.category = category;
        this.name = name;
        this.root = root;
        this.nots = nots;
        this.price = price;
    }



    public static void main(String[] args) {

        foods = new Food[]{
            new Food("버거", "치즈 버거", 5000, "3075977.png", new String[] {"양파 제거", "오이 제거"}),
            new Food("버거", "고기 버거", 6000, "1037762.png", new String[] {"양파 제거", "오이 제거"}),
            new Food("버거", "치킨 버거", 7000, "1448787.png", new String[] {"양파 제거", "오이 제거"}),   
            new Food("피자", "치즈 피자", 8000, "image.png", new String[] {"토핑 추가"}),
            new Food("음료", "콜라", 1000, "image.png")
        };

        // for (Food food : foods) {
        //     System.out.println(food.name);
        // }
    }
}
