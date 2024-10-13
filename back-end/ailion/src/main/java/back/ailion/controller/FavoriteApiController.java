package back.ailion.controller;

import back.ailion.model.dto.FavoriteDto;
import back.ailion.model.dto.HeartDto;
import back.ailion.model.dto.request.FavoriteDeleteDto;
import back.ailion.model.dto.request.FavoriteRequestDto;
import back.ailion.model.dto.request.HeartRequestDto;
import back.ailion.service.FavoriteService;
import back.ailion.service.HeartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/ailion/favorite")
@RequiredArgsConstructor
public class FavoriteApiController {

    private final FavoriteService favoriteService;

    @PostMapping
    public FavoriteDto favorite(@Valid @RequestBody FavoriteRequestDto favoriteRequestDto) {
        return favoriteService.favorite(favoriteRequestDto);
    }

    @DeleteMapping
    public boolean cancelFavorite(@Valid @RequestBody FavoriteDeleteDto favoriteDeleteDto) {
        return favoriteService.cancelFavorite(favoriteDeleteDto);
    }

    @GetMapping("/{userId}/{aiInfoId}")
    public boolean existFavorite(@PathVariable Long userId, @PathVariable Long aiInfoId){
        return favoriteService.existFavorite(userId, aiInfoId);
    }
}