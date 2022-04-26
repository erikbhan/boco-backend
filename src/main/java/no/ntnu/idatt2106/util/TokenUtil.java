package no.ntnu.idatt2106.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import no.ntnu.idatt2106.model.DTO.TokenDTO;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class TokenUtil {
    private static final Algorithm algorithm = Algorithm.HMAC256("tiL8yZXjlEvxKImZS0YeIQC5V29PFDcm2wSHn47texw6fpNKv34uqyWe/NUz5go3aAkRflcDFVfpfYwoLPZrFA==".getBytes(StandardCharsets.UTF_8));
    public static String getToken() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String token = request.getHeader("Authorization");
        if(token == null) return null;
        try {
            return token.split(" ")[1];
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }



    public static boolean verifyToken(String token) {
        try {
            // Validate the JWT signature
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(token);
        } catch (Exception e) { // Catch every exception throw by verify and return false
            return false;
        }

        // Check expiration
        return JWT.decode(token).getExpiresAt().after(new Date());
    }

    /**
     * Grabs the data segment of the JWT
     * @param token JWT token
     * @return Data from the JWT token as TokenDTO
     */
    public static TokenDTO getDataJWT(String token) {
        return new TokenDTO(JWT.decode(token).getClaims());
    }

    /**
     * A method which combines the {@link #getDataJWT(String) getDataJWT(String)}
     * method with the {@link #getToken() getToken()} method.
     * @return Returns a TokenDTO object containing the information stored in the token.
     */
    public static TokenDTO getDataJWT() {
        return TokenUtil.getDataJWT(TokenUtil.getToken());
    }
}
