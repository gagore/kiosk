package kiosk;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class PopupGUI {
    JFrame frame;
    private Integer mount;
    private JPanel titlePanel;
    private JLabel titleLabel;
    private JLabel textLabel;
    private JCheckBox[] checkBoxes;

    public PopupGUI(Food food) {
        // 프레임 설정
        frame = new JFrame();
        frame.setTitle("Popup GUI");
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setSize(400, 600);
        frame.setLayout(new BorderLayout());
        frame.setResizable(false);
        
        mount = 1;

        Font defaultFont = new Font("Nanum Gothic", Font.PLAIN, 20);
        UIManager.put("Button.font", FontSet.fontNow);
        UIManager.put("Label.font", FontSet.fontNow);
        UIManager.put("TextField.font", FontSet.fontNow);

        // Image originalImage = new ImageIcon("image.png").getImage();
        Image originalImage = new ImageIcon("images/" + food.root).getImage();
        // Image orImageiImage = new ImageIcon("image.png");
        int desiredWidth = 350;
        int desiredHeight = 350;
        Image scaledImage = originalImage.getScaledInstance(desiredWidth, desiredHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        // 제목 패널
        titlePanel = new JPanel(new BorderLayout());
        // ImageIcon titleIcon = new ImageIcon("title_image.png"); // 제목 이미지 로드
        titleLabel = new JLabel(scaledIcon);
        titlePanel.add(titleLabel, BorderLayout.NORTH);

        textLabel = new JLabel(food.name + "\n " + MainGUI.formattedNumber(mount * food.price));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        titlePanel.add(textLabel, BorderLayout.CENTER);
        frame.add(titlePanel, BorderLayout.NORTH);

        

        JPanel panel = new JPanel(new GridLayout(1, 3));
        JButton button1 = new JButton("-");
        JButton button2 = new JButton("수량: " + mount);
        JButton button3 = new JButton("+"); 
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mount > 1) {
                    mount --;
                };
                button2.setText("수량: " + mount);
                textLabel.setText(food.name + "\n " + MainGUI.formattedNumber(mount * food.price));
            }
        });

        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mount ++;
                button2.setText("수량: " + mount);
                textLabel.setText(food.name + "\n " + MainGUI.formattedNumber(mount * food.price));
            }
        });

        panel.add(button1);
        panel.add(button2);
        panel.add(button3);

        if (food.nots != null) {
            // frame.add(panel, BorderLayout.CENTER);
            frame.add(panel, BorderLayout.WEST);

            // 체크박스
            Integer size = food.nots.length;
            checkBoxes = new JCheckBox[size];
            JPanel checkBoxPanel = new JPanel(new GridBagLayout()); // GridBagLayout 사용
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = GridBagConstraints.RELATIVE;
            gbc.anchor = GridBagConstraints.EAST;
            gbc.insets = new Insets(0, 0, 0, 0); // 체크박스 간격을 10으로 설정
            
            for (int i = 0; i < checkBoxes.length; i++) {
                checkBoxes[i] = new JCheckBox(food.nots[i]); // 체크박스 생성
                checkBoxPanel.add(checkBoxes[i], gbc.clone()); // 체크박스를 패널에 추가
            }
            frame.add(checkBoxPanel, BorderLayout.CENTER);
            
            
        } else {
            frame.add(panel, BorderLayout.CENTER);
        };

        // 버튼
        JButton button = new JButton("확인");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                check(food, mount); // 버튼 클릭 시 팝업 메시지 표시
            }
        });
        frame.add(button, BorderLayout.SOUTH);
        
        frame.setVisible(true);
        
        // dispose();
    }

    private void check(Food food, Integer mount) {
        // StringBuilder message = new StringBuilder();
        String[] array = new String[0]; // 배열을 빈 배열로 초기화
        String lore = "";
        if (food.nots != null) {
            for (JCheckBox checkBox : checkBoxes) {
                if (checkBox.isSelected()) {
                    lore = lore + checkBox.getText() + " ";
                    // System.out.println(checkBox.getText());

                    // array = appendToString(array, checkBox.getText()); // 배열 크기 동적 확장
                    
                } 
            }
        }

        // System.out.println(Arrays.toString(array)); // 배열 값 출력
        frame.dispose();
        Basket basket = new Basket(food, mount, lore);
        Tts.playSound(food.name + "을 장바구니에 담았습니다");
        if (lore != "") {
            Tts.playSound("추가 옵션" + lore);
        }
        
    }

    public static String[] appendToString(String[] existingArray, String string) {
        String[] newArray = new String[existingArray.length + 1];
        System.arraycopy(existingArray, 0, newArray, 0, existingArray.length);
        newArray[newArray.length - 1] = string;
        return newArray;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                String[] nots = {"dd", "dd" };
                Food food = new Food("버거", "치즈 버거", 1000, "image.png", nots);
                new PopupGUI(food);
                
            }
        });
    }



    public static void changeFont() {
        UIManager.put("Button.font", FontSet.fontNow);
        UIManager.put("Label.font", FontSet.fontNow);
        UIManager.put("TextField.font", FontSet.fontNow);
    }

}
