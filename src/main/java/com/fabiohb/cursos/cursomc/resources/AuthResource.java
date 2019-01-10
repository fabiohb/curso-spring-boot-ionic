package com.fabiohb.cursos.cursomc.resources;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.cglib.core.EmitUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fabiohb.cursos.cursomc.dto.EmailDTO;
import com.fabiohb.cursos.cursomc.security.JWTUtil;
import com.fabiohb.cursos.cursomc.security.UserSS;
import com.fabiohb.cursos.cursomc.services.AuthService;
import com.fabiohb.cursos.cursomc.services.UserService;

@RestController
@RequestMapping("/auth")
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class AuthResource {

	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private AuthService authService;

	@PostMapping(value = "/refresh_token")
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		UserSS user = UserService.authenticated();
		String token = jwtUtil.generateToken(user.getUsername());
		response.addHeader("Authorization", "Bearer " + token);
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping(value = "/forgot")
	public ResponseEntity<Void> forgot(@Valid @RequestBody EmailDTO emailDTO) {
		authService.senNewPassword(emailDTO.getEmail());
		return ResponseEntity.noContent().build();
	}
	
}
