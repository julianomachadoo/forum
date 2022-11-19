package com.github.julianomachadoo.forumapi.repository;

import com.github.julianomachadoo.forumapi.modelo.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {
    Curso findByNome(String nomeCurso);
}
