package customs;

public class EscapeString {

	public static String Escape(String input) {
		
		if (input == null) {
            return null;
        }
		else {
			input = input.replaceAll("[_!@#$%^&*()-=+~.;:,\\\\Q[\\\\E\\\\Q]\\\\E<>{}\\\\/?]|","");
			return input;
		}
		
		
	}
	public static String EscapePassword(String input) {
		if (input == null) {
            return null;
        }
		else {
			input = input.replaceAll("[^()-=+~.;:,\\\\Q[\\\\E\\\\Q]\\\\E<>{}\\\\/?]|","");
			return input;
		}
	}
}
