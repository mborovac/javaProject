package hr.fer.zemris.web.radionice;

import hr.fer.zemris.web.radionice.exception.InconsistentDatabaseException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class used to create workshops and modify them.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class RadionicaBaza {
	
	private String directory;
	private Map<String, Radionica> mapaRadionica;
	private List<Opcija> radioniceOprema;
	private List<Opcija> radionicePublika;
	private Map<String, Opcija> mapaOpreme;
	private Map<String, Opcija> mapaPublike;
	private Map<String, Opcija> mapaTrajanja;
	private Long maxID = null;
	
	/**
	 * Class constructor.
	 * 
	 * @param directory directory containing the database.
	 */
	private RadionicaBaza(String directory) {
		if(directory == null || directory.isEmpty()) {
			throw new IllegalArgumentException("Directory can not be null.");
		}
		if(!directory.startsWith("\\")) {
			directory += "\\";
		}
		this.directory = directory;
		this.mapaRadionica = new HashMap<>();
		this.mapaOpreme = new HashMap<>();
		this.mapaPublike = new HashMap<>();
		this.mapaTrajanja = new HashMap<>();
		this.radioniceOprema = new ArrayList<>();
		this.radionicePublika = new ArrayList<>();
		ucitajOpcije(mapaOpreme, null, directory + "oprema.txt");
		ucitajOpcije(mapaPublike, null, directory + "publika.txt");
		ucitajOpcije(mapaTrajanja, null, directory + "trajanje.txt");
		ucitajOpcije(null, radioniceOprema, directory + "radionice_oprema.txt");
		ucitajOpcije(null, radionicePublika, directory + "radionice_publika.txt");
		ucitajRadionice();
	}
	
	/**
	 * Read the workshops from database.
	 */
	private void ucitajRadionice() {
		String filePath = directory + "radionice.txt";
		Path path = Paths.get(filePath);
		if(!Files.exists(path)) {
			return;
		}
		List<String> lines = null;
		try {
			lines = Files.readAllLines(path, StandardCharsets.UTF_8);
		} catch (IOException e) {
			System.out.println("tu1");
			System.out.println("Can not read file " + filePath);
			return;
		}
		for(String line : lines) {
			if(line.isEmpty()) {
				continue;
			}
			String[] elementi = line.split("\t", -1);
			if(elementi.length != 7) {
				System.out.println("Illformed file " + filePath);
				return;
			}
			List<Opcija> listaOpreme = new ArrayList<>();
			Long currentID = Long.parseLong(elementi[0]);
			for(Opcija opcija: radioniceOprema) {
				if(Long.parseLong(opcija.getID()) == currentID) {
					String value = opcija.getVrijednost();
					listaOpreme.add(mapaOpreme.get(value));
				}
			}
			List<Opcija> listaPublike = new ArrayList<>();
			for(Opcija opcija: radionicePublika) {
				if(Long.parseLong(opcija.getID()) == currentID) {
					String value = opcija.getVrijednost();
					listaPublike.add(mapaPublike.get(value));
				}
			}
			Radionica radionica = new Radionica(currentID, elementi[1], elementi[2],
					Integer.parseInt(elementi[3]), this.mapaTrajanja.get(elementi[4]), elementi[5], elementi[6], 
					listaOpreme, listaPublike);
			mapaRadionica.put(elementi[0], radionica);
		}
	}

	/**
	 * Read the possible options. Used to read audience, equipment and duration.
	 * 
	 * @param mapaOpcija all possible options, audience and duration
	 * @param skupOpcija all possible options, equipment
	 * @param filePath file containing the options
	 */
	private void ucitajOpcije(Map<String, Opcija> mapaOpcija, List<Opcija> skupOpcija, String filePath) {
		Path path = Paths.get(filePath);
		if(!Files.exists(path)) {
			return;
		}
		List<String> lines = null;
		try {
			lines = Files.readAllLines(path, StandardCharsets.UTF_8);
		} catch (IOException e) {
			System.out.println("tu2");
			System.out.println("Can not read file " + filePath);
			return;
		}
		for(String line : lines) {
			line = line.trim();
			if(line.isEmpty()) continue;
			String[] elementi = line.split("\t");
			if(elementi.length != 2) {
				System.out.println("Illformed file " + filePath);
				return;
			}
			Opcija opcija = new Opcija(elementi[0], elementi[1]);
			if(mapaOpcija != null && skupOpcija == null) {
				mapaOpcija.put(elementi[0], opcija);
			} else if(mapaOpcija == null && skupOpcija != null) {
				skupOpcija.add(opcija);
			}
		}
	}

	/**
	 * Reads the workshop info from the database and creates the workshops.
	 * 
	 * @param directory directory containing the database
	 * @return returns a new workshop creator
	 */
	public static RadionicaBaza ucitaj(String directory) {
		Path path = Paths.get(directory);
		if(!Files.isDirectory(path)) {
			throw new IllegalArgumentException("Given path does not lead to a directory.");
		}
		RadionicaBaza baza = new RadionicaBaza(directory);
		return baza;
	}
	
	/**
	 * Saves the current state to the original database.
	 */
	public void snimi() {
		snimi(this.directory);
	}
	
	/**
	 * Saves the current state to the new database in the given directory.
	 * 
	 * @param directory directory with new database
	 */
	public void snimi(String directory) {
		provjeriIspravnostOpcija();
		snimiOpcije(this.mapaOpreme, "oprema.txt", directory);
		snimiOpcije(this.mapaPublike, "publika.txt", directory);
		snimiOpcije(this.mapaTrajanja, "trajanje.txt", directory);
		spremiRadionice(directory);
	}

	/**
	 * Checks whether all the options in all the workshops are in the database and are available.
	 */
	private void provjeriIspravnostOpcija() {
		List<Opcija> tempRadioniceOprema = new ArrayList<>();
		List<Opcija> tempRadionicePublika = new ArrayList<>();
		Map<String, Opcija> tempMapaTrajanja = new HashMap<>();
		ucitajOpcije(null, tempRadioniceOprema, directory + "oprema.txt");
		ucitajOpcije(null, tempRadionicePublika, directory + "publika.txt");
		ucitajOpcije(tempMapaTrajanja, null, directory + "trajanje.txt");
		for(Radionica radionica: mapaRadionica.values()) {
			for(Opcija oprema: radionica.getOprema()) {
				if(!tempRadioniceOprema.contains(oprema)) {
					throw new InconsistentDatabaseException("Oprema " + oprema + " does not exist in the database!");
				}
			}
			for(Opcija publika: radionica.getPublika()) {
				if(!tempRadionicePublika.contains(publika)) {
					throw new InconsistentDatabaseException("Publika " + publika + " does not exist in the database!");
				}
			}
			if(!tempMapaTrajanja.containsValue(radionica.getTrajanje())) {
				throw new InconsistentDatabaseException("Duration " + radionica.getTrajanje() + 
						" does not exist in the database!");
			}
		}
	}
	
	/**
	 * Saves the options in to the database.
	 * 
	 * @param mapaOpcija map of all the options
	 * @param filePath file to be saved to
	 * @param directory directory containing the database
	 */
	private void snimiOpcije(Map<String, Opcija> mapaOpcija, String filePath, String directory) {
		if(!directory.startsWith("/")) {
			directory += "/";
		}
		String path = directory + filePath;
		List<Opcija> list = new ArrayList<>(mapaOpcija.values());
		Collections.sort(list);
		BufferedWriter bw;
		try {
			bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(path), StandardCharsets.UTF_8));
		} catch (FileNotFoundException e) {
			System.out.println("Can not save file " + path);
			return;
		}
		for(Opcija opcija: list) {
			try {
				bw.write(opcija.getID() + "\t" + opcija.getVrijednost() + "\n");
			} catch (IOException e) {
				System.out.println("Could not save file " + path);
				try {
					bw.close();
				} catch (IOException ignore) {
				}
				return;
			}
		}
		try {
			bw.close();
		} catch (IOException ignore) {
		}
	}
	
	/**
	 * Saves the current workshop.
	 * 
	 * @param r workshop to be saved
	 */
	public void snimi(Radionica r) {
		if(r.getId() == null) {
			Long noviId = maxID == null ? 1 : maxID+1;
			r.setId(noviId);
		}
		mapaRadionica.put(Long.toString(r.getId()), r);
		if(maxID == null || r.getId().compareTo(maxID) > 0) {
			maxID = r.getId();
		}
	}
	
	/**
	 * @return the mapaRadionica
	 */
	public Map<String, Radionica> getMapaRadionica() {
		return new HashMap<>(mapaRadionica);
	}
	
	/**
	 * @return the mapaOpreme
	 */
	public Map<String, Opcija> getMapaOpreme() {
		return new HashMap<>(mapaOpreme);
	}
	
	/**
	 * @return the mapaPublike
	 */
	public Map<String, Opcija> getMapaPublike() {
		return new HashMap<>(mapaPublike);
	}
	
	/**
	 * @return the mapaTrajanja
	 */
	public Map<String, Opcija> getMapaTrajanja() {
		return new HashMap<>(mapaTrajanja);
	}
	
	/**
	 * Saves the workshops and all the options required for them (audience and equipment).
	 * 
	 * @param direktorij directory containing the database
	 */
	private void spremiRadionice(final String direktorij) {

		FileWriter writer = null;
		FileWriter writerOprema = null;
		FileWriter writerPublika = null;
		try {
			writer = new FileWriter(direktorij + File.separator
					+ "radionice.txt");
			writerOprema = new FileWriter(direktorij + File.separator
					+ "radionice_oprema.txt");
			writerPublika = new FileWriter(direktorij + File.separator
					+ "radionice_publika.txt");
			
			for (final Radionica radionica : this.mapaRadionica.values()) {
				
				String entry = radionica.getId() + "\t" + radionica.getNaziv()
						+ "\t" + radionica.getDatum() + "\t"
						+ radionica.getMaksPolaznika() + "\t"
						+ radionica.getTrajanje().getID() + "\t"
						+ radionica.getEmail();
				entry += System.getProperty("line.separator");
				writer.append(entry);
				
				for (final Opcija currentOprema : radionica.getOprema()) {
					writerOprema.append(radionica.getId() + "\t"
							+ currentOprema.getID()
							+ System.getProperty("line.separator"));
				}
				
				for (final Opcija currentPublika : radionica.getPublika()) {
					writerPublika.append(radionica.getId() + "\t"
							+ currentPublika.getID()
							+ System.getProperty("line.separator"));
				}
			}
		} catch (final IOException e) {
			System.out.println("Error while writing to file.");
		}
		
		try {
			writer.close();
			writerOprema.close();
			writerPublika.close();
		} catch (final IOException ignorable) {
		}
	}
}
