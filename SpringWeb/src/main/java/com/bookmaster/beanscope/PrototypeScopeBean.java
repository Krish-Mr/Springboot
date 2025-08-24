package com.bookmaster.beanscope;

import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.PostConstruct;

@RestController
@RequestMapping("/prototype")
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class PrototypeScopeBean {

	private static AtomicReference<HashMap<Integer, String>> proObj = new AtomicReference<HashMap<Integer,String>>();
	private static AtomicInteger beanCount = new AtomicInteger(0);
	HashMap<Integer, String> tMap = Optional.ofNullable(proObj.get()).orElseGet(HashMap::new);
	{
		proObj.set(tMap);
	}
	public PrototypeScopeBean() {
		System.err.println("________________________________________________");
		System.err.println("Prototype Bean is Created..." + this.hashCode());
		System.err.println("________________________________________________");

		proObj.get().put(this.hashCode(), "Prototype Bean: " + beanCount.incrementAndGet());
	}

	@GetMapping()
	private String getId() {
		return "<h3>For Each Hit the bean will create a new Instance. We can access these created bean later</h3><br/>"+this.getClass() + ": " + this.hashCode()
			+ "<br/>List out all created objects: <br/>"
			+ proObj.get().entrySet().stream().map(e-> String.valueOf( e.getKey() )).collect(Collectors.joining("<br/>"));
	}

	@GetMapping("/{hashcode}")
	public String getInstanceValue(@PathVariable int hashcode) {
		proObj.get().remove(hashcode);
		preDestroy();
		return "The Prototype bean will be destroyed: " + hashcode;
	}
	
	@PostConstruct
	public void postConstruct() {
		System.err.println("\nPost Constructor Called - Prototype Scope Bean");
	}

	public void preDestroy() {
		System.err.println("\nPre Destroy Called - Prototype Scope Bean");
	}
}