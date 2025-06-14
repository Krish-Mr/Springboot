package com.bookmaster.beanscope;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

@RestController
@RequestMapping("request")
@Scope(value = WebApplicationContext.SCOPE_REQUEST)
public class RequestScopeBean {
	public static AtomicReference<List<RequestScopeBean>> raObj = new AtomicReference<>();

	public RequestScopeBean() {
		System.err.println("________________________________________________");
		System.err.println("Request Bean is Created..." + this.hashCode());
		System.err.println("________________________________________________");
		
		List<RequestScopeBean> ifNotPresent = Optional.ofNullable(raObj.get()).orElseGet(ArrayList::new);
		raObj.set(ifNotPresent);
		raObj.get().add(this);
	}
	
	@GetMapping("")
	public String getId() {
		return "<h3>For Each Hit the bean will create a new Instance. This bean is no longer in the Bean Factory</h3><br/>"+this.getClass() + ": " + this.hashCode()
		+ "<br/>List out all created objects: <br/>"
		+ raObj.get().stream().map(e-> String.valueOf(e.hashCode())).collect(Collectors.joining("<br/>"));
	}
}
