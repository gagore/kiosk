package kiosk;
public class Category {
    public static Category[] categorys; // public으로 변경
    String category;
    String root;

    public Category(String category, String root) {
        this.category = category;
        this.root = root;
    }
    public static void main(String[] args) {
        categorys = new Category[] {
            new Category("버거", "image.png"),
            new Category("피자", "6598203.png"),
            new Category("음료", "2405597.png")
        };
    }
}