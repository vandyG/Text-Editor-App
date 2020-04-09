package textgen;

public class LLClient {
	public static void main(String[] args) {
		MyLinkedList<Integer> ll = new MyLinkedList<>();
		
		ll.add(1);
		ll.add(2);
		
		System.out.println(ll.get(0));
		System.out.println(ll.get(1));
	}

}
