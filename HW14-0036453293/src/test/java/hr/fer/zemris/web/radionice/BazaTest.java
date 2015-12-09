package hr.fer.zemris.web.radionice;

import static org.junit.Assert.*;

import hr.fer.zemris.web.radionice.exception.InconsistentDatabaseException;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Test;

public class BazaTest {
	
	@Test
	public void prviTest() {
		RadionicaBaza baza = RadionicaBaza.ucitaj("./baza");
		File d = null;
		try {
			d = Files.createTempDirectory(null).toFile();
		} catch (IOException e) {
			fail("Can not create temp file.");
		}
		baza.snimi(d.getAbsolutePath());
		File originalBaza = new File("./baza");
		String[] entries = d.list();
		String[] bazaEntries = originalBaza.list();
		boolean theSame = true;
		if(entries.length != bazaEntries.length) {
			theSame = false;
		}
		for(String entry: bazaEntries) {
			Path path1 = Paths.get("/WEB-INF/baza/" + entry);
			Path path2 = Paths.get(d.getAbsolutePath() + "/" + entry);
			List<String> lines1 = null;
			try {
				lines1 = Files.readAllLines(path1, StandardCharsets.UTF_8);
			} catch (IOException e) {
				System.out.println("Can not read file " + path1);
				return;
			}
			List<String> lines2 = null;
			try {
				lines2 = Files.readAllLines(path2, StandardCharsets.UTF_8);
			} catch (IOException e) {
				System.out.println("Can not read file " + path2);
				return;
			}
			if(lines1.size() != lines2.size()) {
				theSame = false;
			}
			if(theSame) {
				for(int i = 0; i < lines1.size(); i++) {
					if(lines1.get(i).compareTo(lines2.get(i)) != 0) {
						theSame = false;
						break;
					}
				}
			}
		}
		for(String s: entries){
		    File currentFile = new File(d.getPath(), s);
		    currentFile.delete();
		}
		d.delete();
		if(!theSame) {
			fail("File contents are not the same.");
		}
	}
	
	@Test
	public void drugiTest() {
		RadionicaBaza baza = RadionicaBaza.ucitaj("./baza");
		Radionica radionica = baza.getMapaRadionica().get("1");
		radionica.getOprema().add(new Opcija("101", "USB stick"));
		File d = null;
		try {
			d = Files.createTempDirectory(null).toFile();
		} catch (IOException e) {
			fail("Can not create temp file.");
		}
		boolean problems = false;
		try {
			baza.snimi(d.getAbsolutePath());
		} catch (InconsistentDatabaseException e) {
			problems = true;
		}
		String[] entries = d.list();
		for(String s: entries){
		    File currentFile = new File(d.getPath(), s);
		    currentFile.delete();
		}
		d.delete();
		if(!problems) {
			fail("Shouldn't have been able to save.");
		}
	}
}
