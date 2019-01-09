package com.fabiohb.cursos.cursomc.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fabiohb.cursos.cursomc.dto.CredenciaisDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JWTAutheticationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager manager;

	private JWTUtil jwtUtil;

	public JWTAutheticationFilter(AuthenticationManager manager, JWTUtil jwtUtil) {
		super();
		// setAuthenticationFailureHandler(new JWTAuthenticationFailureHandler());
		this.manager = manager;
		this.jwtUtil = jwtUtil;
		
		setAuthenticationSuccessHandler(
			(request, response, authentication) -> {
				String username = ((UserSS) authentication.getPrincipal()).getUsername();
		        String token = this.jwtUtil.generateToken(username);
		        response.addHeader("Authorization", "Bearer " + token);
			}
		);
		
		setAuthenticationFailureHandler(
			(request, response, exception) -> {
				response.setStatus(401);
				response.setContentType("application/json");
				PrintWriter out = response.getWriter();
				out.append("{\"timestamp\": " + System.currentTimeMillis() + ", ");
				out.append("\"status\": 401, ");
				out.append("\"error\": \"Não autorizado\", ");
				out.append("\"message\": \"Email ou senha inválidos\", ");
				out.append("\"path\": \"/login\"}");
			}
		);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			CredenciaisDTO creds = new ObjectMapper().readValue(request.getInputStream(), CredenciaisDTO.class);
	
	        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
	        		creds.getEmail(), creds.getSenha(), new ArrayList<>());
	        
	        Authentication auth = manager.authenticate(authToken);
	        
	        return auth;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	/*-
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		String username = ((UserSS) authResult.getPrincipal()).getUsername();
        String token = jwtUtil.generateToken(username);
        response.addHeader("Authorization", "Bearer " + token);
	}
	
	private class JWTAuthenticationFailureHandler implements AuthenticationFailureHandler {

		@Override
		public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
				AuthenticationException exception) throws IOException, ServletException {
			response.setStatus(401);
			response.setContentType("application/json");
			response.getWriter().append(json());
		}

		private String json() {
			return "{\"timestamp\": " + System.currentTimeMillis() + ", " 
					+ "\"status\": 401, " 
					+ "\"error\": \"Não autorizado\", "
					+ "\"message\": \"Email ou senha inválidos\", " 
					+ "\"path\": \"/login\"}";
		}
	}
	*/
}
