package raisetech.Student.management;

import ch.qos.logback.core.util.StringUtil;
import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application {


	private Map<String,Integer> student = new HashMap<>();

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	@GetMapping("/Student")
	public Map<String,Integer> student() {
		return student;
	}
	@PostMapping("/Student")
	public String addStudentInfo(String name , 	Integer age){
		student.put(name,age);
		return "got it~";
	}
}
