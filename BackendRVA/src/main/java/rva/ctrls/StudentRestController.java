package rva.ctrls;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import rva.jpa.Departman;
import rva.jpa.Student;
import rva.reps.DepartmanRepository;
import rva.reps.StudentRepository;

@Api(tags= {"Student CRUD operacije"})
@RestController
public class StudentRestController {
	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private DepartmanRepository departmanRepository;
	
	@GetMapping("student")
	@ApiOperation(value="Vraca kolekciju svih studenata iz baze podataka")
	public Collection<Student> getStudenti(){
		return studentRepository.findAll();
	}
	
	@GetMapping("studentId/{id}")
	@ApiOperation(value="Vraca studenta iz baze podataka na osnovu prosledjenog id-ja kroz path varijablu")
	public Student getStudent(@PathVariable ("id") Integer id) {
		return studentRepository.getOne(id);
	}
	
	@GetMapping("studentiDepartmana/{id}")
	@ApiOperation(value="Vraca studente koji studiraju na departmanu koji ima prosledjen id kroz path varijablu")
	public Collection<Student> getStudentiDepartmana(@PathVariable ("id") Integer id){
		Departman d = departmanRepository.getOne(id);
		return studentRepository.findByDepartman(d);
	}
	
	//DELETE
	@CrossOrigin
	@DeleteMapping("student/{id}")
	@ApiOperation(value="Brise studenta iz baze podataka na osnovu id-ja koji se prosledjuje kao path varijabla")
	public ResponseEntity<Student> deleteStudent(@PathVariable ("id") Integer id){
		if(!studentRepository.existsById(id))
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		studentRepository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	//INSERT
	@CrossOrigin
	@PostMapping("student")
	@ApiOperation(value="Upisuje studenta u bazu podataka")
	public ResponseEntity<Student> insertStudent(@RequestBody Student student){
		if(!studentRepository.existsById(student.getId())) {
			studentRepository.save(student);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.CONFLICT);
	}
	
	//UPDATE
	@CrossOrigin
	@PutMapping("student")
	@ApiOperation(value="Modifikuje postojeceg studenta iz baze podataka")
	public ResponseEntity<Student> updateStudent(@RequestBody Student student){
		if(!studentRepository.existsById(student.getId())) 
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		studentRepository.save(student);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
