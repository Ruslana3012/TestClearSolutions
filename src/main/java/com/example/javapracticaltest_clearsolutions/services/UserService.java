package com.example.javapracticaltest_clearsolutions.services;

import com.example.javapracticaltest_clearsolutions.models.User;
import com.example.javapracticaltest_clearsolutions.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserService {
    @Value("${minimum.age}")
    private int minAge;
    private final UserRepository userRepository;

    public User createUser(User user) {
        if (!checkThatUserHasMinimumAge(user)) throw new IllegalArgumentException("It allows to register users who are more than 18 years old!");
        if (!isValidEmail(user)) throw new IllegalArgumentException("Email is not valid!");
        return userRepository.save(user);
    }

    public User updateUserFields(String email, User user) {
        User newUser = userRepository.findByEmail(email);
        if (user.getEmail() != null) {
            if (!isValidEmail(user)) throw new IllegalArgumentException("Email is not valid");
            newUser.setEmail(user.getEmail());
        }
        if (user.getFirstName() != null) newUser.setFirstName(user.getFirstName());
        if (user.getLastName() != null) newUser.setLastName(user.getLastName());
        if (user.getBirthDate() != null) newUser.setBirthDate(user.getBirthDate());
        if (user.getAddress() != null) newUser.setAddress(user.getAddress());
        if (user.getPhoneNumber() != null) newUser.setPhoneNumber(user.getPhoneNumber());

        return userRepository.save(newUser);
    }

    public void deleteUser(String email) {
        userRepository.removeUserByEmail(email);
    }

    public List<User> findUsersByBirthDate(String from, String to) throws ParseException {
        if (format(from).compareTo(format(to)) <= 0)
            return userRepository.findUsersByBirthDateBetween(format(from), format(to));
        else throw new IllegalArgumentException("Date FROM is less than Date TO. Please check!");

    }

    private boolean isValidEmail(User user) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(user.getEmail()).matches();
    }

    private Date format(String date) throws ParseException {
        return new SimpleDateFormat("dd.MM.yyyy").parse(date);
    }

    private boolean checkThatUserHasMinimumAge(User user) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(user.getBirthDate());
        int age = Year.now().getValue() - calendar.get(Calendar.YEAR);
        return age > minAge;
    }
}
