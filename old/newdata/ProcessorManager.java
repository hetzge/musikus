package newdata;

import java.util.HashMap;
import java.util.Map;

public class ProcessorManager {

	public static final ProcessorManager INSTANCE = new ProcessorManager();
	
	private final Map<Processor<?, ?>, Processor<?, ?>> processors = new HashMap<>();
	
	public void registerProcessor(Processor<?, ?> processor){
		if(processor == null) throw new IllegalArgumentException("The parameter processor is null");
		if(processors.containsKey(processor)) throw new IllegalStateException("A processor from class " + processor.classFrom + " to class " + processor.classTo + " is already registered");
		processors.put(processor, processor);
	}
	
	@SuppressWarnings("unchecked")
	public <TYPE_FROM, TYPE_TO> Processor<TYPE_FROM, TYPE_TO> getProcessor(Class<TYPE_FROM> from, Class<TYPE_TO> to){
		Processor<TYPE_FROM, TYPE_TO> processor = (Processor<TYPE_FROM, TYPE_TO>) processors.get(new Processor<>(from, to, null));
		if(processor == null) throw new IllegalStateException("ProcessorManager manages no processor from class " + from + " to class " + to);
		return processor;
	}
	
}
