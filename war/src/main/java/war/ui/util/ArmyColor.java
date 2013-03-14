package war.ui.util;

public enum ArmyColor {

	BLACK("black", "Preto"), BLUE("blue", "Azul"), GREEN("green", "Verde"), RED(
			"red", "Vermelho"), WHITE("white", "Branco"), YELLOW("yellow",
			"Amarelo");

	private String colorName = null;
	private String screenName = null;

	private ArmyColor(String colorName, String screenName) {
		this.colorName = colorName;
		this.screenName = screenName;
	}

	public String getName() {
		return this.colorName;
	}

	public String getScreenName() {
		return this.screenName;
	}

	public static ArmyColor colorByName(String colorName) {
		for (ArmyColor color : ArmyColor.values()) {
			if (color.getName().equalsIgnoreCase(colorName)) {
				return color;
			}
		}

		return null;
	}
}
