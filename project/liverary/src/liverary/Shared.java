package liverary;

import java.util.HashMap;

import javafx.scene.Parent;
import javafx.stage.Stage;
import liverary.view.LayoutsEnum;
import liverary.vo.AccountVO;

public class Shared {
	private static Stage stage;
	private static HashMap<LayoutsEnum, Parent> layoutMap = new HashMap<LayoutsEnum, Parent>();
	private static AccountVO currentSession;

	public static Stage getStage() {
		return stage;
	}

	public static void setStage(Stage stage) {
		Shared.stage = stage;
	}

	public static HashMap<LayoutsEnum, Parent> getLayoutMap() {
		return layoutMap;
	}

	public static void setLayoutMap(HashMap<LayoutsEnum, Parent> layoutMap) {
		Shared.layoutMap = layoutMap;
	}

	public static AccountVO getCurrentSession() {
		return currentSession;
	}

	public static void setCurrentSession(AccountVO currentSession) {
		Shared.currentSession = currentSession;
	}
}
