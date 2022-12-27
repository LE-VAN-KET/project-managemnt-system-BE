package com.dut.team92.organizationservice.utils.validator;

import com.dut.team92.organizationservice.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ExistProjectKetValidator implements ConstraintValidator<ExistProjectKey, String> {
    @Autowired
    private ProjectRepository projectRepository;
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !projectRepository.existsByKey(value);
    }
}
