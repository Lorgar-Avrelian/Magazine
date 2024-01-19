package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.dto.AdsDTO;
import ru.skypro.homework.dto.CreateOrUpdateAdDTO;
import ru.skypro.homework.dto.ExtendedAdDTO;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.User;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link Mapper} that converts DAO {@link Ad} to DTO's {@link AdDTO}, {@link AdsDTO}, {@link CreateOrUpdateAdDTO} and {@link ExtendedAdDTO}, and vice versa. <br>
 * <br>
 * <hr>
 * <br>
 * {@link Mapper}, который конвертирует DAO {@link Ad} в DTO {@link AdDTO}, {@link AdsDTO}, {@link CreateOrUpdateAdDTO} и {@link ExtendedAdDTO}, и наоборот. <br>
 * <br>
 *
 * @see Mapper
 */
@Mapper(componentModel = "spring")
public interface AdMapper {
    default AdDTO adToAdDto(Ad ad) {
        AdDTO adDto = new AdDTO();
        adDto.setAuthor(ad.getAuthor().getId());
        adDto.setImage("/" + ad.getImage());
        adDto.setPk(ad.getPk());
        adDto.setPrice(ad.getPrice());
        adDto.setTitle(ad.getTitle());
        return adDto;
    }

    default AdsDTO adsListToAdsDto(List<Ad> adsList) {
        AdsDTO ads = new AdsDTO();
        ads.setCount(adsList.size());
        List<AdDTO> adsDtoList = new ArrayList<>();
        for (Ad ad : adsList) {
            AdDTO adDto = adToAdDto(ad);
            adsDtoList.add(adDto);
        }
        ads.setResults(adsDtoList);
        return ads;
    }

    Ad createOrUpdateAdDtoToAd(CreateOrUpdateAdDTO createOrUpdateAd);

    CreateOrUpdateAdDTO adToCreateOrUpdateDto(Ad ad);

    default Ad extendedAdDtoToAd(ExtendedAdDTO extendedAd, User author) {
        Ad ad = new Ad(extendedAd.getPk(), author, extendedAd.getImage(), extendedAd.getPrice(), extendedAd.getTitle(), extendedAd.getDescription());
        return ad;
    }

    default ExtendedAdDTO adToExtendedAd(Ad ad, User author) {
        ExtendedAdDTO extendedAd = new ExtendedAdDTO();
        extendedAd.setPk(ad.getPk());
        extendedAd.setAuthorFirstName(author.getFirstName());
        extendedAd.setAuthorLastName(author.getLastName());
        extendedAd.setDescription(ad.getDescription());
        extendedAd.setEmail(author.getEmail());
        extendedAd.setImage("/" + ad.getImage());
        extendedAd.setPhone(author.getPhone());
        extendedAd.setPrice(ad.getPrice());
        extendedAd.setTitle(ad.getTitle());
        return extendedAd;
    }
}
