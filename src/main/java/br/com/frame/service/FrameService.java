package br.com.frame.service;

import br.com.frame.dto.FrameDTO;
import br.com.frame.entity.Frame;

import java.util.List;

public interface FrameService {
    public Frame save(FrameDTO frameDTO) throws Exception;
    public Frame findById(Long id) throws Exception;
    public List<Frame> retrieveAll() throws Exception;
    public boolean update(Long id, FrameDTO frameDTO) throws Exception;
    public boolean delete(Long id) throws Exception;
}
