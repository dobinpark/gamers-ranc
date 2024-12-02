package jp.games_ranc.controller.token;

import jp.games_ranc.DTO.token.CreateAccessTokenRequest;
import jp.games_ranc.DTO.token.CreateAccessTokenResponse;
import jp.games_ranc.service.RefreshTokenService;
import jp.games_ranc.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TokenApiController {

    private final TokenService tokenService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/api/token")
    public ResponseEntity<CreateAccessTokenResponse> createNewAccessToken(@RequestBody CreateAccessTokenRequest request) {
        String newAccessToken = tokenService.createNewAccessToken(request.getRefreshToken());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CreateAccessTokenResponse(newAccessToken));
    }

    @DeleteMapping("/api/refresh-token") 
    public ResponseEntity<Void> deleteRefreshToken() { // ResponseEntity의 타입 파라미터 명시
        refreshTokenService.delete();

        return ResponseEntity.ok()
                .build();
    }
}
