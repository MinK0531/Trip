package com.mink.trip.user.service;

import com.mink.trip.common.FileManager;
import com.mink.trip.user.domain.UserProfile;
import com.mink.trip.user.dto.UserProfileDetail;
import com.mink.trip.user.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;

    public UserProfileDetail getMyProfile(long userId) {
        UserProfile profile = userProfileRepository.findByUserId(userId);

        if (profile == null) {
            return UserProfileDetail.builder()
                    .profileWord(null)
                    .profileImg(null)
                    .build();
        }

        return UserProfileDetail.builder()
                .profileWord(profile.getProfileWord())
                .profileImg(profile.getProfileImg())
                .build();
    }

    @Transactional
    public UserProfileDetail deleteMyProfileImage(long userId) {
        UserProfile profile = userProfileRepository.findByUserId(userId);
        if (profile == null) return null;

        String currentPath = profile.getProfileImg();

        if (currentPath != null && !currentPath.isBlank()) {
            FileManager.removeFile(currentPath);
        }

        profile = profile.toBuilder()
                .profileImg(null)
                .build();


        try {
            userProfileRepository.save(profile);
        } catch (DataAccessException e) {
            return null;
        }

        return UserProfileDetail.builder()
                .profileWord(profile.getProfileWord())
                .profileImg(profile.getProfileImg())
                .build();
    }

    @Transactional
    public UserProfileDetail upsert(
            long userId,
            String profileWord,
            MultipartFile profileImg
    ) {
        UserProfile profile = userProfileRepository.findByUserId(userId);

        String newImagePath = null;
        if (profileImg != null && !profileImg.isEmpty()) {
            newImagePath = FileManager.saveFile(userId, profileImg);
            if (newImagePath == null) return null;
        }

        if (profile == null) {
            profile = UserProfile.builder()
                    .userId(userId)
                    .profileWord(profileWord)
                    .profileImg(newImagePath)
                    .build();
        } else {
            String keepOrNew = (newImagePath != null) ? newImagePath : profile.getProfileImg();

            profile = profile.toBuilder()
                    .profileWord(profileWord)
                    .profileImg(keepOrNew)
                    .build();
        }

        try {
            userProfileRepository.save(profile);
        } catch (DataAccessException e) {
            return null;
        }

        return UserProfileDetail.builder()
                .profileWord(profile.getProfileWord())
                .profileImg(profile.getProfileImg())
                .build();
    }
}
