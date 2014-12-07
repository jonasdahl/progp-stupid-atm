import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * A class that keeps the current language in mind, and translates if we want
 * to.
 * 
 * @author Jonas Dahl & Nick Nyman
 * @version 1.0
 * @date 2014-11-28
 */
public class Language {
	/** The language code used right now. */
	private String languageCode;
	/** The dictionary where we store the language files. */
	private HashMap<String, String> dictionary;

	/**
	 * Creates a new language file with "enUS" as the default language.
	 * 
	 * @throws IOException
	 *             if there is an error opening the language file.
	 */
	public Language() throws IOException {
		this("enUS");
	}

	/**
	 * Constructor for class Language. Sets current language to given parameter
	 * and loads dictionary.
	 * 
	 * @param languageCode
	 *            wanted language code
	 * @prints nothing
	 * @throws IOException
	 *             if language was not found
	 */
	public Language(String languageCode) throws IOException {
		this.languageCode = languageCode;
		update();
	}

	/**
	 * Reads the language file and updates dictionary thereafter.
	 * 
	 * @throws IOException
	 *             if language file was not found.
	 */
	public void update() throws IOException {
		String fileText = "";
		BufferedReader br = new BufferedReader(new FileReader(languageCode
				+ ".lang"));
		StringBuilder sb = new StringBuilder();
		String line = br.readLine();

		while (line != null) {
			sb.append(line);
			sb.append("\n");
			line = br.readLine();
		}
		fileText = sb.toString();
		br.close();

		this.dictionary = parse(fileText);
	}

	/**
	 * Parses input file and returns hashmap of translations.
	 * 
	 * @param file
	 *            the input file as a string (the content). Formatted on form
	 *            englishWord | otherLangWord new English Word |other Lang Words
	 * @return a hashmap of translations between english (key) and the language.
	 */
	public HashMap<String, String> parse(String file) {
		HashMap<String, String> res = new HashMap<String, String>();
		String[] parts = file.split("\n");
		for (String part : parts) {
			String[] langs = part.split(C.LANGUAGE_FILE_SEPARATOR);
			if (langs.length != 2) {
				throw new IllegalArgumentException();
			} else {
				res.put(langs[0].trim(), langs[1].trim());
			}
		}
		return res;
	}

	/**
	 * Returns the actual translation for given input.
	 * 
	 * @param input
	 *            the word to be translated
	 * @return the input in actual language, or the input itself if nothing was
	 *         found
	 * @prints nothing
	 */
	public String t(String input) {
		String ret = dictionary.get(input);
		if (ret == null)
			ret = input;
		return ret.replaceAll("\\\\n", "\n");
	}

	/**
	 * Returns the actual translation for given error input.
	 * 
	 * @param input
	 *            the error to be translated to text
	 * @return the input word in actual language or just the ordinary error
	 *         message if no likvärdigt error could be found
	 * @prints nothing
	 */
	public String e(String error) {
		switch (error) {
		case C.ERROR_AUTHENTICATION:
			return t("auth_error");
		case C.ERROR_INACTIVE:
			return t("inactive_error");
		case C.ERROR_LANGUAGE:
			return t("lang_error");
		case C.ERROR_CODE:
			return t("code_error");
		case C.ERROR_BROKE:
			return t("broke_error");
		case C.ERROR_NEGATIVE:
			return t("negative_error");
		}
		return t("error");
	}
}