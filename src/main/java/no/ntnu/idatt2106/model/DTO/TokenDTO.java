package no.ntnu.idatt2106.model.DTO;
import com.auth0.jwt.interfaces.Claim;


import java.util.Map;

/**
 * A class representing the token information.
 * This class should be used for the transfer of token information.
 */
public class TokenDTO {
    String email;
    int exp;
    String accountId;
    String firstName;
    String lastName;

    public TokenDTO(String email, int exp, String accountId, String firstName, String lastName) {
        this.email = email;
        this.exp = exp;
        this.accountId = accountId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public TokenDTO(Map<String, Claim> claims) {
        this.email = claims.get("email").asString();
        this.exp = claims.get("exp").asInt();
        this.accountId = claims.get("accountId").asString();
        this.firstName = claims.get("firstName").asString();
        this.lastName = claims.get("lastName").asString();
    }

    public String getEmail() {return email;}

    public int getExp() {return exp;}

    public void setExp(int exp) {this.exp = exp;}

    public void setEmail(String email) {this.email = email;}

    public int getAccountId() {return Integer.valueOf(accountId);}

    public void setAccountId(String accountId) {this.accountId = accountId;}

    public String getFirstName() {return firstName;}

    public String getLastName() {return lastName;}

    public void setFirstName(String firstName) {this.firstName = firstName;}

    public void setLastName(String lastName) {this.lastName = lastName;}

}