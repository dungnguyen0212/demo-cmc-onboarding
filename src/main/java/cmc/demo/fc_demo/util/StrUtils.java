package cmc.demo.fc_demo.util;

import com.google.common.base.CaseFormat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.text.Normalizer;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
public class StrUtils {

	public static String normalize(String s) {
		return Normalizer.normalize(s, Normalizer.Form.NFC);
	}

	public static String convertCaseFormat(String s, CaseFormat fromCaseFormat, CaseFormat toCaseFormat) {
		try {
			return fromCaseFormat.to(toCaseFormat, s);
		} catch (Exception e) {
			log.error("Cannot convert case from: {} to: {} cause by: {}", fromCaseFormat.name(), toCaseFormat.name(),
					e.getLocalizedMessage());
			return s;
		}
	}

	public static String toCamelCase(String s) {
		return convertCaseFormat(s, CaseFormat.LOWER_UNDERSCORE, CaseFormat.LOWER_CAMEL);
	}

	public static String generateCacheKey(Object... objects) {
		return Arrays.stream(objects).map(o -> {
			if (!(o instanceof String)) {
				return String.valueOf(o);
			} else {
				return (String) o;
			}
		}).collect(Collectors.joining("-"));
	}

	public static String randomStringWithLegth(Integer targetStringLength) {
		int leftLimit = 97; // letter 'a'
		int rightLimit = 122; // letter 'z'
		Random random = new Random();
		return random.ints(leftLimit, rightLimit + 1).limit(targetStringLength)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
	}

	public static String appendRawUrlWithPath(String rawUrl, String path) {
		if (StringUtils.isEmpty(rawUrl) || StringUtils.isEmpty(path))
			return null;
		String urlSperator = "/";
		if (rawUrl.endsWith(urlSperator) && !path.startsWith(urlSperator)) {
			return rawUrl.concat(path);
		} else if (rawUrl.endsWith(urlSperator) && path.startsWith(urlSperator)) {
			return rawUrl.substring(0, rawUrl.length() - 2).concat(urlSperator);
		} else if (!rawUrl.endsWith(urlSperator) && path.startsWith(urlSperator)) {
			return rawUrl.concat(path);
		} else if (!rawUrl.endsWith(urlSperator) && !path.startsWith(urlSperator)) {
			return rawUrl.concat(urlSperator).concat(path);
		}
		return null;
	}

	public static String extractToListDigit(String source, String delimiter) {
		if (StringUtils.isEmpty(source)) {
			return null;
		}
		final String NUMBER_PATTERN = "([0-9])+";
		Pattern p = Pattern.compile(NUMBER_PATTERN);
		Matcher match = p.matcher(source);
		List<String> numbers = new ArrayList<>();
		while (match.find()) {
			int number = Integer.parseInt(match.group());
			while (number > 0) {
				numbers.add(Integer.toString(number % 10));
				number = number / 10;

			}
		}
		Collections.reverse(numbers);
		return String.join(delimiter, numbers);
	}

	private static Map<Integer, Character> getCharCodeMappingOfLatinSupplement() {
		Map<Integer, Character> charCodeMapping = new HashMap<>();
		for (int i = 0; i < CharCodeTable.LATIN_1_SUPPLEMENT.charCodes.length; i++) {
			charCodeMapping.put(CharCodeTable.LATIN_1_SUPPLEMENT.charCodes[i], CharCodeTable.LATIN_1_SUPPLEMENT.chars[i]);
		}
		return charCodeMapping;
	}

	private static Character convertToStandardCharacter(Character c) {
		Map<Integer, Character> characterMap = getCharCodeMappingOfLatinSupplement();
		if (characterMap.containsKey((int) c)) {
			return characterMap.get((int) c);
		}
		return c;
	}

