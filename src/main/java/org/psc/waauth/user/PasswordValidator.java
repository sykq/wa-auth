package org.psc.waauth.user;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class PasswordValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        // just validate the Customer instances
        return UserRegistrationBean.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "required.password", "Field name is required.");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "required.confirmPassword",
                "Field name is required.");

        UserRegistrationBean userRegistration = (UserRegistrationBean) target;

        if (!(userRegistration.getPassword().equals(userRegistration.getConfirmationPassword()))) {
            errors.rejectValue("password", "notmatch.password");
        }

    }

}
