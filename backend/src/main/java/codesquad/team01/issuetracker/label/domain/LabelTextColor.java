package codesquad.team01.issuetracker.label.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LabelTextColor {

	BLACK("#000000"),
	WHITE("#FFFFFF");

	private final String hexCode;

	public static LabelTextColor fromTextColorStr(String textColor) {
		if (textColor == null) {
			return BLACK; // 기본값
		}

		try {
			return valueOf(textColor.toUpperCase());
		} catch (IllegalArgumentException e) {
			return BLACK; // 기본값
		}
	}

}
