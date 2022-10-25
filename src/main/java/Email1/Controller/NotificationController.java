package Email1.Controller;

import Email1.Entities.NotificationDTO;
import Email1.Entities.Student;
import Email1.Services.EmailService;
import Email1.Services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notification")

public class NotificationController {

    @Autowired
    EmailService emailService;

    @Autowired
    StudentService studentService;

    @PostMapping
    public ResponseEntity<String> sendEmail(@RequestBody NotificationDTO notification) {
        try {
            Student student = studentService.getStudentById(notification.getId());
            if (student == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not find the student");
            }
            emailService.sendTo(student.getEmail(), notification.getTitle(), notification.getText());
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
