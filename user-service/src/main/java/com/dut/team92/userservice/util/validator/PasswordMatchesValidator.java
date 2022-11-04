package com.dut.team92.userservice.util.validator;

import com.dut.team92.userservice.domain.dto.request.CreateUserCommand;
import com.dut.team92.userservice.domain.dto.request.LoginUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {
    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        if (o instanceof CreateUserCommand) {
            CreateUserCommand createUserCommand = (CreateUserCommand) o;
            return createUserCommand.getPassword().equals(createUserCommand.getConfirmationPassword());
        }

        return true;
    }
}
