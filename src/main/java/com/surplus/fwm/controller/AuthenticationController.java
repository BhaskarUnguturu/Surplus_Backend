package com.surplus.fwm.controller;

import java.util.HashMap;
import java.util.Map;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.surplus.fwm.config.JwtTokenUtil;
import com.surplus.fwm.constants.AuthorizationConstants;
import com.surplus.fwm.constants.Constants;
import com.surplus.fwm.dto.ApiResponseDto;
import com.surplus.fwm.dto.LoginUser;
import com.surplus.fwm.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.surplus.fwm.model.User;
import com.surplus.fwm.service.IUserService;

@CrossOrigin(origins = "*", maxAge = 360000000)
@RestController
@RequestMapping(Constants.API_BASE_URL + "/auth")
public class AuthenticationController {

	private static final String TOKEN = "token";
	private static final String USER = "user";
	private static final String LOGINEDUSER = "user_details";

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private IUserService userService;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ApiResponseDto userlogin(@RequestBody LoginUser loginUser) throws AuthenticationException {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		User checkUser = userService.findByMobileNumberOrEmail(loginUser.getUsername(), loginUser.getUsername());
		// 0-superadmin,1-university,2-restaurant,3-individual,4-NGO,5-Recipient_individual,6-Recipient_organization
		if (checkUser == null) {
			apiResponseDtoBuilder.withStatus(HttpStatus.UNAUTHORIZED).withMessage(Constants.NO_Mobile_EXISTS);
			return apiResponseDtoBuilder.build();
		}
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword()));
		final UserDetails user = userDetailsService.loadUserByUsername(loginUser.getUsername());
		final String token = jwtTokenUtil.generateToken(user);
		Map<String, Object> response = setTokenDetails(user, token, checkUser);
		apiResponseDtoBuilder.withStatus(HttpStatus.OK).withMessage(AuthorizationConstants.LOGIN_SUCESSFULL)
				.withData(response);
		return apiResponseDtoBuilder.build();
	}

	private Map<String, Object> setTokenDetails(final UserDetails user, final String token, final User userDetails) {
		Map<String, Object> response = new HashMap<>();
		response.put(USER, user);
		response.put(TOKEN, token);
		response.put(LOGINEDUSER, userDetails);
		return response;
	}
}
