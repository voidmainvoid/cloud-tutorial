package com.hcs.cloud.tutorial.controller;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.hcs.cloud.tutorial.exception.UserNotFoundException;
import com.hcs.cloud.tutorial.model.SomeBean;
import com.hcs.cloud.tutorial.model.User;
import com.hcs.cloud.tutorial.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private MessageSource messageSource;

	@GetMapping("/api/users")
	public List<User> retrieveUsers() {
		List<User> users = this.userService.retrieveUsers();
		return users;
	}

	@GetMapping("/api/users/{id}")
	public User retriveUser(@PathVariable Long id) throws UserNotFoundException {
		User user = this.userService.retrieveUser(id);

		// HATEOAS
		return user;
	}

	@PostMapping("api/users")
	public ResponseEntity<User> saveUser(@Valid @RequestBody User user) {
		User savedUser = this.userService.saveUser(user);

		// státusz kód beállítása
		// return new ResponseEntity(HttpStatus.CREATED);

		// új resource link visszaküldése
		// path: hozzáfűzi a stringet, és az {id} helyére beilleszti a
		// savedUser.getId() -t
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}

	@DeleteMapping("/api/users/{id}")
	public void deleteUser(@PathVariable Long id) {
		this.userService.deleteUser(id);
	}

	@GetMapping("/api/hello")
	public String helloWorld(@RequestHeader(name = "Accept-Language", required = false) Locale locale) {
		return messageSource.getMessage("hello.message", null, locale);
	}

	@GetMapping("/api/hello2")
	public String helloWorld2(@RequestHeader Map map) {
		return messageSource.getMessage("hello.message", null, LocaleContextHolder.getLocale());
	}

	@GetMapping("/api/somebean")
	public MappingJacksonValue someBean() {

		SomeBean bean = new SomeBean("field1", "field2", "field3");

		// dynamic filtering
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field1", "field2");
		// A filter meg kell adni a model-ben is: @JsonFilter
		FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);
		MappingJacksonValue mapping = new MappingJacksonValue(bean);
		mapping.setFilters(filters);

		return mapping;
	}

	@GetMapping("/api/somebeanlist")
	public List<SomeBean> someBeanList() {
		List<SomeBean> beanList = Arrays.asList(new SomeBean("field1", "field2", "field3"),
				new SomeBean("field11", "field22", "field33"));
		return beanList;
	}
}
