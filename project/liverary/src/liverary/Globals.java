package liverary;

import liverary.vo.AccountVO;

public class Globals {
	private static AccountVO currentSession;
	
	public Globals() {
	}
	
	@Deprecated
	public static AccountVO getCurrentSession() {
		return currentSession;
	}

	public static void setCurrentSession(AccountVO currentSession) {
		Globals.currentSession = currentSession;
	}
	
	public static int getCurrentSessionNo() {
		return currentSession.getAno();
	}

	public static String getCurrentSessionName() {
		return currentSession.getAname();
	}

	public static String getCurrentSessionDepartment() {
		return currentSession.getAdepartment();
	}
	
	public static String getCurrentSessionBirth() {
		return currentSession.getAbirth();
	}

	public static String getCurrentSessionCreatedAt() {
		return currentSession.getAcreatedAt();
	}

	public static String getCurrentSessionPhone() {
		return currentSession.getAphone();
	}
	
	public static String getCurrentSessionEmail() {
		return currentSession.getAemail();
	}

	public static String getCurrentSessionAddr() {
		return currentSession.getAaddr();
	}

	public static int getCurrentSessionPoint() {
		return currentSession.getApoint();
	}

	public static int getCurrentSessionLevel() {
		return currentSession.getAlevel();
	}

	public static String getCurrentSessionUsername() {
		return currentSession.getAusername();
	}
}
