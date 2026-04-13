/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Trainer;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 *
 * @author JH
 */
public class Auth {

	private static final Pattern TRID_PATTERN = Pattern.compile("^TR\\d{3}$", Pattern.CASE_INSENSITIVE);
	private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{6,}$");
	private static final String CREDENTIALS_FILE = "Trainer/TrainerCredentials.csv";

	private Auth() {
		// Utility class
	}

	public static boolean isValidTrainerIdFormat(String trainerId) {
		if (trainerId == null) {
			return false;
		}
		return TRID_PATTERN.matcher(trainerId.trim()).matches();
	}

	public static boolean isValidPasswordFormat(String password) {
		if (password == null) {
			return false;
		}
		return PASSWORD_PATTERN.matcher(password).matches();
	}

	public static boolean verifyLogin(String trainerId, String password) {
		if (!isValidTrainerIdFormat(trainerId) || password == null || password.isBlank()) {
			return false;
		}

		Path filePath = resolveCredentialsPath();
		if (!Files.exists(filePath)) {
			return false;
		}

		String normalizedId = trainerId.trim().toUpperCase(Locale.ROOT);

		try (BufferedReader br = Files.newBufferedReader(filePath)) {
			String line;
			boolean isFirstRow = true;

			while ((line = br.readLine()) != null) {
				if (isFirstRow) {
					isFirstRow = false;
					continue;
				}

				String[] parts = line.split(",", -1);
				if (parts.length < 5) {
					continue;
				}

				String fileId = parts[0].trim().toUpperCase(Locale.ROOT);
				String filePassword = parts[4].trim();

				if (fileId.equals(normalizedId) && filePassword.equals(password)) {
					return true;
				}
			}
		} catch (IOException e) {
			return false;
		}

		return false;
	}

	private static Path resolveCredentialsPath() {
		Path primary = Paths.get(CREDENTIALS_FILE);
		if (Files.exists(primary)) {
			return primary;
		}

		// Fallback for running from the src folder as working directory.
		return Paths.get("src", CREDENTIALS_FILE);
	}
}
