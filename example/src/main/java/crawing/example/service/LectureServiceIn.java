package crawing.example.service;

import crawing.example.domain.Lectures;
import java.util.List;

public interface LectureServiceIn {
    Lectures saveLecture (Lectures lectures);
    Lectures getLecture (String lecture);
    List<Lectures> getLectures();
}
