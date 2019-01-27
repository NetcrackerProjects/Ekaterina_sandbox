package com.gigssandbox;

class Rating {
    int calculateBandRating(int givenGigs, int numberOfAlbums, int bandPageVisits, int gigsPagesVizits) {
        if (givenGigs < 0 || numberOfAlbums < 0 || bandPageVisits < 0 || gigsPagesVizits < 0) {
            throw new IllegalArgumentException("Wrong parameter in the Rating#calculateBandRating method");
        }
        float gigsSummon = 0.3f * (givenGigs > 100 ? 1 : (float) givenGigs / 100f);
        float albumsSummon = 0.3f * (numberOfAlbums > 10 ? 1 : (float) numberOfAlbums / 10f);
        float bandVizitsSummon = 0.2f * (bandPageVisits > 1000 ? 1 : (float) bandPageVisits / 1000f);
        float gigsVizitsSummon = 0.2f * (gigsPagesVizits > 1000 ? 1 : (float) gigsPagesVizits / 1000f);
        return (int) (100 * (gigsSummon + albumsSummon + bandVizitsSummon + gigsVizitsSummon));
    }

    int calculateUserRating(int daysFromRegistration, int numberOfAttendedUnfamousGigs, int numberOfAttendedMiddleFamousGigs, int numberOfAttendedFamousGigs) {
        if (daysFromRegistration < 0 || numberOfAttendedUnfamousGigs < 0 || numberOfAttendedMiddleFamousGigs < 0 || numberOfAttendedFamousGigs < 0) {
            throw new IllegalArgumentException("Wrong parameter in the Rating#calculateUserRating method");
        }
        return daysFromRegistration / 10 + numberOfAttendedUnfamousGigs * 6 + numberOfAttendedMiddleFamousGigs * 9 + numberOfAttendedFamousGigs * 12;
    }
}
