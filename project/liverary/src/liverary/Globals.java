package liverary;

import liverary.vo.AccountVO;

public class Globals {
	private static AccountVO currentSession;
	private static int pointPlusAmount;
	private static int pointMinusAmount;
	private static int loanRestrictPoint;
	private static int maxLoanBooksAmount;
	
	public Globals() {
	}
	
	public static int getPointPlusAmount() {
		return pointPlusAmount;
	}

	public static void setPointPlusAmount(int pointPlusAmount) {
		Globals.pointPlusAmount = pointPlusAmount;
	}

	public static int getPointMinusAmount() {
		return pointMinusAmount;
	}

	public static void setPointMinusAmount(int pointMinusAmount) {
		Globals.pointMinusAmount = pointMinusAmount;
	}

	public static int getLoanRestrictPoint() {
		return loanRestrictPoint;
	}

	public static void setLoanRestrictPoint(int loanRestrictPoint) {
		Globals.loanRestrictPoint = loanRestrictPoint;
	}

	public static int getMaxLoanBooksAmount() {
		return maxLoanBooksAmount;
	}

	public static void setMaxLoanBooksAmount(int maxLoanBooksAmount) {
		Globals.maxLoanBooksAmount = maxLoanBooksAmount;
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
	
	public static String getCurrentSessionPassword() {
		return currentSession.getApassword();
	}
}
