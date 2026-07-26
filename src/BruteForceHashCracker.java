
public class BruteForceHashCracker implements HashCracker {

    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    private static final int MAX_LENGTH = 4;

    private long attempts = 0;
    private long durationMs = 0;

    @Override
    public String crack(String hash) {
        long start = System.currentTimeMillis();
        attempts = 0;
        String found = null;

        for (int length = 1; length <= MAX_LENGTH && found == null; length++) {
            found = tryAllCombinationsOfLength(hash, length);
        }

        durationMs = System.currentTimeMillis() - start;
        return found;
    }

    private String tryAllCombinationsOfLength(String hash, int length) {
        int[] indices = new int[length]; // commence à "aaa...a"

        while (true) {
            String candidate = buildCandidate(indices);
            attempts++;

            if (MD5Util.hashMD5(candidate).equals(hash)) {
                return candidate;
            }

            if (!incrementIndices(indices)) {
                break;
            }
        }
        return null;
    }

    private String buildCandidate(int[] indices) {
        StringBuilder sb = new StringBuilder(indices.length);
        for (int idx : indices) {
            sb.append(ALPHABET.charAt(idx));
        }
        return sb.toString();
    }

    private boolean incrementIndices(int[] indices) {
        int pos = indices.length - 1;
        while (pos >= 0) {
            indices[pos]++;
            if (indices[pos] < ALPHABET.length()) {
                return true;
            }
            indices[pos] = 0;
            pos--;
        }
        return false;
    }

    public long getAttempts() {
        return attempts;
    }

    public long getDurationMs() {
        return durationMs;
    }
}
