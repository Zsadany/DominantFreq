package com.dominantfreq.display.model.styles;

public enum Style {
	PROCESS_BUTTON_STYLE(
			"-fx-background-color: linear-gradient(#f0ff35, #a9ff00), radial-gradient(center 50% -40%, radius 200%, #b8ee36 45%, #80c800 50%); -fx-text-fill: black;"
	),
	CLOSE_BUTTON_STYLE(
			"-fx-background-color: linear-gradient(#ff5400, #be1d00); -fx-text-fill: white;"
	), 
	SAVE_BUTTON_STYLE(
			""
	);

	private final String value;

	Style(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return value;
	}
}
