package kiosk;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

public class MainGUI extends JFrame {
  
    public static MainGUI gui;
    private JPanel centerPanel;
    private JPanel bottomPanel;
    private JPanel rightPanel;
    private static String page;    
    private JPanel topPanel;
    private String[] lore;
    public static JButton leftButton2;
    public static PopupGUI pop;

    public MainGUI() {
        setTitle("My GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 1080);
        setResizable(false);
        Font defaultFont = new Font("Nanum Gothic", Font.PLAIN, 20);
        UIManager.put("Button.font", FontSet.fontNow);
        UIManager.put("Label.font", FontSet.fontNow);
        UIManager.put("TextField.font", FontSet.fontNow);

        JPanel mainPanel = new JPanel(new BorderLayout());

        topPanel = new JPanel(new FlowLayout());
        topPanel.setBackground(Color.RED);
        topPanel.setPreferredSize(new Dimension(getWidth(), 100));
        mainPanel.add(topPanel, BorderLayout.NORTH);

        centerPanel = new JPanel();
        centerPanel.setBackground(Color.GREEN);
        centerPanel.setPreferredSize(new Dimension(400, getHeight()));
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        bottomPanel = new JPanel(new GridLayout(0, 3));
        bottomPanel.setBackground(Color.BLUE);
        bottomPanel.setPreferredSize(new Dimension(getWidth(), 100));
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        rightPanel = new JPanel();
        rightPanel.setBackground(Color.YELLOW);
        rightPanel.setPreferredSize(new Dimension(200, getHeight()));
        mainPanel.add(rightPanel, BorderLayout.EAST);

        JButton leftButton = new JButton("직원 호출");
        leftButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PosDevice.needHelp();
            }
        });

        leftButton2 = new JButton("폰크 크기 변경");
        leftButton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                FontSet.changeFont();
            }
        });
        JButton rightButton = new JButton("주문 하기");
        rightButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                kioskMain.orderProcess();
            }
        });
        leftButton.setFont(FontSet.bigFont);
        leftButton2.setFont(FontSet.bigFont);
        rightButton.setFont(FontSet.bigFont);
        bottomPanel.add(leftButton);
        bottomPanel.add(leftButton2);
        bottomPanel.add(rightButton);

        getContentPane().add(mainPanel);

        categoryList(Category.categorys[0].category);
        menuList(Category.categorys[0].category);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    public static void changeFont() {
        UIManager.put("Button.font", FontSet.fontNow);
        UIManager.put("Label.font", FontSet.fontNow);
        UIManager.put("TextField.font", FontSet.fontNow);
        // categoryList(page);
        gui.categoryList(page);
        gui.menuList(page);
        gui.basketReload();
        // ; // 레이아웃 및 구성 요소 계층 재계산
        // gui.repaint(); // 구성 요소 다시 그리기
        // gui.refreshGUI();
    }
    public void categoryList(String sel) {
        page = sel;
        
        topPanel.removeAll();
        for (Category category : Category.categorys) {

            JButton newButton = CustomButton(null, "main", category.category, category.root);
            if (category.category == sel) {
                newButton.setBackground(Color.PINK);
            }
            topPanel.add(newButton);
        }
    }

    public void menuList(String category) {
        centerPanel.removeAll();
        for (Food food : Food.foods) {
            if (food.category.equals(category)) {

                // System.out.println(food.name + "AAA");
        
                String[] lore = {formattedNumber(food.price)};
                JButton newButton = CustomButton(food, "menu", food.name, food.root, lore);
                centerPanel.add(newButton);
            }
        }
    };

    public void basketReload() {
        rightPanel.removeAll();
        if (Basket.basketList != null) {
            for (Basket basket : Basket.basketList) {
                // System.out.println(basket.food.name + "BBB" + basket.nots);
                
                if (basket.nots != null) {
                    lore = new String[] { basket.mount + "개 " + formattedNumber(basket.mount * basket.food.price), basket.nots };
                } else {
                    lore = new String[] { basket.mount + "개 " + formattedNumber(basket.mount * basket.food.price) };
                }
                
                JButton newButton = CustomButton(100, basket.food, "basket", basket.food.name, basket.food.root, lore, basket);
                rightPanel.add(newButton);
                lore = null;
            }
        }

        refreshGUI();
    };

    public static String formattedNumber(Integer value) {
        
        NumberFormat numberFormat = NumberFormat.getInstance();
        String formatted = numberFormat.format(value);
        return formatted + "₩";
    }

    public JButton CustomButton(Food food, String type, String title, String root) {
        return CustomButton(50, food, type, title, root, null, null);
    }
    public JButton CustomButton(Food food, String type, String title, String root, String[] lore) {
        return CustomButton(100, food, type, title, root, lore, null);
    }

    public JButton CustomButton(Integer size, Food food, String type, String title, String root, String[] lore, Basket basket) {
        // System.out.println(basket);
        ImageIcon originalIcon = new ImageIcon( "images/" + root);
        Image originalImage = originalIcon.getImage();
        int desiredWidth = size;
        int desiredHeight = size;
        Image scaledImage = originalImage.getScaledInstance(desiredWidth, desiredHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        JButton buttonPanel = new JButton();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setFont(FontSet.fontNow);

        JLabel textLabel = new JLabel(title);
        textLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.add(textLabel);

        JLabel imageLabel = new JLabel(scaledIcon);
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.add(imageLabel);

        if (lore != null) {
            for (String string : lore) {
                JLabel textLabel2 = new JLabel(string);
                textLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);
                buttonPanel.add(textLabel2);
            }
        }
        buttonPanel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 버튼 클릭 이벤트 처리 메서드 호출
                if (basket != null){
                    buttonClickEvent(type, title, food, basket);
                } else {
                    buttonClickEvent(type, title, food);
                }
                 
            }
        });
        return buttonPanel;
    }

    // @Override
    public void buttonClickEvent(String type, String title, Food food) {
        buttonClickEvent(type, title, food, null);
    }
    public void buttonClickEvent(String type, String title, Food food, Basket basket) {
        // System.out.println("버튼이 클릭되었습니다. 제목: " + type + title);
        if (type.equals("main")){
            categoryList(title);
            menuList(title);
            refreshGUI();
            Tts.playSound(title + "을 선택 하셨습니다");
        } else if (type.equals("menu")) {
            if (pop != null) {
                
                pop.frame.dispose();
            }
            Tts.playSound(food.name + "을 선택 하셨습니다");
            pop = new PopupGUI(food);
        } else if (type.equals("basket")) {

            Basket.basketList = Basket.removeVariableFromList(Basket.basketList, basket);
            Tts.playSound(basket.food.name + "을 주문 취소 하셨습니다");
            basketReload();
        }
        // 추가적인 동작 구현
    }



    public void refreshGUI() {
        revalidate(); // 레이아웃 및 구성 요소 계층 재계산
        repaint(); // 구성 요소 다시 그리기
    }
    public static void main(String[] args) {
        gui = new MainGUI();
    }
}

