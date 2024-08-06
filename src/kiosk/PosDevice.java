package kiosk;

public class PosDevice implements PurchaseOrder{
	@Override
	public void sendOrder(int OrderNum, String menuName, int pcs) {
		sendOrder(OrderNum, menuName, pcs, "없음");
		return;
	}

	public void sendOrder(int OrderNum, String menuName, int pcs, String nots) {

		if (nots == null || nots == " " || nots == "") {
			nots = "없음";
		}
		System.out.println("Order["+OrderNum+"] : "+menuName+"("+pcs+") 메모: " + nots);

		return;
	}

	public static void needHelp() {

		System.out.println("키오스크에서 도움이 필요합니다");

		return;
	};
	
}
