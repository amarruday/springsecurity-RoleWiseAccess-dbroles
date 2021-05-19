package com.springsecuritydemo.commons;

public interface ApplicationConstants {
	
	public interface RoleConstants {
		public static final String ROLE_ADMIN = "ROLE_ADMIN";
		public static final String ROLE_MODERATOR = "ROLE_MODERATOR";
		public static final String ROLE_USER = "ROLE_USER";
	}
	
	public interface GrantAccessConstants {
		public static final String[] ADMIN_GRANT_ACCESS = {ApplicationConstants.RoleConstants.ROLE_ADMIN, ApplicationConstants.RoleConstants.ROLE_MODERATOR};
		public static final String[] MODERATOR_GRANT_ACCESS  =  {ApplicationConstants.RoleConstants.ROLE_MODERATOR};
	}
}
