package com.bookmaster.rest.person;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookmaster.person.Person;

@RestController
public class PersonCRUD {

	AtomicInteger id = new AtomicInteger();
	ConcurrentHashMap<Integer, Person> p = new ConcurrentHashMap<Integer, Person>();
	
	@RequestMapping(value = "/details", produces = MediaType.TEXT_HTML_VALUE)
	private String pathDetails() {
		
		return "<p> <a href=\"./\"> Request Mapping - Root path  </a> </p>\r\n"
				+ "<p> <a href=\"./{id}\">  Get Method - return id person details</a> </p>\r\n"
				+ "<p> <a href=\"../\">  Get Method - return all the person details</a> </p>\r\n"
				+ "<p> <a href=\"./add\">  Post Method - to add a person</a> </p>\r\n"
				+ "<p> <a href=\"./{id}\"> Put method - Path variable to update a person</a></p>\r\n"
				+ "<p> <a href=\"./{id}\">  Delete method - Path varable to delete a person</a> </p>";
		
	}

	@GetMapping("/{id}")
	private Person getPersonId(@PathVariable int id) {
		return p.get(id);
	}
	
	@GetMapping
	private Collection<Person> getAllPerson() {
		return p.values();
	}

	@PostMapping(value = "/add", produces = {"text/plain"})
	private String createPerson(@RequestBody Person person) {
		int id = this.id.incrementAndGet();
		p.put(id, person);
		return "Person created..."+"\n"+person.toString();
	}
	
	@PostMapping(value = "/addAll", produces = {"text/plain"})
	private String createPerson(@RequestBody List<Person> person) {
		person.forEach(e->{
			int id = this.id.incrementAndGet();
			p.put(id, e);
		});
		return "Person created..."+"\n"+person.toString();
	}
	
	@PutMapping(value="/{id}", produces = MediaType.TEXT_PLAIN_VALUE)
	private String updatePerson(@PathVariable("id") int id, @RequestBody Person person) {
		Person person2 = new Person();
		person2 = person;
		person2.setId(id);
		p.put(id, person2);	
		return "Person updated: "+person2.toString();
	}
	
	@PatchMapping("/{id}")
//	@PatchMapping(value = "/{id}", produces = {"text/plain"} , consumes = "text/plain")
//	@PatchMapping(path = "/{id}", produces = {"text/plain"} , consumes = "text/plain")
	private String patchPerson(@RequestBody Person person, @PathVariable("id") int id) {
		Person person2 = null;
		boolean isIdAvail = p.values().stream().filter(e->e.getId()==person.getId()).map(e->true).findFirst().orElse(false);
		if(!isIdAvail) {
			person2 = new Person();
		} else {
			Integer findId = p.entrySet().stream().filter(e-> e.getValue().getId() == person.getId()).map(e->e.getKey()).findFirst().orElse(this.id.getAndIncrement());
			person2 = p.get(findId);
		}
		if(person.getId()!=0 ) {
			person2.setId(person.getId());
		}else {
			person2.setId(id);
		}
		if(person.getFirstName()!=null) {
			person2.setFirstName(person.getFirstName());
		}
		if(person.getLastName()!=null) {
			person2.setLastName(person.getLastName());
		}
		if(person.getRole()!=null) {
			person2.setRole(person.getRole());	
		}
		if(person.getSalary()!=0) {
			person2.setSalary(person.getSalary());
		}
		p.put(id, person2);
		return "Partially updated by the given fields: "+ person2.toString();
	}

	@DeleteMapping("/{id}")
	private String deletePerson(@PathVariable("id") int id) {
		p.remove(id);
		return "person "+id+" is removed";
	}
}
