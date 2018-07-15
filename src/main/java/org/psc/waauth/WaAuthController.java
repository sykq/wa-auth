package org.psc.waauth;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.psc.waauth.domain.ConversionConfiguration;
import org.psc.waauth.user.UserPrincipal;
import org.psc.waauth.user.UserRegistrationBean;
import org.psc.waauth.user.UserRepository;
import org.psc.waauth.user.domain.UserCredentials;
import org.psc.waauth.user.domain.UserRole;
import org.psc.waauth.user.domain.UserRoleId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WaAuthController {
	private static final Logger LOGGER = LoggerFactory.getLogger(WaAuthController.class);

	@Autowired
	private UserRepository userRepository;

	@RequestMapping(value = { "/", "/home" })
	public String welcome(Map<String, Object> model) {
		return "home";
	}

	@RequestMapping(value = "/create-account", method = RequestMethod.GET)
	public ModelAndView createAccountGet() {
		ModelAndView modelAndView = new ModelAndView();

		return modelAndView;
	}

	@RequestMapping(value = "/home", params = { "create-account" }, method = RequestMethod.POST)
	public ModelAndView createAccountPost() {
		ModelAndView modelAndView = new ModelAndView();

		return modelAndView;
	}

	@RequestMapping(value = "/genericcontract2formsheet", method = RequestMethod.GET)
	public ModelAndView getGenericContractToFormSheet(@ModelAttribute ConversionConfiguration config,
			BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();

		modelAndView.addObject("conversionConfiguration", config);

		return modelAndView;
	}

	@RequestMapping(value = "/genericcontract2formsheet", params = { "toFormSheet" }, method = RequestMethod.POST)
	public ModelAndView postGenericContractToFormSheet(@ModelAttribute ConversionConfiguration config,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new RuntimeException("fuck...");
		}

		ModelAndView modelAndView = new ModelAndView();

		modelAndView.addObject("conversionConfiguration", config);
		LOGGER.info("GenericContract to FormSheet XML : {} -> {}", config.getGenericContract(),
				config.getFormSheetXml());

		return modelAndView;
	}

	@RequestMapping(value = "/genericcontract2formsheet", params = { "toGenericContract" }, method = RequestMethod.POST)
	public ModelAndView postFormSheetToGenericContract(@ModelAttribute ConversionConfiguration config,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new RuntimeException("fuck...");
		}

		ModelAndView modelAndView = new ModelAndView();

		modelAndView.addObject("conversionConfiguration", config);
		LOGGER.info("FormSheet to GenericContract XML : {} -> {}", config.getFormSheetXml(),
				config.getGenericContract());

		return modelAndView;
	}

	@RequestMapping(value = "/genericcontract2formsheet", params = { "downloadFormSheet" }, method = RequestMethod.POST)
	public @ResponseBody Resource downloadFormSheet(@ModelAttribute ConversionConfiguration config,
			HttpServletResponse response) throws IOException {
		String fileName = new StringBuilder().append("temp_").append(String.valueOf(System.nanoTime())).append(".xml")
				.toString();

		File formSheetAsFile = new File(fileName);
		FileOutputStream fos = new FileOutputStream(formSheetAsFile);
		fos.write(config.getFormSheetXml().getBytes(StandardCharsets.UTF_8));
		fos.flush();
		fos.close();

		response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
		response.setHeader("Content-Disposition", "attachment; filename=" + formSheetAsFile.getName());
		response.setHeader("Content-Length", String.valueOf(formSheetAsFile.length()));

		return new FileSystemResource(formSheetAsFile);
	}

	@RequestMapping(value = "/create-account", method = RequestMethod.POST)
	public ModelAndView createAccount(
			@ModelAttribute("userRegistrationBean") UserRegistrationBean userRegistrationBean) {
		ModelAndView modelAndView = new ModelAndView();

		UserCredentials userCredentials = new UserCredentials();
		userCredentials.setHash(encodePassword(userRegistrationBean.getPassword()));
		userCredentials.setLoginname(userRegistrationBean.getAccountName());

		UserRoleId userRoleId = new UserRoleId();
		userRoleId.setRole("ROLE_USER");

		UserRole userRole = new UserRole();
		userRole.setUserRoleId(userRoleId);

		List<UserRole> userRoles = new ArrayList<>();
		userRoles.add(userRole);

		org.psc.waauth.user.domain.User user = new org.psc.waauth.user.domain.User();
		user.setUserCredentials(userCredentials);
		user.setUsername(userRegistrationBean.getUserName());
		user.setUserRoles(userRoles);

		userRepository.save(user);

		user = userRepository.findByUsername(userRegistrationBean.getUserName()).orElseGet(null);

		UserPrincipal userPrincipal = new UserPrincipal(user);

		Authentication authentication = new UsernamePasswordAuthenticationToken(user,
				SecurityContextHolder.getContext().getAuthentication().getCredentials(),
				userPrincipal.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);

		/*
		 * List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		 * authorities.add(new SimpleGrantedAuthority("ROLE_USER")); UserDetails user =
		 * User.builder().username(userRegistrationBean.getAccountName())
		 * .password(encodePassword(userRegistrationBean.getPassword())).roles(
		 * "USER").build(); Authentication authentication = new
		 * UsernamePasswordAuthenticationToken(user,
		 * SecurityContextHolder.getContext().getAuthentication().getCredentials (),
		 * authorities);
		 */

		return modelAndView;
	}

	private String encodePassword(byte[] password) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(new String(password, StandardCharsets.UTF_8));
	}
}
