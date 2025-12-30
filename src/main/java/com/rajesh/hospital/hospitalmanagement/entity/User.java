package com.rajesh.hospital.hospitalmanagement.entity;

import java.util.Collection;

import org.aspectj.weaver.tools.Trace;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Index;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import com.rajesh.hospital.hospitalmanagement.entity.type.AuthProviderType;
import com.rajesh.hospital.hospitalmanagement.entity.type.Roletype;
import jakarta.persistence.ElementCollection;
import java.util.Set;
import java.util.HashSet;
import jakarta.persistence.FetchType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.stream.Collectors;





@Entity
@Table(name = "tbl_appuser" , indexes = {
	@Index(name = "idx_providerid_authprovidertype", columnList = "providerId, authProviderType")
})
public class User implements UserDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	@Column(unique = true)
	private String userName;
	
	private String password;

	private String providerId;

	@Enumerated(EnumType.STRING)
	private AuthProviderType authProviderType;

	@ElementCollection(fetch = FetchType.EAGER)
	@Enumerated(EnumType.STRING)
	private Set<Roletype> roles = new HashSet<>();

	public Set<Roletype> getRoles() {
		return roles;
	}

	public void setRoles(Set<Roletype> roles) {
		this.roles = roles;
	}

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}


	public AuthProviderType getAuthProviderType() {
		return authProviderType;
	}
	public void setAuthProviderType(AuthProviderType authProviderType) {
		this.authProviderType = authProviderType;
	}




	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public User(Long id, String userName, String password, String providerId, AuthProviderType authProviderType) {
		super();
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.providerId = providerId;
		this.authProviderType = authProviderType;
	}
	
	public User( String userName, String password) {
		super();
	
		this.userName = userName;
		this.password = password;
	}

	public User( String userName, String password, Set<Roletype> roles) {
		super();
	
		this.userName = userName;
		this.password = password;
		this.roles = roles;
	}
	
	public User() {
		super();
		
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		
		return roles.stream().map(role -> 
		new SimpleGrantedAuthority("ROLE_" + role.name())
		).collect(Collectors.toSet());
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.userName;
	}
	
	

}
