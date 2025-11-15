package com.github.juliherms.report.repository;

import com.github.juliherms.report.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
    List<Funcionario> findByRegiao(String region);
}
