package com.dominantfreq.display.model.styles;

public enum ButtonStyles {
	PROCESS_BUTTON_STYLE(
			"-fx-background-color: linear-gradient(#f0ff35, #a9ff00), " +
					" radial-gradient(center 50% -40%, radius 200%, #b8ee36 45%, #80c800 50%); " +
			"-fx-text-fill: black;"
	),
	CLOSE_BUTTON_STYLE(
			"-fx-background-color: linear-gradient(#ff5400, #be1d00); " +
			"-fx-text-fill: white;"
	), 
	SAVE_BUTTON_STYLE(
			"-fx-background-color: #ecebe9," +
					"rgba(0,0,0,0.05)," +
					"linear-gradient(#dcca8a, #c7a740), " +
					"linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%), " +
					"linear-gradient(#f6ebbe, #e6c34d); " +
			"-fx-font-family: \"Helvetica\"; "+
			"-fx-text-fill: #311c09;"
	);

	private final String value;

	ButtonStyles(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return value;
	}
}
