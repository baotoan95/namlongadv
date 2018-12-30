package net.namlongadv.constant;

public class Constants {
	private Constants( ) {
		// Prevent create this instance
	}
	
	public static final String ADV_PAGE_REDIRECT = "redirect:/adv/view?page=0&size=";
	public static final String ADV_SEARCH_REDIRECT = "redirect:/adv/search?";
	public static final String EMPTY = "";
	public static final String INVALID_USER_SESSION = "Invalid user session";
	public static final String SPACE = " ";
	
	public static final class USER_ROLE {
		private USER_ROLE() {}
		
		public static final String ADMIN = "ROLE_ADMIN";
		public static final String BUSINESS = "ROLE_BUSINESS";
		public static final String ACCOUNTANT = "ROLE_ACCOUNTANT";
		public static final String SURVEYOR = "ROLE_SURVEYOR";
	}
	
	public static final class SESSION_NAME {
		private SESSION_NAME() {}
		
		public static final String PAGE_SIZE = "pageSize";
		public static final String CURRENT_PAGE = "currentPage";
		public static final String SEARCH_CONTENT = "searchContent";
	}
	
	public static final class MODEL_NAME {
		private MODEL_NAME() {}
		
		public static final String PAGE_CONTENT = "pageContent";
		public static final String PROVINCES = "provinces";
		public static final String ADV_DTO = "advertDto";
		public static final String ERROR_MESSAGE = "errorMsg";
		public static final String HISTORY = "history";
	}
	
	public static final class ACTION {
		private ACTION() {}
		
		public static final String ADD = "add";
		public static final String UPDATE = "update";
		public static final String DELETE = "delete";
	}
}
