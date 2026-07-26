public class PasswordCracker {

    public static void main(String[] args) {
        String method = null;
        String hash = null;

        // Parsing des arguments -m et -h
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-m") && i + 1 < args.length) {
                method = args[i + 1];
                i++;
            } else if (args[i].equals("-h") && i + 1 < args.length) {
                hash = args[i + 1];
                i++;
            }
        }

        if (method == null || hash == null) {
            System.out.println("Usage: passwordCracker -m BRUTE|DICO -h <hash_md5>");
            return;
        }

        try {
            HashCracker cracker = HashCrackerFactory.create(method);

            long start = System.currentTimeMillis();
            String result = cracker.crack(hash);
            long durationMs = System.currentTimeMillis() - start;

            if (result != null) {
                System.out.println("Password found: " + result);
            } else {
                System.out.println("Password not found");
            }

            // Affiche les stats si la stratégie utilisée les fournit (ex: BruteForceHashCracker)
            if (cracker instanceof BruteForceHashCracker) {
                BruteForceHashCracker bfc = (BruteForceHashCracker) cracker;
                System.out.println("Tentatives: " + bfc.getAttempts());
                System.out.println("Temps d'exécution: " + bfc.getDurationMs() + " ms");
            } else {
                System.out.println("Temps d'exécution: " + durationMs + " ms");
            }

        } catch (IllegalArgumentException e) {
            System.out.println("Erreur: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erreur inattendue: " + e.getMessage());
        }
    }
}