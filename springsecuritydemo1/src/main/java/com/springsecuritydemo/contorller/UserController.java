package com.springsecuritydemo.contorller;

import java.security.Principal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springsecuritydemo.commons.ApplicationConstants;
import com.springsecuritydemo.entity.Role;
import com.springsecuritydemo.entity.User;
import com.springsecuritydemo.entity.UserRole;
import com.springsecuritydemo.repository.RoleRepository;
import com.springsecuritydemo.repository.UserRepository;
import com.springsecuritydemo.repository.UserRoleRepository;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepository roleRepo;

	@Autowired
	private UserRoleRepository userRoleRepo;

	@GetMapping("/welcome")
	public List<User> indexPage() {
		List<User> users = userRepo.findAll();
		users.forEach(singleUser ->{
			System.out.println(singleUser.getUserRoles());
		});
		return users;
	}

	@PostMapping("/joinGroup")
	public String joinGroup(@RequestBody User user) {
		user.setProfilePic("/default.png");
		user.setEnabled(true);
		String encryptedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encryptedPassword);
		user = userRepo.save(user);

		Role role = roleRepo.findByRolename(ApplicationConstants.RoleConstants.ROLE_USER);
		UserRole userRole = new UserRole();
		userRole.setRole(role);
		userRole.setUser(user);
		userRoleRepo.save(userRole);
		return "Welcome to the Group " + user.getFirstName();
	}

	@GetMapping("/grantAccess/{userId}/{grantRoleOf}")
	public String grantAccess(@PathVariable("userId") Long userId, @PathVariable("grantRoleOf") String grantRoleOf,
			Principal pricipal) {
		return null;
	}

	// only user can access this
	@GetMapping("/")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public List<User> getAllUsers() {
		return userRepo.findAll();
	}

	// only User can access
	@GetMapping("/onlyuser")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public String testUserAccess() {
		return "Only User can Access this...!";
	}

	// only moderator can access
	@GetMapping("/onlymoderator")
	@PreAuthorize("hasAuthority('ROLE_MODERATOR')")
	public String testModeratorAccess() {
		System.out.println("In Moderator");
		return "Only Moderator can Access this...!";
	}
	
	@PostMapping("/addModerator")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String addNewModerator(@RequestBody User user) {
		user.setProfilePic("/default.png");
		user.setEnabled(true);
		String encryptedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encryptedPassword);
		user = userRepo.save(user);

		Role role = roleRepo.findByRolename(ApplicationConstants.RoleConstants.ROLE_MODERATOR);
		UserRole userRole = new UserRole();
		userRole.setRole(role);
		userRole.setUser(user);
		userRoleRepo.save(userRole);
		return "Welcome to the Group " + user.getFirstName();
	}

	/*
	 * private List<String> getLoggedInUserRoles(Principal principal) { List<Role>
	 * roles = null;
	 * getCurrentLoggedInUser(principal).getUserRoles().forEach(userRole ->{
	 * roles.add(userRole.getRole()); });;
	 * 
	 * for(Role role : roles) {
	 * if(role.getrolename().equals(ApplicationConstants.RoleConstants.ROLE_ADMIN))
	 * { return
	 * Arrays.stream(ApplicationConstants.GrantAccessConstants.ADMIN_GRANT_ACCESS).
	 * collect(Collectors.toList()); }
	 * 
	 * if(role.getrolename().equals(ApplicationConstants.RoleConstants.
	 * ROLE_MODERATOR)) { return
	 * Arrays.stream(ApplicationConstants.GrantAccessConstants.
	 * MODERATOR_GRANT_ACCESS).collect(Collectors.toList()); } } return
	 * Collections.emptyList(); }
	 * 
	 * private User getCurrentLoggedInUser(Principal principal) { return
	 * userRepo.findByUsername(principal.getName()).get(); }
	 */
}
