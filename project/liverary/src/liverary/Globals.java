package liverary;

import liverary.vo.AccountVO;

public class Globals {
	private static AccountVO currentSession;
	
	public Globals() {
	}

	public static AccountVO getCurrentSession() {
		return currentSession;
	}

	public static void setCurrentSession(AccountVO currentSession) {
		Globals.currentSession = currentSession;
	}
}
