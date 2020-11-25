package br.com.frame.repository;

import br.com.frame.entity.Frame;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FrameRepository extends JpaRepository<Frame, Long> {}