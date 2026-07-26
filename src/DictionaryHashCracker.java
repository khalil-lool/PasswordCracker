import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DictionaryHashCracker implements HashCracker {

    private final String dictionaryPath;

    public DictionaryHashCracker() {
        this.dictionaryPath = "dictionary.txt";
    }

    public DictionaryHashCracker(String dictionaryPath) {
        this.dictionaryPath = dictionaryPath;
    }

    @Override
    public String crack(String hash) {
        if (hash == null || hash.trim().isEmpty()) {
            return null;
        }

        long startTime = System.currentTimeMillis();
        int attempts = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(dictionaryPath))) {
            String word;
            while ((word = reader.readLine()) != null) {
                attempts++;
                word = word.trim();

                // Calcul du hash MD5 via MD5Util et comparaison (insensible à la casse)
                String currentHash = MD5Util.hashMD5(word);
                if (currentHash != null && currentHash.equalsIgnoreCase(hash)) {
                    long duration = System.currentTimeMillis() - startTime;
                    System.out.println("[+] Mot de passe trouvé dans le dictionnaire !");
                    System.out.println("[+] Tentatives : " + attempts);
                    System.out.println("[+] Temps d'exécution : " + duration + " ms");
                    return word;
                }
            }
        } catch (IOException e) {
            System.err.println("[-] Erreur de lecture du fichier dictionnaire : " + e.getMessage());
        }

        long duration = System.currentTimeMillis() - startTime;
        System.out.println("[-] Mot de passe non trouvé dans le dictionnaire.");
        System.out.println("[-] Tentatives effectuées : " + attempts);
        System.out.println("[-] Temps d'exécution : " + duration + " ms");

        return null;
    }
}