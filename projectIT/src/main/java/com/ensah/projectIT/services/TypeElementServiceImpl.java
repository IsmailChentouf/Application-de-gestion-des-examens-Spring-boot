package com.ensah.projectIT.services;

import com.ensah.projectIT.models.TypeElement;
import com.ensah.projectIT.repositories.TypeElementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeElementServiceImpl implements TypeElementService {

    @Autowired
    private TypeElementRepository typeElementRepository;

    @Override
    public List<TypeElement> getAllTypeElements() {
        return typeElementRepository.findAll();
    }

    @Override
    public TypeElement getTypeElementById(Long id) {
        return typeElementRepository.findById(id).orElse(null);
    }
}
