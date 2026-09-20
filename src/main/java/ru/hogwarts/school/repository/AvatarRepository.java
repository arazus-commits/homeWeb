package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.model.Avatar;

import java.util.Optional;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {
    Optional<Avatar> findByStudentId(Long studentId);

org.springframework.data.domain.Page<ru.hogwarts.school.model.Avatar> findAll(org.springframework.data.domain.Pageable pageable);
}


