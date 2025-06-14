package com.bookmaster.beanscope;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/prototype")
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class PrototypeScopeBean {
	
	private static AtomicReference<List<PrototypeScopeBean>> paObj = new AtomicReference<List<PrototypeScopeBean>>();
	
	public PrototypeScopeBean() {
		System.err.println("________________________________________________");
		System.err.println("Prototype Bean is Created..." + this.hashCode());
		System.err.println("________________________________________________");
		
		List<PrototypeScopeBean> init = Optional.ofNullable(paObj.get()).orElseGet(ArrayList::new);
		paObj.set(init);
		paObj.get().add(this);
	}
	
	@GetMapping()
	private String getId() {
		return "<h3>For Each Hit the bean will create a new Instance. We can access these created bean later</h3><br/>"+this.getClass() + ": " + this.hashCode()
			+ "<br/>List out all created objects: <br/>"
			+ paObj.get().stream().map(e-> String.valueOf(e.hashCode())).collect(Collectors.joining("<br/>"));
	}
	
}