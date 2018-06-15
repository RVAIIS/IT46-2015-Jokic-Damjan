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

import rva.jpa.Status;
import rva.jpa.Student;
import rva.reps.StatusRepository;

@RestController
public class StatusRestController {
	@Autowired
	private StatusRepository statusRepository;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@GetMapping("status")
	public Collection<Status> getStatusi(){
		return statusRepository.findAll();
	}
	
	@GetMapping("statusId/{id}")
	public Status getStatus(@PathVariable ("id") Integer id) {
		return statusRepository.getOne(id);
	}
	
	@GetMapping("statusNaziv/{naziv}")
	public Collection<Status> getStatusByNaziv(@PathVariable("naziv") String naziv){
		return statusRepository.findByNazivContainingIgnoreCase(naziv);
	}
	
	//DELETE
	@CrossOrigin
	@DeleteMapping("status/{id}")
	public ResponseEntity<Status> deleteStatus(@PathVariable ("id") Integer id){
		if(!statusRepository.existsById(id))
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		statusRepository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	//INSERT
	@CrossOrigin
	@PostMapping("status")
	public ResponseEntity<Status> insertStatus(@RequestBody Status status){
		if(!statusRepository.existsById(status.getId())) {
			statusRepository.save(status);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.CONFLICT);
	}
	
	//UPDATE
	@CrossOrigin
	@PutMapping("status")
	public ResponseEntity<Status> updateStatus(@RequestBody Status status){
		if(statusRepository.existsById(status.getId())) {
			statusRepository.save(status);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
