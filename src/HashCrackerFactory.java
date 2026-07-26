public class HashCrackerFactory {

    public static HashCracker create(String method) {
        if (method == null || method.isBlank()) {
            throw new IllegalArgumentException("La méthode ne peut pas être vide.");
        }

        switch (method.toUpperCase()) {
            case "BRUTE":
                return new BruteForceHashCracker();
            case "DICO":
                return new DictionaryHashCracker();
            default:
                throw new IllegalArgumentException(
                    "Méthode inconnue: '" + method + "'. Valeurs acceptées: BRUTE, DICO."
                );
        }
    }
}