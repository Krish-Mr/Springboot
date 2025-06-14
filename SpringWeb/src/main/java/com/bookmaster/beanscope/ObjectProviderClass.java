package com.bookmaster.beanscope;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bean")
public class ObjectProviderClass {
	/**
	 * refresh the page to see the bean behaviour of scope
	 */
	
	@Autowired
	ObjectProvider<PrototypeScopeBean> prototypeObj;
	@Autowired
	ObjectProvider<SingletonScopeBean> singltypeObj;
	@Autowired
	ObjectProvider<RequestScopeBean> reqtypeObj;
	
	@GetMapping("prototype")
	public String getNewObjectForPrototype(){
		PrototypeScopeBean p1 = prototypeObj.getObject();
		PrototypeScopeBean p2 = prototypeObj.getObject();
		String s = "________________________________________________\r\n<br/>"
				+"new Bean is created for Each time requested...\r\n<br/>"
				+ "\r\n<br/>p1: "+p1.getClass() + "  |  "+p1.hashCode()
				+ "\r\n<br/>p2: "+p2.getClass() + "  |  "+p2.hashCode()
				+"\r\n<br/>________________________________________________";
		System.err.println(s);
		return s;
	}
	
	@GetMapping("singleton")
	public String getNewObjectForSingleton() {
		SingletonScopeBean s1 = singltypeObj.getObject();
		SingletonScopeBean s2 = singltypeObj.getObject();
		String s = "________________________________________________\r\n<br/>"
				+"Only one bean is created for Singleton...\r\n<br/>"
				+ "\r\n<br/>s1: "+s1.getClass() + "  |  "+s1.hashCode()
				+ "\r\n<br/>s2: "+s2.getClass() + "  |  "+s2.hashCode()
				+ "\r\n<br/>________________________________________________";
		System.err.println(s);
		return s;
	}
	
	@GetMapping("request")
	public String getNewObjectForRequest() {
		RequestScopeBean s1 = reqtypeObj.getObject();
		RequestScopeBean s2 = reqtypeObj.getObject();
		String s = "________________________________________________\r\n<br/>"
				+"new Object is created for each http request...\r\n<br/>"
				+ "\r\n<br/>s1: "+s1.getClass() + "  |  "+s1.hashCode()
				+ "\r\n<br/>s2: "+s2.getClass() + "  |  "+s2.hashCode()
				+ "\r\n<br/>________________________________________________";
		System.err.println(s);
		return s;
	}

	@GetMapping(value = "", produces = MediaType.TEXT_HTML_VALUE)
	public String viewHtml(){
		return " <ul> <li>	<a href = \"./bean/singleton\"  value=\"singleton\">Singleton</a> </li>"
				+"	<li>	<a href = \"./bean/prototype\"  value=\"prototype\">Prototype</a> </li>"
				+"	<li>	<a href = \"./bean/request\"  value=\"Request\">Request</a> </li> </ul>";
	}
}
