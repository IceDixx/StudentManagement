package raisetech.Student.management;

import ch.qos.logback.core.util.StringUtil;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application {
@Autowired
private  StudentRepository repository;

	private Map<String,Integer> student = new HashMap<>();

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	@GetMapping("/Student")
	public String studentinfo(@RequestParam String name){
		Student student =repository.searchByName(name);
		return student.getName()+" "+ student.getAge()+"Old";
	}
	@PostMapping("/Student")
	public void registerStudent(String name ,int age){
		repository.registerStudent(name, age);
  }

	@PatchMapping("/StudentInfo")
	public void updateStudent(String name,int age) {
		repository.updateStudent(name, age);
	}

	@DeleteMapping("/Student")
	public void deleteStudent(@RequestParam String name){
		repository.deleteStudent(name);
		}

	}


