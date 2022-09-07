package crawing.example.repository;

import crawing.example.domain.Lectures;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LecturesRepo extends JpaRepository<Lectures,Long>{
    Lectures findByLecture (String name);
}
