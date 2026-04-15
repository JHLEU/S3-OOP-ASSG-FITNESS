package Trainer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ReportEquipment {
	private static final String EQUIPMENT_FILE = "Trainer/EquipmentStatus.csv";

	private final String equipmentId;
	private final String equipmentName;
	private String condition;

	public ReportEquipment(String equipmentId, String equipmentName, String condition) {
		this.equipmentId = equipmentId;
		this.equipmentName = equipmentName;
		this.condition = condition;
	}

	public String getEquipmentId() {
		return equipmentId;
	}

	public String getEquipmentName() {
		return equipmentName;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public static Path resolveEquipmentPath() {
		Path primary = Paths.get(EQUIPMENT_FILE);
		if (Files.exists(primary)) {
			return primary;
		}
		return Paths.get("src", EQUIPMENT_FILE);
	}

	public static void ensureEquipmentFileExists() {
		Path filePath = resolveEquipmentPath();
		if (Files.exists(filePath)) {
			return;
		}

		try {
			Files.createDirectories(filePath.getParent());
			try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
				writer.write("equipmentID,equipmentName,condition");
				writer.newLine();
				writer.write("EQ001,Treadmill,Good");
				writer.newLine();
				writer.write("EQ002,Stationary Bike,Good");
				writer.newLine();
				writer.write("EQ003,Rowing Machine,Good");
				writer.newLine();
				writer.write("EQ004,Dumbbell Set,Good");
				writer.newLine();
				writer.write("EQ005,Bench Press,Good");
				writer.newLine();
			}
		} catch (IOException e) {
			System.out.println("Error creating equipment file: " + e.getMessage());
		}
	}

	public static List<ReportEquipment> loadAll() {
		ensureEquipmentFileExists();
		Path filePath = resolveEquipmentPath();
		List<ReportEquipment> equipmentList = new ArrayList<>();

		try (BufferedReader reader = new BufferedReader(new FileReader(filePath.toFile()))) {
			String line;
			boolean header = true;

			while ((line = reader.readLine()) != null) {
				if (header) {
					header = false;
					continue;
				}

				String[] parts = line.split(",", -1);
				if (parts.length < 3) {
					continue;
				}

				equipmentList.add(new ReportEquipment(
					parts[0].trim(),
					parts[1].trim(),
					parts[2].trim()
				));
			}
		} catch (IOException e) {
			System.out.println("Error reading equipment file: " + e.getMessage());
		}

		return equipmentList;
	}

	public static boolean markEquipmentBrokenByIndex(int index) {
		List<ReportEquipment> equipmentList = loadAll();
		if (index < 0 || index >= equipmentList.size()) {
			return false;
		}

		equipmentList.get(index).setCondition("Broken");
		return saveAll(equipmentList);
	}

	public static boolean markEquipmentGoodByIndex(int index) {
		List<ReportEquipment> equipmentList = loadAll();
		if (index < 0 || index >= equipmentList.size()) {
			return false;
		}

		equipmentList.get(index).setCondition("Good");
		return saveAll(equipmentList);
	}

	public static boolean toggleEquipmentConditionByIndex(int index) {
		List<ReportEquipment> equipmentList = loadAll();
		if (index < 0 || index >= equipmentList.size()) {
			return false;
		}

		ReportEquipment equipment = equipmentList.get(index);
		if ("Good".equalsIgnoreCase(equipment.getCondition())) {
			equipment.setCondition("Broken");
		} else {
			equipment.setCondition("Good");
		}

		return saveAll(equipmentList);
	}

	public static boolean saveAll(List<ReportEquipment> equipmentList) {
		ensureEquipmentFileExists();
		Path filePath = resolveEquipmentPath();

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile(), false))) {
			writer.write("equipmentID,equipmentName,condition");
			writer.newLine();

			for (ReportEquipment equipment : equipmentList) {
				writer.write(String.format("%s,%s,%s",
					equipment.getEquipmentId(),
					equipment.getEquipmentName(),
					equipment.getCondition()));
				writer.newLine();
			}

			return true;
		} catch (IOException e) {
			System.out.println("Error saving equipment file: " + e.getMessage());
			return false;
		}
	}

	public static long countBrokenEquipment() {
		List<ReportEquipment> equipmentList = loadAll();
		return equipmentList.stream()
			.filter(equipment -> "Broken".equalsIgnoreCase(equipment.getCondition()))
			.count();
	}
}
