/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.rave.portal.web.validator;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.EmailValidator;
import org.apache.rave.portal.model.NewUser;
import org.apache.rave.portal.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;

@Component
public class NewAccountValidator implements Validator {

    private static final int MINIMUM_PASSWORD_LENGTH = 4;
    private static final String FIELD_USERNAME = "username";
    private static final String FIELD_PASSWORD = "password";
    private static final String FIELD_CONFIRM_PASSWORD = "confirmPassword";
    private static final String FIELD_EMAIL = "email";
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final String USERNAME_PATTERN = "[\\w\\+\\-\\.@]{2,}";
    private UserService userService;

    @Autowired
    public NewAccountValidator(UserService userService) {
        this.userService = userService;
    }

    public boolean supports(Class aClass) {
        return NewUser.class.isAssignableFrom(aClass);
    }

    public void validate(Object obj, Errors errors) {
        logger.debug("Validator called");
        NewUser newUser = (NewUser) obj;

        validateUsername(errors, newUser);
        validatePassword(errors, newUser);
        validateConfirmPassword(errors, newUser);
        validateEmail(errors, newUser.getEmail());

        writeResultToLog(errors);
    }

    private void validateUsername(Errors errors, NewUser newUser) {
        final String username = newUser.getUsername();
        if (StringUtils.isBlank(username)) {
            errors.rejectValue(FIELD_USERNAME, "username.required");
            logger.info("Username required");
        } else if (!username.matches(USERNAME_PATTERN)) {
            errors.rejectValue(FIELD_USERNAME, "username.invalid.pattern");
            logger.info("Username has invalid pattern");
        } else if (isExistingUsername(username)) {
            errors.rejectValue(FIELD_USERNAME, "username.exists");
            logger.info("Username already exists");
        }
    }

    private boolean isExistingUsername(String username) {
        return userService.getUserByUsername(username) != null;
    }

    private void validatePassword(Errors errors, NewUser newUser) {
        if (StringUtils.isBlank(newUser.getPassword())) {
            errors.rejectValue(FIELD_PASSWORD, "password.required");
            logger.info("Password required");
        } else if (newUser.getPassword().length() < MINIMUM_PASSWORD_LENGTH) {
            errors.rejectValue(FIELD_PASSWORD, "password.invalid.length");
            logger.info("Password must be at least {} characters long", MINIMUM_PASSWORD_LENGTH);
        }
    }

    private void validateConfirmPassword(Errors errors, NewUser newUser) {
        if (StringUtils.isBlank(newUser.getConfirmPassword())) {
            errors.rejectValue(FIELD_CONFIRM_PASSWORD, "confirmPassword.required");
            logger.info("Confirm Password required");
        } else if (isConfirmPasswordDifferentThanPassword(newUser)) {
            errors.rejectValue(FIELD_CONFIRM_PASSWORD, "confirmPassword.mismatch");
            logger.info("Password mismatch");
        }
    }

    private boolean isConfirmPasswordDifferentThanPassword(NewUser newUser) {
        return !(newUser.getConfirmPassword().equals(newUser.getPassword()));
    }

    private void validateEmail(Errors errors, String email) {
        if (StringUtils.isBlank(email)) {
            errors.rejectValue(FIELD_EMAIL, "email.required");
        } else if (isInvalidEmailAddress(email)) {
            errors.rejectValue(FIELD_EMAIL, "email.invalid");
        } else if (isExistingEmailAddress(email)) {
            errors.rejectValue(FIELD_EMAIL, "email.exists");
        }
    }

    private boolean isInvalidEmailAddress(String emailAddress) {
        return !EmailValidator.getInstance().isValid(emailAddress);
    }

    private boolean isExistingEmailAddress(String email) {
        return userService.getUserByEmail(email) != null;
    }

    private void writeResultToLog(Errors errors) {
        if (errors.hasErrors()) {
            if (logger.isInfoEnabled()) {
                for (ObjectError error : errors.getAllErrors()) {
                    logger.info("Validation error: {}", error.toString());
                }
            }
        } else {
            logger.debug("Validation successful");
        }
    }
}