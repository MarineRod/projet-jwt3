package com.diginamic.demo.service;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.diginamic.demo.entity.CustomUserDetails;
import com.diginamic.demo.entity.UserInfo;
import com.diginamic.demo.entity.UserRole;
import com.diginamic.demo.repository.UserInfoRepository;
import com.diginamic.demo.repository.UserRoleRepository;

import jakarta.annotation.PostConstruct;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserInfoRepository userInfoRepository; // Corrected injection for user repository

	@Autowired
	private UserRoleRepository userRoleRepository; // Assuming you need this for roles

	@Autowired
	private PasswordEncoder passwordEncoder; // Fixed incorrect naming for password encoder

	private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

	@PostConstruct
	public void init() {

			UserRole userRole = new UserRole();
			userRole.setName("ROLE_USER");

			UserRole adminRole = new UserRole();
			adminRole.setName("ROLE_ADMIN");

			// Sauvegarder les rôles dans le repository
			userRoleRepository.save(userRole);
			userRoleRepository.save(adminRole);

			// Création d'un utilisateur standard avec le rôle USER
			UserInfo userInfo = new UserInfo();
			userInfo.setUsername("user");
			userInfo.setPassword(passwordEncoder.encode("user")); // Encodage du mot de passe
			userInfo.setRoles(new HashSet<>(Set.of(userRole))); // Assignation du rôle USER

			// Sauvegarde de l'utilisateur dans le repository
			userInfoRepository.save(userInfo);

			// Création d'un administrateur avec le rôle ADMIN
			UserInfo adminInfo = new UserInfo();
			adminInfo.setUsername("admin");
			adminInfo.setPassword(passwordEncoder.encode("admin")); // Encodage du mot de passe
			adminInfo.setRoles(new HashSet<>(Set.of(adminRole))); // Assignation du rôle ADMIN

			// Sauvegarde de l'administrateur dans le repository
			userInfoRepository.save(adminInfo);
		}
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		logger.debug("Entering in loadUserByUsername Method...");
		UserInfo user = userInfoRepository.findByUsername(username);
		if (user == null) {
			logger.error("Username not found: " + username);
			throw new UsernameNotFoundException("could not found user..!!");
		}
		logger.info("User Authenticated Successfully..!!!");
		return new CustomUserDetails(user);
	}
}