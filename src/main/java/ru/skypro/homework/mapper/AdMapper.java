package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import ru.skypro.homework.dto.Ad;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.entity.User;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface AdMapper {
    default Ad adToAdDto(ru.skypro.homework.entity.Ad ad) {
        Ad adDto = new Ad();
        adDto.setAuthor(ad.getAuthor().getId());
        adDto.setImage(ad.getImage());
        adDto.setPk(ad.getPk());
        adDto.setPrice(ad.getPrice());
        adDto.setTitle(ad.getTitle());
        return adDto;
    }

    default ru.skypro.homework.entity.Ad adDtoToAd(Ad adDto, User author) {
        ru.skypro.homework.entity.Ad ad = new ru.skypro.homework.entity.Ad(null, author, adDto.getImage(), adDto.getPk(), adDto.getPrice(), adDto.getTitle(), null);
        return ad;
    }

    default Ads adsListToAdsDto(List<ru.skypro.homework.entity.Ad> adsList) {
        Ads ads = new Ads();
        ads.setCount(adsList.size());
        List<Ad> adsDtoList = new ArrayList<>();
        for (ru.skypro.homework.entity.Ad ad : adsList) {
            Ad adDto = adToAdDto(ad);
            adsDtoList.add(adDto);
        }
        ads.setResult(adsDtoList);
        return ads;
    }

    ru.skypro.homework.entity.Ad createOrUpdateAdDtoToAd(CreateOrUpdateAd createOrUpdateAd);

    default ru.skypro.homework.entity.Ad extendedAdDtoToAd(ExtendedAd extendedAd, User author) {
        ru.skypro.homework.entity.Ad ad = new ru.skypro.homework.entity.Ad(null, author, extendedAd.getImage(), extendedAd.getPk(), extendedAd.getPrice(), extendedAd.getTitle(), extendedAd.getDescription());
        return ad;
    }

    default ExtendedAd adToExtendedAd(ru.skypro.homework.entity.Ad ad, User author) {
        ExtendedAd extendedAd = new ExtendedAd();
        extendedAd.setPk(ad.getPk());
        extendedAd.setAuthorFirstName(author.getFirstName());
        extendedAd.setAuthorLastName(author.getLastName());
        extendedAd.setDescription(ad.getDescription());
        extendedAd.setEmail(author.getEmail());
        extendedAd.setImage(ad.getImage());
        extendedAd.setPhone(author.getPhone());
        extendedAd.setPrice(ad.getPrice());
        extendedAd.setPrice(ad.getPrice());
        return extendedAd;
    }
}
