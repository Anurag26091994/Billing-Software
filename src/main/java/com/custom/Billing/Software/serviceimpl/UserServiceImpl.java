package com.custom.Billing.Software.serviceimpl;


import com.custom.Billing.Software.config.JwtUtil;
import com.custom.Billing.Software.dto.LoginRequestDto;
import com.custom.Billing.Software.dto.LoginResponseDto;
import com.custom.Billing.Software.dto.UserRequestDto;
import com.custom.Billing.Software.entity.User;
import com.custom.Billing.Software.exception.BusinessException;
import com.custom.Billing.Software.repository.UserRepository;
import com.custom.Billing.Software.service.UserService;
import com.custom.Billing.Software.util.CommonValidations;
import com.custom.Billing.Software.util.FormLogger;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.exc.UnrecognizedPropertyException;

@Service
public  class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

//    private final EventHandler eventHandler;

    private final ObjectMapper objectMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public UserServiceImpl(UserRepository userRepository, ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
    }


    @Transactional
    @Override
    public String createUser(JsonNode data) {
        FormLogger.info("get user data: "+data);
        try{
//            data.get("firstName").asString();
            UserRequestDto userRequestDto= objectMapper.treeToValue(data, UserRequestDto.class);

            //validate dto
            validateCreateUser(userRequestDto);

            if (userRepository.findByEmailId(userRequestDto.getEmailId()).isPresent()) {
                throw new BusinessException(
                        "User already exists with emailId: " + userRequestDto.getEmailId()
                );
            }
            if (userRepository.findByUserName(userRequestDto.getUserName()).isPresent()) {
                throw new BusinessException(
                        "User already exists with userName: " + userRequestDto.getUserName()
                );
            }

            User user= User.builder()
                    .firstName(userRequestDto.getFirstName())
                    .lastName(userRequestDto.getLastName())
                    .userName(userRequestDto.getUserName())
                    .password(passwordEncoder.encode(userRequestDto.getPassword()))
                    .emailId(userRequestDto.getEmailId())
                    .mobileNumber(userRequestDto.getMobileNumber())
                    .address(userRequestDto.getAddress())
                    .role(userRequestDto.getRole())
                    .build();

            String token = jwtUtil.generateToken(user.getUserName(), user.getRole());

            userRepository.save(user);

            FormLogger.info("UserService: User created successfully");
            return "User created successfully"+objectMapper.valueToTree(
                    new LoginResponseDto(token, user.getRole())
            ).toString();

        }
        catch (IllegalArgumentException e) {
            FormLogger.warn("UserService: Validation failed - " , e);
            return  e.getMessage();

        }
        catch (Exception e) {
            FormLogger.error("UserService: Error creating user", e);
//            throw new RuntimeException("Failed to create user"+e.getMessage());
            return "Error: "+e.getMessage();
        }
    }

    @Override
    @Transactional
    public String login(JsonNode data) {

        LoginRequestDto loginRequest;

        try {
            loginRequest = objectMapper.treeToValue(data, LoginRequestDto.class);
        } catch (UnrecognizedPropertyException e) {
            throw new BusinessException("Extra fields are not allowed in LOGIN request");
        } catch (Exception e) {
            throw new BusinessException("Invalid LOGIN request");
        }

        User user = (User) userRepository.findByUserName(loginRequest.getUserName())
                .orElseThrow(() -> new BusinessException("Invalid username or password"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new BusinessException("Invalid username or password");
        }

        String token = jwtUtil.generateToken(user.getUserName(), user.getRole());

        return objectMapper.valueToTree(
                new LoginResponseDto(token, user.getRole())
        ).toString();
    }


    @Override
    public String getUserInfo(JsonNode data) {
        FormLogger.info("UserServiceImpl : get User data  " + data);
        try {
            //Convert Json data to Object
            LoginRequestDto loginRequestDto = objectMapper.treeToValue(data, LoginRequestDto.class);

            //validate object
            if (CommonValidations.isNullOrEmpty(loginRequestDto.getUserName())) {
                throw new BusinessException("Username is required");
            }
            if (CommonValidations.isNullOrEmpty(loginRequestDto.getPassword())) {
                throw new BusinessException("Password is required");
            }

            //check username and password from DB

            User user = (User) userRepository.findByUserName(loginRequestDto.getUserName())
                    .orElseThrow(() -> new BusinessException("Invalid username "));

            if (!passwordEncoder.matches(
                    loginRequestDto.getPassword(),
                    user.getPassword())) {
                throw new BusinessException("Invalid username or password");
            }

            FormLogger.info("User logged in successfully: " + user.getUserName());

            return "Login successful"+user;

        } catch (BusinessException e) {
            FormLogger.warn("Check User's username and password correctly ", e);
            return e.getMessage();
        }


        catch (Exception e) {
            FormLogger.error("Login failed", e);
            throw new RuntimeException("Login failed");
        }

    }

    @Override
    public String getUserDetailsByEmail(JsonNode data) {
        return "";
    }

    @Override
    public String deleteUser(JsonNode data) {
        FormLogger.info("get username gor delete data of user"+data.get("userName").asString());
        try{
            UserRequestDto userRequestDto =objectMapper.treeToValue(data,UserRequestDto.class);

            //validate object
            if (CommonValidations.isNullOrEmpty(userRequestDto.getUserName())) {
                throw new BusinessException("Username is required");
            }
            if (CommonValidations.isNullOrEmpty(userRequestDto.getPassword())) {
                throw new BusinessException("Password is required");
            }

           User user = (User) userRepository.findByUserName(userRequestDto.getUserName()).orElseThrow(()->new BusinessException("User for "+userRequestDto.getUserName()+" is not found"));

            userRepository.delete(user);
            return "User data deleted successfully";

        }
        catch (JacksonException e){
          FormLogger.warn("Invalid json get");
          return e.getMessage();
        }

    }

    @Override
    public String updateUser(JsonNode payload) {
        FormLogger.info("get user data for update "+payload);
        try {
           UserRequestDto userRequestDto =objectMapper.treeToValue(payload, UserRequestDto.class);

           FormLogger.info("data into userRequestDto for validation"+userRequestDto);
            //validate object
            if (CommonValidations.isNullOrEmpty(userRequestDto.getUserName())) {
                throw new BusinessException("Username is required");
            }
//            if (CommonValidations.isNullOrEmpty(userRequestDto.getPassword())) {
//                throw new BusinessException("Password is required");
//            }

//            if (CommonValidations.isValidEmail(userRequestDto.getEmailId())){
//                throw new BusinessException("Email format wrong");
//            }
//            if (CommonValidations.isValidName(userRequestDto.getFirstName()) && CommonValidations.isValidName(userRequestDto.getLastName())){
//                throw new BusinessException("write valid firstname or lastname");
//            }
//            if (CommonValidations.isValidPhoneNumber(Long.valueOf(userRequestDto.getMobileNumber()))){
//                throw new BusinessException("Enter 10 digit mobile number");
//            }
//            if (CommonValidations.isNullOrEmpty(userRequestDto.getAddress())){
//                throw new BusinessException("Please enter valid address");
//            }

           User user = (User) userRepository.findByUserName(userRequestDto.getUserName()).orElseThrow(()->new BusinessException("Username not found"));

           user.setAddress(userRequestDto.getAddress());
           user.setEmailId(userRequestDto.getEmailId());
           user.setFirstName(userRequestDto.getFirstName());
           user.setLastName(userRequestDto.getLastName());
           user.setMobileNumber(userRequestDto.getMobileNumber());

           FormLogger.info("update data"+user);
           userRepository.save(user);
           return "update data successfully";
        }
        catch (RuntimeException e){

            FormLogger.warn("UserServiceImpl: update user data failed: "+e.getMessage());
            return e.getMessage();
        }

    }

    private void validateCreateUser( UserRequestDto dto) {

        if (CommonValidations.isNullOrEmpty(dto.getFirstName())
                || !CommonValidations.isValidName(dto.getFirstName())) {
            throw new IllegalArgumentException("Invalid first name");
        }

        if (CommonValidations.isNullOrEmpty(dto.getLastName())
                || !CommonValidations.isValidName(dto.getLastName())) {
            throw new IllegalArgumentException("Invalid last name");
        }

        if (CommonValidations.isNullOrEmpty(dto.getEmailId())
                || !CommonValidations.isValidEmail(dto.getEmailId())) {
            throw new IllegalArgumentException("Invalid email address");
        }

        if (CommonValidations.isNullOrEmpty(dto.getMobileNumber())
                || !CommonValidations.isNumeric(dto.getMobileNumber())) {
            throw new IllegalArgumentException("Invalid mobile number");
        }
    }

}
