package com.example.study.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.study.model.SearchParam;

@RestController
@RequestMapping("/api")
public class GetController {
	@RequestMapping(method=RequestMethod.GET, path="/getMethod")
	public String getRequest() {
		return "Hi getMethod";
	}
	
	@GetMapping("/getParameter")
	public String getParameter(@RequestParam String id, @RequestParam (name = "password") String pwd) {
		System.out.println("id : "+id);
		System.out.println("password : "+pwd);
		
		return id+pwd;
	}
	@GetMapping("/getMultiParameter")
	public String getMultiParameter(SearchParam searchParam) {
		System.out.println(searchParam.getAccount());
		System.out.println(searchParam.getEmail());
		System.out.println(searchParam.getPage());
		
		return "OK";
		
	}

}
