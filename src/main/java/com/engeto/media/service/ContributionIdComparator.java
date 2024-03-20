package com.engeto.media.service;

import com.engeto.media.model.Contribution;
import com.engeto.media.model.ContributionException;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class ContributionIdComparator {

    private final List<Contribution> contributionList = new ArrayList<>();

    public void addContribution(Contribution contribution) {
        contributionList.add(contribution);
    }

    public void addContributionToFile(String fileName) throws ContributionException, IOException {
        String defineDelimiter = Settings.getDelimiter();
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(fileName)))) {
            for (Contribution contribution : contributionList) {
                writer.println(contribution.getContributionText() + defineDelimiter
                        + contribution.getContributionId() + defineDelimiter
                        + contribution.isVisible() + defineDelimiter
                        + contribution.getUser() + defineDelimiter);
            }
        }catch (FileNotFoundException e) {
            throw new ContributionException("Soubor " + Settings.getFileName() + "nenalezen." + e.getLocalizedMessage() + ".");
        }
    }
    public Boolean isIdInList(Contribution contribution) {
        for (Contribution contributionFromList : contributionList) {
             return contributionFromList.getContributionId().equals(contribution.getContributionId());
        }
        return false;
    }

    public StringBuilder getContribution() {
        StringBuilder stringBuilder = new StringBuilder();
        for(Contribution contribution : contributionList) {
            if (contribution.isVisible()) {
                stringBuilder.append(contribution.getContributionText()).append("\t").append(contribution.getContributionId()).append("\t").append(contribution.isVisible()).append("\t").append(contribution.getUser()).append("\n");
            }
        }
        return stringBuilder;
    }
}