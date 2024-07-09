package com.ensah.projectIT.services;

import com.ensah.projectIT.models.TypeElement;
import java.util.List;

public interface TypeElementService {
    List<TypeElement> getAllTypeElements();
    TypeElement getTypeElementById(Long id);
}
