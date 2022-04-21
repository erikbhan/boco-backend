package no.ntnu.idatt2106.model.DTO;
import com.auth0.jwt.interfaces.Claim;


import java.util.Map;

public class TokenDTO {

    String email;
    int exp;
    String accountId;

    public TokenDTO(String email, int exp, String accountId) {

        this.email = email;
        this.exp = exp;
    }

    public TokenDTO(Map<String, Claim> claims) {
        this.email = claims.get("email").asString();
        this.exp = claims.get("exp").asInt();
        this.accountId = claims.get("accountId").asString();
    }



    public String getEmail() {
        return email;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}