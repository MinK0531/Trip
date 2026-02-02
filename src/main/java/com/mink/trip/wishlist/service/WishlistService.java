package com.mink.trip.wishlist.service;

import com.mink.trip.country.domain.Country;
import com.mink.trip.country.repository.CountryRepository;
import com.mink.trip.user.domain.User;
import com.mink.trip.user.service.UserService;
import com.mink.trip.wishlist.domain.Wishlist;
import com.mink.trip.wishlist.dto.WishlistDetail;
import com.mink.trip.wishlist.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final CountryRepository countryRepository;
    private final UserService userService;


    public boolean createWishlist(
            long userId,
            long countryId,
            String cityName,
            String period,
            String startDate,
            String endDate,
            String memo,
            Double latitude,
            Double longitude
    ) {
        Wishlist wishlist = Wishlist.builder()
                .userId(userId)
                .countryId(countryId)
                .cityName(cityName)
                .period(period)
                .startDate(startDate == null || startDate.isBlank() ? null : java.time.LocalDate.parse(startDate))
                .endDate(endDate == null || endDate.isBlank() ? null : java.time.LocalDate.parse(endDate))
                .memo(memo)
                .latitude(latitude)
                .longitude(longitude)
                .build();

        try {
            wishlistRepository.save(wishlist);
        } catch (DataAccessException e) {
            return false;
        }
        return true;
    }

    public boolean updateWishlist(
            long userId,
            long wishlistId,
            long countryId,
            String cityName,
            String period,
            String startDate,
            String endDate,
            String memo,
            Double latitude,
            Double longitude
    ) {
        Optional<Wishlist> optional = wishlistRepository.findById(wishlistId);
        if (optional.isEmpty()) return false;

        Wishlist w = optional.get();
        if (w.getUserId() != userId) return false;

        Wishlist updated = w.toBuilder()
                .countryId(countryId)
                .cityName(cityName)
                .period(period)
                .startDate(startDate == null || startDate.isBlank() ? null : java.time.LocalDate.parse(startDate))
                .endDate(endDate == null || endDate.isBlank() ? null : java.time.LocalDate.parse(endDate))
                .memo(memo)
                .latitude(latitude)
                .longitude(longitude)
                .build();

        try {
            wishlistRepository.save(updated);
        } catch (DataAccessException e) {
            return false;
        }
        return true;
    }

    public boolean deleteWishlist(long userId, long wishlistId) {
        Optional<Wishlist> optional = wishlistRepository.findById(wishlistId);
        if (optional.isEmpty()) return false;

        Wishlist w = optional.get();
        if (w.getUserId() != userId) return false;

        try {
            wishlistRepository.delete(w);
        } catch (DataAccessException e) {
            return false;
        }
        return true;
    }

    public List<WishlistDetail> getWishlistList(long userId) {

        List<Wishlist> list = wishlistRepository.findAll(Sort.by("id").descending());
        List<WishlistDetail> result = new ArrayList<>();

        for (Wishlist w : list) {

            User user = userService.getUserById(w.getUserId());

            String countryName = countryRepository.findById(w.getCountryId())
                    .map(Country::getCountryName)
                    .orElse("알 수 없는 나라");

            result.add(WishlistDetail.builder()
                    .id(w.getId())
                    .userId(w.getUserId())
                    .nickName(user.getNickName())
                    .countryId(w.getCountryId())
                    .countryName(countryName)
                    .cityName(w.getCityName())
                    .period(w.getPeriod())
                    .startDate(w.getStartDate())
                    .endDate(w.getEndDate())
                    .memo(w.getMemo())
                    .latitude(w.getLatitude())
                    .longitude(w.getLongitude())
                    .createdAt(w.getCreatedAt())
                    .build());
        }

        return result;
    }


    public List<WishlistDetail> getWishlistListByCountry(long userId, long countryId) {
        List<Wishlist> list = wishlistRepository.findByUserIdOrderByIdDesc(userId);

        return list.stream()
                .filter(w -> w.getCountryId().equals(countryId))
                .map(w -> {
                    String countryName = countryRepository.findById(w.getCountryId())
                            .map(Country::getCountryName)
                            .orElse("알 수 없는 나라");

                    return WishlistDetail.builder()
                            .id(w.getId())
                            .userId(w.getUserId())
                            .countryId(w.getCountryId())
                            .countryName(countryName)
                            .cityName(w.getCityName())
                            .period(w.getPeriod())
                            .startDate(w.getStartDate())
                            .endDate(w.getEndDate())
                            .memo(w.getMemo())
                            .latitude(w.getLatitude())
                            .longitude(w.getLongitude())
                            .createdAt(w.getCreatedAt())
                            .build();
                })
                .toList();
    }
}
