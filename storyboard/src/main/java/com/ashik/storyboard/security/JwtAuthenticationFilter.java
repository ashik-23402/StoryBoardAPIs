package com.ashik.storyboard.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JWTtokenHelper jwTtokenHelper;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		//GET TOKEN
		String requestToken= request.getHeader("Authorization");
		
		//bearer 23565 
		
		System.out.println(requestToken);
		
		String userName = null;
		String token = null;
		
		if(requestToken != null && requestToken.startsWith("Bearer")) {
			
			token =requestToken.substring(7);
			
			try {
				userName =this.jwTtokenHelper.getUserNamefromToken(token);
			} catch (IllegalArgumentException e) {
				// TODO: handle exception
				
				System.out.println("unable to get token");
			}
			catch (ExpiredJwtException e) {
				// TODO: handle exception
				System.out.println("jwt token has expired");
			}
			catch (MalformedJwtException e) {
				// TODO: handle exception
				System.out.println("invalid jwt");
			}
			
			
			
		}else {
			System.out.println("jwt doesnt start with Bearer");
		}
		
		
		if(userName != null && SecurityContextHolder.getContext().getAuthentication()==null) {
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);
			if(this.jwTtokenHelper.validateToken(token, userDetails)) {
				//authenticate korte hbe
				
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=
						new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
				
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().
						buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				
				
			}else {
				System.out.println("invalid token");
			}
			
			
		}else {
			System.out.println("username null");
		}
		
		filterChain.doFilter(request, response);
		
	}

}
