package com.example.demo.repositories;

import com.example.demo.entities.AtendimentoPacienteV;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AtendimentPacienteRepositories extends JpaRepository<AtendimentoPacienteV, Long> {
}
