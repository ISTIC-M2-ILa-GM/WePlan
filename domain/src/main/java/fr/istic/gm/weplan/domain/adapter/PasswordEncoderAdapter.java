package fr.istic.gm.weplan.domain.adapter;

/**
 * The password encoder adapter
 */
public interface PasswordEncoderAdapter {

    /**
     * Encode a password.
     *
     * @param password the password to encode
     * @return the encoded password
     */
    String encode(String password);
}
