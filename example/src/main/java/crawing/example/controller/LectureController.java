package crawing.example.controller;

import crawing.example.domain.Lectures;
import crawing.example.service.LectureService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
@CrossOrigin(origins="*", allowedHeaders = "*")
@RestController
@RequestMapping("/main")
@RequiredArgsConstructor
public class LectureController {
    private final LectureService lectureService;
    @GetMapping("/lecture")
    public ResponseEntity<List<Lectures>> getLectures(){
        return ResponseEntity.ok().body(lectureService.getLectures());
    }
}
@Data
class LocationToLectForm {
    private String LectureName;
    private String Location;

}