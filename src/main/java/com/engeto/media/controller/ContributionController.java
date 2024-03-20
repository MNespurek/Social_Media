package com.engeto.media.controller;

import com.engeto.media.model.Contribution;
import com.engeto.media.model.ContributionException;
import com.engeto.media.service.ContributionIdComparator;
import com.engeto.media.service.Settings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

@RestController
@RequestMapping("v1")
public class ContributionController {
    @Autowired
    ContributionIdComparator contributionIdComparator;

    @PostMapping("contribution")
    public ResponseEntity<String> postContribution(
            @RequestBody Contribution contribution
    ) {
        if (contributionIdComparator.isIdInList(contribution)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Příspěvek s tímto ID již existuje!");
        } else try {
            contributionIdComparator.addContribution(contribution);
            contributionIdComparator.addContributionToFile(Settings.getFileName());
            return ResponseEntity.ok("Příspěvek byl úspěšně přidán!");

        } catch (ContributionException | IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Nastala chyba při zpracování požadavku");
        }
    }
    @GetMapping("contribution")
    public StringBuilder getContribution(String fileName, @RequestParam(required = false) Boolean isVisibleUrl) throws FileNotFoundException, ContributionException {
        fileName = Settings.getFileName();
       try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(fileName)))) {
            if (!scanner.hasNextLine()) {
                throw new ContributionException("Soubor " + fileName + "je prázdný a není tedy co načítat:-)");
            }
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] part = line.split(Settings.getDelimiter());
                String contributionText = part[0];
                Integer contributionId = Integer.valueOf(part[1]);
                Boolean isVisible;
                System.out.println("url" +isVisibleUrl);
                if (isVisibleUrl != null && isVisibleUrl) {
                    isVisible = true;
                }else {
                    isVisible = Boolean.valueOf(part[2]);
                }
                System.out.println(isVisible);
                String user = part[3];
                Contribution contribution = new Contribution(contributionText, contributionId, isVisible, user);
                contributionIdComparator.addContribution(contribution);
            }
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException(e.getLocalizedMessage());
        } catch (ContributionException e) {
            System.out.println(e.getLocalizedMessage());
        }
        return contributionIdComparator.getContribution();
    }

}
