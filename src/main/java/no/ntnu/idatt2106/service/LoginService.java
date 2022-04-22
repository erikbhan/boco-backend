package no.ntnu.idatt2106.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import no.ntnu.idatt2106.model.DAO.UserDAO;
import no.ntnu.idatt2106.model.DTO.TokenDTO;
import no.ntnu.idatt2106.util.TokenUtil;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;

@Service
public class LoginService {
    private UserService userService;
    private Algorithm algorithm = Algorithm.HMAC256("tiL8yZXjlEvxKImZS0YeIQC5V29PFDcm2wSHn47texw6fpNKv34uqyWe/NUz5go3aAkRflcDFVfpfYwoLPZrFA==".getBytes(StandardCharsets.UTF_8));

    public LoginService(UserService userService) {
        this.userService = userService;
    }

    public boolean attemptAuthentication(String email, String password) throws NoSuchAlgorithmException {

        UserDAO user = userService.findUserByEmail(email);
        if (user == null) {
            return false;
        }
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(Base64.getDecoder().decode(user.getSalt()));
        byte[] hashedPassword = Base64.getEncoder().encode(md.digest(password.getBytes(StandardCharsets.UTF_8)));

        if (new String(hashedPassword).equals(new String(user.getHash().getBytes(StandardCharsets.UTF_8)))) {
            return true;
        }
        System.out.println("Password entered by user: " + new String(hashedPassword));
        System.out.println("Password in database: " + new String(user.getHash().getBytes(StandardCharsets.UTF_8)));
        return false;
    }

    public String successfulAuthentication(UserDAO user) throws IOException, ServletException {

        Algorithm algorithm = Algorithm.HMAC256("tiL8yZXjlEvxKImZS0YeIQC5V29PFDcm2wSHn47texw6fpNKv34uqyWe/NUz5go3aAkRflcDFVfpfYwoLPZrFA==".getBytes(StandardCharsets.UTF_8));
        //Making a jwt token
        String access_token = JWT.create()
                .withClaim("email", user.getEmail())
                .withClaim("first_name", user.getFirstName())
                .withClaim("last_name", user.getLastName())
                .withClaim("account_id", String.valueOf(user.getUserID()))
                .withExpiresAt(new Date(System.currentTimeMillis() + 60 * 48 * 60 * 1000))
                .sign(algorithm);


        System.out.println("You were authenticated : " + access_token);

        return access_token;
    }

    public TokenDTO tokenDTO(String token){
        return TokenUtil.getDataJWT(token);
    }
}