	public static String convertVietnameseToLatin(String s) {
		String[][] vietnameseToLatinCMapping = getVietnameseToLatinCharacterMapping();
		String output = s;
		for (String[] vietnameseToLatinCharacter : vietnameseToLatinCMapping) {
			output = output.replaceAll(vietnameseToLatinCharacter[0], vietnameseToLatinCharacter[1]);
		}
		return output;
	}

	public static String convertToStandard(String s) {
		char[] inputChars = s.toCharArray();
		char[] outputChars = new char[inputChars.length];
		for (int i = 0; i < inputChars.length; i++) {
			outputChars[i] = convertToStandardCharacter(inputChars[i]);
		}
		return String.valueOf(outputChars);
	}

	public static String capitalize(String input) {
		input = input.toLowerCase();
		// stores each characters to a char array
		char[] charArray = input.toCharArray();
		boolean foundSpace = true;
		for (int i = 0; i < charArray.length; i++) {
			// if the array element is a letter
			if (Character.isLetter(charArray[i])) {
				// check space is present before the letter
				if (foundSpace) {
					// change the letter into uppercase
					charArray[i] = Character.toUpperCase(charArray[i]);
					foundSpace = false;
				}
			} else {
				// if the new character is not character
				foundSpace = true;
			}
		}
		// convert the char array to the string
		return String.valueOf(charArray);
	}

	private static String[][] getVietnameseToLatinCharacterMapping() {
		return new String[][]{
				{"à|á|ạ|ả|ã|â|ầ|ấ|ậ|ẩ|ẫ|ă|ằ|ắ|ặ|ẳ|ẵ", "a"},
				{"è|é|ẹ|ẻ|ẽ|ê|ề|ế|ệ|ể|ễ", "e"},
				{"ì|í|ị|ỉ|ĩ", "i"},
				{"ò|ó|ọ|ỏ|õ|ô|ồ|ố|ộ|ổ|ỗ|ơ|ờ|ớ|ợ|ở|ỡ", "o"},
				{"ù|ú|ụ|ủ|ũ|ư|ừ|ứ|ự|ử|ữ", "u"},
				{"ỳ|ý|ỵ|ỷ|ỹ", "y"},
				{"đ", "d"},
				{"À|Á|Ạ|Ả|Ã|Â|Ầ|Ấ|Ậ|Ẩ|Ẫ|Ă|Ằ|Ắ|Ặ|Ẳ|Ẵ", "A"},
				{"È|É|Ẹ|Ẻ|Ẽ|Ê|Ề|Ế|Ệ|Ể|Ễ", "E"},
				{"Ì|Í|Ị|Ỉ|Ĩ", "I"},
				{"Ò|Ó|Ọ|Ỏ|Õ|Ô|Ồ|Ố|Ộ|Ổ|Ỗ|Ơ|Ờ|Ớ|Ợ|Ở|Ỡ", "O"},
				{"Ù|Ú|Ụ|Ủ|Ũ|Ư|Ừ|Ứ|Ự|Ử|Ữ", "U"},
				{"Ỳ|Ý|Ỵ|Ỷ|Ỹ", "Y"},
				{"Đ", "D"}
		};
	}

	public static String extractFirstName(String fullName) {
		if (!StringUtils.isEmpty(fullName)) {
			String[] names = fullName.split(" ");
			return names[names.length - 1];
		}
		return null;
	}

	public static String extractLastName(String fullName) {
		if (!StringUtils.isEmpty(fullName)) {
			String[] names = fullName.split(" ");
			if (names.length == 1)
				return names[0];
			else {
				String[] arrFirstName = Arrays.copyOfRange(names, 0, names.length - 1);
				return String.join(" ", arrFirstName);
			}
		}
		return null;
	}

	public static String replaceCodeIndex(String code, String delimiter, int index) {
		return code == null ? null : code.replace(delimiter + "\\d+$", delimiter + index);
	}

	public static String concat(Object... objects) {
		return Arrays.stream(objects)
				.filter(Objects::nonNull)
				.map(String::valueOf)
				.collect(Collectors.joining(""));
	}
}
