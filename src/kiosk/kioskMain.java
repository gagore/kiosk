package kiosk;
import javax.swing.JOptionPane;

public class kioskMain {
    OrderNumber order;
    PurchaseOrder pos;
    PaymentReq pay;
    public static boolean orderProcessing;
    public static boolean newOrderReceive;

    public kioskMain(OrderNumber order, PurchaseOrder pos, PaymentReq pay) throws InterruptedException {
        this.order = order;
        this.pos = pos;
        this.pay = pay;

        while (true) {
            Thread.sleep(100);
            if (newOrderReceive == true) {
                if (orderProcessing == true) {
                    

                    newOrderReceive = false;
                    continue;
                }
                if (Basket.basketList == null) {
                    

                    newOrderReceive = false;
                    continue;
                }
                if (Basket.basketList.length < 1) {
                    

                    newOrderReceive = false;
                    continue;
                }                

                orderProcessing = true;
                
                if(pay.sendPaymentReq(2, 50, 10)) {
                    while(pay.receivePaymentRes()==PaymentReq.RES_WAIT) {
                        System.out.println("Wait");
                    }

                    int orderNum = order.getOrderNumber();
                    System.out.println(orderNum);

                    for (Basket basket : Basket.basketList) {
                        pos.sendOrder(orderNum, basket.food.name, basket.mount, basket.nots);
                    }
                    orderProcessing = false;
                    newOrderReceive = false;
                    String message = "주문 번호:" + orderNum; 
                    Tts.playSound("주문을 완료 했습니다");
                    JOptionPane.showOptionDialog(null, message, "팝업 창", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{"확인"}, null);
                    resetAll();
                } else {
                    Tts.playSound("카드가 유효하지 않습니다");
                    String message = "카드가 유효하지 않음";
                    JOptionPane.showOptionDialog(null, message, "팝업 창", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{"확인"}, null);
                }
                



            }

        }
        // new Gui();
        // orderProcess(pay);

    }

    public static void orderProcess() {
        System.out.println("주문 요청 받음");
        newOrderReceive = true;
    }


    public static void main(String[] args) throws InterruptedException {
        FontSet.main(args);
        Category.main(args);
        Food.main(args);
        MainGUI.main(args);
        
        OrderNumber order = new OrderDevice();
        PurchaseOrder pos = new PosDevice();
        PaymentReq pay = new PaymentDevice();
        new kioskMain(order, pos, pay);


    }

    public static void resetAll() {
        Basket.resetBasket();
        MainGUI.gui.basketReload();
        MainGUI.gui.categoryList(Category.categorys[0].category);
        MainGUI.gui.menuList(Category.categorys[0].category);
        MainGUI.gui.refreshGUI();
    }
}
