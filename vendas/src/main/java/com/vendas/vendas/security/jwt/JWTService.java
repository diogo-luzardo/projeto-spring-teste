package com.vendas.vendas.security.jwt;

import com.vendas.vendas.entity.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class JWTService {

    @Value("${security.jwt.expiracao}")
    private String expiracao;

    @Value("${security.jwt.chave-assinatura}")
    private String chaveAssinatura;

    public String gerarToken(Usuario usuario){
        Long expString = Long.valueOf(expiracao);
        LocalDateTime dataHoraExpiracao = LocalDateTime.now().plusMinutes(expString);
        Instant instant = dataHoraExpiracao.atZone(ZoneId.systemDefault()).toInstant();
        Date data = Date.from(instant);
        return Jwts.builder()
                .setSubject(usuario.getNome())
                .setExpiration(data)
                .signWith(SignatureAlgorithm.HS512,chaveAssinatura)
                .compact();
    }

    private Claims obterClaims(String token) throws ExpiredJwtException {
        return Jwts.parser()
                .setSigningKey(chaveAssinatura)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean tokenValido(String token){
        try{
            Claims claims = obterClaims(token);
            Date dataExpiracao = claims.getExpiration();
            LocalDateTime data = dataExpiracao.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            return !LocalDateTime.now().isAfter(data);
        }catch (Exception e){
            return false;
        }
    }

    public String obterLoginUsuario(String token) throws ExpiredJwtException{
        return (String) obterClaims(token).getSubject();
    }

//    public static void main(String[] args) {
//        ConfigurableApplicationContext context = SpringApplication.run(VendasApplication.class);
//        JWTService service = context.getBean(JWTService.class);
//        Usuario usuario = new Usuario();
//        usuario.setNome("Diogo");
//        String token = service.gerarToken(usuario);
//        System.out.println(token);
//    }
}