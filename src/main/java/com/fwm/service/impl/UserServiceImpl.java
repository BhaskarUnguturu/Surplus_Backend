package com.fwm.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fwm.constants.Constants;
import com.fwm.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.fwm.dto.PaginationDto;
import com.fwm.dto.UserFilterWithPaginationDto;
import com.fwm.dto.UserRequestDto;
import com.fwm.mapper.CustomMapper;
import com.fwm.model.User;
import com.fwm.repository.UserRepository;
import com.fwm.repository.custom.UserRepositoryCustom;
import com.fwm.service.IEmailService;
import com.fwm.service.IUserService;
import com.fwm.service.IVerificationTokenService;
import com.fwm.utility.Utility;

@Service("userService")
public class UserServiceImpl implements IUserService, UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private CustomMapper customMapper;

	@Autowired
	private UserRepositoryCustom userRepositoryCustom;

	@Autowired
	private IVerificationTokenService verificationTokenService;

	@Autowired
	private IEmailService emailService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByMobileNumberOrEmail(username, username);
		if (user == null) {
			throw new UsernameNotFoundException(Constants.INVALID_USERNAME_OR_PASSWORD);
		}
		return new org.springframework.security.core.userdetails.User(user.getMobileNumber(), user.getPassword(),
				getAuthority());
	}

	private List<SimpleGrantedAuthority> getAuthority() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
	}

	@Override
	public void addUser(UserRequestDto userRequestDto, ApiResponseDtoBuilder apiResponseDtoBuilder,
			HttpServletRequest request) {
		if (userRepository.existsByMobileNumber(userRequestDto.getMobileNumber())) {
			apiResponseDtoBuilder.withMessage(Constants.MOBILE_NUMBER_ALREADY_EXISTS)
					.withStatus(HttpStatus.ALREADY_REPORTED);
			return;
		}
		if (userRepository.existsByEmail(userRequestDto.getEmail())) {
			apiResponseDtoBuilder.withMessage(Constants.EMAIL_ALREADY_EXISTS).withStatus(HttpStatus.ALREADY_REPORTED);
			return;
		}
		User user = customMapper.userRequestDtoToUser(userRequestDto);
		String newPasswordEncodedString = bCryptPasswordEncoder.encode(user.getPassword());
		user.setPassword(newPasswordEncodedString);
		user.setCreatedAt(new Date());
		save(user);
		apiResponseDtoBuilder.withMessage(Constants.USER_ADD_SUCCESS).withStatus(HttpStatus.OK).withData(user);
		verificationTokenService.sendVerificationToken(user);
	}

	@Override
	public User findByMobileNumber(String username) {
		return userRepository.findByMobileNumber(username);
	}

	@Override
	public User findByMobileNumberOrEmail(String mobileNumber, String email) {
		return userRepository.findByMobileNumberOrEmail(mobileNumber, email);
	}

	@Override
	public void save(User user) {
		userRepository.save(user);
	}

	@Override
	public User findById(Long id) {
		Optional<User> user = userRepository.findById(id);
		return user.isPresent() ? user.get() : null;
	}

	@Override
	@Transactional
	public void deleteUserById(long id, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		Optional<User> user = userRepository.findById(id);
		if (user.isPresent()) {
			userRepository.deleteById(id);
			apiResponseDtoBuilder.withMessage(Constants.USER_DELETED_SUCCESSFULLY).withStatus(HttpStatus.OK);
		} else {
			apiResponseDtoBuilder.withMessage("fail").withStatus(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public User getSessionUser() {
		return Utility.getSessionUser(userRepository);
	}

	@Override
	public User findByMobileNumberOrEmailAndPassword(String username, String username2, String password) {
		return userRepository.findByMobileNumberOrEmailAndPassword(username, username2, password);
	}

	@Override
	public void sendTemporaryPassword(String email, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		User user = findByEmail(email);
		if (user == null) {
			apiResponseDtoBuilder.withMessage(Constants.NO_EMAIL_EXISTS).withStatus(HttpStatus.NOT_FOUND);
			return;
		}
		String password = Utility.generateRandomPassword(8);
		String newPasswordEncodedString = bCryptPasswordEncoder.encode(password);
		user.setPassword(newPasswordEncodedString);
		save(user);
		apiResponseDtoBuilder.withMessage(Constants.SEND_DETAILS_TO_YOUR_EMAIL).withStatus(HttpStatus.OK);
		String subject = "Temporary Password";
		String body = emailService.createEmailBodyForForgotPassword(user.getFullName(), password);
		emailService.sendEmail(user.getEmail(), subject, body, "", null, null);
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public void changePassword(long userId, String oldPassword, String newPassword,
			ApiResponseDtoBuilder apiResponseDtoBuilder) {
		Optional<User> user = userRepository.findById(userId);
		if (user.isPresent()) {
			try {
				if (bCryptPasswordEncoder.matches(oldPassword, user.get().getPassword())) {
					String newPasswordEncodedString = bCryptPasswordEncoder.encode(newPassword);
					user.get().setPassword(newPasswordEncodedString);
					userRepository.save(user.get());
					apiResponseDtoBuilder.withMessage("Chaange Password Successfully ").withStatus(HttpStatus.OK);
				} else {
					apiResponseDtoBuilder.withMessage("Old Password Is Worng ").withStatus(HttpStatus.BAD_REQUEST);
				}

			} catch (Exception e) {
				apiResponseDtoBuilder.withMessage("Exception in Password change : " + e.getMessage())
						.withStatus(HttpStatus.BAD_REQUEST);
			}
		} else {
			apiResponseDtoBuilder.withMessage(Constants.USER_NOT_FOUND).withStatus(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public void getAllUserDetails(ApiResponseDtoBuilder apiResponseDtoBuilder) {
		List<User> userList = userRepository.findAll();
		apiResponseDtoBuilder.withMessage("success").withStatus(HttpStatus.OK).withData(userList);

	}

	@Override
	public void isActiveUser(long id, boolean active, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		Optional<User> user = userRepository.findById(id);
		if (user.isPresent()) {
			user.get().setActive(active);
			save(user.get());
			apiResponseDtoBuilder.withMessage(active ? Constants.USER_ACTIVE_SUCCESS : Constants.USER_DEACTIVE_SUCCESS)
					.withStatus(HttpStatus.OK);
		} else {
			apiResponseDtoBuilder.withMessage(Constants.USER_NOT_FOUND).withStatus(HttpStatus.NOT_FOUND);
		}

	}

	@Override
	public void getUserListByFilterWithPagination(UserFilterWithPaginationDto filterWithPagination,
			ApiResponseDtoBuilder apiResponseDtoBuilder) {
		PaginationDto pagination = userRepositoryCustom.getUserListByFilterWithPagination(filterWithPagination);
		apiResponseDtoBuilder.withMessage(Constants.DATA_LIST).withStatus(HttpStatus.OK).withData(pagination);

	}

	@Override
	public void updateUser(@Valid User user, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		if (!userRepository.existsByMobileNumber(user.getMobileNumber())) {
			apiResponseDtoBuilder.withMessage(Constants.NO_USER_EXISTS).withStatus(HttpStatus.OK).withData(user);
			return;
		}
		String newPasswordEncodedString = bCryptPasswordEncoder.encode(user.getMobileNumber());
		user.setPassword(newPasswordEncodedString);
		user.setUpdatedAt(new Date());
		save(user);
		apiResponseDtoBuilder.withMessage(Constants.USER_UPDATED_SUCCESSFULLY).withStatus(HttpStatus.OK).withData(user);

	}

	@Override
	public void getUserDetailsById(long id, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		Optional<User> user = userRepository.findById(id);
		if (user.isPresent()) {
			apiResponseDtoBuilder.withMessage("success").withStatus(HttpStatus.OK).withData(user);
		} else {
			apiResponseDtoBuilder.withMessage("fail").withStatus(HttpStatus.NOT_FOUND);
		}

	}

	@Override
	public void referFriendByEmail(String email, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		User user = getSessionUser();
		new Thread(() -> {
			String subject = "Refer Friend";
			String body = "Hi " + email.split("@")[0] + " your friend " + user.getFullName()
					+ " has invited to check our Surplus Share application. Please install and try it out.\n\nKind Regards\nTeam Surplus Share";
			emailService.sendEmail(email, subject, body, "", null, null);
		}).start();
		apiResponseDtoBuilder.withMessage("Successfully refered!!").withStatus(HttpStatus.OK);
	}

}