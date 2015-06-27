package newdata;

public class NewDataTest {

	public static void main(String[] args) {
		
		Processor<String, Integer> processorStringToInteger = new Processor<String, Integer>(String.class, Integer.class, value -> Integer.valueOf(value));
		ProcessorManager.INSTANCE.registerProcessor(processorStringToInteger);
		
		new Node<String>("10");
		new Node<Integer>(10);
		
		
	}
	
}
