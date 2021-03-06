package net.sf.jabref.journals.logic;


import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

public class Abbreviation implements Comparable<Abbreviation> {

    private final String name;
    private final String abbreviation;

    public Abbreviation(String name, String abbreviation) {
        this.name = Preconditions.checkNotNull(name).trim();
        this.abbreviation = Preconditions.checkNotNull(abbreviation).trim();
    }

    public String getName() {
        return name;
    }

    public String getIsoAbbreviation() {
        String SPLITTER = ";"; // elements after SPLITTER are not used at the moment
        if (abbreviation.contains(SPLITTER)) {
            String[] restParts = abbreviation.split(SPLITTER);
            return restParts[0].trim();
        } else {
            return abbreviation;
        }
    }

    public String getMedlineAbbreviation() {
        return getIsoAbbreviation().replaceAll("\\.", " ").replaceAll("  ", " ").trim();
    }

    public boolean hasIsoAndMedlineAbbreviationsAreSame() {
        return getIsoAbbreviation().equals(getMedlineAbbreviation());
    }

    @Override
    public int compareTo(Abbreviation o) {
        return name.compareTo(o.name);
    }

    public String getNext(String current) {
        String currentTrimmed = current.trim();

        if (getMedlineAbbreviation().equals(currentTrimmed)) {
            return getName();
        } else if (getName().equals(currentTrimmed)) {
            return getIsoAbbreviation();
        } else {
            return getMedlineAbbreviation();
        }
    }

    @Override
    public String toString() {
        return String.format("Abbreviation{name=%s, iso=%s, medline=%s}", name, getIsoAbbreviation(), getMedlineAbbreviation());
    }

    public String toPropertiesLine() {
        return String.format("%s = %s", name, getAbbreviation());
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Abbreviation that = (Abbreviation) o;
        return Objects.equal(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
