package kiosk;
public interface PurchaseOrder {
	public abstract void sendOrder(int OrderNum, String menuName, int pcs);
	public abstract void sendOrder(int OrderNum, String menuName, int pcs, String nots);

}

