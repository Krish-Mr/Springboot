package com.bookmaster.beanscope;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@RestController(value = "SingletonBean")
@RequestMapping("/singleton")
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class SingletonScopeBean {

	@Autowired
	PrototypeScopeBean prototypeBeanObj;
	
	public SingletonScopeBean() {
		System.err.println("________________________________________________");
		System.err.println("Singleton Scope Bean Created..."+ this.hashCode());
		System.err.println("________________________________________________");
	}
	
	@PostConstruct
	public void postConstruct() {
		System.err.println("\nPost Constructor Called - Singleton Scope Bean");
	}
	
	@PreDestroy
	public void preDestroy() {
		System.err.println("\nPre Destroy Called - Singleton Scope Bean");
	}
	
	@GetMapping("/this")
	public String getId() {
		return this.getClass() + ": " + this.hashCode();
	}

	@GetMapping("/get-prototype")
	public String getPrototypeBean() {
		return this.prototypeBeanObj + ": " + this.prototypeBeanObj.hashCode();
	}
	
	
	@GetMapping(produces = "text/html")
	public String renderHTML() {
		return "<h3>The Bean created in this class will never changed after initialization:</h3>"
				+ "<a href = \"./singleton/get-prototype\">Get Prototype</a><br/>"
				+ "<a href = \"./singleton/this\">Get this Object</a>";
	}
}